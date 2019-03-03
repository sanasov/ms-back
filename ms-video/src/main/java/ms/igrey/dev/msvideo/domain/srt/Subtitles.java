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
    private final List<Subtitle> subtitles;

    public List<Subtitle> mergedSubtitles() {
        LinkedNode<Subtitle> node = new LinkedNode<>(subtitles);
        do {
            if (shortShortOrShortIdealCase(node)) {
                node.setElm(node.elm().union(node.next().elm()));
                node = node.next().remove();
            } else {
                node = node.next();
            }
        } while (node != null && node.hasNext());
        return node.list().stream()
                .filter(subtitle -> subtitle.quality() == SubtitleQuality.IDEAL)
                .collect(Collectors.toList());
    }

    private boolean shortShortOrShortIdealCase(LinkedNode<Subtitle> subNode) {
        return subNode.elm().quality() == SubtitleQuality.SHORT &&
                (subNode.next().elm().quality() == SubtitleQuality.SHORT || subNode.next().elm().quality() == SubtitleQuality.IDEAL);
    }


}
