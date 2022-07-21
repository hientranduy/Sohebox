package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.cache.MediaTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.GoogleConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.YoutubeChannelTblEnum;
import com.hientran.sohebox.constants.enums.YoutubeVideoTblEnum;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.entity.YoutubeChannelTbl;
import com.hientran.sohebox.entity.YoutubeChannelVideoTbl;
import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.YoutubeChannelRepository;
import com.hientran.sohebox.repository.YoutubeChannelVideoRepository;
import com.hientran.sohebox.repository.YoutubeVideoRepository;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.YoutubeChannelSCO;
import com.hientran.sohebox.sco.YoutubeChannelVideoSCO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.transformer.MediaTypeTransformer;
import com.hientran.sohebox.utils.ObjectMapperUtil;
import com.hientran.sohebox.vo.MediaTypeVO;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.YoutubeReponseVO;
import com.hientran.sohebox.vo.YoutubeResponseVideoVO;
import com.hientran.sohebox.vo.YoutubeVideoIdVO;
import com.hientran.sohebox.vo.YoutubeVideoSendVO;
import com.hientran.sohebox.webservice.YoutubeWebService;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class YoutubeChannelVideoService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private YoutubeChannelVideoRepository youtubeChannelVideoRepository;

    @Autowired
    private YoutubeChannelRepository youtubeChannelRepository;

    @Autowired
    private YoutubeVideoService youtubeVideoService;

    @Autowired
    private YoutubeChannelService youtubeChannelService;

    @Autowired
    private YoutubeVideoRepository youtubeVideoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MediaTypeCache mediaTypeCache;

    @Autowired
    private ConfigCache configCache;

    @Autowired
    private MediaTypeTransformer mediaTypeTransformer;

    @Autowired
    private YoutubeWebService youtubeWebService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    /**
     * Search
     * 
     * @param sco
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public List<YoutubeChannelVideoTbl> search(YoutubeChannelVideoSCO sco) {
        // Declare result
        List<YoutubeChannelVideoTbl> result = new ArrayList<>();

        // Get data
        Page<YoutubeChannelVideoTbl> page = youtubeChannelVideoRepository.findAll(sco);

        // Transformer
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            result = page.getContent();
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
    public YoutubeChannelVideoTbl getChannelVideo(Long channelId, Long videoID) {
        // Declare result
        YoutubeChannelVideoTbl result = null;

        // Get data
        SearchNumberVO channelSearch = new SearchNumberVO();
        channelSearch.setEq(channelId.doubleValue());
        SearchNumberVO videoSearch = new SearchNumberVO();
        videoSearch.setEq(videoID.doubleValue());

        YoutubeChannelVideoSCO sco = new YoutubeChannelVideoSCO();
        sco.setChannelId(channelSearch);
        sco.setVideoId(videoSearch);
        Page<YoutubeChannelVideoTbl> page = youtubeChannelVideoRepository.findAll(sco);

        // Transformer
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            result = new YoutubeChannelVideoTbl();
            result = page.getContent().get(0);
        }

        // Return
        return result;
    }

    /**
     * Get private video
     *
     * @return
     */
    public APIResponse<?> getPrivateVideo() {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Get login user
        UserTbl loggedUser = userService.getCurrentLoginUser();

        // Get user channel
        YoutubeChannelTbl userChannel = null;
        if (loggedUser != null) {
            SearchNumberVO userIdSearch = new SearchNumberVO();
            userIdSearch.setEq(loggedUser.getId().doubleValue());
            YoutubeChannelSCO channelSCO = new YoutubeChannelSCO();
            channelSCO.setUserId(userIdSearch);
            List<YoutubeChannelTbl> userChannels = youtubeChannelRepository.findAll(channelSCO).getContent();

            if (CollectionUtils.isNotEmpty(userChannels)) {
                userChannel = userChannels.get(0);
            }
        }

        // Search personal video
        if (userChannel != null) {
            SearchNumberVO channelSearch = new SearchNumberVO();
            channelSearch.setEq(userChannel.getId().doubleValue());

            YoutubeChannelVideoSCO sco = new YoutubeChannelVideoSCO();
            sco.setChannelId(channelSearch);
            sco.setMaxRecordPerPage(100);
            sco.setDeleteFlag(false);
            List<YoutubeChannelVideoTbl> videoList = search(sco);

            if (CollectionUtils.isNotEmpty(videoList)) {
                List<YoutubeVideoSendVO> videoSends = new ArrayList<YoutubeVideoSendVO>();
                for (YoutubeChannelVideoTbl video : videoList) {
                    YoutubeVideoSendVO videoSend = new YoutubeVideoSendVO();
                    videoSend.setVideoId(video.getVideo().getVideoId());
                    videoSend.setPublishedAt(video.getVideo().getPublishedAt().toString());
                    videoSend.setTitle(video.getVideo().getTitle());
                    videoSend.setDescription(video.getVideo().getDescription());
                    videoSend.setThumbnailMediumtUrl(video.getVideo().getUrlThumbnail());
                    videoSend.setThumbnailHighUrl(video.getVideo().getUrlThumbnail());
                    videoSends.add(videoSend);
                }

                // Set data return
                PageResultVO<YoutubeVideoSendVO> data = new PageResultVO<YoutubeVideoSendVO>();
                data.setElements(videoSends);
                data.setCurrentPage(0);
                data.setTotalPage(1);
                data.setTotalElement(videoSends.size());
                result.setData(data);
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Merge channel video
     * 
     * @param vo
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<YoutubeChannelVideoTbl> mergeChannelVideo(YoutubeChannelTbl channelTbl,
            YoutubeVideoTbl videoTbl) {
        // Declare result
        APIResponse<YoutubeChannelVideoTbl> result = new APIResponse<YoutubeChannelVideoTbl>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Channel id must not null
            if (channelTbl == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { YoutubeChannelTblEnum.channelId.name() }));
            }

            // Video id must not null
            if (videoTbl == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { YoutubeVideoTblEnum.videoId.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<YoutubeChannelVideoTbl>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        /////////////////////
        // Read old record //
        /////////////////////
        if (result.getStatus() == null) {
            // Transform
            YoutubeChannelVideoTbl tbl = getChannelVideo(channelTbl.getId(), videoTbl.getId());

            // Set data
            if (tbl == null) {
                tbl = new YoutubeChannelVideoTbl();
                tbl.setChannel(channelTbl);
                tbl.setVideo(videoTbl);
                tbl.setDeleteFlag(false);

                // Save
                tbl = youtubeChannelVideoRepository.save(tbl);

                // Set id return
                result.setData(tbl);
            }
        }

        // Return
        return result;
    }

    /**
     * Add personal video
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<?> addPrivateVideo(@Validated
    YoutubeVideoIdVO vo) {
        // Declare result
        APIResponse<YoutubeChannelVideoTbl> result = new APIResponse<YoutubeChannelVideoTbl>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Video id must not null
            if (vo.getVideoId() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { YoutubeVideoTblEnum.videoId.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<YoutubeChannelVideoTbl>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Get login user
        UserTbl loggedUser = userService.getCurrentLoginUser();

        // Get private channel
        YoutubeChannelTbl privateChannel = null;
        if (result.getStatus() == null) {
            SearchNumberVO userIdSearch = new SearchNumberVO();
            userIdSearch.setEq(loggedUser.getId().doubleValue());
            YoutubeChannelSCO channelSCO = new YoutubeChannelSCO();
            channelSCO.setUserId(userIdSearch);
            List<YoutubeChannelTbl> userChannels = youtubeChannelRepository.findAll(channelSCO).getContent();
            if (CollectionUtils.isNotEmpty(userChannels)) {
                privateChannel = userChannels.get(0);
            } else {
                privateChannel = new YoutubeChannelTbl();
                privateChannel.setUser(loggedUser);
                privateChannel.setChannelId(loggedUser.getUsername());
                privateChannel.setName(loggedUser.getFirstName() + " " + loggedUser.getLastName());
                MediaTypeVO category = mediaTypeCache.getType(DBConstants.TYPE_CLASS_MEDIA_YOUTUBE_CHANNEL_CATEGORY,
                        DBConstants.TYPE_CODE_MEDIA_YOUTUBE_CHANNEL_CATEGORY_PERSONAL);
                privateChannel.setCategory(mediaTypeTransformer.convertToMediaTypeTbl(category));

                privateChannel = youtubeChannelRepository.save(privateChannel);
            }
        }

        // Get video
        YoutubeVideoTbl videoTbl = null;
        if (result.getStatus() == null) {
            videoTbl = youtubeVideoService.getByVideoId(vo.getVideoId());

            if (videoTbl == null) {
                // Call Youtube API
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put(GoogleConstants.YOUTUBE_PARAM_KEY,
                        configCache.getValueByKey(GoogleConstants.GOOGLE_KEY_API));
                parameters.put(GoogleConstants.YOUTUBE_PARAM_PART, GoogleConstants.YOUTUBE_DEFAUT_VALUE_SNIPPET);
                parameters.put(GoogleConstants.YOUTUBE_PARAM_ID, vo.getVideoId());
                try {
                    String responseData = youtubeWebService.get(GoogleConstants.YOUTUBE_API_SEARCH_VIDEO, parameters);
                    YoutubeReponseVO response = objectMapperUtil.readValue(responseData, YoutubeReponseVO.class);

                    if (response.getItems() != null) {
                        List<YoutubeResponseVideoVO> youtubeVideos = new ArrayList<YoutubeResponseVideoVO>();
                        youtubeVideos = objectMapperUtil.readValue(response.getItems(),
                                new TypeReference<List<YoutubeResponseVideoVO>>() {
                                });
                        if (CollectionUtils.isNotEmpty(youtubeVideos)) {
                            YoutubeResponseVideoVO video = youtubeVideos.get(0);
                            videoTbl = new YoutubeVideoTbl();
                            videoTbl.setVideoId(vo.getVideoId());
                            videoTbl.setTitle(video.getSnippet().getTitle());
                            videoTbl.setUrlThumbnail(video.getSnippet().getThumbnails().getMedium().getUrl());
                            videoTbl.setPublishedAt(video.getSnippet().getPublishedAt());
                            videoTbl = youtubeVideoRepository.save(videoTbl);
                        } else {
                            result = new APIResponse<YoutubeChannelVideoTbl>(HttpStatus.BAD_REQUEST,
                                    buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "youtube video" }));
                        }

                    } else {
                        result = new APIResponse<YoutubeChannelVideoTbl>(HttpStatus.BAD_REQUEST,
                                buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { "youtube video" }));

                    }
                } catch (Exception e) {
                    result = new APIResponse<YoutubeChannelVideoTbl>(HttpStatus.BAD_REQUEST,
                            buildMessage(MessageConstants.ERROR_EXCEPTION, new String[] { e.getMessage() }));
                }
            }
        }

        // Add video channel
        if (result.getStatus() == null) {
            APIResponse<YoutubeChannelVideoTbl> youtubeChannelVideoTbl = mergeChannelVideo(privateChannel, videoTbl);
            result.setData(youtubeChannelVideoTbl.getData());
        }

        // Return
        return result;
    }

    /**
     * Delete video by videoId
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<?> removeVideo(String videoId) {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Video id must not null
            if (videoId == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { YoutubeVideoTblEnum.videoId.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        if (result.getStatus() == null) {
            // Get login user
            UserTbl loggedUser = userService.getCurrentLoginUser();

            // Get video and private channel
            YoutubeVideoTbl videoTbl = youtubeVideoService.getByVideoId(videoId);
            YoutubeChannelTbl channelTbl = youtubeChannelService.getByKey(loggedUser.getUsername());

            // Get channel video
            YoutubeChannelVideoTbl tbl = getChannelVideo(channelTbl.getId(), videoTbl.getId());

            // Remove video
            if (tbl != null) {
                tbl.setDeleteFlag(true);
                youtubeChannelVideoRepository.save(tbl);
            }
        }

        // Return
        return result;
    }

}
