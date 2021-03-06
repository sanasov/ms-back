package ms.igrey.dev.msvideo.config;

import ms.igrey.dev.msvideo.repository.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Configuration
@EnableElasticsearchRepositories(basePackages = "ms.igrey.dev.msvideo.repository")
public class DaoConfig {

    //    @Value("${elasticsearch.host}")
    private String esHost = "localhost";

    //    @Value("${elasticsearch.home}")
    private String elasticsearchHome;

    //    @Value("${elasticsearch.cluster.name}")
    private String esClusterName;

    //    @Value("${elasticsearch.port}")
    private Integer esPort = 9300;


    @Bean
    public Client client() {
        TransportClient client = null;
        try {
            final Settings esSettings = Settings.builder()
// TODO: what is this?
//                    .put("client.transport.sniff", true)
//                    .put("path.home", elasticsearchHome)
                    .put("cluster.name", esClusterName)
                    .build();
            client = new PreBuiltTransportClient(esSettings);
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

    @Bean
    public SrtRepository srtRepository() {
        return new GoogleDriveSrtRepository();
    }

    @Bean
    public FilmMetaInfoRepository filmRepository() {
        return new OmdbFilmMetaInfoRepository();
    }

    @Bean
    public MovieRepository movieRepository() {
        return new FileSystemMovieRepository();
    }
}