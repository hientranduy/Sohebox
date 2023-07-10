package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.AccountVO;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.TypeVO;
import com.hientran.sohebox.dto.UserVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.AccountRepository;
import com.hientran.sohebox.sco.AccountSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.specification.AccountSpecs.AccountTblEnum;
import com.hientran.sohebox.transformer.AccountTransformer;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService extends BaseService {

	private final AccountRepository accountRepository;
	private final AccountTransformer accountTransformer;
	private final MdpService mdpService;
	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final TypeCache typeCache;
	private final UserActivityService userActivityService;

	/**
	 *
	 * Create new Account
	 *
	 * Anyone
	 *
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(AccountVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Account type not null
			if (vo.getAccountType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.type.name()));
			}

			// Account name not null
			if (StringUtils.isBlank(vo.getAccountName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.accountName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Record existed already
		if (result.getStatus() == null) {
			if (recordIsExisted(loggedUser, vo.getAccountType(), vo.getAccountName())) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.EXISTED_RECORD,
						"account " + vo.getAccountType().getTypeCode() + "<" + vo.getAccountName() + ">"));
			}
		}

		// Create new
		if (result.getStatus() == null) {
			// Transform
			AccountTbl tbl = new AccountTbl();
			tbl.setUser(loggedUser);

			TypeTbl accountType = new TypeTbl();
			BeanUtils.copyProperties(vo.getAccountType(), accountType);
			tbl.setType(accountType);
			tbl.setAccountName(vo.getAccountName());
			if (StringUtils.isNotBlank(vo.getMdp())) {
				tbl.setMdp(mdpService.getMdp(vo.getMdp()));
			}
			if (StringUtils.isNotBlank(vo.getNote())) {
				tbl.setNote(vo.getNote());
			}

			// Create
			tbl = accountRepository.save(tbl);
			result.setData(tbl.getId());
		}

		// Write activity type "create account"
		recordUserActivity(DBConstants.USER_ACTIVITY_ACCOUNT_CREATE);

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
	public APIResponse<Long> update(AccountVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// Account type not null
			if (vo.getAccountType() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.type.name()));
			}

			// Account name not null
			if (StringUtils.isBlank(vo.getAccountName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.accountName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Get updated account
		AccountTbl updateAccount = null;
		if (result.getStatus() == null) {
			updateAccount = getAccountIdByUser(loggedUser, vo.getId());
			if (updateAccount == null) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD,
						"account " + vo.getAccountType().getTypeCode() + "<" + vo.getAccountName() + ">"));
			}
		}

		// Update
		if (result.getStatus() == null) {
			if (!StringUtils.equals(vo.getAccountType().getTypeCode(), updateAccount.getType().getTypeCode())) {
				TypeTbl accountType = new TypeTbl();
				BeanUtils.copyProperties(vo.getAccountType(), accountType);
				updateAccount.setType(accountType);
			}
			if (!StringUtils.equals(vo.getAccountName(), updateAccount.getAccountName())) {
				updateAccount.setAccountName(vo.getAccountName());
			}

			if (updateAccount.getMdp() == null && vo.getMdp() == null) {
			} else {
				if (updateAccount.getMdp() == null && vo.getMdp() != null) {
					updateAccount.setMdp(mdpService.getMdp(vo.getMdp()));
				} else {
					if (!StringUtils.equals(vo.getMdp(),
							accountTransformer.convertMdp(updateAccount.getMdp().getDescription()))) {
						updateAccount.setMdp(mdpService.getMdp(vo.getMdp()));
					}
				}
			}

			if (!StringUtils.equals(vo.getNote(), updateAccount.getNote())) {
				updateAccount.setNote(vo.getNote());
			}
			accountRepository.save(updateAccount);
		}

		// Write activity type "update account"
		recordUserActivity(DBConstants.USER_ACTIVITY_ACCOUNT_UPDATE);

		// Return
		return result;
	}

	/**
	 *
	 * Check if record is existed
	 *
	 * @param userOwnerId
	 * @param accountTypeId
	 * @param accountName
	 * @return
	 */
	private boolean recordIsExisted(UserTbl user, TypeVO accountType, String accountName) {
		// Declare result
		boolean result = false;

		// Prepare search
		SearchNumberVO userIdSearch = new SearchNumberVO();
		userIdSearch.setEq(user.getId().doubleValue());

		SearchNumberVO accountTypeIdSearch = new SearchNumberVO();
		accountTypeIdSearch.setEq(accountType.getId().doubleValue());

		SearchTextVO accountNameSearch = new SearchTextVO();
		accountNameSearch.setEq(accountName);

		AccountSCO sco = new AccountSCO();
		sco.setUser(userIdSearch);
		sco.setAccountType(accountTypeIdSearch);
		sco.setAccountName(accountNameSearch);

		// Get data
		List<AccountTbl> listAccount = accountRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(listAccount)) {
			result = true;
		}

		// Return
		return result;
	}

	/**
	 *
	 * Get account
	 *
	 * @param userOwnerId
	 * @param accountTypeId
	 * @param accountName
	 * @return
	 */
	private AccountTbl getAccountIdByUser(UserTbl loggedUser, Long accountId) {
		// Declare result
		AccountTbl result = null;

		// Prepare search
		SearchNumberVO userSearch = new SearchNumberVO();
		userSearch.setEq(loggedUser.getId().doubleValue());
		SearchNumberVO id = new SearchNumberVO();
		id.setEq(accountId.doubleValue());

		AccountSCO sco = new AccountSCO();
		sco.setId(id);
		if (StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_USER)) {
			sco.setUser(userSearch);
		}

		// Get data
		List<AccountTbl> listAccount = accountRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(listAccount)) {
			result = listAccount.get(0);
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
	public APIResponse<Object> search(AccountSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check if input user is existed
		UserVO userVO = null;
		if (sco.getUserName() != null) {
			userVO = userService.getByUserName(sco.getUserName().getEq());
		}

		if (result.getStatus() == null && userVO == null) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_USERNAME, sco.getUserName().getEq()));
		}

		// Check authentication data
		if (result.getStatus() == null) {
			if (StringUtils.equals(userVO.getRoleName(), DBConstants.USER_ROLE_CREATOR)) {
				// Role creator: get all accounts
				sco.setUserName(null);
			} else {
				// Other role: return account of logged user
				if (userService.isDataOwner(sco.getUserName().getEq())) {
					SearchNumberVO userIdSearch = new SearchNumberVO();
					userIdSearch.setEq(userVO.getId().doubleValue());
					sco.setUser(userIdSearch);
				} else {
					result = new APIResponse<>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
				}
			}
		}

		// Get data
		if (result.getStatus() == null) {
			if (sco.getAccountName() != null && sco.getAccountName().getLike() != null) {
				SearchTextVO typeClass = new SearchTextVO();
				typeClass.setEq(DBConstants.TYPE_CLASS_ACCOUNT);
				SearchTextVO typeCode = new SearchTextVO();
				typeCode.setLike(sco.getAccountName().getLike());
				TypeSCO typeSCO = new TypeSCO();
				typeSCO.setTypeClass(typeClass);
				typeSCO.setTypeCode(typeCode);
				List<TypeTbl> accountTypes = typeCache.searchList(typeSCO);

				if (CollectionUtils.isNotEmpty(accountTypes)) {
					List<Double> ids = new ArrayList<>();
					for (TypeTbl typeTbl : accountTypes) {
						ids.add(typeTbl.getId().doubleValue());
					}

					SearchNumberVO accountTypeId = new SearchNumberVO();
					accountTypeId.setIn(ids.toArray(new Double[ids.size()]));
					sco.setAccountType(accountTypeId);
				}
			}

			Page<AccountTbl> page = accountRepository.findAll(sco);

			// Transformer
			PageResultVO<AccountVO> data = accountTransformer.convertToPageReturn(page);

			// Set data return
			result.setData(data);
		}

		// Write activity type "access account"
		if (userVO != null) {
			userActivityService.recordUserActivity(userVO, DBConstants.USER_ACTIVITY_ACCOUNT_ACCESS);
		}

		// Return
		return result;
	}

	/**
	 * Delete by id
	 *
	 * Only role creator
	 *
	 * @param User
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> deleteById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check account is existed
		Optional<AccountTbl> accountTbl = accountRepository.findById(id);
		if (!accountTbl.isPresent()) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Check logged user have permission to delete
		if (result.getStatus() == null && !userService.isDataOwner(accountTbl.get().getUser().getUsername())) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Process delete
		if (result.getStatus() == null) {
			accountRepository.delete(accountTbl.get());
		}

		// Write activity type "delete account"
		if (accountTbl.isPresent()) {
			userActivityService.recordUserActivity(accountTbl.get().getUser(),
					DBConstants.USER_ACTIVITY_ACCOUNT_DELETE);
		}

		// Return
		return result;
	}

	/**
	 * Get by id
	 *
	 * @param User
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Check existence
		Optional<AccountTbl> accountTbl = accountRepository.findById(id);
		if (!accountTbl.isPresent()) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD, "account"));
		}

		// Check if user is the owner of data
		if (result.getStatus() == null) {
			AccountVO vo = accountTransformer.convertToAccountVO(accountTbl.get());
			if (!userService.isDataOwner(vo.getUser().getUsername())) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
			} else {
				// Set return data
				result.setData(vo);
			}
		}

		// Return
		return result;
	}

	/**
	 * Get clear password
	 *
	 * @return
	 */
	public APIResponse<?> showPassword(AccountVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get current logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Check authentication
		if (loggedUser.getPrivateKey() != null) {
			if (!mdpService.isValidPassword(vo.getUser().getPrivateKey(), loggedUser.getPrivateKey().getMdp())) {
				result = new APIResponse<>(HttpStatus.OK, "Incorrect private key");
			}
		} else {
			result = new APIResponse<>(HttpStatus.OK, "Private key is not set yet");
		}

		// Get data
		AccountTbl accountTbl = accountRepository.findById(vo.getId()).get();

		// Verify data owner
		if (result.getStatus() == null) {
			if (accountTbl == null || accountTbl.getUser().getId() != loggedUser.getId()) {
				result = new APIResponse<>(HttpStatus.OK, "You are not data owner");
			}
		}

		// Return
		if (result.getStatus() == null) {
			List<AccountVO> accountList = new ArrayList<>();
			vo.setMdp(accountTbl.getMdp().getDescription());
			accountList.add(vo);

			PageResultVO<AccountVO> data = new PageResultVO<>();
			data.setElements(accountList);
			data.setCurrentPage(0);
			data.setTotalPage(1);
			data.setTotalElement(accountList.size());

			result.setData(data);
		}

		// Return
		return result;
	}

}