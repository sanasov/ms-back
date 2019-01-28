package ms.igrey.dev.msvideo.srt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Accessors(fluent = true)
public class Subtitle {
    private static final String NEXT_LINE = "\r\n";
    private static final String TIME_SEPARATOR = " --> ";
    LocalTime start;
    LocalTime end;
    List<String> lines;
    Integer numberSeq;
    String filmId;

    public Subtitle(String subElement, String filmId) {
        String[] subElementRows = subElement.split(NEXT_LINE);
        numberSeq = Integer.valueOf(subElementRows[0]);
        this.filmId = filmId;
        String[] startEnd = subElementRows[1].replace(",", ".").split(TIME_SEPARATOR); //  00:05:31,999 --> 00:05:34,583
        this.start = LocalTime.parse(startEnd[0]);
        this.end = LocalTime.parse(startEnd[1]);
        this.lines = parsedLines(subElementRows);
    }

    List<String> parsedLines(String[] subElementRws) {
        return Stream.of(subElementRws)
                .collect(Collectors.toList())
                .subList(2, subElementRws.length);
    }
}
