package ms.igrey.dev.msreport.domain;


import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubtitleTest {

    private Subtitle subtitle;

    @Before
    public void init() {
        subtitle = new Subtitle("56\r\n00:06:37,954 --> 00:06:39,021\r\nHumpy Bong.",
                "Bohemian Rhapsody (2018)");
    }

    @Test
    public void startOffsetDurationTest() {
        assertThat(subtitle.duration()).isEqualTo(1067);
        assertThat(subtitle.startOffset()).isEqualTo(6 * 60 * 1000 + 37954);
        assertThat(subtitle.numberSeq()).isEqualTo(56);
    }

}
