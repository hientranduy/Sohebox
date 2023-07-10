package com.hientran.sohebox.dto;

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
public class EnglishVO extends BaseVO {
	private Long id;
	private String keyWord;
	private EnglishTypeVO wordLevel;
	private EnglishTypeVO category;
	private EnglishTypeVO learnDay;
	private String imageName;
	private String imageFile;
	private String explanationEn;
	private String explanationVn;
	private String voiceUkFile;
	private String voiceUsFile;
	private EnglishTypeVO vusGrade;

	// Other fields//
	private String imageExtention;
	private Long recordTimes;
}