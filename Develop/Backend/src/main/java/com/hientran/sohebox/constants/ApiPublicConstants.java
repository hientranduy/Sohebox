package com.hientran.sohebox.constants;

import java.io.Serializable;

/**
 * 
 * Public API services output definition
 *
 * @author hientran
 */
public class ApiPublicConstants implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String AUTHENTICATE = "/login";

	public static final String LOGOUT = "/logout";

	public static final String CHANGE_PASSWORD = "/changePassword";

	public static final String CHANGE_PASSWORD_LOGGED_USER = "/changePasswordLoggedUser";

	public static final String CHANGE_PRIVATE_KEY = "/changePrivateKey";

	public static final String SEARCH = "/search";

	public static final String SEARCH_BY_CHANNEL = "/searchByChannel";

	public static final String SEARCH_MY_OWNER = "/searchMyOwner";

	public static final String SEARCH_LOW_LEARN = "/searchLowLearn";

	public static final String ENGLISH_TOP_LEARN = "/englishTopLearn";

	public static final String ADD = "/add";

	public static final String SET = "/set";

	public static final String GET = "/get";

	public static final String ID = "/{id}";

	/////////
	// API //
	/////////

	public static final String API_ROLE = "/role";

	public static final String API_USER = "/users";

	public static final String API_ACCOUNT = "/accounts";

	public static final String API_ACCOUNT_SHOW_PASSWORD = "/showPassword";

	public static final String API_TYPE = "/types";

	public static final String API_ENGLISH_TYPE = "/englishTypes";

	public static final String API_MEDIA_TYPE = "/mediaTypes";

	public static final String API_FOOD_TYPE = "/foodTypes";

	public static final String API_CONFIG = "/configs";

	public static final String API_TYPE_CLASS = "/typeclass";

	public static final String API_ENGLISH = "/english";

	public static final String API_CRYPTO_TOKEN_CONFIG = "/cryptoTokenConfig";

	public static final String API_CRYPTO_PORTFOLIO = "/cryptoPortfolio";

	public static final String API_CRYPTO_PORTFOLIO_HISTORY = "/cryptoPortfolioHistory";

	public static final String API_YOUTUBE_CHANNEL = "/youtubeChannel";

	public static final String API_YOUTUBE_VIDEO = "/youtubeVideo";

	public static final String API_YOUTUBE_PRIVATE_VIDEO = "/privateVideo";

	public static final String API_FOOD = "/food";

	public static final String API_ENGLISH_LEARN_RECORD = "/englishLearnRecord";

	public static final String API_ENGLISH_LEARN_REPORT = "/englishLearnReport";

	public static final String API_ENGLISH_USER_GRADE = "/englishUserGrade";

	public static final String API_USER_STATUS = "/status";

	public static final String API_ACTIVE_USER = "/activeUser";

	public static final String API_ENGLISH_DOWNLOAD_FILE_MP3 = "/downloadFileMp3";

	public static final String API_IMAGE = "/images";

	public static final String API_IMAGE_SYNC = "/image/sync";

	public static final String API_BITTREX_PAIR_EXTRACT = "/bittrex/extractionpairs";

	public static final String API_REPORT_SUMMARY = "/report/summary";

	public static final String API_QUANDL = "/quandl";

	public static final String API_QUANDL_OPEC_ORB = "/OPECORB";

	public static final String API_FINANCE = "/finance";

	public static final String API_CURRENCY_VCB_RATE = "/vietcombankRate";

	public static final String API_GOLD_SJC = "/goldSjc";

	public static final String API_TRADINGECONOMICS = "/tradingeconomics";

	public static final String API_TRADING_SYMBOL = "/tradingSymbol";

	public static final String API_TRADINGECONOMICS_PRICE_OIL = "/oilprice";

	public static final String API_TRADINGECONOMICS_PRICE_STOCK = "/stockprice";
}
