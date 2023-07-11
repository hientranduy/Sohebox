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
public class UserStatusVO  {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String roleName;
	private String avatarUrl;
	private String status;
	private String durationTime;
	private long durationSeconds;
}