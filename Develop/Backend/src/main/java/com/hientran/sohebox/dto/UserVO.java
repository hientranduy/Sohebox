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
public class UserVO {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String roleName;
	private String token;
	private String avatarUrl;
	private String privateKey;
}