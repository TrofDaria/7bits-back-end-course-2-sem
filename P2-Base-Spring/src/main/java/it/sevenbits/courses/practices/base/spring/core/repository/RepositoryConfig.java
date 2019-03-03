package it.sevenbits.courses.practices.base.spring.core.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public ItemsRepository itemsRepository() {
        return new TasksRepository();
    }
}
