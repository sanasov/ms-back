package ms.igrey.dev.msvideo.api;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;
import ms.igrey.dev.msvideo.config.GoogleDriveConfig;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleDriveUtils {

    public static void uploadFileToFolder(java.io.File file, String folderId) {
        Drive driveService = GoogleDriveConfig.getDriveService();
        File fileMetadata = new File();
        fileMetadata.setName(file.getName());
        fileMetadata.setParents(Arrays.asList(folderId));
        try {
            File uploadedFile = driveService.files()
                    .create(fileMetadata, new FileContent("application/json", file))
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + uploadedFile.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: not work
    public static void updateFileToFolder(File fileMetaInfo, java.io.File fileContent) {
        Drive driveService = GoogleDriveConfig.getDriveService();
        try {
            System.out.println("Start update File ID: " + fileMetaInfo.getId());
            File uploadedFile = driveService.files()
                    .update(fileMetaInfo.getId(), fileMetaInfo, new FileContent("application/json", fileContent))
                    .execute();
            System.out.println("Updated File ID: " + uploadedFile.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String fileId) {
        Drive driveService = GoogleDriveConfig.getDriveService();
        try {
            driveService.files()
                    .delete(fileId)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String downloadFileContent(String fileId) {
        if(StringUtils.isBlank(fileId)) {
            throw new RuntimeException("File id should not be empty");
        }
        try {
            Drive driveService = GoogleDriveConfig.getDriveService();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            return outputStream.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File findFileInFolderByTitle(String fileTitle, String parentFolderId) {
        File emptyFile = new File();
        emptyFile.setId("");
        return retrieveFilesInFolder(parentFolderId).stream()
                .filter(file -> file.getName().equals(fileTitle))
                .findAny()
                .orElse(emptyFile);
    }

    public static List<File> retrieveFilesInFolder(String folderId) {
        Drive driveService = GoogleDriveConfig.getDriveService();
        String query = "'" + folderId + "' in parents";
        List<File> result = new ArrayList<>();
        Drive.Files.List request = null;
        try {
            request = driveService.files().list().setQ(query);
            driveService.files().get(folderId).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        do {
            try {
                FileList files = request.execute();

                result.addAll(files.getFiles());
                request.setPageToken(files.getNextPageToken());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
                request.setPageToken(null);
            }
        } while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);

        return result;
    }
//TODO: What difference between retrieveFilesInFolder and retrieveFilesInFolder2 ?
    public static List<File> retrieveFilesInFolder2(String parentFolderId) {

        Drive driveService = GoogleDriveConfig.getDriveService();

        String pageToken = null;
        List<File> list = new ArrayList<>();

        String query = null;
        if (parentFolderId == null) {
            query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and 'root' in parents";
        } else {
            query = " mimeType = 'application/vnd.google-apps.folder' " //
                    + " and '" + parentFolderId + "' in parents";
        }

        do {
            FileList result = null;
            try {
                result = driveService.files()
                        .list()
                        .setQ(query)
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name, createdTime)")  // Fields will be assigned values: id, name, createdTime
                        .setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        System.out.println(new Gson().toJson(list));
        return list;
    }
}
