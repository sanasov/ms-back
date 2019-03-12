package ms.igrey.dev.msvideo.domain.srt;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;
import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import org.apache.commons.collections.CollectionUtils;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MILLIS;

@Data
@Accessors(fluent = true)
public class Subtitle {
    private static final String NEXT_ROWS = "\r\n";
    private static final String TIME_SEPARATOR = " --> ";
    private static final Integer MAX_SHORT_DURATION_MILLSEC = 1500;
    private static final Integer MAX_TRASH_DURATION_MILLSEC = 500;

    private final Integer numberSeq;
    private final String filmId;
    private final String start; // 00:05:31,999
    private final String end;   // 00:05:34,583
    private final List<String> lines;
    private final SubtitleQuality quality;


    Subtitle union(Subtitle nextSub) {
        List<String> lines = (List) CollectionUtils.union(this.lines, nextSub.lines);
        return new Subtitle(
                this.numberSeq,
                this.filmId,
                this.start,
                nextSub.end,
                lines,
                estimateQuality(startTime().until(nextSub.endTime(), MILLIS), lines)
        );
    }

    public Subtitle(Integer numberSeq, String filmId, String start, String end, List<String> lines, SubtitleQuality quality) {
        this.numberSeq = numberSeq;
        this.filmId = filmId;
        this.start = start;
        this.end = end;
        this.lines = lines;
        this.quality = quality;
    }

    public Subtitle(String subElement, String filmId) {
        String[] subElementRows = subElement.split(NEXT_ROWS);
        numberSeq = Integer.parseInt(subElementRows[0].replaceAll("[^\\d.]", ""));
        this.filmId = filmId;
        String[] startEnd = subElementRows[1].split(TIME_SEPARATOR); //  00:05:31,999 --> 00:05:34,583
        this.start = startEnd[0];
        this.end = startEnd[1];
        this.lines = parsedLines(subElementRows);
        this.quality = estimateQuality(duration(), lines);
    }

    public Subtitle(SubtitleEntity entity) {
        this.numberSeq = entity.getNumberSeq();
        this.filmId = entity.getFilmId();
        this.start = entity.getStart();
        this.end = entity.getEnd();
        this.lines = entity.getLines();
        this.quality = estimateQuality(duration(), entity.getLines());
    }

    public static SubtitleEntity toEntity(Subtitle subtitle, String videoTypeTag) {
        SubtitleEntity entity = new SubtitleEntity();
        entity.setNumberSeq(subtitle.numberSeq());
        entity.setFilmId(subtitle.filmId());
        entity.setStart(subtitle.start());
        entity.setEnd(subtitle.end());
        entity.setLines(subtitle.lines());
        entity.setTagArray(videoTypeTag);
        return entity;
    }

    SubtitleQuality estimateQuality(Long durationMilisec, List<String> lines) {
        if (durationMilisec < MAX_TRASH_DURATION_MILLSEC || wordCount(lines) <= 2) {
            return SubtitleQuality.TRASH;
        } else if (durationMilisec < MAX_SHORT_DURATION_MILLSEC || wordCount(lines) <= 3) {
            return SubtitleQuality.SHORT;
        } else {
            return SubtitleQuality.IDEAL;
        }
    }

    private Long wordCount(List<String> lines) {
        return lines.stream()
                .map(row -> Lists.newArrayList(row.split("\\s+")))
                .flatMap(Collection::stream)
                .filter(word -> word.length() > 1)
                .count();
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

    public Long startOffset() {
        return startTime().toNanoOfDay() / 1000_000;
    }

    public Long duration() {
        return (endTime().toNanoOfDay() - startTime().toNanoOfDay()) / 1000_000;
    }
}
