package com.hientran.sohebox.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(name = "responseWithBody", description = "API flatform base response")
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWithBody<T> {

	@Schema(name = "body", description = "Body payload")
	private T body;

	ResponseStatus status;
}