package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.dto.YoutubeVideoVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.repository.YoutubeVideoRepository;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.YoutubeVideoSCO;
import com.hientran.sohebox.specification.YoutubeVideoSpecs.YoutubeVideoTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YoutubeVideoService extends BaseService {

	private final YoutubeVideoRepository youtubeVideoRepository;

	/**
	 *
	 * Get video by id
	 *
	 */
	public YoutubeVideoTbl getByVideoId(String videoId) {
		// Declare result
		YoutubeVideoTbl result = null;

		SearchTextVO videoSearch = new SearchTextVO();
		videoSearch.setEq(videoId);

		YoutubeVideoSCO sco = new YoutubeVideoSCO();
		sco.setVideoId(videoSearch);

		// Get data
		List<YoutubeVideoTbl> list = youtubeVideoRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		}

		// Return
		return result;
	}

	/**
	 *
	 * Add video if not exist
	 *
	 * @param vo
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<YoutubeVideoTbl> mergeVideo(YoutubeVideoVO vo) {
		// Declare result
		APIResponse<YoutubeVideoTbl> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Video id must not null
			if (vo.getId().getVideoId() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, YoutubeVideoTblEnum.videoId.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		/////////////////////
		// Read old record //
		/////////////////////
		if (result.getStatus() == null) {
			// Transform
			YoutubeVideoTbl tbl = getByVideoId(vo.getId().getVideoId());

			// Set data
			if (tbl == null) {
				tbl = new YoutubeVideoTbl();
				tbl.setVideoId(vo.getId().getVideoId());
			}

			tbl.setTitle(vo.getSnippet().getTitle().replaceAll("[^\\p{ASCII}]", ""));
			tbl.setDescription(vo.getSnippet().getDescription().replaceAll("[^\\p{ASCII}]", ""));
			tbl.setUrlThumbnail(vo.getSnippet().getThumbnails().getMedium().getUrl());
			tbl.setPublishedAt(vo.getSnippet().getPublishedAt());

			// Save
			tbl = youtubeVideoRepository.save(tbl);

			// Set id return
			result.setData(tbl);
		}

		// Return
		return result;
	}
}
