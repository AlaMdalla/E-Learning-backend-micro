package com.example.pidevfinal.controller;
<<<<<<< HEAD
import com.example.pidevfinal.DTO.CandidateRequest;
import com.example.pidevfinal.DTO.CandidateResponse;
import com.example.pidevfinal.services.CandidateService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
=======

import com.example.pidevfinal.DTO.CandidateRequest;
import com.example.pidevfinal.DTO.CandidateResponse;
import com.example.pidevfinal.services.CandidateService;
>>>>>>> origin/job
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
<<<<<<< HEAD
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
=======
import java.util.List;
>>>>>>> origin/job

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;
<<<<<<< HEAD

    private static final String UPLOAD_DIR = "C:/pidev/attachments/";

=======
    private static final String UPLOAD_DIR = "C:/pidev/attachments/"; // Base directory for files
>>>>>>> origin/job
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
        System.out.println("CandidateController initialized");
    }

    @GetMapping
    public List<CandidateResponse> getAllCandidates() {
        List<CandidateResponse> candidates = candidateService.getAllCandidates();
<<<<<<< HEAD
        candidates.forEach(candidate -> {
            if (candidate.getResumeUrl() != null && candidate.getResumeUrl().length() > 1000) {
                candidate.setResumeUrl(candidate.getResumeUrl().substring(0, 1000) + "...");
                System.out.println("Truncated resumeUrl for candidate " + candidate.getId());
            }
        });
=======
>>>>>>> origin/job
        System.out.println("Returning candidates: " + candidates.size());
        return candidates;
    }

    @GetMapping("/{id}")
    public CandidateResponse getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    @GetMapping("/{id}/resume")
    public ResponseEntity<String> getCandidateResume(@PathVariable Long id) {
        try {
            CandidateResponse candidate = candidateService.getCandidateById(id);
            if (candidate == null || candidate.getResumeUrl() == null) {
                return ResponseEntity.notFound().build();
            }
<<<<<<< HEAD
            System.out.println("Fetching resume for candidate " + id + ", URL: " + candidate.getResumeUrl());
=======
            System.out.println("Fetching resume URL for candidate " + id + ": " + candidate.getResumeUrl());
>>>>>>> origin/job
            return ResponseEntity.ok(candidate.getResumeUrl());
        } catch (Exception e) {
            System.err.println("Error fetching resume for candidate " + id + ": " + e.getMessage());
            return ResponseEntity.status(500).body("Error fetching resume");
        }
    }

    @PostMapping
    public ResponseEntity<Void> createCandidate(@RequestBody CandidateRequest request) {
        System.out.println("Received request: " + request);
        candidateService.saveCandidate(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
<<<<<<< HEAD
            dest.getParentFile().mkdirs(); // Create directory if it doesn’t exist
            file.transferTo(dest);
            String fileUrl = "/attachments/" + fileName; // URL relative to static serving path
            System.out.println("File uploaded: " + fileUrl);
=======
            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            if (!dest.exists() || !dest.canRead()) {
                System.err.println("File not saved or unreadable: " + dest.getAbsolutePath());
                return ResponseEntity.status(500).body("File not accessible");
            }
            String fileUrl = "/attachments/" + fileName;
            System.out.println("File uploaded: " + fileUrl + " at " + dest.getAbsolutePath());
>>>>>>> origin/job
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            System.err.println("Error uploading file: " + e.getMessage());
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        try {
            System.out.println("Deleting candidate " + id);
            candidateService.deleteCandidate(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error deleting candidate " + id + ": " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public CandidateResponse updateCandidate(@PathVariable Long id, @RequestBody CandidateRequest candidateDetails) {
        return candidateService.updateCandidate(id, candidateDetails);
<<<<<<< HEAD
    }
    @GetMapping("/attachments/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        // Create the full file path
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource = new FileSystemResource(filePath);

        // Check if file exists


        // Return the file as a response
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
=======
    }}
>>>>>>> origin/job
