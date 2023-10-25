package com.javateam.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="subreddits")
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subredditId;
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "subreddit", cascade = {CascadeType.REMOVE})
    private List<Post> posts;
    private LocalDateTime createdAt;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="media_id")
    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(Integer subredditId) {
        this.subredditId = subredditId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
