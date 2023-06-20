
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
public class TypeVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String typeClass;
	private String typeCode;
	private String typeName;
	private String description;
	private String iconUrl;
	private String url;
	private boolean deleteFlag;

	/**
	 * Constructor
	 *
	 * @param typeClass
	 * @param typeCode
	 */
	public TypeVO(String typeClass, String typeCode) {
		super();
		this.typeClass = typeClass;
		this.typeCode = typeCode;
	}
}