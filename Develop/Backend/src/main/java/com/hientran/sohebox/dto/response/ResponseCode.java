package com.hientran.sohebox.dto.response;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public enum ResponseCode {

	// Response
	SUCCESSFUL("0", "SUCCESS"), FAILED("1", "FAILED"),

	EXISTED_USERNAME("ERR_0001", "The username %s is existed, please chose another account"),
	FILED_EMPTY("ERR_0002", "The field %s must be not null"),
	UNAUTHORIZED_USER("ERR_0003", "Incorrect username/password or account is expired"),
	UNAUTHORIZED("ERR_0004", "Unauthorized Login"), UNAUTHORIZED_DATA("ERR_0005", "Unauthorized Data"),
	NO_UPDATE_DATA("ERR_0006", "Have no data to update"), NO_LOGIN_USER("ERR_0007", "You are not logged in"),
	INVALID_FIELD("ERR_0008", "The field %s is invalid"),
	INEXISTED_USERNAME("ERR_0009", "The user owner %s is not existed"), EXISTED_RECORD("ERR_0010", "The %s is existed"),
	INEXISTED_RECORD("ERR_0011", "The %s is not existed"),

	ERROR_EXCEPTION("ERR_9999", "Exception Error: %s");

	public static ResponseCode fromValue(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		for (ResponseCode e : ResponseCode.values()) {
			if (code.equalsIgnoreCase(e.code)) {
				return e;
			}
		}

		return null;
	}

	public static String mapParam(ResponseCode code, String param) {
		if (ObjectUtils.isEmpty(code)) {
			return null;
		}
		if (StringUtils.isNotEmpty(param)) {

			return String.format(code.getDescription(), param);
		} else {
			return code.getDescription();
		}
	}

	public static String mapParams(ResponseCode code, List<String> params) {
		if (ObjectUtils.isEmpty(code)) {
			return null;
		}
		if (!CollectionUtils.isEmpty(params)) {
			return String.format(code.getDescription(), params);
		} else {
			return code.getDescription();
		}
	}

	private final String code;

	private final String description;

	ResponseCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
