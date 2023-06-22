package com.hientran.sohebox.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.EnglishLearnReportRepository;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.transformer.EnglishLearnReportTransformer;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.vo.EnglishLearnReportVO;
import com.hientran.sohebox.vo.PageResultVO;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class EnglishLearnReportService extends BaseService {

    @Autowired
    private EnglishLearnReportRepository englishLearnReportRepository;

    @Autowired
    private EnglishLearnReportTransformer englishLearnReportTransformer;

    @Autowired
    private UserService userService;

    /**
     * 
     * Count learn
     * 
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> add(EnglishLearnReportVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Get user
        UserTbl userTbl = userService.getTblByUserName(vo.getUser().getUsername());

        // Insert
        EnglishLearnReportTbl tbl = new EnglishLearnReportTbl();
        tbl.setUser(userTbl);
        tbl.setLearnedDate(vo.getLearnedDate());
        tbl.setLearnedTotal(vo.getLearnedTotal());
        tbl = englishLearnReportRepository.save(tbl);

        // Return
        return result;
    }

    /**
     * Search
     * 
     * @param sco
     * @return
     */
    public APIResponse<Object> search(EnglishLearnReportSCO sco) {
        // Declare result
        APIResponse<Object> result = new APIResponse<Object>();

        // Check data authentication
        result = isDataAuthentication(sco.getUserId().getEq().longValue());

        // Check authentication data
        if (result.getStatus() == null) {
            // Get data
            Page<EnglishLearnReportTbl> page = englishLearnReportRepository.findAll(sco);

            // Transformer
            PageResultVO<EnglishLearnReportVO> data = englishLearnReportTransformer.convertToPageReturn(page);

            // Set data return
            result.setData(data);
        }

        // Return
        return result;
    }

    /**
     * Daily fill English learn data
     *
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void fillDailyEnglishLearn() {
        // Get data
        List<Object[]> searchResult = englishLearnReportRepository.findDailyLearn(entityManager);

        // Insert
        if (CollectionUtils.isNotEmpty(searchResult)) {
            EnglishLearnReportTbl tbl;
            BigInteger userId;
            UserTbl user;
            BigDecimal learnedCount;
            Date learnedDate = MyDateUtils.addMinusDate(new Date(), -1);
            for (Object[] objects : searchResult) {

                // Get user
                userId = (BigInteger) objects[0];
                user = userService.getTblById(userId.longValue());

                // Get count total
                learnedCount = (BigDecimal) objects[1];

                // Insert
                tbl = new EnglishLearnReportTbl();
                tbl.setUser(user);
                tbl.setLearnedDate(learnedDate);
                tbl.setLearnedTotal(learnedCount.longValue());
                tbl = englishLearnReportRepository.save(tbl);
            }
        }
    }
}
