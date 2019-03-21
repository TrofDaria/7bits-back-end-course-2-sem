package it.sevenbits.courses.homeworks.base.spring.config;

import it.sevenbits.courses.homeworks.base.spring.core.repository.InMemoryTasksRepository;
import it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public TasksRepository TasksRepository() {
        return new InMemoryTasksRepository();
    }
}
