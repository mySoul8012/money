package money;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class IndexConfigServerApplication {
    public static void main(String[] args) {
        int port = 8060;
        new SpringApplicationBuilder(IndexConfigServerApplication.class).properties("server.port=" + port).run(args);
    }
}
