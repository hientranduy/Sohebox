package com.hientran.sohebox.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ReportConstants;
import com.hientran.sohebox.constants.enums.BittrexMarketSummary24hTblEnum;
import com.hientran.sohebox.entity.BittrexMarketSummary24hTbl;
import com.hientran.sohebox.repository.BittrexMarketSummary24hRepository;
import com.hientran.sohebox.sco.BittrexMarketSummary24hSCO;
import com.hientran.sohebox.sco.SearchDateVO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.utils.LogUtils;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.vo.ReportSummaryItemVO;
import com.hientran.sohebox.vo.ReportSummaryVO;
import com.hientran.sohebox.vo.SendEmailContentVO;

/**
 * @author hientran
 */
@Service
public class ReportService extends BaseService {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${report.return.max.record}")
    private int reportReturnMaxRecord;

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${report.path.output}")
    private String reportOutputPath;

    @Autowired
    private BittrexMarketSummary24hRepository bittrexMarketSummary24hRepository;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private ConfigCache configCache;

    /**
     * 
     * Create list of summary report by pairs
     *
     * @param vo
     */
    public void createListSummaryReport(ReportSummaryVO vo) {
        if (CollectionUtils.isNotEmpty(vo.getReportItems())) {
            for (ReportSummaryItemVO reportSummaryVO : vo.getReportItems()) {
                try {
                    createSummaryReport(reportSummaryVO, vo.getSendMailFalg());
                } catch (Exception e) {
                    LogUtils.writeLogError(e);
                }
            }
        }
    }

    /**
     * 
     * Create summary report
     *
     * @param vo
     * @param sendMailFlag
     * @throws Exception
     */
    private void createSummaryReport(ReportSummaryItemVO vo, boolean sendMailFlag) throws Exception {
        // Get report Data
        SearchTextVO marketName = new SearchTextVO();
        marketName.setEq(vo.getPair());

        SearchDateVO timeStamp = new SearchDateVO();
        if (vo.getFromDate() != null) {
            timeStamp.setGe(MyDateUtils.getDateMin(vo.getFromDate()));
        }
        if (vo.getToDate() != null) {
            timeStamp.setLe(MyDateUtils.getDateMax(vo.getToDate()));
        }

        List<Sorter> sorters = new ArrayList<>();
        sorters.add(new Sorter(BittrexMarketSummary24hTblEnum.marketName.name(), DBConstants.DIRECTION_ACCENT));
        sorters.add(new Sorter(BittrexMarketSummary24hTblEnum.timeStamp.name(), DBConstants.DIRECTION_ACCENT));

        BittrexMarketSummary24hSCO sco = new BittrexMarketSummary24hSCO();
        sco.setMarketName(marketName);
        sco.setTimeStamp(timeStamp);
        sco.setMaxRecordPerPage(reportReturnMaxRecord);
        sco.setReportFlag(true);
        sco.setSorters(sorters);
        Page<BittrexMarketSummary24hTbl> reportData = bittrexMarketSummary24hRepository.findAll(sco);
        int sizeData = reportData.getContent().size();

        if (CollectionUtils.isNotEmpty(reportData.getContent())) {
            List<BittrexMarketSummary24hTbl> listReportRecord = new ArrayList<>();
            // Select data to show report
            if (sizeData > ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE) {
                double divNum = (double) sizeData / (double) ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE;
                int lowSize = (int) divNum;
                int numberUpSize = (int) Math.round((divNum - lowSize) * ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE);
                int numberKeepSize = ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE - numberUpSize;

                // Add first record
                int index = 0;
                listReportRecord.add(reportData.getContent().get(index));

                // Add record min size.
                if (numberKeepSize > 1) {
                    for (int i = 0; i < numberKeepSize - 1; i++) {
                        index = index + lowSize;
                        listReportRecord.add(reportData.getContent().get(index));
                    }
                }

                // Add record max size
                if (numberUpSize > 1) {
                    for (int i = 0; i < numberUpSize - 1; i++) {
                        index = index + lowSize + 1;
                        listReportRecord.add(reportData.getContent().get(index));
                    }

                    // Add last record
                    index = sizeData - 1;
                    listReportRecord.add(reportData.getContent().get(index));
                }
            } else {
                BittrexMarketSummary24hTbl latestTbl = new BittrexMarketSummary24hTbl();
                for (BittrexMarketSummary24hTbl item : reportData.getContent()) {
                    listReportRecord.add(item);
                    latestTbl = item;
                }

                // Fill missing record to enough max node
                for (int i = 0; i < ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE - sizeData; i++) {
                    listReportRecord.add(latestTbl);
                }
            }

            // Create excel file
            File reportFile = null;
            if (CollectionUtils.isNotEmpty(listReportRecord)) {
                reportFile = createExcelFileSummaryReport(listReportRecord, vo.getPair());
            }

            // Send mail
            if (sendMailFlag && reportFile != null) {
                if (reportFile != null) {
                    SendEmailContentVO mailContent = new SendEmailContentVO();
                    String[] listMailTo = configCache.getValueByKey(ReportConstants.REPORT_SUMMARY_MAIL_TO).split(",");

                    Map<String, String> templateParams = new HashMap<String, String>();
                    templateParams.put("requestName", "Hien");

                    List<String> attachments = new ArrayList<>();
                    attachments.add(reportFile.toString());

                    mailContent.setListMailTo(listMailTo);
                    mailContent.setSubject(configCache.getValueByKey(ReportConstants.REPORT_SUMMARY_MAIL_SUBJECT));
                    mailContent.setParameters(templateParams);
                    mailContent.setPathToAttachments(attachments);
                    mailContent.setTemplatePath(ReportConstants.REPORT_PATH_MAIL_TEMPLATE
                            + ReportConstants.REPORT_SUMMARY_MAIL_TEMPLATE_NAME);

                    try {
                        sendMailService.sendMail(mailContent);
                    } catch (JsonProcessingException | MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 
     * Create excel file summary report
     * 
     * @param pair
     *
     * @param listReportRecord
     * @return
     * @throws Exception
     */
    private File createExcelFileSummaryReport(List<BittrexMarketSummary24hTbl> tbls, String pair) throws Exception {
        // Declare result
        File result = null;

        // Get report template path
        String filePath = resourcePath + ReportConstants.REPORT_PATH_EXCEL_TEMPLATE
                + ReportConstants.REPORT_SUMMARY_EXCEL_TEMPLATE_NAME;

        // Create workbook
        FileInputStream file = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        file.close();

        // Fill sheet data
        if (CollectionUtils.isNotEmpty(tbls)) {
            // Clone template sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            workbook.setSheetName(workbook.getSheetIndex(sheet),
                    configCache.getValueByKey(ReportConstants.REPORT_SUMMARY_EXCEL_SHEET_NAME));

            XSSFRow row;

            // Fill data header
            row = sheet.getRow(0);
            row.getCell(0).setCellValue(pair);
            row.getCell(12).setCellValue(pair);
            row.getCell(25).setCellValue(pair);

            // Fill sheet data
            int rowIndex = ReportConstants.REPORT_SUMMARY_EXCEL_START_ROW;
            BittrexMarketSummary24hTbl latestRowData = null;
            for (BittrexMarketSummary24hTbl rowData : tbls) {
                latestRowData = rowData;

                // Get row
                row = sheet.getRow(rowIndex);

                // Fill row data
                int columnIndex = ReportConstants.REPORT_SUMMARY_EXCEL_START_COLUMN;
                row.getCell(columnIndex++).setCellValue(rowData.getMarketName());
                row.getCell(columnIndex++).setCellValue(rowData.getTimeStamp());
                row.getCell(columnIndex++).setCellValue(rowData.getOpenBuyOrders());
                row.getCell(columnIndex++).setCellValue(rowData.getOpenSellOrders());
                row.getCell(columnIndex++).setCellValue(rowData.getLast().doubleValue());
                row.getCell(columnIndex++).setCellValue(rowData.getVolume().doubleValue());

                // Next row
                rowIndex = rowIndex + 1;
            }

            // Fill the rest rows with latest record
            if (latestRowData != null) {
                for (int i = rowIndex + 1; i < ReportConstants.REPORT_SUMMARY_EXCEL_MAX_NODE + 2; i++) {
                    row = sheet.getRow(i);
                    int columnIndex = ReportConstants.REPORT_SUMMARY_EXCEL_START_COLUMN;
                    row.getCell(columnIndex++).setCellValue(latestRowData.getMarketName());
                    row.getCell(columnIndex++).setCellValue(latestRowData.getTimeStamp());
                    row.getCell(columnIndex++).setCellValue(latestRowData.getOpenBuyOrders());
                    row.getCell(columnIndex++).setCellValue(latestRowData.getOpenSellOrders());
                    row.getCell(columnIndex++).setCellValue(latestRowData.getLast().doubleValue());
                    row.getCell(columnIndex++).setCellValue(latestRowData.getVolume().doubleValue());
                }
            }

            // Get file name
            String fileName = String.format("%s_%s_%s.xlsx",
                    configCache.getValueByKey(ReportConstants.REPORT_SUMMARY_EXCEL_FILE_NAME), pair,
                    MyDateUtils.formatDate(new Date(), MyDateUtils.REPORT_DATE_FORMAT));
            logger.info("File is created: " + fileName);

            // Create path file
            Path resultFilePath = Paths.get(reportOutputPath, fileName);
            Files.createDirectories(resultFilePath.getParent());
            Files.deleteIfExists(resultFilePath);
            result = new File(resultFilePath.toString());

            // Write to file
            FileOutputStream outputFile = new FileOutputStream(result);
            workbook.write(outputFile);
            outputFile.flush();
            outputFile.close();
        }

        // Close workbook
        workbook.close();

        // Return
        return result;
    }

}
