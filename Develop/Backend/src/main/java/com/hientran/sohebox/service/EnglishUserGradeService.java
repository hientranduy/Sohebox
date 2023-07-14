package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.EnglishTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.EnglishTypeTbl;
import com.hientran.sohebox.entity.EnglishUserGradeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.EnglishUserGradeRepository;
import com.hientran.sohebox.sco.EnglishUserGradeSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.specification.EnglishUserGradeSpecs.EnglishUserGradeTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnglishUserGradeService extends BaseService {
	private final EnglishUserGradeRepository EnglishUserGradeRepository;
	private final UserService userService;
	private final EnglishTypeCache englishTypeCache;

	/**
	 *
	 * Get record by key
	 *
	 * @param table key
	 * @return table data
	 */
	private EnglishUserGradeTbl getByKey(Long userId) {
		// Declare result
		EnglishUserGradeTbl result = null;

		// Prepare search
		SearchNumberVO userIdSearch = new SearchNumberVO();
		userIdSearch.setEq(userId.doubleValue());

		EnglishUserGradeSCO sco = new EnglishUserGradeSCO();
		sco.setUserId(userIdSearch);

		// Get data
		List<EnglishUserGradeTbl> list = EnglishUserGradeRepository.findAll(sco).getContent();
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
	public APIResponse<Object> search(EnglishUserGradeSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get data
		Page<EnglishUserGradeTbl> page = EnglishUserGradeRepository.findAll(sco);

		// Transformer
		PageResultVO<EnglishUserGradeTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			data.setElements(page.getContent());
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 *
	 * Set english user grade
	 *
	 * @param rq
	 * @return id
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> setEnglishUserGrade(EnglishUserGradeTbl rq) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate empty input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// user must not null
			if (rq.getUser() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishUserGradeTblEnum.user.name()));
			}

			// grade must not null
			if (rq.getVusGrade() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishUserGradeTblEnum.vusGrade.name()));
			}

			// learn day must not null
			if (rq.getLearnDay() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, EnglishUserGradeTblEnum.learnDay.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Validate in-existed input user
		UserTbl userTbl = userService.getTblByUserName(rq.getUser().getUsername());
		if (result.getStatus() == null && userTbl == null) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_USERNAME, rq.getUser().getUsername()));
		}

		// Check if logged user is the same input user
		if (result.getStatus() == null && !userService.isDataOwner(rq.getUser().getUsername())) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// PROCESS INSERT/UPDATE
		if (result.getStatus() == null) {
			EnglishUserGradeTbl tbl = getByKey(userTbl.getId());

			// Get grade
			EnglishTypeTbl vusGrade = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE,
					rq.getVusGrade().getTypeCode());

			// Get learn day
			EnglishTypeTbl learnDay = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY,
					rq.getLearnDay().getTypeCode());

			if (tbl == null) {
				tbl = new EnglishUserGradeTbl();
				tbl.setUser(userTbl);
				tbl.setVusGrade(vusGrade);
				tbl.setLearnDay(learnDay);
				tbl = EnglishUserGradeRepository.save(tbl);
			} else {
				tbl.setVusGrade(vusGrade);
				tbl.setLearnDay(learnDay);
				tbl = EnglishUserGradeRepository.save(tbl);
			}
		}

		// Return
		return result;
	}
}
