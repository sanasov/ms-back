//package ms.igrey.dev.msvideo;
//
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.FileList;
//import ms.igrey.dev.msvideo.config.GoogleDriveApiConfig;
//import org.apache.commons.io.output.ByteArrayOutputStream;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class GetSubFolders {
//
//    private static String SRT_FILE_ID =  "1l65MuTDhFNOxv-8IIt7mKRh8nzkioFDo";
//
//    public static final List<File> getGoogleSubFolders(String googleFolderIdParent) throws IOException {
//
//        Drive driveService = GoogleDriveApiConfig.getDriveService();
//
//        String pageToken = null;
//        List<File> list = new ArrayList<>();
//
//        String query = null;
//        if (googleFolderIdParent == null) {
//            query = " mimeType = 'application/vnd.google-apps.folder' " //
//                    + " and 'root' in parents";
//        } else {
//            query = " mimeType = 'application/vnd.google-apps.folder' " //
//                    + " and '" + googleFolderIdParent + "' in parents";
//        }
//
//        do {
//            FileList result = driveService.files()
//                    .list()
//                    .setQ(query)
//                    .setSpaces("drive")
//                    .setFields("nextPageToken, files(id, name, createdTime)")  // Fields will be assigned values: id, name, createdTime
//                    .setPageToken(pageToken).execute();
//            for (File file : result.getFiles()) {
//                list.add(file);
//            }
//            pageToken = result.getNextPageToken();
//        } while (pageToken != null);
//        //
//        return list;
//    }
//
//    // com.google.api.services.drive.model.File
//    public static final List<File> getGoogleRootFolders() throws IOException {
//        return getGoogleSubFolders(null);
//    }
//
//    public static String fileDownload(String fileId) {
//        Drive driveService = GoogleDriveApiConfig.getDriveService();
//        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
//            return outputStream.toString("UTF-8");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static List<File> retrieveFilesInFolder(String folderId) throws IOException {
//        Drive driveService = GoogleDriveApiConfig.getDriveService();
//        String query = "'" + folderId + "' in parents";
//        List<File> result = new ArrayList<>();
//        Drive.Files.List request = driveService.files().list().setQ(query);
//        driveService.files().get(SRT_FILE_ID).execute();
//        do {
//            try {
//                FileList files = request.execute();
//
//                result.addAll(files.getFiles());
//                request.setPageToken(files.getNextPageToken());
//            } catch (IOException e) {
//                System.out.println("An error occurred: " + e);
//                request.setPageToken(null);
//            }
//        } while (request.getPageToken() != null &&
//                request.getPageToken().length() > 0);
//
//        return result;
//    }
//
//    public static void main(String[] args) throws IOException {
//        Drive driveService = GoogleDriveApiConfig.getDriveService();
//
//        List<File> googleRootFolders = getGoogleRootFolders();
//        for (File folder : googleRootFolders) {
//
//            System.out.println("Folder ID: " + folder.getId() + " --- Name: " + folder.getName());
//        }
//
////        fileDownload("srt");
//        System.out.println(fileDownload(retrieveFilesInFolder(SRT_FILE_ID).get(0).getId()));
//    }
//
//}
