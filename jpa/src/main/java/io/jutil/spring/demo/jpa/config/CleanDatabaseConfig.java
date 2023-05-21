package io.jutil.spring.demo.jpa.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2023-05-12
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "clean-database", havingValue = "true")
public class CleanDatabaseConfig {

    @Bean
    public CleanDatabase cleanDatabase(Flyway flyway) {
        log.info("Clean database is enabled");
        return new CleanDatabase(flyway);
    }

    @Slf4j
    public static class CleanDatabase implements ApplicationRunner {
        private final Flyway flyway;

        public CleanDatabase(Flyway flyway) {
            this.flyway = flyway;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            flyway.clean();
            flyway.migrate();
        }
    }
}
