package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class FileSystemMovieRepository implements MovieRepository {

    private final static String CUT_MOVIE_PATH = System.getProperty("user.home") + "/movieHero/cutMovies";

    @Override
    public File findMovie(Subtitle subtitle) {
        return new File(CUT_MOVIE_PATH + "/" + subtitle.filmId() + "/" + subtitle.numberSeq());
    }
}
