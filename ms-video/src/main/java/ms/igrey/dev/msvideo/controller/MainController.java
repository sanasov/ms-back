package ms.igrey.dev.msvideo.controller;

import ms.igrey.dev.msvideo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return movieService.getMovieFragmentByPhrase(phrase);
    }
}
