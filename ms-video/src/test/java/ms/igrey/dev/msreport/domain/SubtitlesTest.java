package ms.igrey.dev.msreport.domain;

import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

public class SubtitlesTest {

    private Subtitles subtitles;

    @Before
    public void init() throws IOException {
        subtitles = new Subtitles(
                new SrtParser(
                        "BohemianRhapsody2018",
                        FileUtils.readFileToString(ResourceUtils.getFile("classpath:BohemianRhapsody2018.srt"), "UTF-8")
                ).parsedSubtitlesFromOriginalSrtRows()
        );
    }

    @Test
    public void test() {
        System.out.println(subtitles.mergedSubtitles().size());
    }

}
