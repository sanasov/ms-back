package ms.igrey.dev.gateway.config;

import com.netflix.zuul.ZuulFilter;
import ms.igrey.dev.gateway.filters.SimpleLoggingPreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by max on 30.06.17.
 */
@Configuration
public class ZuulConfiguration {

    @Bean
    public ZuulFilter simplePreFilter() {
        return new SimpleLoggingPreFilter();
    }
}
