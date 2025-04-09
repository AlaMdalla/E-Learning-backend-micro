package esprit.subscription.Service;

import esprit.subscription.Entity.Subs;
import esprit.subscription.dao.SubsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.Arrays;
=======
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
import java.util.List;
import java.util.Optional;

@Service
public class SubsService {

    @Autowired
    private SubsDao subsDao;

<<<<<<< HEAD
    private static final List<String> VALID_TYPES = Arrays.asList("1 month", "3 months", "6 months", "1 year");

    // Define subscription type hierarchy (higher index = higher tier)
    private static final List<String> TYPE_HIERARCHY = Arrays.asList("1 month", "3 months", "6 months", "1 year");

    public Subs addNewSubs(Subs subs) {
        if (!VALID_TYPES.contains(subs.getTypesub())) {
            throw new IllegalArgumentException("Invalid subscription type. Must be '1 month', '3 months', '6 months', or '1 year'.");
        }
        if (subsDao.findByTypesub(subs.getTypesub()).size() > 0) {
            throw new IllegalArgumentException("A subscription with type '" + subs.getTypesub() + "' already exists.");
        }
        if (subs.getSubsDiscountedPrice() == null || subs.getSubsDiscountedPrice() < 0) {
            throw new IllegalArgumentException("Discounted price must be non-negative.");
        }
        if (subs.getSubsActualPrice() == null || subs.getSubsActualPrice() <= 0) {
            throw new IllegalArgumentException("Actual price must be greater than 0.");
        }
        if (subs.getStatus() == null || !subs.getStatus().equals("success")) {
            subs.setEndDate(calculateEndDate(subs.getTypesub()));
        }
        return subsDao.save(subs);
    }

    private LocalDateTime calculateEndDate(String typesub) {
        LocalDateTime now = LocalDateTime.now();
        switch (typesub) {
            case "1 month": return now.plusMonths(1);
            case "3 months": return now.plusMonths(3);
            case "6 months": return now.plusMonths(6);
            case "1 year": return now.plusYears(1);
            default: throw new IllegalArgumentException("Unknown subscription type: " + typesub);
        }
    }

=======
    // Create (Add a new Subscription)
    public Subs addNewSubs(Subs subs) {
        return subsDao.save(subs);
    }

    // Read (Get all subscriptions)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    public List<Subs> getAllSubs() {
        return subsDao.findAll();
    }

<<<<<<< HEAD
    public Subs getSubsById(Integer id) {
        Optional<Subs> subs = subsDao.findById(id);
        return subs.orElse(null);
    }

    public Subs updateSubs(Integer id, Subs newSubsData) {
        return subsDao.findById(id).map(existingSubs -> {
            if (!newSubsData.getTypesub().equals(existingSubs.getTypesub()) &&
                    subsDao.findByTypesub(newSubsData.getTypesub()).size() > 0) {
                throw new IllegalArgumentException("A subscription with type '" + newSubsData.getTypesub() + "' already exists.");
            }
            if (!VALID_TYPES.contains(newSubsData.getTypesub())) {
                throw new IllegalArgumentException("Invalid subscription type. Must be '1 month', '3 months', '6 months', or '1 year'.");
            }
=======
    // Read (Get a subscription by ID)
    public Subs getSubsById(Integer id) {
        Optional<Subs> subs = subsDao.findById(id);
        return subs.orElse(null); // Return null if not found, or handle differently
    }

    // Update (Modify an existing subscription)
    public Subs updateSubs(Integer id, Subs newSubsData) {
        return subsDao.findById(id).map(existingSubs -> {
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
            existingSubs.setTypesub(newSubsData.getTypesub());
            existingSubs.setSubsDescription(newSubsData.getSubsDescription());
            existingSubs.setSubsDiscountedPrice(newSubsData.getSubsDiscountedPrice());
            existingSubs.setSubsActualPrice(newSubsData.getSubsActualPrice());
<<<<<<< HEAD
            existingSubs.setEndDate(calculateEndDate(newSubsData.getTypesub()));
            return subsDao.save(existingSubs);
        }).orElse(null);
    }

=======
            return subsDao.save(existingSubs);
        }).orElse(null); // Handle not found case properly in your controller
    }

    // Delete (Remove a subscription by ID)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    public boolean deleteSubs(Integer id) {
        if (subsDao.existsById(id)) {
            subsDao.deleteById(id);
            return true;
        }
<<<<<<< HEAD
        return false;
    }

    // Method to compare subscription types
    public boolean isHigherTier(String newType, String existingType) {
        int newIndex = TYPE_HIERARCHY.indexOf(newType);
        int existingIndex = TYPE_HIERARCHY.indexOf(existingType);
        return newIndex > existingIndex;
    }
}
=======
        return false; // Handle not found case properly in your controller
    }
}
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
