package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Entity.Reclamation;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Repository.ReclamationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

@Service
public class ReclamationServiceImpl implements ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Reclamation createReclamation(Long postId, Integer userId, String reason) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Reclamation reclamation = new Reclamation();
        reclamation.setPost(post);
        reclamation.setUserId(userId);
        reclamation.setReason(reason);

        // Sauvegarder la réclamation sans QR code pour l'instant
        Reclamation savedReclamation = reclamationRepository.save(reclamation);

        // Générer le QR code et l'encoder en Base64
        String qrCodeBase64 = generateQrCode(savedReclamation);

        // Mettre à jour la réclamation avec le QR code
        savedReclamation.setQrCode(qrCodeBase64);
        return reclamationRepository.save(savedReclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
    }

    @Override
    @Transactional
    public Reclamation updateReclamation(Long id, Reclamation reclamationDetails) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
        reclamation.setReason(reclamationDetails.getReason());
        return reclamationRepository.save(reclamation);
    }

    @Override
    @Transactional
    public void deleteReclamation(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
        reclamationRepository.delete(reclamation);
    }

    @Override
    public List<Reclamation> getReclamationByPostId(Long postId) {
        return reclamationRepository.findByPostId(postId);
    }

    public ByteArrayInputStream exportReclamationsToExcel() throws IOException {
        List<Reclamation> reclamations = reclamationRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reclamations");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Reason", "User ID", "Post ID", "Created At"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }
            int rowNum = 1;
            for (Reclamation reclamation : reclamations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reclamation.getId());
                row.createCell(1).setCellValue(reclamation.getReason());
                row.createCell(2).setCellValue(reclamation.getUserId());
                row.createCell(3).setCellValue(reclamation.getPost().getId());
                row.createCell(4).setCellValue(reclamation.getCreatedAt().toString());
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private String generateQrCode(Reclamation reclamation) {
        try {
            // Générer le contenu du QR code
            String qrContent = getPostDetailsForQrCode(reclamation);

            // Générer le QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 250, 250);

            // Convertir en image PNG
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            // Encoder l'image en Base64
            return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }

    private String getPostDetailsForQrCode(Reclamation reclamation) {
        Post post = reclamation.getPost();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return String.format(
                "{\"userId\": %d, \"postId\": %d, \"postContent\": \"%s\", \"createdAt\": \"%s\"}",
                reclamation.getUserId(),
                post.getId(),
                post.getContent() != null ? post.getContent() : "N/A",
                dateFormat.format(reclamation.getCreatedAt())
        );
    }

    // Méthode existante (peut être supprimée si non utilisée)
    public String getPostDetailsForQrCode(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        Reclamation reclamation = reclamationRepository.findByPostId(postId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No reclamation found for post id: " + postId));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return String.format(
                "{\"userId\": %d, \"postId\": %d, \"postContent\": \"%s\", \"createdAt\": \"%s\"}",
                reclamation.getUserId(),
                post.getId(),
                post.getContent(),
                dateFormat.format(reclamation.getCreatedAt())
        );
    }
}