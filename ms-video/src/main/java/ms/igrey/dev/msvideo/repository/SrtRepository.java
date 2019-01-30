package ms.igrey.dev.msvideo.repository;

import java.util.List;

public interface SrtRepository {

    String findSrtByFilmTitle(String filmTitle);

    List<String> findAllSrtFileTitles();
}
