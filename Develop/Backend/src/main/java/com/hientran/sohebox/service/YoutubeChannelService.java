package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.cache.MediaTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.repository.YoutubeChannelRepository;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.YoutubeChannelSCO;
import com.hientran.sohebox.specification.YoutubeChannelSpecs.YoutubeChannelTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YoutubeChannelService extends BaseService {

	private final YoutubeChannelRepository youtubeChannelRepository;
	private final MediaTypeCache mediaTypeCache;

	@Autowired
	private UserDetailsServiceImpl userService;

	/**
	 *
	 * Create
	 *
	 * @param rq
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(YoutubeChannelTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Channel id must not null
			if (rq.getChannelId() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.channelId.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(rq.getChannelId())) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "channel ID <" + rq.getId() + ">"));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			YoutubeChannelTbl tbl = rq;

			// Set category
			tbl.setCategory(mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
					rq.getCategory().getTypeCode()));

			// Create
			tbl = youtubeChannelRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get record by key
	 *
	 * @param key
	 * @return
	 */
	public YoutubeChannelTbl getById(Long id) {
		// Declare result
		YoutubeChannelTbl result = null;

		// Get data
		YoutubeChannelTbl channel = youtubeChannelRepository.findById(id).get();
		if (channel != null) {
			result = channel;
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get record by key
	 *
	 * @param key
	 * @return
	 */
	public YoutubeChannelTbl getByKey(String key) {
		// Declare result
		YoutubeChannelTbl result = null;

		SearchTextVO keyWordSearch = new SearchTextVO();
		keyWordSearch.setEq(key);

		YoutubeChannelSCO sco = new YoutubeChannelSCO();
		sco.setChannelId(keyWordSearch);

		// Get data
		List<YoutubeChannelTbl> list = youtubeChannelRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}

		// Return
		return result;
	}

	/**
	 *
	 * Check if record is existed
	 *
	 * @param keyWord
	 * @return
	 */
	private boolean recordIsExisted(String key) {
		// Declare result
		boolean result = false;

		SearchTextVO keyWordSearch = new SearchTextVO();
		keyWordSearch.setEq(key);

		YoutubeChannelSCO sco = new YoutubeChannelSCO();
		sco.setChannelId(keyWordSearch);

		// Get data
		List<YoutubeChannelTbl> list = youtubeChannelRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = true;
		}

		// Return
		return result;
	}

	/**
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> search(YoutubeChannelSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<YoutubeChannelTbl> page = youtubeChannelRepository.findAll(sco);

		// Transformer
		PageResultVO<YoutubeChannelTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Write activity type "YoutubeChannel access"
		recordUserActivity(DBConstants.USER_ACTIVITY_MEDIA_YOUTUBE_CHANNEL_ACCESS);

		// Return
		return result;
	}

	/**
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchMyChannel(YoutubeChannelSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get public channel
		MediaTypeTbl privateChannelType = mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
				DBConstants.TYPE_CODE_MEDIA_YOUTUBE_CHANNEL_CATEGORY_PERSONAL);
		SearchNumberVO categoryId = new SearchNumberVO();
		categoryId.setNotEq(privateChannelType.getId().doubleValue());
		sco.setCategoryId(categoryId);
		List<YoutubeChannelTbl> publicChannel = youtubeChannelRepository.findAll(sco).getContent();

		// Get private channel
		UserTbl loggedUser = userService.getCurrentLoginUser();
		List<YoutubeChannelTbl> privateChannel = new ArrayList<>();
		if (loggedUser != null) {
			SearchNumberVO userIdSearch = new SearchNumberVO();
			userIdSearch.setEq(loggedUser.getId().doubleValue());
			YoutubeChannelSCO channelSCO = new YoutubeChannelSCO();
			channelSCO.setUserId(userIdSearch);
			privateChannel = youtubeChannelRepository.findAll(channelSCO).getContent();
		}

		// Merge to result channel
		List<YoutubeChannelTbl> resultChannel = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(publicChannel)) {
			resultChannel.addAll(publicChannel);
		}
		if (CollectionUtils.isNotEmpty(privateChannel)) {
			resultChannel.addAll(privateChannel);
		}

		// Set data return
		PageResultVO<YoutubeChannelTbl> data = new PageResultVO<>();
		data.setElements(resultChannel);
		data.setCurrentPage(0);
		data.setTotalPage(1);
		data.setTotalElement(resultChannel.size());

		// Write activity type "YoutubeChannel access"
		recordUserActivity(DBConstants.USER_ACTIVITY_MEDIA_YOUTUBE_CHANNEL_ACCESS);

		// Return
		result.setData(data);
		return result;
	}

	/**
	 *
	 * Update
	 *
	 * @param rq
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(YoutubeChannelTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Channel id must not null
			if (rq.getChannelId() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.channelId.name()));
			}

			// Category must not null
			if (rq.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.category.name()));
			}

			// Name must not null
			if (StringUtils.isBlank(rq.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.name.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		YoutubeChannelTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByKey(rq.getChannelId());
			if (updateTbl == null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "Channel ID <" + rq.getChannelId() + ">"));
			}
		}

		// PROCESS UPDATE

		/////////////////////
		// Update word //
		/////////////////////
		if (result.getStatus() == null) {

			// Set category
			if (rq.getCategory() != null) {
				updateTbl.setCategory(mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
						rq.getCategory().getTypeCode()));
			}

			// Set name
			if (rq.getName() != null) {
				updateTbl.setName(rq.getName());
			}

			// Set description
			if (rq.getDescription() != null) {
				updateTbl.setDescription(rq.getDescription());
			}

			// Update word
			updateTbl = youtubeChannelRepository.save(updateTbl);

			// Set id return
			result.setData(updateTbl.getId());
		}

		// Return
		return result;
	}
}
