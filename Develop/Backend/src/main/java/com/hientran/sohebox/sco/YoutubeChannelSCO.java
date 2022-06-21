package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class YoutubeChannelSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO channelId;

    private SearchTextVO name;

    private SearchTextVO description;

    private SearchNumberVO categoryId;

    private SearchNumberVO userId;

    /**
     * Set default constructor
     *
     */
    public YoutubeChannelSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get channelId
     *
     * @return channelId
     */
    public SearchTextVO getChannelId() {
        return channelId;
    }

    /**
     * Set channelId
     *
     * @param channelId
     *            the channelId to set
     */
    public void setChannelId(SearchTextVO channelId) {
        this.channelId = channelId;
    }

    /**
     * Get name
     *
     * @return name
     */
    public SearchTextVO getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(SearchTextVO name) {
        this.name = name;
    }

    /**
     * Get description
     *
     * @return description
     */
    public SearchTextVO getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(SearchTextVO description) {
        this.description = description;
    }

    /**
     * Get categoryId
     *
     * @return categoryId
     */
    public SearchNumberVO getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     *
     * @param categoryId
     *            the categoryId to set
     */
    public void setCategoryId(SearchNumberVO categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get userId
     *
     * @return userId
     */
    public SearchNumberVO getUserId() {
        return userId;
    }

    /**
     * Set userId
     *
     * @param userId
     *            the userId to set
     */
    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeChannelSCO [id=" + id + ", channelId=" + channelId + ", name=" + name + ", description="
                + description + ", categoryId=" + categoryId + ", userId=" + userId + "]";
    }

}
