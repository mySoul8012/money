package money;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class App 
{
    public static void main( String[] args )
    {
        int port = 8090;
        new SpringApplicationBuilder(App.class).properties("server.port=" + port).run(args);
    }
}
