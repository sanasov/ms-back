package ms.igrey.dev.msvideo.srt;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;

@AllArgsConstructor
public class SrtParser {

    private final String srtFileName;
    private static final String EMPTY_LINE_REGEX = "\r\n\r\n";

    private String fullSrt() {
        try {
            File srtFile = ResourceUtils.getFile("classpath:" + srtFileName);
            return FileUtils.readFileToString(srtFile, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[] parsedElements() {
        return fullSrt().split(EMPTY_LINE_REGEX);
    }

}
