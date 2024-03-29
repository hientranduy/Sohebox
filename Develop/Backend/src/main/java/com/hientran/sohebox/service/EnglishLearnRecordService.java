package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.EnglishLearnRecordRepository;
import com.hientran.sohebox.sco.EnglishLearnRecordSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.specification.EnglishLearnRecordSpecs.EnglishLearnRecordTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnglishLearnRecordService extends BaseService {
	private final EnglishLearnRecordRepository englishLearnRecordRepository;
	private final EnglishService englishService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 *
	 * Count learn
	 *
	 * @param rq
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> addLearn(EnglishLearnRecordTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// english must not null
			if (rq.getEnglish() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishLearnRecordTblEnum.english.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get logged user
		UserTbl userTbl = userDetailsServiceImpl.getCurrentLoginUser();

		// Check if english word is existed
		EnglishTbl englishTbl = englishService.getByKey(rq.getEnglish().getKeyWord());
		if (result.getStatus() == null && englishTbl == null) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, rq.getEnglish().getKeyWord()));
		}

		// Get the old record
		EnglishLearnRecordTbl tbl = null;
		if (result.getStatus() == null) {
			tbl = getByKey(userTbl.getId(), englishTbl.getId());
		}

		// PROCESS INSERT/UPDATE
		if (result.getStatus() == null) {
			// Update
			if (tbl != null) {
				Long newRecord = tbl.getRecordTimes() + 1;
				tbl.setRecordTimes(newRecord);

				if (DateUtils.isSameDay(tbl.getUpdatedDate(), new Date())) {
					tbl.setLearnedToday(tbl.getLearnedToday() + 1);
				} else {
					tbl.setLearnedToday(Long.valueOf(1));
				}

				tbl = englishLearnRecordRepository.save(tbl);

			} else {
				// Insert new
				tbl = new EnglishLearnRecordTbl();
				tbl.setUser(userTbl);
				tbl.setEnglish(englishTbl);
				tbl.setRecordTimes(Long.valueOf(1));
				tbl.setLearnedToday(Long.valueOf(1));
				tbl = englishLearnRecordRepository.save(tbl);
			}
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get record by key
	 *
	 * @param userId
	 * @param englishId
	 * @return
	 */
	private EnglishLearnRecordTbl getByKey(Long userId, Long englishId) {
		// Declare result
		EnglishLearnRecordTbl result = null;

		// Prepare search
		SearchNumberVO userIdSearch = new SearchNumberVO();
		userIdSearch.setEq(userId.doubleValue());

		SearchNumberVO englishIdSearch = new SearchNumberVO();
		englishIdSearch.setEq(englishId.doubleValue());

		EnglishLearnRecordSCO sco = new EnglishLearnRecordSCO();
		sco.setUserId(userIdSearch);
		sco.setEnglishId(englishIdSearch);

		// Get data
		List<EnglishLearnRecordTbl> list = englishLearnRecordRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
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
	public APIResponse<Object> search(EnglishLearnRecordSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check data authentication
		result = isDataAuthentication(sco.getUserId().getEq().longValue());

		// Check authentication data
		if (result.getStatus() == null) {
			// Get data
			Page<EnglishLearnRecordTbl> page = englishLearnRecordRepository.findAll(sco);

			// Transformer
			PageResultVO<EnglishLearnRecordTbl> data = new PageResultVO<>();
			if (!CollectionUtils.isEmpty(page.getContent())) {
				data.setElements(page.getContent());
				setPageHeader(page, data);
			}

			// Set data return
			result.setData(data);
		}

		// Return
		return result;
	}
}
