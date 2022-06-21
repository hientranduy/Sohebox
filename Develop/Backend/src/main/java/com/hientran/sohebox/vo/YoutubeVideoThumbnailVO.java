package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeVideoThumbnailVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String url;

    private Integer width;

    private Integer height;

    /**
     * Constructor
     *
     */
    public YoutubeVideoThumbnailVO() {
        super();
    }

    /**
     * Get url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     *
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get width
     *
     * @return width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Set width
     *
     * @param width
     *            the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Get height
     *
     * @return height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Set height
     *
     * @param height
     *            the height to set
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoThumbnailVO [url=" + url + ", width=" + width + ", height=" + height + "]";
    }

}