package ms.igrey.dev.msvideo.repository;

import java.io.File;

public interface MovieRepository {

    File findMovie(String filmId, Integer numberSeq);

}
