package com.hientran.sohebox.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.entity.CryptoPortfolioHistoryTbl;
import com.hientran.sohebox.entity.CryptoPortfolioTbl;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.CryptoPortfolioHistoryRepository;
import com.hientran.sohebox.repository.CryptoPortfolioRepository;
import com.hientran.sohebox.sco.CryptoPortfolioHistorySCO;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.transformer.CryptoPortfolioHistoryTransformer;
import com.hientran.sohebox.utils.LogUtils;
import com.hientran.sohebox.vo.CryptoPortfolioHistoryVO;
import com.hientran.sohebox.vo.PageResultVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CryptoPortfolioHistoryService extends BaseService {
	private final CryptoPortfolioHistoryRepository cryptoPortfolioHistoryRepository;
	private final CryptoPortfolioRepository cryptoPortfolioRepository;
	private final CryptoPortfolioHistoryTransformer cryptoPortfolioHistoryTransformer;
	private final UserDetailsServiceImpl userService;

	DecimalFormat df = new DecimalFormat("#.###");

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void cronjobCalculTotalPortfolio() {
		try {
			// Get raw data
			List<CryptoPortfolioTbl> rawData = cryptoPortfolioRepository.findAll();

			// Group by user
			HashMap<UserTbl, List<CryptoPortfolioTbl>> mapUserToken = new HashMap<UserTbl, List<CryptoPortfolioTbl>>();
			for (CryptoPortfolioTbl item : rawData) {
				UserTbl user = item.getUser();
				if (mapUserToken.containsKey(user)) {
					List<CryptoPortfolioTbl> mapValue = mapUserToken.get(user);
					mapValue.add(item);
					mapUserToken.replace(user, mapValue);
				} else {
					List<CryptoPortfolioTbl> mapValue = new ArrayList<>();
					mapValue.add(item);
					mapUserToken.put(user, mapValue);
				}
			}

			// Loop user
			for (UserTbl user : mapUserToken.keySet()) {
				List<CryptoPortfolioTbl> portfolioList = mapUserToken.get(user);

				// Group by token
				HashMap<CryptoTokenConfigTbl, CryptoPortfolioHistoryTbl> mapToken = new HashMap<CryptoTokenConfigTbl, CryptoPortfolioHistoryTbl>();
				for (CryptoPortfolioTbl portfolio : portfolioList) {
					CryptoTokenConfigTbl token = portfolio.getToken();
					if (mapToken.containsKey(token)) {
						CryptoPortfolioHistoryTbl history = mapToken.get(token);
						if (portfolio.getAmtAvailable() != null) {
							history.setTotalAvailable(history.getTotalAvailable() + portfolio.getAmtAvailable());
						}
						if (portfolio.getAmtTotalDelegated() != null) {
							history.setTotalDelegated(history.getTotalDelegated() + portfolio.getAmtTotalDelegated());
						}
						if (portfolio.getAmtTotalReward() != null) {
							history.setTotalReward(history.getTotalReward() + portfolio.getAmtTotalReward());
						}
						if (portfolio.getAmtTotalUnbonding() != null) {
							history.setTotalUnbonding(history.getTotalUnbonding() + portfolio.getAmtTotalUnbonding());
						}
						mapToken.replace(token, history);
					} else {
						CryptoPortfolioHistoryTbl history = new CryptoPortfolioHistoryTbl();
						history.setTimeStamp(new Date());
						history.setUser(user);
						history.setToken(token);
						history.setLastSyncDate(portfolio.getSyncDate());

						if (portfolio.getAmtAvailable() != null) {
							history.setTotalAvailable(portfolio.getAmtAvailable());
						} else {
							history.setTotalAvailable(Double.valueOf(0));
						}

						if (portfolio.getAmtTotalDelegated() != null) {
							history.setTotalDelegated(portfolio.getAmtTotalDelegated());
						} else {
							history.setTotalAvailable(Double.valueOf(0));
						}

						if (portfolio.getAmtTotalReward() != null) {
							history.setTotalReward(portfolio.getAmtTotalReward());
						} else {
							history.setTotalReward(Double.valueOf(0));
						}

						if (portfolio.getAmtTotalUnbonding() != null) {
							history.setTotalUnbonding(portfolio.getAmtTotalUnbonding());
						} else {
							history.setTotalUnbonding(Double.valueOf(0));
						}

						mapToken.put(token, history);
					}
				}

				// Loop token to write
				for (CryptoTokenConfigTbl token : mapToken.keySet()) {
					CryptoPortfolioHistoryTbl tbl = mapToken.get(token);

					// Rounding
					tbl.setTotalAvailable(Double.parseDouble(df.format(tbl.getTotalAvailable())));
					tbl.setTotalDelegated(Double.parseDouble(df.format(tbl.getTotalDelegated())));
					tbl.setTotalReward(Double.parseDouble(df.format(tbl.getTotalReward())));
					tbl.setTotalUnbonding(Double.parseDouble(df.format(tbl.getTotalUnbonding())));

					// Get last record
					CryptoPortfolioHistoryTbl lastItem = cryptoPortfolioHistoryRepository
							.findTopByUserAndTokenOrderByTimeStampDesc(tbl.getUser(), token);

					// Set total increase
					if (lastItem != null) {
						tbl.setTotalIncrease(tbl.getTotalAvailable() + tbl.getTotalDelegated() + tbl.getTotalReward()
								+ tbl.getTotalUnbonding() - (lastItem.getTotalAvailable() + lastItem.getTotalDelegated()
										+ lastItem.getTotalReward() + lastItem.getTotalUnbonding()));
					} else {
						tbl.setTotalIncrease(tbl.getTotalAvailable() + tbl.getTotalDelegated() + tbl.getTotalReward()
								+ tbl.getTotalUnbonding());
					}
					tbl.setTotalIncrease(Double.parseDouble(df.format(tbl.getTotalIncrease())));

					cryptoPortfolioHistoryRepository.save(tbl);
				}
			}

		} catch (Exception e) {
			LogUtils.writeLogError(e);
		}

	}

	/**
	 * Create
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private APIResponse<Long> create(CryptoPortfolioHistoryVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Transform
		CryptoPortfolioHistoryTbl tbl = cryptoPortfolioHistoryTransformer.convertToTbl(vo);

		// Create
		tbl = cryptoPortfolioHistoryRepository.save(tbl);

		// Set id return
		result.setData(tbl.getId());

		// Return
		return result;
	}

	public APIResponse<Object> getPortfolioSummary(CryptoPortfolioHistorySCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get logged user
		UserTbl loggedUser = userService.getCurrentLoginUser();

		// Get latest date by user
		Date latestReportDate = getLatestDateByUser(loggedUser);

		// Get Date
		if (latestReportDate != null) {
			// Prepare search
			SearchNumberVO userIdSearch = new SearchNumberVO(loggedUser.getId().doubleValue());
			sco.setUser(userIdSearch);

			SearchDateVO dateSearch = new SearchDateVO(latestReportDate);
			sco.setTimeStamp(dateSearch);

			// Get data
			Page<CryptoPortfolioHistoryTbl> page = cryptoPortfolioHistoryRepository.findAll(sco);

			// Transformer
			PageResultVO<CryptoPortfolioHistoryVO> data = cryptoPortfolioHistoryTransformer.convertToPageReturn(page);

			// Set data return
			result.setData(data);
		}

		// Return
		return result;
	}

	private Date getLatestDateByUser(UserTbl loggedUser) {
		CryptoPortfolioHistoryTbl tbl = cryptoPortfolioHistoryRepository.findTopByUserOrderByIdDesc(loggedUser);
		if (tbl != null) {
			return tbl.getTimeStamp();
		} else {
			return null;
		}
	}
}
