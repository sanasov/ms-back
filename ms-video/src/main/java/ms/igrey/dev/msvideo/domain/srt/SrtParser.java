package ms.igrey.dev.msvideo.domain.srt;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class SrtParser {

    private final String srtFileTitle;
    private final String srtFileContent;
    private static final String EMPTY_LINE_REGEX = "\r\n\r\n";

    public List<Subtitle> parsedSubtitlesFromOriginalSrtRows() {
        return Stream.of(srtFileContent.split(EMPTY_LINE_REGEX))
                .map(element -> new Subtitle(element, srtFileTitle.replace(".srt", "")))
                .collect(Collectors.toList());
    }
}
