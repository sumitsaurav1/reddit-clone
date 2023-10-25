package com.javateam.repository;

import com.javateam.model.Comment;
import com.javateam.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT COUNT(c) FROM Comment c JOIN c.post p WHERE p.user.userId = :userId AND c.isViewed = false")
    Integer countUnviewedCommentsByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Comment c JOIN c.post p WHERE p.user.userId = :userId AND c.isViewed = false")
    List<Comment> findUnviewedCommentsByUserId(@Param("userId") Integer userId);



    @Modifying
    @Query("UPDATE Comment c SET c.isViewed = true WHERE c.post.postId = :postId")
    void markCommentsAsViewed(@Param("postId") Integer postId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.parentComment.user.userId = :userId AND c.isViewed = false")
    Integer countUnviewedChildCommentsByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Comment c WHERE c.parentComment.user.userId = :userId AND c.isViewed = false")
    List<Comment> findUnviewedChildCommentsByUserId(@Param("userId") Integer userId); // Change the return type to List<Comment>
}

