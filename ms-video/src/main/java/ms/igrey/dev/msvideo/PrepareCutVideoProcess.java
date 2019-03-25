package ms.igrey.dev.msvideo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.ffmpeg.MovieCutter;
import ms.igrey.dev.msvideo.repository.MovieRepository;
import ms.igrey.dev.msvideo.repository.SrtRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Component
public class PrepareCutVideoProcess {

    private final SrtRepository srtRepository;
    private final MovieRepository movieRepository;

    public void cutNewMovies() {
        Collection<String> notCutYetMovieTitles = CollectionUtils.subtract(
                movieRepository.findAllPreparedMovieTitles(),
                movieRepository.findAllCutMovieTitles()
        );
        for (String movieTitleForCutting : notCutYetMovieTitles) {
            log.info("start cut movie: " + movieTitleForCutting);
            cutMovie(movieTitleForCutting);
        }
    }

    //Bohemian Rhapsody (2018)
    //TODO need find subtitles from Elastic
    public void cutMovie(String movieTitleForCutting) {
        new MovieCutter().cut(
                movieTitleForCutting,
                new Subtitles(
                        new SrtParser(
                                movieTitleForCutting,
                                srtRepository.findSrtByFilmTitle(movieTitleForCutting)
                        ).parsedSubtitlesFromOriginalSrtRows()
                ).mergedSubtitles()
        );
    }

}
