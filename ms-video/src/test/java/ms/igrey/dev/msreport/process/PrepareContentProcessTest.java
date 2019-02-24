package ms.igrey.dev.msreport.process;

import com.google.gson.Gson;
import ms.igrey.dev.msvideo.PrepareContentProcess;
import ms.igrey.dev.msvideo.config.DaoConfig;
import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.ffmpeg.MovieCutter;
import ms.igrey.dev.msvideo.repository.SrtRepository;
import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
public class PrepareContentProcessTest {

    @Autowired
    private PrepareContentProcess process;
    @Autowired
    private SubtitleService subtitleService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SrtRepository srtRepository;


    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(SubtitleEntity.class);
        process.fillContentInElastic();
    }

    @Test
    public void findByPhrase() {
        System.out.println(
                new Gson().toJson(subtitleService.findByPhrase("mother"))
        );
    }

    @Test
    public void cutMovie() {
        String movieTitle = "Bohemian Rhapsody (2018)";
        new MovieCutter().cut(
                movieTitle,
                new Subtitles(
                        new SrtParser(
                                movieTitle,
                                srtRepository.findSrtByFilmTitle(movieTitle)
                        ).parsedSubtitlesFromOriginalSrtRows()
                )
        );
    }
}
