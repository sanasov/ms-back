package ms.igrey.dev.msvideo.service;

import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.dto.OmdbFilmDto;
import ms.igrey.dev.msvideo.repository.FilmMetaInfoRepository;
import ms.igrey.dev.msvideo.repository.SrtRepository;

import java.util.List;

@RequiredArgsConstructor
public class OmdbFilmMetaInfoService {

    private final FilmMetaInfoRepository filmRepository;
    private final SrtRepository srtRepository;

    public List<OmdbFilmDto> getFilmsInfo() {
        return filmRepository.findByTitles(srtRepository.findAllSrtFileTitles());
    }


}
