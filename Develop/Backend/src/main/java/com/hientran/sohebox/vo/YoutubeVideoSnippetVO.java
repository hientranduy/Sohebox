package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeVideoSnippetVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String publishedAt;

    private String channelId;

    private String title;

    private String description;

    private YoutubeVideoThumbnailsVO thumbnails;

    private String channelTitle;

    private String liveBroadcastContent;

    /**
     * Constructor
     *
     */
    public YoutubeVideoSnippetVO() {
        super();
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
     * Get channelId
     *
     * @return channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Set channelId
     *
     * @param channelId
     *            the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
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
     * Get thumbnails
     *
     * @return thumbnails
     */
    public YoutubeVideoThumbnailsVO getThumbnails() {
        return thumbnails;
    }

    /**
     * Set thumbnails
     *
     * @param thumbnails
     *            the thumbnails to set
     */
    public void setThumbnails(YoutubeVideoThumbnailsVO thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     * Get channelTitle
     *
     * @return channelTitle
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * Set channelTitle
     *
     * @param channelTitle
     *            the channelTitle to set
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    /**
     * Get liveBroadcastContent
     *
     * @return liveBroadcastContent
     */
    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    /**
     * Set liveBroadcastContent
     *
     * @param liveBroadcastContent
     *            the liveBroadcastContent to set
     */
    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoSnippetVO [publishedAt=" + publishedAt + ", channelId=" + channelId + ", title=" + title
                + ", description=" + description + ", thumbnails=" + thumbnails + ", channelTitle=" + channelTitle
                + ", liveBroadcastContent=" + liveBroadcastContent + "]";
    }

}