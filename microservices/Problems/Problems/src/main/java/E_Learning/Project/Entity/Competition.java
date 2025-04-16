package E_Learning.Project.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Competition {
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String title;
    private  String description;
    private List<String> prices;
    private Date dateOfComp ;
    @OneToMany
    private List<Problem> problems;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(columnDefinition = "MEDIUMTEXT")
    private String image;

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}