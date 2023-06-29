
package com.hientran.sohebox.vo;

import java.io.Serializable;

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
public class MediaTypeVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String typeClass;
	private String typeCode;
	private String typeName;
	private String description;
	private String iconUrl;
	private boolean deleteFlag;
}