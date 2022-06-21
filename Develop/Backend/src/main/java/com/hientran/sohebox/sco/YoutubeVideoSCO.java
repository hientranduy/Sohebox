package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class YoutubeVideoSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO videoId;

    /**
     * Set default constructor
     *
     */
    public YoutubeVideoSCO() {
        super();
    }

    /**
     * Get videoId
     *
     * @return videoId
     */
    public SearchTextVO getVideoId() {
        return videoId;
    }

    /**
     * Set videoId
     *
     * @param videoId
     *            the videoId to set
     */
    public void setVideoId(SearchTextVO videoId) {
        this.videoId = videoId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoSCO [videoId=" + videoId + "]";
    }

}
