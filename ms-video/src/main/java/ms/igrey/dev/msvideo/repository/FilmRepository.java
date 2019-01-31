package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.dto.OmdbFilmDto;

import java.util.List;

public interface FilmRepository {

    OmdbFilmDto findByTitle(String filmTitle);

    List<OmdbFilmDto> findByTitles(List<String> filmTitles);
}
