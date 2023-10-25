package com.javateam.repository;

import com.javateam.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND p.user.userId = :userId ORDER BY p.voteCount DESC")
    List<Post> findALlPostsByUserIdOrderByVoteCountDesc(Integer userId);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true ORDER BY p.createdAt DESC , p.user.karma DESC")
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p WHERE p.isPublished=true AND p.url LIKE %?1% OR p.postName = ?1 " +
            "                       OR p.description LIKE %?1% OR p.subreddit.name = ?1 ORDER BY p.user.karma DESC ")
    List<Post> SearchByPostNameSubredditDescriptionURL(String text);

    @Query("SELECT p FROM Post p WHERE p.subreddit.name = ?1 AND p.isPublished=true")
    List<Post> subredditRelatedPost(String subredditName);

    @Query("SELECT p FROM Post p WHERE isPublished = true ORDER BY p.voteCount DESC , p.createdAt ASC")
    List<Post> findAllByVoteCountDescAndRecentlyCreatedDesc();
    @Query("SELECT post FROM Post post WHERE post.user.userId = :userId AND post.isPublished = false")
    List<Post> findAllDraftPostsOfUser(@Param("userId")Integer userId);

//    @Query("SELECT p FROM Post p WHERE isPublished = true ORDER BY ((p.voteCount - 1) /" +
//            " POW(((EXTRACT(EPOCH FROM NOW()) - EXTRACT(EPOCH FROM p.publishedAt)) / 3600) + 2, 1.8)) DESC, " +
//            "p.publishedAt ASC")
//    List<Post> findHotPosts();

    @Query("SELECT p FROM Post p WHERE isPublished = true ORDER BY ((p.voteCount - 1) /" +
            " POW(((EXTRACT(EPOCH FROM NOW()) - EXTRACT(EPOCH FROM p.publishedAt)) / 3600) + 2, 1.8)) DESC, " +
            "p.publishedAt ASC")
    List<Post> findHotPosts();


}
