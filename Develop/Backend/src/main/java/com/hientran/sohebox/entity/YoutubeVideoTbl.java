package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "youtube_video_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_youtube_video", columnNames = { "videoId" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeVideoTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "videoId")
    private String videoId;

    @Column(name = "publishedAt")
    private String publishedAt;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "urlThumbnail")
    private String urlThumbnail;

    /**
     * Explanation of processing
     *
     */
    public YoutubeVideoTbl() {
        super();
    }

    /**
     * Get createdDate
     *
     * @return createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get videoId
     *
     * @return videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * Set videoId
     *
     * @param videoId
     *            the videoId to set
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Get publishedAt
     *
     * @return publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * Set publishedAt
     *
     * @param publishedAt
     *            the publishedAt to set
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     *
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get urlThumbnail
     *
     * @return urlThumbnail
     */
    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    /**
     * Set urlThumbnail
     *
     * @param urlThumbnail
     *            the urlThumbnail to set
     */
    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoTbl [createdDate=" + createdDate + ", videoId=" + videoId + ", publishedAt=" + publishedAt
                + ", title=" + title + ", description=" + description + ", urlThumbnail=" + urlThumbnail + "]";
    }

}
