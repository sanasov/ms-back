package ms.igrey.dev.msvideo.domain.srt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Subtitles {
    private final LinkedList<Subtitle> subtitles;

//    List<Subtitle> unitedSubtitles() {
//        ListIterator<Subtitle> li = subtitles.listIterator(0);
//        while (li.hasNext()) {
//            if(li.next().quality() == SubtitleQuality.SHORT) {
//
//            }
//        }
//    }


}
