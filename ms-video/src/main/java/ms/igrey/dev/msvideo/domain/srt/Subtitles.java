package ms.igrey.dev.msvideo.domain.srt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Subtitles {
    private final List<Subtitle> subtitles;
}
