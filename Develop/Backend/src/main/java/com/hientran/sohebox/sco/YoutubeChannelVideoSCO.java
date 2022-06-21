package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class YoutubeChannelVideoSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO channelId;

    private SearchNumberVO videoId;

    /**
     * Set default constructor
     *
     */
    public YoutubeChannelVideoSCO() {
        super();
    }

    /**
     * Get channelId
     *
     * @return channelId
     */
    public SearchNumberVO getChannelId() {
        return channelId;
    }

    /**
     * Set channelId
     *
     * @param channelId
     *            the channelId to set
     */
    public void setChannelId(SearchNumberVO channelId) {
        this.channelId = channelId;
    }

    /**
     * Get videoId
     *
     * @return videoId
     */
    public SearchNumberVO getVideoId() {
        return videoId;
    }

    /**
     * Set videoId
     *
     * @param videoId
     *            the videoId to set
     */
    public void setVideoId(SearchNumberVO videoId) {
        this.videoId = videoId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeChannelVideoSCO [channelId=" + channelId + ", videoId=" + videoId + "]";
    }

}
