package it.sevenbits.courses.homeworks.postgresql.config;

import it.sevenbits.courses.homeworks.postgresql.core.repository.DatabaseTasksRepository;
import it.sevenbits.courses.homeworks.postgresql.core.service.TasksService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Configures repository.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
@Configuration
public class RepositoryConfig {

    /**
     * Creates service with repository for tasks.
     *
     * @param jdbcOperations - jdbcOperations to pass to repository.
     * @return service.
     */
    @Bean
    public TasksService tasksService(
            @Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new TasksService(new DatabaseTasksRepository(jdbcOperations));
    }
}
