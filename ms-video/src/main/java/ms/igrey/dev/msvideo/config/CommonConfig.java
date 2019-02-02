package ms.igrey.dev.msvideo.config;

import ms.igrey.dev.msvideo.repository.SubtitleRepository;
import ms.igrey.dev.msvideo.service.OmdbFilmService;
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
    public SubtitleService SubtitleService() {
        return new SubtitleService(subtitleRepository);
    }

    @Bean
    public OmdbFilmService OmdbFilmService() {
        return new OmdbFilmService(daoConfig.filmRepository(), daoConfig.srtRepository());
    }
}
