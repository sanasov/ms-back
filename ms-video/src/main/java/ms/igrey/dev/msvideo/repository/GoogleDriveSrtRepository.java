package ms.igrey.dev.msvideo.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.api.GoogleDriveUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class GoogleDriveSrtRepository implements SrtRepository {

    private static String SRT_FOLDER_ID = "1l65MuTDhFNOxv-8IIt7mKRh8nzkioFDo";

    @Override
    public String findSrtByFilmTitle(String filmTitle) {
        filmTitle = filmTitle.replace(".srt", "") + ".srt";
        log.info("Load Srt content of file: " + filmTitle);
        return GoogleDriveUtils.downloadFileContent(
                GoogleDriveUtils.findFileInFolderByTitle(filmTitle, SRT_FOLDER_ID).getId()
        );
    }

    @Override
    public List<String> findAllSrtFileTitles() {
        return GoogleDriveUtils.retrieveFilesInFolder(SRT_FOLDER_ID).stream()
                .map((file) -> file.getName().replace(".srt", ""))
                .collect(Collectors.toList());
    }

}
