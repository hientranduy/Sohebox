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
public class YoutubeVideoVO {
	private String kind;
	private String etag;
	private YoutubeVideoIdVO id;
	private YoutubeVideoSnippetVO snippet;
}