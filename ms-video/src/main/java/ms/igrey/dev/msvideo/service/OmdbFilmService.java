package ms.igrey.dev.msvideo.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.dto.OmdbFilmDto;
import ms.igrey.dev.msvideo.repository.FilmRepository;
import ms.igrey.dev.msvideo.repository.GoogleDriveApiSrtRepository;
import ms.igrey.dev.msvideo.repository.OmdbApiFilmRepository;
import ms.igrey.dev.msvideo.repository.SrtRepository;

import java.util.List;

@RequiredArgsConstructor
public class OmdbFilmService {

    private final FilmRepository filmRepository;
    private final SrtRepository srtRepository;

    public List<OmdbFilmDto> getFilmsInfo() {
        return filmRepository.findByTitles(srtRepository.findAllSrtFileTitles());
    }

    public static void main(String[] args) {
        System.out.println(
                new Gson().toJson(new OmdbFilmService(
                        new OmdbApiFilmRepository(),
                        new GoogleDriveApiSrtRepository()
                ).getFilmsInfo())
        );
    }
}
