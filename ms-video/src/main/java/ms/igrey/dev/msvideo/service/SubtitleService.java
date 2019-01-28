package ms.igrey.dev.msvideo.service;

import lombok.RequiredArgsConstructor;
import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.repository.SubtitleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubtitleService {

    private final SubtitleRepository subtitleRepository;
    private final static PageRequest PAGE_REQUEST = PageRequest.of(0, 10);

    public void save(Subtitles subtitles) {
        subtitleRepository.saveAll(
                subtitles
                        .subtitles().stream()
                        .map(sub -> Subtitle.toEntity(sub))
                        .collect(Collectors.toList())
        );
    }

    public List<Subtitle> findByPhrase(String phraseOrWord) {
        return subtitleRepository.findByLinesContaining(phraseOrWord, PAGE_REQUEST)
                .stream()
                .map(entity -> new Subtitle(entity))
                .collect(Collectors.toList());
    }

}
