package ms.igrey.dev.gateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    @Value("${spring.application.name}")
    private String myAppName;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public DocumentationController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        for (String serviceName : discoveryClient.getServices()){
            if (myAppName.equals(serviceName)) continue;
            resources.add(swaggerResource(serviceName));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/"+name+"/v2/api-docs");
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder() //
                .displayRequestDuration(true) //
                .validatorUrl(StringUtils.EMPTY) // Disable the validator to avoid "Error" at the bottom of the Swagger UI page
                .build();
    }

}