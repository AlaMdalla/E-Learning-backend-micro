package esprit.subscription.Controller;

import esprit.subscription.Entity.Subs;
import esprit.subscription.Service.SubsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subs")
public class SubsController {

    @Autowired
    private SubsService subsService;

    // Create (Add a new subscription)
    @PostMapping("/add")
    public ResponseEntity<Subs> addNewSubs(@RequestBody Subs subs) {
        Subs createdSubs = subsService.addNewSubs(subs);
        return ResponseEntity.ok(createdSubs);
    }

    // Read (Get all subscriptions)
    @GetMapping("/all")
    public ResponseEntity<List<Subs>> getAllSubs() {
        return ResponseEntity.ok(subsService.getAllSubs());
    }

    // Read (Get a subscription by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Subs> getSubsById(@PathVariable Integer id) {
        Subs subs = subsService.getSubsById(id);
        return subs != null ? ResponseEntity.ok(subs) : ResponseEntity.notFound().build();
    }

    // Update (Modify an existing subscription)
    @PutMapping("/update/{id}")
    public ResponseEntity<Subs> updateSubs(@PathVariable Integer id, @RequestBody Subs newSubsData) {
        Subs updatedSubs = subsService.updateSubs(id, newSubsData);
        return updatedSubs != null ? ResponseEntity.ok(updatedSubs) : ResponseEntity.notFound().build();
    }

    // Delete (Remove a subscription by ID)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubs(@PathVariable Integer id) {
        boolean isDeleted = subsService.deleteSubs(id);
        return isDeleted ? ResponseEntity.ok("Subscription deleted successfully") : ResponseEntity.notFound().build();
    }
}
