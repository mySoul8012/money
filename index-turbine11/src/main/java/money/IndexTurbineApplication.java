package money;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class IndexTurbineApplication {
    public static void main(String[] args) {
        int port = 8080;
        new SpringApplicationBuilder(IndexTurbineApplication.class).properties("server.port=" + port).run(args);
    }
}