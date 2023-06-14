package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "youtube_channel_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_youtube_channel", columnNames = { "channelId" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeChannelTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "channelId")
    private String channelId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelTbl_MediaTypeTbl_category"))
    private MediaTypeTbl category;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelTbl_UserTbl_user"))
    private UserTbl user;

    /**
     * Explanation of processing
     *
     */
    public YoutubeChannelTbl() {
        super();
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
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * Get category
     *
     * @return category
     */
    public MediaTypeTbl getCategory() {
        return category;
    }

    /**
     * Set category
     *
     * @param category
     *            the category to set
     */
    public void setCategory(MediaTypeTbl category) {
        this.category = category;
    }

    /**
     * Get user
     *
     * @return user
     */
    public UserTbl getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(UserTbl user) {
        this.user = user;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeChannelTbl [channelId=" + channelId + ", name=" + name + ", description=" + description
                + ", category=" + category + ", user=" + user + "]";
    }

}
