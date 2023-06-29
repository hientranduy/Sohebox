package com.hientran.sohebox.vo;

import java.util.List;
import java.util.Map;

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
public class SendEmailContentVO extends BaseVO {
	private String mailFrom;
	private String[] listMailTo;
	private String subject;
	private String textBody;
	private String templatePath;
	private Map<String, String> parameters;
	private List<String> pathToAttachments;
}