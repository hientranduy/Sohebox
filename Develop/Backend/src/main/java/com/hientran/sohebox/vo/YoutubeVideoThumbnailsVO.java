package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeVideoThumbnailsVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private YoutubeVideoThumbnailVO medium;

    private YoutubeVideoThumbnailVO high;

    /**
     * Constructor
     *
     */
    public YoutubeVideoThumbnailsVO() {
        super();
    }

    /**
     * Get medium
     *
     * @return medium
     */
    public YoutubeVideoThumbnailVO getMedium() {
        return medium;
    }

    /**
     * Set medium
     *
     * @param medium
     *            the medium to set
     */
    public void setMedium(YoutubeVideoThumbnailVO medium) {
        this.medium = medium;
    }

    /**
     * Get high
     *
     * @return high
     */
    public YoutubeVideoThumbnailVO getHigh() {
        return high;
    }

    /**
     * Set high
     *
     * @param high
     *            the high to set
     */
    public void setHigh(YoutubeVideoThumbnailVO high) {
        this.high = high;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoThumbnailsVO [medium=" + medium + ", high=" + high + "]";
    }
}