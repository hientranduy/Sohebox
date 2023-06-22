package com.hientran.sohebox.cache;

import java.io.Serializable;

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
public class TypeCacheKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String typeClass;
	private String typeCode;
}
