package money;

import brave.sampler.Sampler;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCaching
public class IndexGatherStoreApplication {
    public static void main(String[] args) {

        int port = RandomUtils.nextInt(1000,4000);
        new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);
    	
    }
    
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
