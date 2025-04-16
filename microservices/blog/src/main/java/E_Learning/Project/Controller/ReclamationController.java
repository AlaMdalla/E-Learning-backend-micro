package E_Learning.Project.Controller;

import E_Learning.Project.Entity.Reclamation;
import E_Learning.Project.Service.ReclamationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/blog/posts")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @PostMapping("reclamations/create")
    public ResponseEntity<?> createReclamation(
            @RequestHeader("User-Id") Integer userId, // Added userId from header
            @RequestParam Long postId,
            @RequestParam String reason) {
        try {
            return ResponseEntity.ok(reclamationService.createReclamation(postId, userId, reason));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("reclamations")
    public ResponseEntity<?> getAllReclamations() {
        try {
            return ResponseEntity.ok(reclamationService.getAllReclamations());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("reclamations/{id}")
    public ResponseEntity<?> getReclamationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reclamationService.getReclamationById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("reclamations/{id}")
    public ResponseEntity<?> updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamationDetails) {
        try {
            return ResponseEntity.ok(reclamationService.updateReclamation(id, reclamationDetails));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("reclamations/{id}")
    public ResponseEntity<?> deleteReclamation(@PathVariable Long id) {
        try {
            reclamationService.deleteReclamation(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("reclamations/{postId}")
    public ResponseEntity<?> getReclamationsByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(reclamationService.getReclamationByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @GetMapping("/reclamations/export-excel")
    public ResponseEntity<InputStreamResource> exportReclamationsToExcel() {
        try {
            ByteArrayInputStream in = reclamationService.exportReclamationsToExcel();

            // Configurer les en-têtes pour le téléchargement
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reclamations.xlsx");

            // Retourner le fichier Excel
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(in));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/{postId}/qr-code")
    public ResponseEntity<InputStreamResource> generateQrCode(@PathVariable Long postId) {
        try {
            // Récupérer les détails pour le QR code
            String qrContent = reclamationService.getPostDetailsForQrCode(postId);

            // Générer le QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 250, 250);

            // Convertir en image PNG
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            // Configurer les en-têtes pour l'image
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=qrcode.png");

            // Retourner l'image
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(pngOutputStream.toByteArray())));
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}