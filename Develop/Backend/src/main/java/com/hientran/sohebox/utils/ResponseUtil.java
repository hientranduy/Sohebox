package com.hientran.sohebox.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.response.ErrorCode;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.dto.response.ResponseStatus;

public class ResponseUtil {

	/**
	 * Response SUCCESS
	 *
	 * @return
	 */
	public static ResponseStatus createSuccessStatus() {
		ResponseStatus status = new ResponseStatus();
		status.setCode(Integer.valueOf(ResponseCode.SUCCESSFUL.getCode()));
		status.setMessage(ResponseCode.SUCCESSFUL.getDescription());
		return status;
	}

	/**
	 * Response FAILED
	 *
	 * @return
	 */
	public static ResponseStatus createFailedStatus() {
		ResponseStatus status = new ResponseStatus();
		status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
		status.setMessage(ResponseCode.FAILED.getDescription());
		return status;
	}

	/**
	 * Response by error list
	 *
	 * @param errors
	 * @return
	 */
	public static ResponseStatus createResponseStatusFromErrorList(List<ErrorCode> errors) {
		ResponseStatus status = null;
		if (!CollectionUtils.isEmpty(errors)) {
			status = createFailedStatus();
			status.setErrors(errors);
		}
		return status;
	}

	/**
	 * Response by response code with param
	 *
	 * @param responseCode
	 * @return
	 */
	public static ResponseStatus createErrorStatusByResponseCode(ResponseCode responseCode, String param) {
		ResponseStatus status = createFailedStatus();
		status.setErrors(Arrays.asList(createErrorCode(responseCode, param)));
		return status;
	}

	/**
	 * Response by response code without param
	 *
	 * @param responseCode
	 * @return
	 */
	public static ResponseStatus createErrorStatusByResponseCode(ResponseCode responseCode) {
		ResponseStatus status = createFailedStatus();
		status.setErrors(Arrays.asList(createErrorCode(responseCode, null)));
		return status;
	}

	/**
	 * Prepare ErrorCode
	 *
	 * @param responseCode
	 * @param param
	 * @return
	 */
	public static ErrorCode createErrorCode(ResponseCode responseCode, String param) {
		ErrorCode errorCode = new ErrorCode();
		errorCode.setCode(responseCode.getCode());
		if (StringUtils.isNotEmpty(param)) {
			errorCode.setMessage(String.format(responseCode.getDescription(), param));
		} else {
			errorCode.setMessage(responseCode.getDescription());
		}
		return errorCode;
	}

	/**
	 * Prepare ErrorCode without param
	 *
	 * @param responseCode
	 * @return
	 */
	public static ErrorCode createErrorCode(ResponseCode responseCode) {
		ErrorCode errorCode = new ErrorCode();
		errorCode.setCode(responseCode.getCode());
		errorCode.setMessage(responseCode.getDescription());
		return errorCode;
	}

	public static ResponseStatus createFailedInvalidInputStatus(ResponseCode responseCode, Set<String> fields) {
		ResponseStatus status = new ResponseStatus();
		status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
		status.setMessage(ResponseCode.FAILED.getDescription());

		// set errorCode
		List<ErrorCode> errorCodes = new ArrayList<>();

		for (String field : fields) {
			ErrorCode errorCode = new ErrorCode();
			errorCode.setCode(responseCode.getCode());
			errorCode.setMessage(String.format(responseCode.getDescription(), field));
			errorCodes.add(errorCode);
		}

		status.setErrors(errorCodes);
		return status;
	}

	public static ErrorCode createErrorCodeWithParam(ResponseCode responseCode, String param) {
		ErrorCode errorCode = new ErrorCode();
		errorCode.setCode(responseCode.getCode());
		errorCode.setMessage(String.format(responseCode.getDescription(), param));
		return errorCode;
	}

	public static ResponseStatus createResponseStatusFromResponseCode(ResponseCode responseCode) {
		ResponseStatus status = null;
		if (ObjectUtils.isNotEmpty(responseCode)) {
			status = new ResponseStatus();

			status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
			status.setMessage(ResponseCode.FAILED.getDescription());

			List<ErrorCode> errors = new ArrayList<>();
			errors.add(createErrorCode(responseCode));
			status.setErrors(errors);
		}
		return status;
	}

	public static ResponseStatus createFailedStatus(List<ErrorCode> errorCodes) {
		ResponseStatus status = new ResponseStatus();
		status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
		status.setMessage(ResponseCode.FAILED.getDescription());

		status.setErrors(errorCodes);
		return status;
	}
}
