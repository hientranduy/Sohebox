package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.DownloadFileVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.EnglishLearnRecordTbl;
import com.hientran.sohebox.entity.EnglishTbl;
import com.hientran.sohebox.entity.EnglishUserGradeTbl;
import com.hientran.sohebox.sco.EnglishLearnRecordSCO;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;
import com.hientran.sohebox.sco.EnglishSCO;
import com.hientran.sohebox.sco.EnglishUserGradeSCO;
import com.hientran.sohebox.service.EnglishLearnRecordService;
import com.hientran.sohebox.service.EnglishLearnReportService;
import com.hientran.sohebox.service.EnglishService;
import com.hientran.sohebox.service.EnglishUserGradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EnglishRestController extends BaseRestController {

	private final EnglishService englishService;
	private final EnglishLearnRecordService englishLearnRecordService;
	private final EnglishLearnReportService englishLearnReportService;
	private final EnglishUserGradeService englishUserGradeService;

	/**
	 *
	 * Add learn
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_LEARN_RECORD + ApiPublicConstants.ADD)
	public ResponseEntity<?> addLearn(@RequestBody EnglishLearnRecordTbl request) {
		// Update Account
		APIResponse<?> result = englishLearnRecordService.addLearn(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Add new
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH)
	public ResponseEntity<?> create(@Validated @RequestBody EnglishTbl request) {
		// Create English
		APIResponse<?> result = englishService.create(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_DOWNLOAD_FILE_MP3)
	public ResponseEntity<?> downloadFileMp3(@RequestBody DownloadFileVO vo) {
		// Search
		APIResponse<?> result = englishService.downloadFileMp3(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Get by ID
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
		// Delete
		APIResponse<?> result = englishService.getById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_LEARN_RECORD
			+ ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody EnglishLearnRecordSCO sco) {
		// Search
		APIResponse<?> result = englishLearnRecordService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_LEARN_REPORT
			+ ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody EnglishLearnReportSCO sco) {
		// Search
		APIResponse<?> result = englishLearnReportService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody EnglishSCO sco) {
		// Search
		APIResponse<?> result = englishService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 *
	 * Search english user grade
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_USER_GRADE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody EnglishUserGradeSCO sco) {
		// Search
		APIResponse<?> result = englishUserGradeService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search low learn
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.ENGLISH_TOP_LEARN)
	public ResponseEntity<?> searchEnglishTopLearn(@RequestBody EnglishLearnReportSCO sco) {
		// Search
		APIResponse<?> result = englishService.searchTopLearn((long) 50);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 *
	 * Search low learn
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.SEARCH_LOW_LEARN)
	public ResponseEntity<?> searchLowLearn(@RequestBody EnglishSCO sco) {
		// Search
		APIResponse<?> result = englishService.searchLowLearn(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 *
	 * Set english user grade
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_ENGLISH + ApiPublicConstants.API_ENGLISH_USER_GRADE + ApiPublicConstants.SET)
	public ResponseEntity<?> setEnglishUserGrade(@Validated @RequestBody EnglishUserGradeTbl request) {
		// Update Account
		APIResponse<?> result = englishUserGradeService.setEnglishUserGrade(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Update
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_ENGLISH)
	public ResponseEntity<?> update(@Validated @RequestBody EnglishTbl request) {
		// Update Account
		APIResponse<?> result = englishService.update(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}
}
