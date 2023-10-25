package com.javateam.service;

import com.javateam.model.Comment;
import com.javateam.model.User;
import com.javateam.model.Vote;
import com.javateam.repository.CommentRepository;
import com.javateam.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private CommentRepository commentRepository;
    private VoteRepository voteRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository,VoteRepository voteRepository) {
        this.commentRepository = commentRepository;
        this.voteRepository=voteRepository;
    }

    public Comment saveComment(Comment comment) {
        if (comment.getCommentId() == null) {
            comment.setCreatedAt(LocalDateTime.now());
        }

        return commentRepository.save(comment);
    }
    public Vote getVoteByComment(User user, Comment comment) {
        return voteRepository.findByUserAndComment(user,comment);
    }
    public void updateVote(Vote vote) {
        voteRepository.save(vote);
    }
    public void createVote(Vote vote) {
        voteRepository.save(vote);
    }
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId).get();
    }

    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    public int findNotification(Integer userId) {
        return commentRepository.countUnviewedCommentsByUserId(userId);
    }

    public List<Comment> findListOfNotifictions(Integer userId) {
       return commentRepository.findUnviewedCommentsByUserId(userId);
    }

    @Transactional
    public void markCommentsAsViewed(Integer postId) {
        commentRepository.markCommentsAsViewed(postId);
    }

    public Integer findNumberVoteNotification(Integer userId) {
        return voteRepository.countUnviewedVotesByUserId(userId);
    }

    public List<Vote> findListOfVoteNotifications(Integer userId) {
       return voteRepository.findUnviewedVotesByUserId(userId);
    }

    public Integer countUnviewedChildCommentsByUserId(Integer userId) {
        return commentRepository.countUnviewedChildCommentsByUserId(userId);
    }

    public List<Comment> listOfUnviewedChildCommentsByUserId(Integer userId) {
        return commentRepository.findUnviewedChildCommentsByUserId(userId);
    }
}
