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
import com.hientran.sohebox.transformer.YoutubeChannelTransformer;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.YoutubeChannelVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YoutubeChannelService extends BaseService {

	private final YoutubeChannelRepository youtubeChannelRepository;
	private final YoutubeChannelTransformer youtubeChannelTransformer;
	private final MediaTypeCache mediaTypeCache;

	@Autowired
	private UserDetailsServiceImpl userService;

	/**
	 * 
	 * Create
	 * 
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(YoutubeChannelVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Channel id must not null
			if (vo.getChannelId() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.channelId.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(vo.getChannelId())) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_RECORD, "channel ID <" + vo.getId() + ">"));
			}
		}

		/////////////////////
		// Record new //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			YoutubeChannelTbl tbl = youtubeChannelTransformer.convertToTbl(vo);

			// Set category
			tbl.setCategory(mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
					vo.getCategory().getTypeCode()));

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
	 * Update
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(YoutubeChannelVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Channel id must not null
			if (vo.getChannelId() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.channelId.name()));
			}

			// Category must not null
			if (vo.getCategory() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.category.name()));
			}

			// Name must not null
			if (StringUtils.isBlank(vo.getName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeChannelTblEnum.name.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get the old record
		YoutubeChannelTbl updateTbl = null;
		if (result.getStatus() == null) {
			updateTbl = getByKey(vo.getChannelId());
			if (updateTbl == null) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "Channel ID <" + vo.getChannelId() + ">"));
			}
		}

		// PROCESS UPDATE

		/////////////////////
		// Update word //
		/////////////////////
		if (result.getStatus() == null) {

			// Set category
			if (vo.getCategory() != null) {
				updateTbl.setCategory(mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
						vo.getCategory().getTypeCode()));
			}

			// Set name
			if (vo.getName() != null) {
				updateTbl.setName(vo.getName());
			}

			// Set description
			if (vo.getDescription() != null) {
				updateTbl.setDescription(vo.getDescription());
			}

			// Update word
			updateTbl = youtubeChannelRepository.save(updateTbl);

			// Set id return
			result.setData(updateTbl.getId());
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
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<YoutubeChannelTbl> page = youtubeChannelRepository.findAll(sco);

		// Transformer
		PageResultVO<YoutubeChannelVO> data = youtubeChannelTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Write activity type "YoutubeChannel access"
		recordUserActivity(DBConstants.USER_ACTIVITY_MEDIA_YOUTUBE_CHANNEL_ACCESS);

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
		Boolean result = false;

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
	 * Search
	 * 
	 * @param sco
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchMyChannel(YoutubeChannelSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get public channel
		MediaTypeTbl privateChannelType = mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
				DBConstants.TYPE_CODE_MEDIA_YOUTUBE_CHANNEL_CATEGORY_PERSONAL);
		SearchNumberVO categoryId = new SearchNumberVO();
		categoryId.setNotEq(privateChannelType.getId().doubleValue());
		sco.setCategoryId(categoryId);
		List<YoutubeChannelTbl> publicChannel = youtubeChannelRepository.findAll(sco).getContent();

		// Get private channel
		UserTbl loggedUser = userService.getCurrentLoginUser();
		List<YoutubeChannelTbl> privateChannel = new ArrayList<YoutubeChannelTbl>();
		if (loggedUser != null) {
			SearchNumberVO userIdSearch = new SearchNumberVO();
			userIdSearch.setEq(loggedUser.getId().doubleValue());
			YoutubeChannelSCO channelSCO = new YoutubeChannelSCO();
			channelSCO.setUserId(userIdSearch);
			privateChannel = youtubeChannelRepository.findAll(channelSCO).getContent();
		}

		// Merge to result channel
		List<YoutubeChannelVO> resultChannel = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(publicChannel)) {
			for (YoutubeChannelTbl channel : publicChannel) {
				resultChannel.add(youtubeChannelTransformer.convertToVO(channel));
			}
		}
		if (CollectionUtils.isNotEmpty(privateChannel)) {
			for (YoutubeChannelTbl channel : privateChannel) {
				resultChannel.add(youtubeChannelTransformer.convertToVO(channel));
			}
		}

		// Set data return
		PageResultVO<YoutubeChannelVO> data = new PageResultVO<>();
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
}
