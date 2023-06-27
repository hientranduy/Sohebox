package com.hientran.sohebox.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "youtube_channel_video_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_youtube_channel_video", columnNames = { "channel_id", "video_id" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeChannelVideoTbl extends GenericTbl {
	@Column(name = "delete_flag", nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean deleteFlag;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelVideoTbl_YoutubeChannelTbl_channel"))
	private YoutubeChannelTbl channel;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_YoutubeChannelVideoTbl_YoutubeVideoTbl_video"))
	private YoutubeVideoTbl video;
}
