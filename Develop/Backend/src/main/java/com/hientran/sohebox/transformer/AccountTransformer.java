package com.hientran.sohebox.transformer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.vo.AccountVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Component
public class AccountTransformer extends BaseTransformer {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("objectMapper")
	private Mapper objectMapper;

	@Autowired
	private TypeTransformer typeTransformer;

	/**
	 * Convert Page<AccountTbl> to PageResultVO<AccountVO>
	 *
	 * @param Page<AccountTbl>
	 * 
	 * @return PageResultVO<AccountVO>
	 */
	public PageResultVO<AccountVO> convertToPageReturn(Page<AccountTbl> pageTbl) {
		// Declare result
		PageResultVO<AccountVO> result = new PageResultVO<AccountVO>();

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
		result.setAccountType(typeTransformer.convertToVO(tbl.getType()));

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

	/**
	 * Convert tbl to vo
	 *
	 * @param Account
	 * @return
	 */
	public AccountTbl convertToAccountTbl(AccountVO vo) {
		// Declare result
		AccountTbl result = new AccountTbl();

		// Transformation
		objectMapper.map(vo, result);

		// Account Type
		result.setType(typeTransformer.convertToTbl(vo.getAccountType()));

		// Return
		return result;
	}
}
