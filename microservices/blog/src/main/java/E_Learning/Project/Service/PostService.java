package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    Post savePost(Integer userId, Post post);
    List<Post> getAllPosts();

    Post getPostById(Integer userId, Long postId);
    void deletePost(Integer userId, Long postId);
    void reactPost(Integer userId, Long postId, String reactionType);
    Post updatePost(Integer userId, Long postId, Post updatedPost);
    void viewPost(Integer userId, Long postId); // Add this method


    Post getPostByIdAndUpdating(Integer userId, Long postId);
    void sharePostOnFacebook(Integer userId, Long postId, String accessToken) throws Exception;
    String exchangeAccessToken(String shortLivedToken) throws Exception;
    Map<String, String> getPages(String userAccessToken) throws Exception;
}
