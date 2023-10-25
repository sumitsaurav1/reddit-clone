package com.javateam.model;

import jakarta.persistence.*;

import java.sql.Blob;
@Entity
@Table(name="medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mediaId;
    private Blob media;
    private String contentType;
    @OneToOne(mappedBy = "media")
    private Post post;
    @OneToOne(mappedBy = "media")
    private Subreddit subreddit;

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Blob getMedia() {
        return media;
    }

    public void setMedia(Blob media) {
        this.media = media;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


}
