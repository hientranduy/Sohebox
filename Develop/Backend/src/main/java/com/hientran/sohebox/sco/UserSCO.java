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
public class UserSCO extends BaseSCO {
	private SearchNumberVO id;
	private SearchTextVO userName;
	private SearchTextVO firstName;
	private SearchTextVO lastName;
}
