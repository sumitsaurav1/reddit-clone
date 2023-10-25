package com.javateam.repository;

import com.javateam.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Vote findByUserAndPost(User user, Post post);
    Vote findByUserAndComment(User user, Comment comment);
    @Query("SELECT v FROM Vote v WHERE v.user.userId = :userId AND v.voteType=:voteType AND v.comment.commentId = null")
    List<Vote> findAllPostByUserIdAndVoteType(Integer userId, VoteType voteType);

    @Query("SELECT COUNT(v) FROM Vote v JOIN v.post p WHERE p.user.userId = :userId AND v.isViewed = false")
    Integer countUnviewedVotesByUserId(@Param("userId") Integer userId);

    @Query("SELECT v FROM Vote v JOIN v.post p WHERE p.user.userId = :userId AND v.isViewed = false")
    List<Vote> findUnviewedVotesByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE Vote v SET v.isViewed = true WHERE v.post.postId = :postId")
    void markVotesAsViewedForPost(@Param("postId") Integer postId);
}
