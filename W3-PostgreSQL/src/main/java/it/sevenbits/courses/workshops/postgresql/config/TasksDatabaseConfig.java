package it.sevenbits.courses.workshops.postgresql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configures tasks database.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
@Configuration
public class TasksDatabaseConfig {

    /**
     * Returns jdbsOperations object for database manipulation.
     *
     * @param tasksDataSource - task data source.
     * @return jdbsOperations.
     */
    @Bean
    @Qualifier("tasksJdbcOperations")
    public JdbcOperations tasksJdbcOperations(@Qualifier("tasksDataSource") final DataSource tasksDataSource) {
        return new JdbcTemplate(tasksDataSource);
    }

    /**
     * Returns data source.
     *
     * @return data source.
     */
    @Bean
    @Qualifier("tasksDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tasks")
    @FlywayDataSource
    public DataSource tasksDataSource() {
        return DataSourceBuilder.create().build();
    }

}
