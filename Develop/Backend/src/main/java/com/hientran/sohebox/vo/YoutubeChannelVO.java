package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeChannelVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String channelId;

    private String name;

    private String description;

    private MediaTypeVO category;

    private UserVO user;

    /**
     * Constructor
     *
     */
    public YoutubeChannelVO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
    public MediaTypeVO getCategory() {
        return category;
    }

    /**
     * Set category
     *
     * @param category
     *            the category to set
     */
    public void setCategory(MediaTypeVO category) {
        this.category = category;
    }

    /**
     * Get user
     *
     * @return user
     */
    public UserVO getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(UserVO user) {
        this.user = user;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeChannelVO [id=" + id + ", channelId=" + channelId + ", name=" + name + ", description="
                + description + ", category=" + category + ", user=" + user + "]";
    }

}