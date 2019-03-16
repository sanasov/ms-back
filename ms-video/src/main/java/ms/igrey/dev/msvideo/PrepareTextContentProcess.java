package ms.igrey.dev.msvideo;

import com.google.api.services.drive.model.File;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.api.GoogleDriveUtils;
import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.dto.OmdbFilmDto;
import ms.igrey.dev.msvideo.repository.SrtRepository;
import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import ms.igrey.dev.msvideo.service.OmdbFilmMetaInfoService;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class PrepareTextContentProcess {
    private final static String MOVIE_HERO_FOLDER_ID = "1J7WNanjAo-ytQSJ6oLCbiSUhBm6wXCTX";
    private final static String FILM_INFO_FILE_TITLE = "filmInfo.json";
    private final SrtRepository srtRepository;
    private final SubtitleService subtitleService;
    private final OmdbFilmMetaInfoService omdbFilmService;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public void fillNewContentInElastic() {
        Collection<String> newFilmsTitle = newFilmsTitle();
//        uploadFilmInfoFileToGoogleDrive(filledFileInfoJson());
        saveNewSubtitlesInElasticSearch(newFilmsTitle);
    }

    public void fillAllContentInElastic() {
        elasticsearchTemplate.deleteIndex(SubtitleEntity.class);
        elasticsearchTemplate.createIndex(SubtitleEntity.class);
        saveNewSubtitlesInElasticSearch(srtRepository.findAllSrtFileTitles());
    }


    public void uploadFilmInfoFileToGoogleDrive(java.io.File file) {
        File fileMetaInfo = GoogleDriveUtils.findFileInFolderByTitle(FILM_INFO_FILE_TITLE, MOVIE_HERO_FOLDER_ID);
        if (StringUtils.isNotBlank(fileMetaInfo.getId())) {
            GoogleDriveUtils.delete(fileMetaInfo.getId());
        }
        GoogleDriveUtils.uploadFileToFolder(file, MOVIE_HERO_FOLDER_ID);
        log.info(FILM_INFO_FILE_TITLE + " is uploaded to google disk");
    }

    public void saveNewSubtitlesInElasticSearch(Collection<String> newFilmsTitle) {
        if (!elasticsearchTemplate.indexExists(SubtitleEntity.class)) {
            elasticsearchTemplate.createIndex(SubtitleEntity.class);
        }
        for (String fileTitle : newFilmsTitle) {
            subtitleService.save(
                    new Subtitles(
                            new SrtParser(
                                    fileTitle,
                                    srtRepository.findSrtByFilmTitle(fileTitle)
                            ).parsedSubtitlesFromOriginalSrtRows()
                    )
            );
        }
        log.info("New subtitles is saved in ES");
    }

    private Collection<String> newFilmsTitle() {
        List<String> filmsTitleFromFileJson = loadOmdbFilmsInfoFromFileJson().stream()
                .map(film -> film.getTitleWithYear())
                .collect(Collectors.toList());
        Collection<String> newFilmsTitle = CollectionUtils.subtract(
                srtRepository.findAllSrtFileTitles(),
                filmsTitleFromFileJson
        );
        log.info("New film titles: " + new Gson().toJson(newFilmsTitle));
        return newFilmsTitle;
    }


    private ArrayList<OmdbFilmDto> loadOmdbFilmsInfoFromFileJson() {
        return Lists.newArrayList(
                new Gson().fromJson(
                        GoogleDriveUtils.downloadFileContent(
                                GoogleDriveUtils.findFileInFolderByTitle(FILM_INFO_FILE_TITLE, MOVIE_HERO_FOLDER_ID).getId()
                        ),
                        OmdbFilmDto[].class
                )
        );
    }

    private java.io.File filledFileInfoJson() {
        try {
            java.io.File file = new java.io.File(FILM_INFO_FILE_TITLE);
            FileUtils.writeStringToFile(
                    file,
                    new Gson().toJson(omdbFilmService.getFilmsInfo()),
                    "UTF-8"
            );
            log.info(FILM_INFO_FILE_TITLE + " is filled by Omdb service");
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
