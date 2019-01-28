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
    private final LocalTime start;
    private final LocalTime end;
    private final List<String> lines;
    private final Integer numberSeq;
    private final String filmId;

    public Subtitle(String subElement, String filmId) {
        String[] subElementRows = subElement.split(NEXT_LINE);
        numberSeq = Integer.parseInt(subElementRows[0].replaceAll("[^\\d.]", ""));
        this.filmId = filmId;
        String[] startEnd = subElementRows[1].replace(",", ".").split(TIME_SEPARATOR); //  00:05:31,999 --> 00:05:34,583
        this.start = LocalTime.parse(startEnd[0]);
        this.end = LocalTime.parse(startEnd[1]);
        this.lines = parsedLines(subElementRows);
    }

    public Subtitle(SubtitleEntity entity) {
        this.numberSeq = entity.getNumberSeq();
        this.filmId = entity.getFilmId();
        this.start = LocalTime.parse(entity.getStart().replace(",", "."));
        this.end = LocalTime.parse(entity.getEnd().replace(",", "."));
        this.lines = entity.getLines();
    }

    public static SubtitleEntity toEntity(Subtitle subtitle) {
        SubtitleEntity entity = new SubtitleEntity();
        entity.setNumberSeq(subtitle.numberSeq());
        entity.setFilmId(subtitle.filmId());
        entity.setStart(subtitle.start().toString().replace(".", ","));
        entity.setEnd(subtitle.end().toString().replace(".", ","));
        entity.setLines(subtitle.lines());
        return entity;
    }

    List<String> parsedLines(String[] subElementRws) {
        return Stream.of(subElementRws)
                .collect(Collectors.toList())
                .subList(2, subElementRws.length);
    }
}
