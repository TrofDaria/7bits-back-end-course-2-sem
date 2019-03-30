package it.sevenbits.courses.practices.postgresql.config;

import it.sevenbits.courses.practices.postgresql.core.repository.DatabaseTasksRepository;
import it.sevenbits.courses.practices.postgresql.core.repository.TasksRepository;
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
     * Creates repository for tasks.
     *
     * @param jdbcOperations - jdbcOperations to pass to repository.
     * @return repository.
     */
    @Bean
    public TasksRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }
}
