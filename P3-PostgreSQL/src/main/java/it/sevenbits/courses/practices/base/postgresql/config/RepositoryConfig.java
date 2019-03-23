package it.sevenbits.courses.practices.base.postgresql.config;

import it.sevenbits.courses.practices.base.postgresql.core.repository.DatabaseTasksRepository;
import it.sevenbits.courses.practices.base.postgresql.core.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class RepositoryConfig {
    @Bean
    public TasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }
}
