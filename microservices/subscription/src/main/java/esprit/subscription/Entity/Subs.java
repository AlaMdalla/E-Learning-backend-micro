package esprit.subscription.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Subs {

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

    private String typesub;
    private String subsDescription;
    private Double subsDiscountedPrice;
    private Double subsActualPrice;



}