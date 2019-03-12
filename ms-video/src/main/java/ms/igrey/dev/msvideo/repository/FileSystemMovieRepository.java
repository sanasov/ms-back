package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.FSPath;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSystemMovieRepository implements MovieRepository {
    private final static List<String> vidoExtensions = Arrays.asList("avi", "mov", "mpeg", "mp4");

    @Override
    public File findMovie(String filmId, Integer numberSeq) {
        return new File((FSPath.CUT_MOVIE_PATH + "/" + filmId + "/" + numberSeq + ".mp4"));
    }

    @Override
    public List<String> findAllPreparedMovieTitles() {
        return Stream.of(new File(FSPath.MOVIE_STORAGE_PATH).listFiles())
                .filter(file -> file.isFile())
                .filter(file -> vidoExtensions.contains(FilenameUtils.getExtension(file.getName())))
                .map(file -> FilenameUtils.getBaseName(file.getName()))
                .collect(Collectors.toList());
    }
}
