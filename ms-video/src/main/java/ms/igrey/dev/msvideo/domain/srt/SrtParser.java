package ms.igrey.dev.msvideo.domain.srt;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class SrtParser {

    private final String srtFileTitle;
    private final String srtFileContent;
    private static final String EMPTY_LINE_REGEX = "\n\n";

    public List<Subtitle> parsedSubtitlesFromOriginalSrtRows() {
        return Stream.of(srtFileContent.replace("\r", "").split(EMPTY_LINE_REGEX))
                .map(element -> new Subtitle(element, srtFileTitle.replace(".srt", "")))
                .collect(Collectors.toList());
    }
}
