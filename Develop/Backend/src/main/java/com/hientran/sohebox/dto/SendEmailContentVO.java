package com.hientran.sohebox.dto;

import java.util.List;
import java.util.Map;

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
public class SendEmailContentVO  {
	private String mailFrom;
	private String[] listMailTo;
	private String subject;
	private String textBody;
	private String templatePath;
	private Map<String, String> parameters;
	private List<String> pathToAttachments;
}