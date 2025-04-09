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

<<<<<<< HEAD
=======
    // Create (Add a new subscription)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    @PostMapping("/add")
    public ResponseEntity<Subs> addNewSubs(@RequestBody Subs subs) {
        Subs createdSubs = subsService.addNewSubs(subs);
        return ResponseEntity.ok(createdSubs);
    }

<<<<<<< HEAD
=======
    // Read (Get all subscriptions)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    @GetMapping("/all")
    public ResponseEntity<List<Subs>> getAllSubs() {
        return ResponseEntity.ok(subsService.getAllSubs());
    }

<<<<<<< HEAD
=======
    // Read (Get a subscription by ID)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    @GetMapping("/{id}")
    public ResponseEntity<Subs> getSubsById(@PathVariable Integer id) {
        Subs subs = subsService.getSubsById(id);
        return subs != null ? ResponseEntity.ok(subs) : ResponseEntity.notFound().build();
    }

<<<<<<< HEAD
    @GetMapping("/status/{id}")
    public ResponseEntity<String> getSubsStatus(@PathVariable Integer id) {
        Subs subs = subsService.getSubsById(id);
        return subs != null ? ResponseEntity.ok(subs.getStatus()) : ResponseEntity.notFound().build();
    }

=======
    // Update (Modify an existing subscription)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    @PutMapping("/update/{id}")
    public ResponseEntity<Subs> updateSubs(@PathVariable Integer id, @RequestBody Subs newSubsData) {
        Subs updatedSubs = subsService.updateSubs(id, newSubsData);
        return updatedSubs != null ? ResponseEntity.ok(updatedSubs) : ResponseEntity.notFound().build();
    }

<<<<<<< HEAD
=======
    // Delete (Remove a subscription by ID)
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubs(@PathVariable Integer id) {
        boolean isDeleted = subsService.deleteSubs(id);
        return isDeleted ? ResponseEntity.ok("Subscription deleted successfully") : ResponseEntity.notFound().build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
