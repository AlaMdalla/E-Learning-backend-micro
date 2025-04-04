package E_Learning.Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Submition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean passed;

    private Integer userId;

    private Integer problemIdent;

    @JsonIgnore
    @ManyToOne
    private Problem problem;

    private LocalDateTime submittedAt;

    public Submition() {
        // Default constructor
    }

    public Submition(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.passed = builder.passed;
        this.problem = builder.problem;
        this.problemIdent = builder.problemIdent;
        this.submittedAt = builder.submittedAt != null ? builder.submittedAt : LocalDateTime.now();
    }

    public static class Builder {
        private Integer id;
        private Integer userId;
        private Integer problemIdent;
        private boolean passed;
        private Problem problem;
        private LocalDateTime submittedAt;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder problem(Problem problem) {
            this.problem = problem;
            return this;
        }

        public Builder problemIdent(Integer problemIdent) {
            this.problemIdent = problemIdent;
            return this;
        }

        public Builder passed(boolean passed) {
            this.passed = passed;
            return this;
        }

        public Builder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public Submition build() {
            return new Submition(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getproblemIdent() {
        return problem != null ? problem.getId() : null;
    }

    public void setproblemIdent(Integer problemIdent) {
        this.problemIdent = problemIdent;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
