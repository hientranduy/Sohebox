package com.hientran.sohebox.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.RequestExternalSCO;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.utils.MyDateUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService extends TransformerService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private TypeCache typeCache;

	@Autowired
	private RequestExternalService requestExternalService;

	/**
	 * Check if data is out update
	 *
	 * @param string
	 * @return
	 */
	protected boolean dataIsOutUpdate(String url, int lateTimeSecond) {
		// Declare result
		boolean result = true;

		// Search request
		SearchTextVO requestUrl = new SearchTextVO();
		requestUrl.setEq(url);

		TypeTbl requestType = typeCache.getType(DBConstants.TYPE_CLASS_REQUEST_EXTERNAL_TYPE,
				DBConstants.REQUEST_EXTERNAL_TYPE_DATA);
		SearchNumberVO requestTypeId = new SearchNumberVO();
		requestTypeId.setEq(requestType.getId().doubleValue());

		SearchDateVO createdDate = new SearchDateVO();
		createdDate.setGe(MyDateUtils.addMinusSecond(new Date(), lateTimeSecond * -1));

		RequestExternalSCO sco = new RequestExternalSCO();
		sco.setRequestUrl(requestUrl);
		sco.setRequestTypeId(requestTypeId);
		sco.setCreatedDate(createdDate);
		List<RequestExternalTbl> requests = requestExternalService.search(sco);

		if (CollectionUtils.isNotEmpty(requests)) {
			result = false;
		}

		// Return
		return result;
	}

	/**
	 *
	 * Check data authentication
	 *
	 */
	protected APIResponse<Object> isDataAuthentication(long userID) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get current logged user
		UserTbl loggedUser = userService.getCurrentLoginUser();

		// Check authentication if not role creator
		if (!StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_CREATOR)) {

			// Just return data of logged user
			if (userID != loggedUser.getId()) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
			}
		}

		// Return
		return result;

	}

	/**
	 * Record external request
	 *
	 * @throws Exception
	 *
	 */
	protected void recordRequestExternal(String requestUrl, String requestTypeCode, String note) throws Exception {
		TypeTbl type = new TypeTbl();
		type.setTypeCode(requestTypeCode);

		RequestExternalTbl tbl = new RequestExternalTbl();
		tbl.setRequestUrl(requestUrl);
		tbl.setNote(note);
		tbl.setRequestType(type);
		requestExternalService.create(tbl);
	}
}
