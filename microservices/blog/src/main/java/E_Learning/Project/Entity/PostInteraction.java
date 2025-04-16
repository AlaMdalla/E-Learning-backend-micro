package E_Learning.Project.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post_interactions")
public class PostInteraction {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isHasViewed() {
        return hasViewed;
    }

    public void setHasViewed(boolean hasViewed) {
        this.hasViewed = hasViewed;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "has_viewed", nullable = false)
    private boolean hasViewed = false;

    @Column(name = "reaction_type")
    private String reactionType; // "like", "laugh", "angry", or null if no reaction
}