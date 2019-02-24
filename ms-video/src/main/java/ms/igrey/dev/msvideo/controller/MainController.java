package ms.igrey.dev.msvideo.controller;

import lombok.extern.slf4j.Slf4j;
import ms.igrey.dev.msvideo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    private final MovieService movieService;

    @Autowired
    public MainController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("test")
    public String test() {
        return "Hello";
    }

    @GetMapping(value = "video", produces = "video/mp4")
    public FileSystemResource video(@RequestParam("phrase") String phrase) {
        log.info(phrase);
        return movieService.getMovieFragmentByPhrase(phrase);
    }

//    @GetMapping(value = "video", produces="video/mp4")
//    public FileSystemResource video() throws FileNotFoundException {
//        return new FileSystemResource(ResourceUtils.getFile("classpath:IMG_7859.mov"));
//    }
}
