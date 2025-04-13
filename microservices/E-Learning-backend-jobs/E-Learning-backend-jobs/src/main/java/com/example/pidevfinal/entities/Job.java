package com.example.pidevfinal.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String title;
    private String department;
    private String location; // New field for job address

    @Column(columnDefinition = "MEDIUMTEXT")
    private String image;

    private String description;
    private String requirements;
    private Date postedDate;
    private String status;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Candidate> candidates = new ArrayList<>();

    // Getters and setters
    public Long getJobId() { return jobId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    public Date getPostedDate() { return postedDate; }
    public void setPostedDate(Date postedDate) { this.postedDate = postedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}