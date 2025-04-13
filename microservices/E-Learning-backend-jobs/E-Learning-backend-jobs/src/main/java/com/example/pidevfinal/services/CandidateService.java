package com.example.pidevfinal.services;

import com.example.pidevfinal.DTO.CandidateRequest;
import com.example.pidevfinal.DTO.CandidateResponse;
import com.example.pidevfinal.entities.Candidate;
import com.example.pidevfinal.entities.Job;
import com.example.pidevfinal.repository.CandidateRepository;
import com.example.pidevfinal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD

=======
>>>>>>> origin/job
@Service
public class CandidateService {
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;
<<<<<<< HEAD

    @Autowired
    public CandidateService(JobRepository jobRepository, CandidateRepository candidateRepository) {
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
=======
    private final EmailService emailService;

    @Autowired
    public CandidateService(JobRepository jobRepository, CandidateRepository candidateRepository, EmailService emailService) {
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
        this.emailService = emailService;
>>>>>>> origin/job
    }

    public List<CandidateResponse> getAllCandidates() {
        return candidateRepository.findAll().stream().map(candidate ->
                new CandidateResponse(
                        candidate.getId(),
                        candidate.getEmail(),
                        candidate.getPhone(),
                        candidate.getResumeUrl(),
                        candidate.getApplicationDate(),
                        candidate.getStatus(),
                        candidate.getJob().getJobId(),
                        candidate.getJob().getTitle()
                )
        ).collect(Collectors.toList());
    }

    public CandidateResponse getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        return new CandidateResponse(
                candidate.getId(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getResumeUrl(),
                candidate.getApplicationDate(),
                candidate.getStatus(),
                candidate.getJob().getJobId(),
                candidate.getJob().getTitle()
        );
    }

    public CandidateResponse saveCandidate(CandidateRequest request) {
<<<<<<< HEAD
=======
        System.out.println("Saving candidate: email=" + request.getEmail() + ", jobId=" + request.getJobId());
>>>>>>> origin/job
        if (request.getJobId() == null) {
            throw new RuntimeException("Job ID is required to create a candidate.");
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + request.getJobId()));

        Candidate candidate = new Candidate();
        candidate.setEmail(request.getEmail());
        candidate.setPhone(request.getPhone());
        candidate.setResumeUrl(request.getResumeUrl());
        candidate.setApplicationDate(request.getApplicationDate());
<<<<<<< HEAD
        candidate.setStatus(request.getStatus());
        candidate.setJob(job);

        Candidate savedCandidate = candidateRepository.save(candidate);
=======
        candidate.setStatus("applied");
        candidate.setJob(job);

        Candidate savedCandidate = candidateRepository.save(candidate);
        System.out.println("Candidate saved: ID=" + savedCandidate.getId());

        emailService.sendApplicationEmail(
                savedCandidate.getEmail(),
                job.getTitle(),
                savedCandidate.getStatus()
        );
>>>>>>> origin/job

        return new CandidateResponse(
                savedCandidate.getId(),
                savedCandidate.getEmail(),
                savedCandidate.getPhone(),
                savedCandidate.getResumeUrl(),
                savedCandidate.getApplicationDate(),
                savedCandidate.getStatus(),
                savedCandidate.getJob().getJobId(),
                savedCandidate.getJob().getTitle()
        );
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    public CandidateResponse updateCandidate(Long id, CandidateRequest candidateDetails) {
<<<<<<< HEAD
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + id));

=======
        System.out.println("Updating candidate: ID=" + id + ", new status=" + candidateDetails.getStatus());
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + id));

        String oldStatus = candidate.getStatus();
>>>>>>> origin/job
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setPhone(candidateDetails.getPhone());
        candidate.setResumeUrl(candidateDetails.getResumeUrl());
        candidate.setApplicationDate(candidateDetails.getApplicationDate());
        candidate.setStatus(candidateDetails.getStatus());

        Job job = jobRepository.findById(candidateDetails.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + candidateDetails.getJobId()));
        candidate.setJob(job);

        Candidate updatedCandidate = candidateRepository.save(candidate);
<<<<<<< HEAD
=======
        System.out.println("Candidate updated: ID=" + updatedCandidate.getId());

        String newStatus = updatedCandidate.getStatus();
        if (newStatus != null && !newStatus.equalsIgnoreCase(oldStatus)) {
            emailService.sendApplicationEmail(
                    updatedCandidate.getEmail(),
                    job.getTitle(),
                    newStatus
            );
        }
>>>>>>> origin/job

        return new CandidateResponse(
                updatedCandidate.getId(),
                updatedCandidate.getEmail(),
                updatedCandidate.getPhone(),
                updatedCandidate.getResumeUrl(),
                updatedCandidate.getApplicationDate(),
                updatedCandidate.getStatus(),
                updatedCandidate.getJob().getJobId(),
                updatedCandidate.getJob().getTitle()
        );
    }
}