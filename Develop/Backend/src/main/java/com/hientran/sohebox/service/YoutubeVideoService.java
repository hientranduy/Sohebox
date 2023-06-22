package com.hientran.sohebox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.YoutubeVideoTblEnum;
import com.hientran.sohebox.entity.YoutubeVideoTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.YoutubeVideoRepository;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.YoutubeVideoSCO;
import com.hientran.sohebox.utils.MessageUtil;
import com.hientran.sohebox.vo.YoutubeVideoVO;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class YoutubeVideoService extends BaseService {

    @Autowired
    private YoutubeVideoRepository youtubeVideoRepository;

    /**
     * Search
     * 
     * @param sco
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public List<YoutubeVideoTbl> search(YoutubeVideoSCO sco) {
        // Declare result
        List<YoutubeVideoTbl> result = new ArrayList<>();

        // Get data
        Page<YoutubeVideoTbl> page = youtubeVideoRepository.findAll(sco);

        // Transformer
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            result = page.getContent();
        }

        // Return
        return result;
    }

    /**
     * 
     * Add video if not exist
     * 
     * @param vo
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<YoutubeVideoTbl> mergeVideo(YoutubeVideoVO vo) {
        // Declare result
        APIResponse<YoutubeVideoTbl> result = new APIResponse<YoutubeVideoTbl>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // Video id must not null
            if (vo.getId().getVideoId() == null) {
                errors.add(MessageUtil.buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { YoutubeVideoTblEnum.videoId.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<YoutubeVideoTbl>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        /////////////////////
        // Read old record //
        /////////////////////
        if (result.getStatus() == null) {
            // Transform
            YoutubeVideoTbl tbl = getByVideoId(vo.getId().getVideoId());

            // Set data
            if (tbl == null) {
                tbl = new YoutubeVideoTbl();
                tbl.setVideoId(vo.getId().getVideoId());
            }

            tbl.setTitle(vo.getSnippet().getTitle().replaceAll("[^\\p{ASCII}]", ""));
            tbl.setDescription(vo.getSnippet().getDescription().replaceAll("[^\\p{ASCII}]", ""));
            tbl.setUrlThumbnail(vo.getSnippet().getThumbnails().getMedium().getUrl());
            tbl.setPublishedAt(vo.getSnippet().getPublishedAt());

            // Save
            tbl = youtubeVideoRepository.save(tbl);

            // Set id return
            result.setData(tbl);
        }

        // Return
        return result;
    }

    /**
     * 
     * Get video by id
     *
     */
    public YoutubeVideoTbl getByVideoId(String videoId) {
        // Declare result
        YoutubeVideoTbl result = null;

        SearchTextVO videoSearch = new SearchTextVO();
        videoSearch.setEq(videoId);

        YoutubeVideoSCO sco = new YoutubeVideoSCO();
        sco.setVideoId(videoSearch);

        // Get data
        List<YoutubeVideoTbl> list = youtubeVideoRepository.findAll(sco).getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }

        // Return
        return result;
    }

}
