package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.MediaTypeTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YoutubeChannelVO  {
	private Long id;
	private String channelId;
	private String name;
	private String description;
	private MediaTypeTbl category;
	private UserVO user;
}