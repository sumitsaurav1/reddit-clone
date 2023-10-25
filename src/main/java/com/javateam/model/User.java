package com.javateam.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;
    private Integer karma = 0;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Post> posts;
    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Subreddit> subreddits;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Vote> votes;

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVote(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Subreddit> getSubreddits() {
        return subreddits;
    }

    public void setSubreddits(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "media_id")
    private Media media;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name = "language", columnDefinition = "VARCHAR(255) DEFAULT 'en'")
    private String language = "en"; // Default value is "en" for English

}
