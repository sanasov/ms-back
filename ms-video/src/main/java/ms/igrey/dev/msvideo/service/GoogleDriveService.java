package ms.igrey.dev.msvideo.service;

import com.google.api.services.drive.Drive;
import ms.igrey.dev.msvideo.config.GoogleDriveConfig;

public class GoogleDriveService {

    private final Drive driveService;


    public GoogleDriveService() {
        this.driveService = GoogleDriveConfig.getDriveService();
    }




}
