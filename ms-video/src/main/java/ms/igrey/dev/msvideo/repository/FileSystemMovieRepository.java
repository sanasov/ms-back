package ms.igrey.dev.msvideo.repository;

import java.io.File;

public class FileSystemMovieRepository implements MovieRepository {

    private final static String CUT_MOVIE_PATH = System.getProperty("user.home") + "/movieHero/cutMovies";

    @Override
    public File findMovie(String filmId, Integer numberSeq) {
        return new File((CUT_MOVIE_PATH + "/" + filmId + "/" + numberSeq + ".mp4"));
    }
}
