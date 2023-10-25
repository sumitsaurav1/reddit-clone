package com.javateam.model;

import jakarta.persistence.*;
@Entity
@Table(name="votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer voteId;
    private VoteType voteType;
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "is_viewed", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isViewed;
}
