package ms.igrey.dev.msvideo.service;

import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import ms.igrey.dev.msvideo.repository.MovieRepository;
import org.springframework.core.io.FileSystemResource;

@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final SubtitleService subtitleService;


    public FileSystemResource getMovieFragmentByPhrase(String phrase) {
        Subtitle subtitle = subtitleService.findByPhrase(phrase).stream()
                .filter(sub -> sub.filmId().equals("Bohemian Rhapsody (2018)"))
                .findAny()
                .orElse(null);
        return new FileSystemResource(movieRepository.findMovie(subtitle));
    }
}
