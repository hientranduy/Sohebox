package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class YoutubeVideoSnippetVO extends BaseVO {
	private String publishedAt;
	private String channelId;
	private String title;
	private String description;
	private YoutubeVideoThumbnailsVO thumbnails;
	private String channelTitle;
	private String liveBroadcastContent;
}