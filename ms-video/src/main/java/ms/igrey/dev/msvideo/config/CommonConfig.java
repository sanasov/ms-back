package ms.igrey.dev.msvideo.config;

import ms.igrey.dev.msvideo.repository.SubtitleRepository;
import ms.igrey.dev.msvideo.service.MovieService;
import ms.igrey.dev.msvideo.service.OmdbFilmMetaInfoService;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Autowired
    private DaoConfig daoConfig;
    @Autowired
    private SubtitleRepository subtitleRepository;

    @Bean
    public SubtitleService subtitleService() {
        return new SubtitleService(subtitleRepository);
    }

    @Bean
    public OmdbFilmMetaInfoService OmdbFilmService() {
        return new OmdbFilmMetaInfoService(daoConfig.filmRepository(), daoConfig.srtRepository());
    }

    @Bean
    public MovieService movieService() {
        return new MovieService(daoConfig.movieRepository(), subtitleService());
    }
}
