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
public class UserStatusVO extends BaseVO {
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