package money;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class App 
{
    public static void main( String[] args )
    {
        int port = 8761;
        new SpringApplicationBuilder(App.class).properties("server.port=" + port).run(args);
    }
}
