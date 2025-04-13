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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        reclamation.setUserId(userId); // Set the userId
        reclamation.setReason(reason);
        return reclamationRepository.save(reclamation);
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
        // Removed setEmail since the email field no longer exists
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

        // Créer un nouveau classeur Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reclamations");

            // Créer l'en-tête
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Reason", "User ID", "Post ID", "Created At"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                // Optionnel : style pour l'en-tête
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }

            // Remplir les données
            int rowNum = 1;
            for (Reclamation reclamation : reclamations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reclamation.getId());
                row.createCell(1).setCellValue(reclamation.getReason());
                row.createCell(2).setCellValue(reclamation.getUserId());
                row.createCell(3).setCellValue(reclamation.getPost().getId());
                row.createCell(4).setCellValue(reclamation.getCreatedAt().toString());
            }

            // Ajuster la taille des colonnes
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Écrire le classeur dans un flux de sortie
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}