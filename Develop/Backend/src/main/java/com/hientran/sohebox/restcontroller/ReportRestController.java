package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.service.ReportService;
import com.hientran.sohebox.vo.ReportSummaryVO;

/**
 * @author hientran
 */
@RestController
public class ReportRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ReportService reportService;

    /**
     * 
     * Create summary report
     *
     * @param vo
     * @return
     */
    @PostMapping(ApiPublicConstants.API_REPORT_SUMMARY)
    public ResponseEntity<?> createListSummaryReport(@Validated @RequestBody ReportSummaryVO vo) {
        reportService.createListSummaryReport(vo);
        return ResponseEntity.ok().build();
    }

}
