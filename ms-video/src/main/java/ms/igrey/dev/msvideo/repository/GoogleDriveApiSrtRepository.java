package ms.igrey.dev.msvideo.repository;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleDriveApiSrtRepository implements SrtRepository {

    private static String SRT_FOLDER_ID = "1l65MuTDhFNOxv-8IIt7mKRh8nzkioFDo";
    private final Drive driveService;


    @Override
    public String findSrtByFilmTitle(String filmTitle) {
        return downloadFileContent(findFileIdInFolderByTitle(filmTitle, SRT_FOLDER_ID));

    }

    @Override
    public List<String> findAllSrtFileTitles() {
        return retrieveFilesInFolder(SRT_FOLDER_ID).stream()
                .map((file) -> file.getName().replace(".srt", ""))
                .collect(Collectors.toList());
    }

    private String downloadFileContent(String fileId) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            return outputStream.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<File> retrieveFilesInFolder(String folderId) {
        String query = "'" + folderId + "' in parents";
        List<File> result = new ArrayList<>();
        Drive.Files.List request = null;
        try {
            request = driveService.files().list().setQ(query);
            driveService.files().get(SRT_FOLDER_ID).execute();

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

    private String findFileIdInFolderByTitle(String fileTitle, String parentFolderId) {
        return retrieveFilesInFolder(parentFolderId).stream()
                .filter(file -> file.getName().equals(fileTitle))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not find file by title " + fileTitle + " in folder " + parentFolderId))
                .getId();
    }

}
