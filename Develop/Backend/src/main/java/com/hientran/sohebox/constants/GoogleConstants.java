package com.hientran.sohebox.constants;

import java.io.Serializable;

/**
 * 
 * Bittrex Constants
 *
 * @author hientran
 */
public class GoogleConstants implements Serializable {

    private static final long serialVersionUID = 1L;

    /////////////////
    // GOOGLE KEY //
    /////////////////
    public static final String GOOGLE_KEY_API = "GOOGLE_KEY_API";

    public static final String GOOGLE_API_CHANNEL_VIDEO_LATE_TIME_SECOND = "GOOGLE_API_CHANNEL_VIDEO_LATE_TIME_SECOND";

    /////////////////
    // SERVICE URL //
    /////////////////

    public static final String GOOGLE_API_URL = "https://www.googleapis.com";

    ///////////////////////
    // YOUTUBE CONSTANTS //
    ///////////////////////

    public static final String YOUTUBE_PARAM_KEY = "key";

    public static final String YOUTUBE_PARAM_CHANNEL_ID = "channelId";

    public static final String YOUTUBE_PARAM_ORDER = "order";

    public static final String YOUTUBE_PARAM_PART = "part";

    public static final String YOUTUBE_PARAM_TYPE = "type";

    public static final String YOUTUBE_PARAM_ID = "id";

    public static final String YOUTUBE_PARAM_MAX_RESULT = "maxResults";

    public static final String YOUTUBE_API_SEARCH_CHANNEL = "/youtube/v3/search";

    public static final String YOUTUBE_API_SEARCH_VIDEO = "/youtube/v3/videos";

    ///////////////////////
    // YOUTUBE DEFAUT VALUE //
    ///////////////////////

    public static final String YOUTUBE_DEFAUT_VALUE_DATE = "date";

    public static final String YOUTUBE_DEFAUT_VALUE_SNIPPET = "snippet";

    public static final String YOUTUBE_DEFAUT_VALUE_VIDEO = "video";
}
