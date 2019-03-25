package ms.igrey.dev.msvideo.domain.srt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ms.igrey.dev.msvideo.domain.VideoInterval;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<Subtitle> partOfSubtitles(Integer partNumber, Integer partsAmount) {
        VideoInterval interval = new VideoInterval(subtitles.size(), partsAmount);
        Long diffTimeMillis = subtitles.get(interval.startNumSeq(partNumber)).startOffset();
        return subtitles.subList(interval.startNumSeq(partNumber), interval.endNumSeq(partNumber) + 1).stream()
                .map(subtitle -> subtitle.shiftedSubtitle(diffTimeMillis))
                .collect(Collectors.toList());
    }

    private boolean shortShortOrShortIdealCase(LinkedNode<Subtitle> subNode) {
        return subNode.elm().quality() == SubtitleQuality.SHORT &&
                (subNode.next().elm().quality() == SubtitleQuality.SHORT || subNode.next().elm().quality() == SubtitleQuality.IDEAL);
    }

}


