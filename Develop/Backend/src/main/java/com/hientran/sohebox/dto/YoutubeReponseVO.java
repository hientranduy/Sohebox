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
public class YoutubeReponseVO {
	private String kind;
	private String etag;
	private String nextPageToken;
	private String regionCode;
	private Object pageInfo;
	private Object items;
}