package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.AccountRepository;
import com.hientran.sohebox.repository.TypeRepository;
import com.hientran.sohebox.sco.AccountSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.specification.AccountSpecs.AccountTblEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService extends BaseService {

	private final AccountRepository accountRepository;
	private final MdpService mdpService;
	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final UserActivityService userActivityService;
	private final TypeRepository typeRepository;

	/**
	 * Create new Account
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(AccountTbl request) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		List<String> errors = new ArrayList<>();

		// Account type not null
		if (request.getType() == null) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.type.name()));
		}

		// Account name not null
		if (StringUtils.isBlank(request.getAccountName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.accountName.name()));
		}

		// Record error
		if (CollectionUtils.isNotEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Record existed already
		if (accountRepository.findFirstByUserAndTypeAndAccountName(loggedUser, request.getType(),
				request.getAccountName()) != null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.EXISTED_RECORD,
					"account " + request.getType().getTypeCode() + "<" + request.getAccountName() + ">"));
		}

		// Create new
		AccountTbl tbl = new AccountTbl();
		tbl.setUser(loggedUser);
		tbl.setType(request.getType());
		tbl.setAccountName(request.getAccountName());
		if (StringUtils.isNotBlank(request.getMdpPlain())) {
			tbl.setMdp(mdpService.getMdp(request.getMdpPlain()));
		}
		if (StringUtils.isNotBlank(request.getNote())) {
			tbl.setNote(request.getNote());
		}
		tbl = accountRepository.save(tbl);
		result.setData(tbl.getId());

		// Return
		return result;
	}

	/**
	 * Delete by id
	 *
	 * Only role creator
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

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Filter by user
		if (!StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_CREATOR)) {
			SearchNumberVO userIdSearch = new SearchNumberVO();
			userIdSearch.setEq(loggedUser.getId().doubleValue());
			sco.setUser(userIdSearch);
		}

		// Filter like by account type
		if (sco.getAccountTypeName() != null && sco.getAccountTypeName().getLike() != null) {
			List<TypeTbl> accountTypes = typeRepository.findAllByTypeClassAndTypeCodeContaining(
					DBConstants.TYPE_CLASS_ACCOUNT, sco.getAccountTypeName().getLike());

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
		PageResultVO<AccountTbl> data = new PageResultVO<>();
		if (!CollectionUtils.isEmpty(page.getContent())) {
			List<AccountTbl> outData = new ArrayList<>();
			for (AccountTbl item : page.getContent()) {
				item.setMdpPlain(hideText(item.getMdp().getDescription()));
				outData.add(item);
			}
			data.setElements(outData);
			setPageHeader(page, data);
		}

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * Get clear password
	 *
	 * @return
	 */
	public APIResponse<?> showPassword(AccountTbl request) {
		// Get current logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Check authentication
		if (loggedUser.getPrivateKey() != null) {
			if (!mdpService.isValidPassword(request.getMdpPlain(), loggedUser.getPrivateKey().getMdp())) {
				return new APIResponse<>(HttpStatus.OK, "Incorrect private key");
			}
		} else {
			return new APIResponse<>(HttpStatus.OK, "Private key is not set yet");
		}

		// Get data
		AccountTbl accountTbl = accountRepository.findFirstByUserAndTypeAndAccountName(loggedUser, request.getType(),
				request.getAccountName());
		if (accountTbl == null) {
			return new APIResponse<>(HttpStatus.OK, "You are not data owner");
		}

		// Body
		List<AccountTbl> accountList = new ArrayList<>();
		request.setMdpPlain(accountTbl.getMdp().getDescription());
		accountList.add(request);

		PageResultVO<AccountTbl> data = new PageResultVO<>();
		data.setElements(accountList);
		data.setCurrentPage(0);
		data.setTotalPage(1);
		data.setTotalElement(accountList.size());

		// return
		APIResponse<Object> result = new APIResponse<>();
		result.setData(data);
		return result;
	}

	/**
	 * Update
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> update(AccountTbl request) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		List<String> errors = new ArrayList<>();
		if (request.getType() == null) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.type.name()));
		}
		if (StringUtils.isBlank(request.getAccountName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, AccountTblEnum.accountName.name()));
		}
		if (CollectionUtils.isNotEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Get logged user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Get updated account
		AccountTbl updateAccount = getAccountIdByUser(loggedUser, request.getId());
		if (updateAccount == null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, ResponseCode.mapParam(ResponseCode.INEXISTED_RECORD,
					"account " + request.getType().getTypeCode() + "<" + request.getAccountName() + ">"));
		}

		// Update
		updateAccount.setType(request.getType());
		if (StringUtils.isNotEmpty(request.getAccountName())
				&& !StringUtils.equals(updateAccount.getAccountName(), request.getAccountName())) {
			updateAccount.setAccountName(request.getAccountName());
		}
		if (StringUtils.isNotEmpty(request.getMdpPlain())) {
			updateAccount.setMdp(mdpService.getMdp(request.getMdpPlain()));
		}
		if (StringUtils.isNotEmpty(request.getNote())
				&& !StringUtils.equals(updateAccount.getNote(), request.getNote())) {
			updateAccount.setNote(request.getNote());
		}
		accountRepository.save(updateAccount);

		// Return
		return result;
	}

}