package money;

import brave.sampler.Sampler;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class TrendTradingBackTestViewApplication {
    public static void main(String[] args){
        int port = RandomUtils.nextInt(1000,4000);
        new SpringApplicationBuilder(TrendTradingBackTestViewApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
