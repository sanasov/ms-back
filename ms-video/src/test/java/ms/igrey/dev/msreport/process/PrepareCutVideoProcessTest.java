package ms.igrey.dev.msreport.process;

import ms.igrey.dev.msvideo.PrepareCutVideoProcess;
import ms.igrey.dev.msvideo.config.CommonConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommonConfig.class})
@ActiveProfiles("no-cloud")
public class PrepareCutVideoProcessTest {

    @Autowired
    private PrepareCutVideoProcess process;


    @Before
    public void before() {
    }

    @Test
    public void cutMovies() {
//        process.cutNewMovies();
    }

    @Test
    public void cutMovieWithNewAlgorithm() {
        process.cutMovieNewAlgorithm("Bohemian Rhapsody (2018)");
    }

}
