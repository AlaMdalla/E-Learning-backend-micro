package esprit.subscription.Entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
@Data
@Entity
public class Subs {

<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer subid;
=======
    public Integer getSubid() {
        return subid;
    }

    public String getSubsDescription() {
        return subsDescription;
    }

    public String getTypesub() {
        return typesub;
    }

    public Double getSubsDiscountedPrice() {
        return subsDiscountedPrice;
    }

    public Double getSubsActualPrice() {
        return subsActualPrice;
    }

    public void setSubid(Integer subid) {
        this.subid = subid;
    }

    public void setTypesub(String typesub) {
        this.typesub = typesub;
    }

    public void setSubsDescription(String subsDescription) {
        this.subsDescription = subsDescription;
    }

    public void setSubsDiscountedPrice(Double subsDiscountedPrice) {
        this.subsDiscountedPrice = subsDiscountedPrice;
    }

    public void setSubsActualPrice(Double subsActualPrice) {
        this.subsActualPrice = subsActualPrice;
    }

    public Subs(Integer subid, Double subsActualPrice, Double subsDiscountedPrice, String typesub, String subsDescription) {
        this.subid = subid;
        this.subsActualPrice = subsActualPrice;
        this.subsDiscountedPrice = subsDiscountedPrice;
        this.typesub = typesub;
        this.subsDescription = subsDescription;
    }
    // **No-arg constructor is required**
    public Subs() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer  subid;
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce

    private String typesub;
    private String subsDescription;
    private Double subsDiscountedPrice;
    private Double subsActualPrice;
<<<<<<< HEAD
    private String status;

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Subs(Integer subid, LocalDateTime endDate, String status, Double subsActualPrice, Double subsDiscountedPrice, String subsDescription, String typesub) {
        this.subid = subid;
        this.endDate = endDate;
        this.status = status;
        this.subsActualPrice = subsActualPrice;
        this.subsDiscountedPrice = subsDiscountedPrice;
        this.subsDescription = subsDescription;
        this.typesub = typesub;
    }

    @Column(name = "end_date")
    private LocalDateTime endDate;
    public Subs() {
    }

    public Subs(Integer subid, String typesub, String subsDescription, Double subsDiscountedPrice, Double subsActualPrice, String status) {
        this.subid = subid;
        this.typesub = typesub;
        this.subsDescription = subsDescription;
        this.subsDiscountedPrice = subsDiscountedPrice;
        this.subsActualPrice = subsActualPrice;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subs subs = (Subs) o;
        return Objects.equals(subid, subs.subid) && Objects.equals(typesub, subs.typesub) &&
                Objects.equals(subsDescription, subs.subsDescription) &&
                Objects.equals(subsDiscountedPrice, subs.subsDiscountedPrice) &&
                Objects.equals(subsActualPrice, subs.subsActualPrice) &&
                Objects.equals(status, subs.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subid, typesub, subsDescription, subsDiscountedPrice, subsActualPrice, status);
    }
=======



>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
}