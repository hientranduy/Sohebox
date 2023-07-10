package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.UserVO;
import com.hientran.sohebox.entity.UserTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<UserTbl> to PageResultVO<UserVO>
	 *
	 * @param Page<UserTbl>
	 *
	 * @return PageResultVO<UserVO>
	 */
	public PageResultVO<UserVO> convertToPageReturn(Page<UserTbl> pageTbl) {
		// Declare result
		PageResultVO<UserVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<UserVO> listVO = new ArrayList<>();
			for (UserTbl tbl : pageTbl.getContent()) {
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
	public UserVO convertToVO(UserTbl tbl) {
		// Declare result
		UserVO result = new UserVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Set mdp
		if (tbl.getMdp() != null) {
			result.setPassword(convertMdp(tbl.getMdp().getDescription()));
		}

		// Set role
		if (tbl.getRole() != null) {
			result.setRoleName(tbl.getRole().getRoleName());
		}

		// Set private key to null
		if (tbl.getPrivateKey() != null) {
			result.setPrivateKey(convertMdp(tbl.getPrivateKey().getDescription()));
		}

		// Return
		return result;
	}

	/**
	 * Conver return mdp
	 *
	 * @param description
	 * @return
	 */
	private String convertMdp(String mdp) {
		// Declare result
		String result = "";

		// Add 1 first chars
		result = mdp.substring(0, 1);

		// Add n middle chars
		for (int i = 0; i < mdp.length() - 2; i++) {
			result = result + "*";
		}

		// Add 1 last chars
		result = result + mdp.substring(mdp.length() - 1);

		// Return
		return result;
	}

	/**
	 * Convert tbl to vo
	 *
	 * @param User
	 * @return
	 */
	public UserTbl convertToTbl(UserVO vo) {
		// Declare result
		UserTbl result = new UserTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Return
		return result;
	}
}
