package ms.igrey.dev.msvideo.domain.srt;

import lombok.Data;
import lombok.experimental.Accessors;
import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Accessors(fluent = true)
public class Subtitle {
    private static final String NEXT_LINE = "\r\n";
    private static final String TIME_SEPARATOR = " --> ";
    private final Integer numberSeq;
    private final String filmId;
    private final String start; // 00:05:31,999
    private final String end;   // 00:05:34,583
    private final List<String> lines;


    public Subtitle(String subElement, String filmId) {
        String[] subElementRows = subElement.split(NEXT_LINE);
        numberSeq = Integer.parseInt(subElementRows[0].replaceAll("[^\\d.]", ""));
        this.filmId = filmId;
        String[] startEnd = subElementRows[1].split(TIME_SEPARATOR); //  00:05:31,999 --> 00:05:34,583
        this.start = startEnd[0];
        this.end = startEnd[1];
        this.lines = parsedLines(subElementRows);
    }

    public Subtitle(SubtitleEntity entity) {
        this.numberSeq = entity.getNumberSeq();
        this.filmId = entity.getFilmId();
        this.start = entity.getStart();
        this.end = entity.getEnd();
        this.lines = entity.getLines();
    }

    public static SubtitleEntity toEntity(Subtitle subtitle) {
        SubtitleEntity entity = new SubtitleEntity();
        entity.setNumberSeq(subtitle.numberSeq());
        entity.setFilmId(subtitle.filmId());
        entity.setStart(subtitle.start());
        entity.setEnd(subtitle.end());
        entity.setLines(subtitle.lines());
        return entity;
    }

    List<String> parsedLines(String[] subElementRws) {
        return Stream.of(subElementRws)
                .collect(Collectors.toList())
                .subList(2, subElementRws.length);
    }

    public LocalTime startTime() {
        return LocalTime.parse(start.replace(",", "."));
    }

    public LocalTime endTime() {
        return LocalTime.parse(end.replace(",", "."));
    }
}
