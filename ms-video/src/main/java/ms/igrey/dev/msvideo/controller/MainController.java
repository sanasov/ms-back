package ms.igrey.dev.msvideo.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

@RestController
public class MainController {

    @GetMapping("test")
    public String test() {
        return "Hello";
    }

    @GetMapping(value = "video", produces="video/mp4")
    public FileSystemResource video() throws FileNotFoundException {
        return new FileSystemResource(ResourceUtils.getFile("classpath:IMG_7859.mov"));
    }
}
