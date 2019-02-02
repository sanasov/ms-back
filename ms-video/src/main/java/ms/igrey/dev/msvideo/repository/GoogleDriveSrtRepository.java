package ms.igrey.dev.msvideo.repository;

import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.api.GoogleDriveUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleDriveSrtRepository implements SrtRepository {

    private static String SRT_FOLDER_ID = "1l65MuTDhFNOxv-8IIt7mKRh8nzkioFDo";

    @Override
    public String findSrtByFilmTitle(String filmTitle) {
        return GoogleDriveUtils.downloadFileContent(
                GoogleDriveUtils.findFileIdInFolderByTitle(filmTitle, SRT_FOLDER_ID).getId()
        );
    }

    @Override
    public List<String> findAllSrtFileTitles() {
        return GoogleDriveUtils.retrieveFilesInFolder(SRT_FOLDER_ID).stream()
                .map((file) -> file.getName().replace(".srt", ""))
                .collect(Collectors.toList());
    }

}
