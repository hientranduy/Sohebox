package com.hientran.sohebox.dto;

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
public class YoutubeVideoSendVO  {
	private String videoId;
	private String kind;
	private String publishedAt;
	private String title;
	private String description;
	private String thumbnailDefaultUrl;
	private String thumbnailMediumtUrl;
	private String thumbnailHighUrl;
}