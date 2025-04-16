package E_Learning.Project.Entity;

import jakarta.persistence.*;


    @Entity
    @Table(name = "comment_interactions")

    public class CommentInteraction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "comment_id", nullable = false)
        private Comment comment;

        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "reaction_type")
        private String reactionType; // "like", "laugh", "angry", or null if no reaction

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getReactionType() {
            return reactionType;
        }

        public void setReactionType(String reactionType) {
            this.reactionType = reactionType;
        }
    }
