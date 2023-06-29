package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.GoogleConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.entity.YoutubeChannelVideoTbl;
import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.YoutubeChannelVideoSCO;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.YoutubeReponseVO;
import com.hientran.sohebox.vo.YoutubeVideoSendVO;
import com.hientran.sohebox.vo.YoutubeVideoVO;
import com.hientran.sohebox.webservice.YoutubeWebService;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@Service
@RequiredArgsConstructor
public class YoutubeService extends BaseService {

	private final YoutubeWebService youtubeWebService;
	private final ObjectMapperUtil objectMapperUtil;
	private final ConfigCache configCache;
	private final YoutubeVideoService youtubeVideoService;
	private final YoutubeChannelService youtubeChannelService;
	private final YoutubeChannelVideoService youtubeChannelVideoService;

	/**
	 * Search video by channel
	 * 
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchChannelVideo(YoutubeChannelVideoSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get channel
		YoutubeChannelTbl channelTbl = youtubeChannelService.getById(sco.getChannelId().getEq().longValue());

		// Prepare parameters
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(GoogleConstants.YOUTUBE_PARAM_KEY, configCache.getValueByKey(GoogleConstants.GOOGLE_KEY_API));
		parameters.put(GoogleConstants.YOUTUBE_PARAM_CHANNEL_ID, channelTbl.getChannelId());
		parameters.put(GoogleConstants.YOUTUBE_PARAM_ORDER, GoogleConstants.YOUTUBE_DEFAUT_VALUE_DATE);
		parameters.put(GoogleConstants.YOUTUBE_PARAM_PART, GoogleConstants.YOUTUBE_DEFAUT_VALUE_SNIPPET);
		parameters.put(GoogleConstants.YOUTUBE_PARAM_TYPE, GoogleConstants.YOUTUBE_DEFAUT_VALUE_VIDEO);
		parameters.put(GoogleConstants.YOUTUBE_PARAM_MAX_RESULT, sco.getMaxRecordPerPage().toString());

		try {
			// Build URL
			URIBuilder builder = new URIBuilder(GoogleConstants.GOOGLE_API_URL);
			builder.setPath(GoogleConstants.YOUTUBE_API_SEARCH_CHANNEL);
			if (parameters != null) {
				for (Map.Entry<String, String> param : parameters.entrySet()) {
					builder.setParameter(param.getKey(), param.getValue());
				}
			}

			// Declare video data
			List<YoutubeVideoSendVO> videoSends = null;

			// Get video data
			int lateTimeSecond = Integer
					.parseInt(configCache.getValueByKey(GoogleConstants.GOOGLE_API_CHANNEL_VIDEO_LATE_TIME_SECOND));
			if (dataIsOutUpdate(builder.toString(), lateTimeSecond)) {
				// Execute request if out update
				String responseData = youtubeWebService.getLatestVideoByChannel(builder);
				YoutubeReponseVO response = objectMapperUtil.readValue(responseData, YoutubeReponseVO.class);
				if (response.getItems() != null) {
					List<YoutubeVideoVO> youtubeVideos = new ArrayList<YoutubeVideoVO>();
					youtubeVideos = objectMapperUtil.readValue(response.getItems(),
							new TypeReference<List<YoutubeVideoVO>>() {
							});

					// Transformer YoutubeVideo to YoutubeVideoSend
					videoSends = new ArrayList<YoutubeVideoSendVO>();
					for (YoutubeVideoVO video : youtubeVideos) {
						YoutubeVideoSendVO videoSend = new YoutubeVideoSendVO();
						videoSend.setVideoId(video.getId().getVideoId());
						videoSend.setKind(video.getId().getKind());
						videoSend.setPublishedAt(video.getSnippet().getPublishedAt());
						videoSend.setTitle(video.getSnippet().getTitle());
						videoSend.setDescription(video.getSnippet().getDescription());
						videoSend.setThumbnailMediumtUrl(video.getSnippet().getThumbnails().getMedium().getUrl());
						videoSend.setThumbnailHighUrl(video.getSnippet().getThumbnails().getHigh().getUrl());
						videoSends.add(videoSend);

						// Update video
						APIResponse<YoutubeVideoTbl> videoTbl = youtubeVideoService.mergeVideo(video);

						// Update channel video
						youtubeChannelVideoService.mergeChannelVideo(channelTbl, videoTbl.getData());
					}
				}

			} else {
				// Get video from local DB
				List<YoutubeChannelVideoTbl> videos = youtubeChannelVideoService.search(sco);
				if (!CollectionUtils.isEmpty(videos)) {
					videoSends = new ArrayList<YoutubeVideoSendVO>();
					for (YoutubeChannelVideoTbl videoList : videos) {
						YoutubeVideoSendVO videoSend = new YoutubeVideoSendVO();
						videoSend.setVideoId(videoList.getVideo().getVideoId());
						videoSend.setPublishedAt(videoList.getVideo().getPublishedAt().toString());
						videoSend.setTitle(videoList.getVideo().getTitle());
						videoSend.setDescription(videoList.getVideo().getDescription());
						videoSend.setThumbnailMediumtUrl(videoList.getVideo().getUrlThumbnail());
						videoSend.setThumbnailHighUrl(videoList.getVideo().getUrlThumbnail());
						videoSends.add(videoSend);
					}
				}
			}

			// Set return data
			if (videoSends != null) {
				PageResultVO<YoutubeVideoSendVO> data = new PageResultVO<YoutubeVideoSendVO>();
				data.setElements(videoSends);
				data.setCurrentPage(0);
				data.setTotalPage(1);
				data.setTotalElement(videoSends.size());

				// Set data return
				result.setData(data);
			}

		} catch (Exception e) {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Return
		return result;
	}
}
