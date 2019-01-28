package ms.igrey.dev.msvideo.domain.srt;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class SrtParser {

    private final String srtFileName;
    private static final String EMPTY_LINE_REGEX = "\r\n\r\n";

    public List<Subtitle> parsedSubtitlesFromOriginalSrtRows() {
        return Stream.of(parsedElements())
                .map(element -> new Subtitle(element, srtFileName.replace(".srt", "")))
                .collect(Collectors.toList());
    }

    private String[] parsedElements() {
        return fullSrt().split(EMPTY_LINE_REGEX);
    }

    private String fullSrt() {
        try {
            File srtFile = ResourceUtils.getFile("classpath:" + srtFileName);
            return FileUtils.readFileToString(srtFile, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
