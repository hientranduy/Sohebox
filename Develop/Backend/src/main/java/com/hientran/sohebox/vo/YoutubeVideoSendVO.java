package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeVideoSendVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String videoId;

    private String kind;

    private String publishedAt;

    private String title;

    private String description;

    private String thumbnailDefaultUrl;

    private String thumbnailMediumtUrl;

    private String thumbnailHighUrl;

    /**
     * Constructor
     *
     */
    public YoutubeVideoSendVO() {
        super();
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
     * Get kind
     *
     * @return kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Set kind
     *
     * @param kind
     *            the kind to set
     */
    public void setKind(String kind) {
        this.kind = kind;
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
     * Get thumbnailDefaultUrl
     *
     * @return thumbnailDefaultUrl
     */
    public String getThumbnailDefaultUrl() {
        return thumbnailDefaultUrl;
    }

    /**
     * Set thumbnailDefaultUrl
     *
     * @param thumbnailDefaultUrl
     *            the thumbnailDefaultUrl to set
     */
    public void setThumbnailDefaultUrl(String thumbnailDefaultUrl) {
        this.thumbnailDefaultUrl = thumbnailDefaultUrl;
    }

    /**
     * Get thumbnailMediumtUrl
     *
     * @return thumbnailMediumtUrl
     */
    public String getThumbnailMediumtUrl() {
        return thumbnailMediumtUrl;
    }

    /**
     * Set thumbnailMediumtUrl
     *
     * @param thumbnailMediumtUrl
     *            the thumbnailMediumtUrl to set
     */
    public void setThumbnailMediumtUrl(String thumbnailMediumtUrl) {
        this.thumbnailMediumtUrl = thumbnailMediumtUrl;
    }

    /**
     * Get thumbnailHighUrl
     *
     * @return thumbnailHighUrl
     */
    public String getThumbnailHighUrl() {
        return thumbnailHighUrl;
    }

    /**
     * Set thumbnailHighUrl
     *
     * @param thumbnailHighUrl
     *            the thumbnailHighUrl to set
     */
    public void setThumbnailHighUrl(String thumbnailHighUrl) {
        this.thumbnailHighUrl = thumbnailHighUrl;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoSendVO [videoId=" + videoId + ", kind=" + kind + ", publishedAt=" + publishedAt + ", title="
                + title + ", description=" + description + ", thumbnailDefaultUrl=" + thumbnailDefaultUrl
                + ", thumbnailMediumtUrl=" + thumbnailMediumtUrl + ", thumbnailHighUrl=" + thumbnailHighUrl + "]";
    }

}