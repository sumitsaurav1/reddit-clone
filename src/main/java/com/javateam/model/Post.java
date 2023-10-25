package com.javateam.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String postName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String url;
    private LocalDateTime createdAt;
    private Integer voteCount = 0;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name="subreddit_id")
    private Subreddit subreddit;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Vote> votes;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="media_id")
    private Media media;
    @Column(name="is_published")
    private Boolean isPublished;

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }

    private LocalDateTime publishedAt;

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postName='" + postName + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", voteCount=" + voteCount +
//                ", comments=" + comments +
                ", subreddit=" + subreddit +
//                ", votes=" + votes +
                ", user=" + user +
//                ", media=" + media +
                '}';
    }

}

