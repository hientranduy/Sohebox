package com.hientran.sohebox.constants;

import java.io.Serializable;

/**
 * 
 * Constants that belong to database
 *
 * @author hientran
 */
public class DBConstants implements Serializable {

    private static final long serialVersionUID = 1L;

    /////////
    // SQL //
    /////////
    public static final String WILDCARD = "%";

    public static final int LIKE_ANY_WHERE = 0;

    public static final int LIKE_START = 1;

    public static final int LIKE_END = 2;

    public static final String DIRECTION_DECCENT = "DESC";

    public static final String DIRECTION_ACCENT = "ASC";

    //////////
    // TYPE //
    //////////
    public static final String TYPE_CLASS_ACCOUNT = "ACCOUNT";

    public static final String TYPE_CLASS_USER_ACTIVITY = "USER_ACTIVITY";

    public static final String TYPE_CLASS_ENGLISH_WORD_LEVEL = "ENGLISH_WORD_LEVEL";

    public static final String TYPE_CLASS_ENGLISH_CATEGORY = "ENGLISH_CATEGORY";

    public static final String TYPE_CLASS_ENGLISH_VUS_GRADE = "ENGLISH_VUS_GRADE";

    public static final String TYPE_CLASS_ENGLISH_LEARN_DAY = "ENGLISH_LEARN_DAY";

    public static final String TYPE_CLASS_FOOD_TYPE = "FOOD_TYPE";

    public static final String TYPE_CLASS_FOOD_CATEGORY = "FOOD_CATEGORY";

    public static final String TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY = "YOUTUBE_CHANNEL_CATEGORY";

    public static final String TYPE_CODE_MEDIA_YOUTUBE_CHANNEL_CATEGORY_PERSONAL = "PERSONAL";

    public static final String TYPE_CLASS_REQUEST_EXTERNAL_TYPE = "REQUEST_EXTERNAL_TYPE";

    public static final String TYPE_CLASS_TRADING_SYMBOL_TYPE = "TRADING_SYMBOL_TYPE";

    public static final String TYPE_CODE_TRADING_SYMBOL_STOCK = "STOCK";

    public static final String TYPE_CLASS_TRADING_SYMBOL_ZONE = "TRADING_SYMBOL_ZONE";

    public static final String TYPE_CODE_TRADING_SYMBOL_ZONE_AMERICA = "AMERICA";

    public static final String TYPE_CODE_TRADING_SYMBOL_ZONE_EU = "EU";

    public static final String TYPE_CODE_TRADING_SYMBOL_ZONE_ASIA = "ASIA";

    public static final String TYPE_CODE_TRADING_SYMBOL_ZONE_AFRICA = "AFRICA";

    //////////
    // USER //
    //////////
    public static final boolean USER_INACTIVE = true;

    public static final String USER_ROLE_USER = "user";

    public static final String USER_ROLE_CREATOR = "creator";

    public static final String USER_DEFAULT_PASSWORD = "NA";

    public static final String USER_DEFAULT_AVATAR = "defaut.png";

    public static final String USER_ACTIVITY_LOGIN = "login";

    public static final String USER_ACTIVITY_LOGOUT = "logout";

    public static final String USER_ACTIVITY_CHANGE_PASSWORD = "change password";

    public static final String USER_ACTIVITY_CHANGE_PASSWORD_WHEN_LOGIN = "change password when login";

    public static final String USER_ACTIVITY_DELETE = "delete";

    public static final String USER_ACTIVITY_UPDATE_INFO = "update info";

    public static final String USER_ACTIVITY_ACCOUNT_ACCESS = "account access";

    public static final String USER_ACTIVITY_ACCOUNT_CREATE = "account create";

    public static final String USER_ACTIVITY_ACCOUNT_UPDATE = "account update";

    public static final String USER_ACTIVITY_ACCOUNT_DELETE = "account delete";

    public static final String USER_ACTIVITY_ENGLISH_ACCESS = "english access";

    public static final String USER_ACTIVITY_ENGLISH_CREATE = "english create";

    public static final String USER_ACTIVITY_ENGLISH_UPDATE = "english update";

    public static final String USER_ACTIVITY_FOOD_ACCESS = "food access";

    public static final String USER_ACTIVITY_FOOD_CREATE = "food create";

    public static final String USER_ACTIVITY_FOOD_UPDATE = "food update";
    
    public static final String USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_CREATE = "token create";

    public static final String USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_UPDATE = "token update";
    
    public static final String USER_ACTIVITY_CRYPTO_TOKEN_CONFIG_ACCESS = "token access";
    
    public static final String USER_ACTIVITY_CRYPTO_PORTFOLIO_CREATE = "portfolio create";

    public static final String USER_ACTIVITY_CRYPTO_PORTFOLIO_UPDATE = "portfolio update";
    
    public static final String USER_ACTIVITY_CRYPTO_PORTFOLIO_ACCESS = "portfolio access";
    
    public static final String USER_ACTIVITY_CRYPTO_PORTFOLIO_DELETE = "portfolio delete";

    public static final String USER_ACTIVITY_MEDIA_YOUTUBE_CHANNEL_ACCESS = "media youtube channel access";

    /////////////
    // ACCOUNT //
    /////////////
    public static final String ACCOUNT_TYPE_DEFAUT_ICON = "ic_defaut.jpg";

    /////////////
    // ENGLISH /0/
    /////////////
    public static final String ENGLISH_LEVEL_1 = "1";

    public static final String ENGLISH_LEVEL_2 = "2";

    public static final String ENGLISH_LEVEL_3 = "3";

    public static final String ENGLISH_LEVEL_4 = "4";

    public static final String ENGLISH_LEVEL_5 = "5";

    ///////////
    // OTHER //
    ///////////

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String REQUEST_EXTERNAL_TYPE_IMG_CHART = "IMAGE CHART";

    public static final String REQUEST_EXTERNAL_TYPE_DATA = "DATA";

}
