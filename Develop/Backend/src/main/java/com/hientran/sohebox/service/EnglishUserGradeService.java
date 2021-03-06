package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.EnglishTypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.EnglishUserGradeTblEnum;
import com.hientran.sohebox.entity.EnglishUserGradeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.EnglishUserGradeRepository;
import com.hientran.sohebox.sco.EnglishUserGradeSCO;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.transformer.EnglishTypeTransformer;
import com.hientran.sohebox.transformer.EnglishUserGradeTransformer;
import com.hientran.sohebox.vo.EnglishTypeVO;
import com.hientran.sohebox.vo.EnglishUserGradeVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class EnglishUserGradeService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EnglishUserGradeRepository EnglishUserGradeRepository;

    @Autowired
    private EnglishUserGradeTransformer EnglishUserGradeTransformer;

    @Autowired
    private UserService userService;

    @Autowired
    private EnglishTypeTransformer englishTypeTransformer;

    @Autowired
    private EnglishTypeCache englishTypeCache;

    /**
     * 
     * Set english user grade
     * 
     * @param vo
     * @return id
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> setEnglishUserGrade(EnglishUserGradeVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate empty input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // user must not null
            if (vo.getUser() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { EnglishUserGradeTblEnum.user.name() }));
            }

            // grade must not null
            if (vo.getVusGrade() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { EnglishUserGradeTblEnum.vusGrade.name() }));
            }

            // learn day must not null
            if (vo.getLearnDay() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { EnglishUserGradeTblEnum.learnDay.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Validate in-existed input user
        UserTbl userTbl = userService.getTblByUserName(vo.getUser().getUsername());
        if (result.getStatus() == null && userTbl == null) {
            result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.INEXISTED_USERNAME, new String[] { vo.getUser().getUsername() }));
        }

        // Check if logged user is the same input user
        if (result.getStatus() == null && !userService.isDataOwner(vo.getUser().getUsername())) {
            result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
                    buildMessage(MessageConstants.UNAUTHORIZED_DATA, null));
        }

        // PROCESS INSERT/UPDATE
        if (result.getStatus() == null) {
            EnglishUserGradeTbl tbl = getByKey(userTbl.getId());

            // Get grade
            EnglishTypeVO vusGrade = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_VUS_GRADE,
                    vo.getVusGrade().getTypeCode());

            // Get learn day
            EnglishTypeVO learnDay = englishTypeCache.getType(DBConstants.TYPE_CLASS_ENGLISH_LEARN_DAY,
                    vo.getLearnDay().getTypeCode());

            if (tbl == null) {
                tbl = new EnglishUserGradeTbl();
                tbl.setUser(userTbl);
                tbl.setVusGrade(englishTypeTransformer.convertToEnglishTypeTbl(vusGrade));
                tbl.setLearnDay(englishTypeTransformer.convertToEnglishTypeTbl(learnDay));
                tbl = EnglishUserGradeRepository.save(tbl);
            } else {
                tbl.setVusGrade(englishTypeTransformer.convertToEnglishTypeTbl(vusGrade));
                tbl.setLearnDay(englishTypeTransformer.convertToEnglishTypeTbl(learnDay));
                tbl = EnglishUserGradeRepository.save(tbl);
            }
        }

        // Return
        return result;
    }

    /**
     * 
     * Get record by key
     *
     * @param table
     *            key
     * @return table data
     */
    private EnglishUserGradeTbl getByKey(Long userId) {
        // Declare result
        EnglishUserGradeTbl result = null;

        // Prepare search
        SearchNumberVO userIdSearch = new SearchNumberVO();
        userIdSearch.setEq(userId.doubleValue());

        EnglishUserGradeSCO sco = new EnglishUserGradeSCO();
        sco.setUserId(userIdSearch);

        // Get data
        List<EnglishUserGradeTbl> list = EnglishUserGradeRepository.findAll(sco).getContent();
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
    public APIResponse<Object> search(EnglishUserGradeSCO sco) {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Get data
        Page<EnglishUserGradeTbl> page = EnglishUserGradeRepository.findAll(sco);

        // Transformer
        PageResultVO<EnglishUserGradeVO> data = EnglishUserGradeTransformer.convertToPageReturn(page);

        // Set data return
        result.setData(data);

        // Return
        return result;
    }
}
