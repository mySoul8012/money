package money;


import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class App 
{
    public static void main( String[] args )
    {
        int port = RandomUtils.nextInt(1000,4000);
        System.out.println(port);
        new SpringApplicationBuilder(App.class).properties("server.port=" + port).run(args);
    }
}
