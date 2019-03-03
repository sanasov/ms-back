package ms.igrey.dev.msvideo.service;

import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import ms.igrey.dev.msvideo.repository.MovieRepository;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final SubtitleService subtitleService;


    public FileSystemResource getMovieFragmentByPhrase(String phrase) {
        Subtitle subtitle = subtitleService.findByPhrase(phrase).stream()
                .filter(sub -> sub.filmId().equals("Bohemian Rhapsody (2018)"))
                .min(Comparator.comparing(Subtitle::numberSeq))
                .orElse(null);
        return new FileSystemResource(movieRepository.findMovie(subtitle.filmId(), subtitle.numberSeq()));
    }

    public List<Subtitle> getSubtitles(String phrase) {
        return subtitleService.findByPhrase(phrase);
    }

    public List<FileSystemResource> getMovieFragments() {
        return Arrays.asList(
                new FileSystemResource(movieRepository.findMovie("Bohemian Rhapsody (2018)", 100)),
                new FileSystemResource(movieRepository.findMovie("Bohemian Rhapsody (2018)", 101)),
                new FileSystemResource(movieRepository.findMovie("Bohemian Rhapsody (2018)", 102))
        );
    }
}
