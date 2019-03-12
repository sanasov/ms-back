package ms.igrey.dev.msvideo.repository;

import java.io.File;
import java.util.List;

public interface MovieRepository {

    File findMovie(String filmId, Integer numberSeq);

    List<String> findAllPreparedMovieTitles();
}
