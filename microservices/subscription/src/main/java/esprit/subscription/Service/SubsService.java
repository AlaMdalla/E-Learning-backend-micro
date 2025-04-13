package esprit.subscription.Service;

import esprit.subscription.Entity.Subs;
import esprit.subscription.dao.SubsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubsService {

    @Autowired
    private SubsDao subsDao;

    // Create (Add a new Subscription)
    public Subs addNewSubs(Subs subs) {
        return subsDao.save(subs);
    }

    // Read (Get all subscriptions)
    public List<Subs> getAllSubs() {
        return subsDao.findAll();
    }

    // Read (Get a subscription by ID)
    public Subs getSubsById(Integer id) {
        Optional<Subs> subs = subsDao.findById(id);
        return subs.orElse(null); // Return null if not found, or handle differently
    }

    // Update (Modify an existing subscription)
    public Subs updateSubs(Integer id, Subs newSubsData) {
        return subsDao.findById(id).map(existingSubs -> {
            existingSubs.setTypesub(newSubsData.getTypesub());
            existingSubs.setSubsDescription(newSubsData.getSubsDescription());
            existingSubs.setSubsDiscountedPrice(newSubsData.getSubsDiscountedPrice());
            existingSubs.setSubsActualPrice(newSubsData.getSubsActualPrice());
            return subsDao.save(existingSubs);
        }).orElse(null); // Handle not found case properly in your controller
    }

    // Delete (Remove a subscription by ID)
    public boolean deleteSubs(Integer id) {
        if (subsDao.existsById(id)) {
            subsDao.deleteById(id);
            return true;
        }
        return false; // Handle not found case properly in your controller
    }
}
