package com.hientran.sohebox.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class ImageService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Value("${photo.path.root}")
    private String photoRootFolder;

    @Value("${photo.path.check.sync}")
    private String syncFolder;

    @Value("${photo.path.output}")
    private String outputFolder;

    /**
     * Get all images
     *
     * @return
     */
    public String sync() {
        // Declare return
        String result = null;

        // Prepare sync path
        File syncFolderPath = new File(photoRootFolder + syncFolder);
        if (syncFolderPath.exists()) {

        } else {
            result = "Failed. Folder sync is not existed, syncFolderPath:" + syncFolderPath;
        }

        // Return
        return result;
    }
}
