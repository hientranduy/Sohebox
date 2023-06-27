package com.hientran.sohebox.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "youtube_video_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_youtube_video", columnNames = { "video_id" }) })
@EntityListeners(AuditingEntityListener.class)
public class YoutubeVideoTbl extends GenericTbl {
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "video_id")
	private String videoId;

	@Column(name = "published_at")
	private String publishedAt;

	@Column(name = "title")
	private String title;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "url_thumbnail")
	private String urlThumbnail;
}
