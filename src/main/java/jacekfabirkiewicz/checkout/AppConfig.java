package jacekfabirkiewicz.checkout;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    public @Bean MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "checkout");
    }

    @Primary @Bean
    public MongoOperations mongoOperations() {
        return (MongoOperations) mongoTemplate();
    }
}
