package com.javateam.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String content;
    private Integer voteCount = 0;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="parentComment_id")
    private Comment parentComment;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vote> votes;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }


    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    @Column(name = "is_viewed", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isViewed;

}
