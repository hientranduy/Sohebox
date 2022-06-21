package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.EnglishLearnRecordTblEnum;
import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.EnglishLearnRecordRepository;
import com.hientran.sohebox.sco.EnglishLearnRecordSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.transformer.EnglishLearnRecordTransformer;
import com.hientran.sohebox.vo.EnglishLearnRecordVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class EnglishLearnRecordService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EnglishLearnRecordRepository englishLearnRecordRepository;

    @Autowired
    private EnglishLearnRecordTransformer englishLearnRecordTransformer;

    @Autowired
    private UserService userService;

    @Autowired
    private EnglishService englishService;

    /**
     * 
     * Count learn
     * 
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> addLearn(EnglishLearnRecordVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // user must not null
            if (vo.getUser() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { EnglishLearnRecordTblEnum.user.name() }));
            }

            // english must not null
            if (vo.getEnglish() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { EnglishLearnRecordTblEnum.english.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Check if user is existed
        UserTbl userTbl = userService.getTblByUserName(vo.getUser().getUsername());
        if (result.getStatus() == null && userTbl == null) {
            result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_USERNAME, new String[] { vo.getUser().getUsername() }));
        }

        // Check if english word is existed
        EnglishTbl englishTbl = englishService.getByKey(vo.getEnglish().getKeyWord());
        if (result.getStatus() == null && englishTbl == null) {
            result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_RECORD, new String[] { vo.getEnglish().getKeyWord() }));
        }

        // Get the old record
        EnglishLearnRecordTbl tbl = null;
        if (result.getStatus() == null) {
            tbl = getByKey(userTbl.getId(), englishTbl.getId());
        }

        // PROCESS INSERT/UPDATE
        if (result.getStatus() == null) {
            // Update
            if (tbl != null) {
                Long newRecord = tbl.getRecordTimes() + 1;
                tbl.setRecordTimes(newRecord);

                if (DateUtils.isSameDay(tbl.getUpdatedDate(), new Date())) {
                    tbl.setLearnedToday(tbl.getLearnedToday() + 1);
                } else {
                    tbl.setLearnedToday(new Long(1));
                }

                tbl = englishLearnRecordRepository.save(tbl);

            } else {
                // Insert new
                tbl = new EnglishLearnRecordTbl();
                tbl.setUser(userTbl);
                tbl.setEnglish(englishTbl);
                tbl.setRecordTimes(new Long(1));
                tbl.setLearnedToday(new Long(1));
                tbl = englishLearnRecordRepository.save(tbl);
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get record by key
     *
     * @param userId
     * @param englishId
     * @return
     */
    private EnglishLearnRecordTbl getByKey(Long userId, Long englishId) {
        // Declare result
        EnglishLearnRecordTbl result = null;

        // Prepare search
        SearchNumberVO userIdSearch = new SearchNumberVO();
        userIdSearch.setEq(userId.doubleValue());

        SearchNumberVO englishIdSearch = new SearchNumberVO();
        englishIdSearch.setEq(englishId.doubleValue());

        EnglishLearnRecordSCO sco = new EnglishLearnRecordSCO();
        sco.setUserId(userIdSearch);
        sco.setEnglishId(englishIdSearch);

        // Get data
        List<EnglishLearnRecordTbl> list = englishLearnRecordRepository.findAll(sco).getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
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
    public APIResponse<Object> search(EnglishLearnRecordSCO sco) {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Check data authentication
        result = isDataAuthentication(sco.getUserId().getEq().longValue());

        // Check authentication data
        if (result.getStatus() == null) {
            // Get data
            Page<EnglishLearnRecordTbl> page = englishLearnRecordRepository.findAll(sco);

            // Transformer
            PageResultVO<EnglishLearnRecordVO> data = englishLearnRecordTransformer.convertToPageReturn(page);

            // Set data return
            result.setData(data);
        }

        // Return
        return result;
    }
}
