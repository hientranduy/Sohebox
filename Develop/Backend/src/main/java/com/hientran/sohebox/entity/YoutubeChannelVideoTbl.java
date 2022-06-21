package com.hientran.sohebox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "youtube_channel_video_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_youtube_channel_video", columnNames = { "channel_id", "video_id" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeChannelVideoTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "deleteFlag", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleteFlag;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelVideoTbl_YoutubeChannelTbl_channel"))
    private YoutubeChannelTbl channel;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelVideoTbl_YoutubeVideoTbl_video"))
    private YoutubeVideoTbl video;

    /**
     * Explanation of processing
     *
     */
    public YoutubeChannelVideoTbl() {
        super();
    }

    /**
     * Get deleteFlag
     *
     * @return deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Set deleteFlag
     *
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Get channel
     *
     * @return channel
     */
    public YoutubeChannelTbl getChannel() {
        return channel;
    }

    /**
     * Set channel
     *
     * @param channel
     *            the channel to set
     */
    public void setChannel(YoutubeChannelTbl channel) {
        this.channel = channel;
    }

    /**
     * Get video
     *
     * @return video
     */
    public YoutubeVideoTbl getVideo() {
        return video;
    }

    /**
     * Set video
     *
     * @param video
     *            the video to set
     */
    public void setVideo(YoutubeVideoTbl video) {
        this.video = video;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeChannelVideoTbl [deleteFlag=" + deleteFlag + ", channel=" + channel + ", video=" + video + "]";
    }

}
