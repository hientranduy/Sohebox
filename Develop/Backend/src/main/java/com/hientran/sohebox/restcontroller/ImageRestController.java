package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.service.ImageService;

/**
 * @author hientran
 */
@RestController
public class ImageRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ImageService imageService;

    /**
     * 
     * Sync images
     *
     * @return
     */
    @GetMapping(ApiPublicConstants.API_IMAGE_SYNC)
    public String syncImages() {
        return imageService.sync();
    }
}
