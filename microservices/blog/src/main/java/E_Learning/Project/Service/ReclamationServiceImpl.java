package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Entity.Reclamation;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Repository.ReclamationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationServiceImpl implements ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Reclamation createReclamation(Long postId, Integer userId, String reason) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Reclamation reclamation = new Reclamation();
        reclamation.setPost(post);
        reclamation.setUserId(userId); // Set the userId
        reclamation.setReason(reason);
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
    }

    @Override
    @Transactional
    public Reclamation updateReclamation(Long id, Reclamation reclamationDetails) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
        reclamation.setReason(reclamationDetails.getReason());
        // Removed setEmail since the email field no longer exists
        return reclamationRepository.save(reclamation);
    }

    @Override
    @Transactional
    public void deleteReclamation(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id: " + id));
        reclamationRepository.delete(reclamation);
    }

    @Override
    public List<Reclamation> getReclamationByPostId(Long postId) {
        return reclamationRepository.findByPostId(postId);
    }
}