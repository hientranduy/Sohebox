package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.UserActivityVO;
import com.hientran.sohebox.entity.UserActivityTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserActivityTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<UserActivityTbl> to PageResultVO<UserActivityVO>
	 *
	 * @param Page<UserActivityTbl>
	 *
	 * @return PageResultVO<UserActivityVO>
	 */
	public PageResultVO<UserActivityVO> convertToPageReturn(Page<UserActivityTbl> pageTbl) {
		// Declare result
		PageResultVO<UserActivityVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<UserActivityVO> listVO = new ArrayList<>();
			for (UserActivityTbl tbl : pageTbl.getContent()) {
				listVO.add(convertToVO(tbl));
			}

			// Set return list to result
			result.setElements(listVO);
		}

		// Set header information
		setPageHeader(pageTbl, result);

		// Return
		return result;
	}

	/**
	 * Convert vo to tbl
	 *
	 * @return
	 */
	public UserActivityVO convertToVO(UserActivityTbl tbl) {
		// Declare result
		UserActivityVO result = new UserActivityVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 *
	 * @param UserActivity
	 * @return
	 */
	public UserActivityTbl convertToTbl(UserActivityVO vo) {
		// Declare result
		UserActivityTbl result = new UserActivityTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
