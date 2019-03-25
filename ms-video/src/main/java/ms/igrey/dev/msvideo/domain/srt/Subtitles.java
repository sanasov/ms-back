package ms.igrey.dev.msvideo.domain.srt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class Subtitles {
    private final List<Subtitle> subtitles;

    public String filmId() {
        return subtitles.get(0).filmId();
    }

    public List<Subtitle> mergedSubtitles() {
        LinkedNode<Subtitle> node = new LinkedNode<>(subtitles);
        do {
            if (isNotFinishedSentence(node) || shortShortOrShortIdealCase(node)) {
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

    private boolean isNotFinishedSentence(LinkedNode<Subtitle> node) {
        return !node.elm().isFinished() && !node.next().elm().startWithNewSentence();
    }

    private boolean shortShortOrShortIdealCase(LinkedNode<Subtitle> subNode) {
        return subNode.elm().quality() == SubtitleQuality.SHORT &&
                (subNode.next().elm().quality() == SubtitleQuality.SHORT || subNode.next().elm().quality() == SubtitleQuality.IDEAL);
    }


}
