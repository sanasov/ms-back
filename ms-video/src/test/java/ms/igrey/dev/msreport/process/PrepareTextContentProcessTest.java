package ms.igrey.dev.msreport.process;

import com.google.gson.Gson;
import ms.igrey.dev.msvideo.PrepareTextContentProcess;
import ms.igrey.dev.msvideo.config.DaoConfig;
import ms.igrey.dev.msvideo.repository.SrtRepository;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
@ActiveProfiles("no-cloud")
public class PrepareTextContentProcessTest {

    @Autowired
    private PrepareTextContentProcess process;
    @Autowired
    private SubtitleService subtitleService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SrtRepository srtRepository;


    @Before
    public void before() {
        process.fillAllContentInElastic();
    }

    @Test
    public void findByPhrase() {
        System.out.println(
                new Gson().toJson(subtitleService.findByPhrase("mother"))
        );
    }

}
