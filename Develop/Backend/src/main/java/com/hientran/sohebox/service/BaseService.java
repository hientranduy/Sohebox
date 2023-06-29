package com.hientran.sohebox.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.entity.RequestExternalTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.RequestExternalSCO;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.utils.FileUtils;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.vo.TypeVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private ConfigCache configCache;

	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private TypeCache typeCache;

	@Autowired
	private RequestExternalService requestExternalService;

	/**
	 * 
	 * Check data authentication
	 *
	 */
	protected APIResponse<Object> isDataAuthentication(long userID) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get current logged user
		UserTbl loggedUser = userService.getCurrentLoginUser();

		// Check authentication if not role creator
		if (!StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_CREATOR)) {

			// Just return data of logged user
			if (userID != loggedUser.getId()) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
			}
		}

		// Return
		return result;

	}

	/**
	 * 
	 * Record user activity
	 *
	 */
	protected void recordUserActivity(String activity) {
		UserTbl userLogin = userService.getCurrentLoginUser();
		if (userLogin != null) {
			userActivityService.recordUserActivity(userLogin, activity);
		}
	}

	/**
	 * Refresh external file
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 *
	 */
	protected void refreshExternalFile(String filePath)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		if (FileUtils.exist(filePath)) {
			// Check if the file is old
			File desFile = new File(filePath);

			Date lastModifiedDate = new Date(desFile.lastModified());
			long diffInSecond = (new Date().getTime() - lastModifiedDate.getTime()) / 1000;

			long updateDuration = 0;
			switch (desFile.getName()) {
			case DataExternalConstants.REQUEST_DATA_FILE_NAME_VCB:
				updateDuration = Long
						.parseLong(configCache.getValueByKey(DataExternalConstants.FINANCE_VCB_LATE_TIME_SECOND));
				break;
			case DataExternalConstants.REQUEST_DATA_FILE_NAME_SJC:
				updateDuration = Long
						.parseLong(configCache.getValueByKey(DataExternalConstants.FINANCE_SJC_LATE_TIME_SECOND));
				break;
			default:
				break;
			}

			if (diffInSecond > updateDuration) {
				downloadUrlFile(filePath);
			}
		} else {
			// Download new file
			downloadUrlFile(filePath);
		}

	}

	/**
	 * Download file from URL
	 *
	 * @param filePath
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void downloadUrlFile(String filePath) throws IOException, NoSuchAlgorithmException, KeyManagementException {

		// ========================================================
		// ==== REMOVE CHECKING CERTIFICATE WHEN DOWNLOAD DATA ====
		// ========================================================
		// Create a trust manager that does not validate certificate chains
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };

		// Install the all-trusting trust manager
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, null);

		// Create an ssl socket factory with our all-trusting manager
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		});

		// ===============================
		// ==== PROCESS DOWNLOAD DATA ====
		// ===============================
		// Prepare URL
		URL url = null;
		switch (new File(filePath).getName()) {
		case DataExternalConstants.REQUEST_DATA_FILE_NAME_VCB:
			url = new URL(configCache.getValueByKey(DataExternalConstants.FINANCE_VCB_XML_URL));
			break;
		case DataExternalConstants.REQUEST_DATA_FILE_NAME_SJC:
			url = new URL(configCache.getValueByKey(DataExternalConstants.FINANCE_SJC_XML_URL));
			break;
		default:
			break;
		}

		// Download
		if (url != null) {
			// fool connection to avoid 403
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
			InputStream inputStream = httpcon.getInputStream();

			FileUtils.writeFile(inputStream, new File(filePath));
		}
	}

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

		TypeVO requestType = typeCache.getType(DBConstants.TYPE_CLASS_REQUEST_EXTERNAL_TYPE,
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
}
