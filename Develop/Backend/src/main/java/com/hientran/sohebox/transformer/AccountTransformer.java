package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.dto.AccountVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.entity.TypeTbl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountTransformer extends BaseTransformer {

	private final Mapper objectMapper;

	/**
	 * Convert Page<AccountTbl> to PageResultVO<AccountVO>
	 *
	 * @param Page<AccountTbl>
	 *
	 * @return PageResultVO<AccountVO>
	 */
	public PageResultVO<AccountVO> convertToPageReturn(Page<AccountTbl> pageTbl) {
		// Declare result
		PageResultVO<AccountVO> result = new PageResultVO<>();

		// Convert data
		if (!CollectionUtils.isEmpty(pageTbl.getContent())) {
			List<AccountVO> listVO = new ArrayList<>();
			for (AccountTbl tbl : pageTbl.getContent()) {
				listVO.add(convertToAccountVO(tbl));
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
	public AccountVO convertToAccountVO(AccountTbl tbl) {
		// Declare result
		AccountVO result = new AccountVO();

		// Transformation
		objectMapper.map(tbl, result);

		// Account Type
		TypeTbl accountType = new TypeTbl();
		BeanUtils.copyProperties(tbl.getType(), accountType);
		result.setAccountType(accountType);

		// Set mdp
		if (tbl.getMdp() != null) {
			result.setMdp(convertMdp(tbl.getMdp().getDescription()));
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
	public String convertMdp(String mdp) {
		// Declare result
		String result = "";

		if (mdp.length() > 0) {

			// Add 1 first chars
			result = mdp.substring(0, 1);

			// Add n middle chars
			for (int i = 0; i < mdp.length() - 2; i++) {
				result = result + "*";
			}

			// Add 1 last chars
			result = result + mdp.substring(mdp.length() - 1);
		}

		// Return
		return result;
	}
}
