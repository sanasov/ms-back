package ms.igrey.dev.msvideo.srt;

import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Subtitles {

    private final List<Subtitle> subtitles;
    private final SrtParser parser;
    private final String filmId;

    public Subtitles(SrtParser parser, String filmId) {
        this.parser = parser;
        this.filmId = filmId;
        this.subtitles = mappedSubtitlesFromOriginalSrtRows();
    }

    private List<Subtitle> mappedSubtitlesFromOriginalSrtRows() {
        return Stream.of(parser.parsedElements())
                .map(element -> new Subtitle(element, filmId))
                .collect(Collectors.toList());
    }

    public List<Subtitle> subtitles() {
        return subtitles;
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(new Subtitles(new SrtParser("Vertigo (1958).srt"), "Vertigo (1958).srt").subtitles()));
    }
}
