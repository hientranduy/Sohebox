package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeVideoSnippetVO  {
	private String publishedAt;
	private String channelId;
	private String title;
	private String description;
	private YoutubeVideoThumbnailsVO thumbnails;
	private String channelTitle;
	private String liveBroadcastContent;
}