package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.YoutubeChannelSCO;
import com.hientran.sohebox.sco.YoutubeChannelVideoSCO;
import com.hientran.sohebox.service.YoutubeChannelService;
import com.hientran.sohebox.service.YoutubeChannelVideoService;
import com.hientran.sohebox.service.YoutubeService;
import com.hientran.sohebox.vo.YoutubeChannelVO;
import com.hientran.sohebox.vo.YoutubeVideoIdVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@RestController
@RequiredArgsConstructor
public class YoutubeRestController extends BaseRestController {

	private final YoutubeChannelService youtubeChannelService;
	private final YoutubeChannelVideoService youtubeChannelVideoService;
	private final YoutubeService youtubeService;

	/**
	 * 
	 * Add new channel
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_YOUTUBE_CHANNEL)
	public ResponseEntity<?> create(@Validated @RequestBody YoutubeChannelVO vo) {
		APIResponse<?> result = youtubeChannelService.create(vo);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Search channel
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody YoutubeChannelSCO sco) {
		APIResponse<?> result = youtubeChannelService.search(sco);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Search youtube channel
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_YOUTUBE_CHANNEL + ApiPublicConstants.SEARCH_MY_OWNER)
	public ResponseEntity<?> searchMyChannel(@RequestBody YoutubeChannelSCO sco) {
		APIResponse<?> result = youtubeChannelService.searchMyChannel(sco);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Update youtube channel
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_YOUTUBE_CHANNEL)
	public ResponseEntity<?> update(@Validated @RequestBody YoutubeChannelVO vo) {
		APIResponse<?> result = youtubeChannelService.update(vo);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Search youtube video
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.SEARCH_BY_CHANNEL)
	public ResponseEntity<?> search(@RequestBody YoutubeChannelVideoSCO sco) {
		APIResponse<?> result = youtubeService.searchChannelVideo(sco);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Add private youtube video
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.API_YOUTUBE_PRIVATE_VIDEO)
	public ResponseEntity<?> addPrivateVideo(@Validated @RequestBody YoutubeVideoIdVO vo) {
		APIResponse<?> result = youtubeChannelVideoService.addPrivateVideo(vo);

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Search youtube video
	 *
	 * @param sco
	 * @return
	 */
	@GetMapping(ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.API_YOUTUBE_PRIVATE_VIDEO)
	public ResponseEntity<?> getPrivateVideo() {
		APIResponse<?> result = youtubeChannelVideoService.getPrivateVideo();

		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Delete video by videoId
	 *
	 * @return
	 */
	@DeleteMapping(ApiPublicConstants.API_YOUTUBE_VIDEO + ApiPublicConstants.API_YOUTUBE_PRIVATE_VIDEO
			+ ApiPublicConstants.ID)
	public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") String id) {
		// Delete
		APIResponse<?> result = youtubeChannelVideoService.removeVideo(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}
}
