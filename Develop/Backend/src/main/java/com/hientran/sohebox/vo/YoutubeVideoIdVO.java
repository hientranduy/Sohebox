package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeVideoIdVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String kind;

    private String videoId;

    /**
     * Constructor
     *
     */
    public YoutubeVideoIdVO() {
        super();
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
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoItemVO [kind=" + kind + ", videoId=" + videoId + "]";
    }

}