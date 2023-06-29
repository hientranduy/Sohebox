package com.hientran.sohebox.sco;

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
public class YoutubeChannelSCO extends BaseSCO {
	private SearchNumberVO id;
	private SearchTextVO channelId;
	private SearchTextVO name;
	private SearchTextVO description;
	private SearchNumberVO categoryId;
	private SearchNumberVO userId;
}
