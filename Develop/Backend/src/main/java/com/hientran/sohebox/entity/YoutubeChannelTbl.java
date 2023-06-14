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
@Table(name = "youtube_channel_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_youtube_channel", columnNames = { "channelId" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeChannelTbl extends BaseTbl {
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
}
