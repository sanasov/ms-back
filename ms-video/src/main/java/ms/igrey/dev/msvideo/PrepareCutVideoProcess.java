package ms.igrey.dev.msvideo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.ffmpeg.VideoCutter;
import ms.igrey.dev.msvideo.repository.MovieRepository;
import ms.igrey.dev.msvideo.repository.SrtRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Component
public class PrepareCutVideoProcess {
    private final static Integer PARTS_AMOUNT = 4;
    private final SrtRepository srtRepository;
    private final MovieRepository movieRepository;

//    public void cutNewMovies() {
//        Collection<String> notCutYetMovieTitles = CollectionUtils.subtract(
//                movieRepository.findAllPreparedMovieTitles(),
//                movieRepository.findAllCutMovieTitles()
//        );
//        for (String movieTitleForCutting : notCutYetMovieTitles) {
//            log.info("start cut movie: " + movieTitleForCutting);
//            cutMovie(movieTitleForCutting);
//        }
//    }

    public void cutNewMoviesNewAlgorithm() {
        Collection<String> notCutYetMovieTitles = CollectionUtils.subtract(
                movieRepository.findAllPreparedMovieTitles(),
                movieRepository.findAllCutMovieTitles()
        );
        for (String movieTitleForCutting : notCutYetMovieTitles) {
            log.info("start cut movie: " + movieTitleForCutting);
            cutMovieNewAlgorithm(movieTitleForCutting);
        }
    }

//    //Bohemian Rhapsody (2018)
//    //TODO need find subtitles from Elastic
//    public void cutMovie(String movieTitleForCutting) {
//        new VideoCutter().cut(
//                movieTitleForCutting,
//                new Subtitles(
//                        new SrtParser(
//                                movieTitleForCutting,
//                                srtRepository.findSrtByFilmTitle(movieTitleForCutting)
//                        ).parsedSubtitlesFromOriginalSrtRows()
//                ).mergedSubtitles().subtitles()
//        );
//    }

    public void cutMovieNewAlgorithm(String movieTitleForCutting) {
        Subtitles subtitles = new Subtitles(
                new SrtParser(
                        movieTitleForCutting,
                        srtRepository.findSrtByFilmTitle(movieTitleForCutting)
                ).parsedSubtitlesFromOriginalSrtRows()
        ).mergedSubtitles();
        VideoCutter videoCutter = new VideoCutter();
        videoCutter.cutIntoParts(movieTitleForCutting, subtitles, PARTS_AMOUNT);
        for (int i = 1; i <= PARTS_AMOUNT; i++) {
            videoCutter.cut(movieTitleForCutting, subtitles.partOfSubtitles(i, PARTS_AMOUNT), i);
        }
    }
}
