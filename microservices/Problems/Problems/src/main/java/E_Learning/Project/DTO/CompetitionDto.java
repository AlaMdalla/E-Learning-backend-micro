package E_Learning.Project.DTO;

import E_Learning.Project.Entity.Problem;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CompetitionDto {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateOfComp() {
        return dateOfComp;
    }

    public void setDateOfComp(Date dateOfComp) {
        this.dateOfComp = dateOfComp;
    }

    private String title;
    private  String description;
    private List<String> prices;
    private List<Problem> problems;
    private String image;
    private Date dateOfComp ;

    private CompetitionDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.prices = builder.prices;
        this.problems = builder.problems;
        this.image=builder.image;

    }
    public static class Builder {
        private int id;
        private String title;
        private  String description;
        private List<String> prices;
        private Date dateOfComp ;
        public Builder dateOfComp(Date dateOfComp) {
            this.dateOfComp = dateOfComp;
            return this;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getPrices() {
            return prices;
        }

        public void setPrices(List<String> prices) {
            this.prices = prices;
        }

        public Date getDateOfComp() {
            return dateOfComp;
        }

        public void setDateOfComp(Date dateOfComp) {
            this.dateOfComp = dateOfComp;
        }

        public List<Problem> getProblems() {
            return problems;
        }

        public void setProblems(List<Problem> problems) {
            this.problems = problems;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        private List<Problem> problems;

        private String image;
        public Builder id(int id){
            this.id=id;
            return  this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder prices(List<String> prices) {
            this.prices = prices;
            return this;
        }
        public Builder problems(List<Problem> problems) {
            this.problems = problems;
            return this;
        }
        public CompetitionDto build() {
            return new CompetitionDto(this  );
        }

    }
}