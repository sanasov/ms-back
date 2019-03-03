package ms.igrey.dev.msvideo.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.dto.VideoInfoDto;
import ms.igrey.dev.msvideo.service.MovieService;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final MovieService movieService;
    private final SubtitleService subtitleService;

    @GetMapping("test")
    public String test() {
        return "Hello";
    }

    @GetMapping("tests")
    public List<String> testList() {
        return Arrays.asList("Hello1", "Hello2");
    }

    @GetMapping(value = "video", produces = "video/mp4")
    public FileSystemResource video(@RequestParam("phrase") String phrase) {
        log.info(phrase);
        return movieService.getMovieFragmentByPhrase(phrase);
    }

    @GetMapping(value = "subtitles/{phrase}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getVideoInfos(@PathVariable("phrase") String phrase) {
        log.info(phrase);
        return new Gson().toJson(
                subtitleService.findByPhrase(phrase).stream()
                        .map(sub -> new VideoInfoDto(sub.numberSeq(), sub.filmId(), sub.lines()))
                        .collect(Collectors.toList())
        );
    }

//    @GetMapping(value = "video", produces="video/mp4")
//    public FileSystemResource video() throws FileNotFoundException {
//        return new FileSystemResource(ResourceUtils.getFile("classpath:IMG_7859.mov"));
//    }
}
