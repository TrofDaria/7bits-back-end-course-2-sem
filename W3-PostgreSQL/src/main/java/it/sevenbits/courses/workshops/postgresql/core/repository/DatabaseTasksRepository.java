package it.sevenbits.courses.workshops.postgresql.core.repository;

import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Repository that stores tasks in PostgreSQL database.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class DatabaseTasksRepository implements TasksRepository {
    private JdbcOperations jdbcOperations;

    /**
     * Constructor.
     *
     * @param jdbcOperations - JdbcOperations to use for database manipulation.
     */
    public DatabaseTasksRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    /**
     * Returns all task with given status.
     *
     * @param status - task status.
     * @return tasks.
     */
    @Override
    public List<Task> getTasksByStatus(final String status) {
        return jdbcOperations.query(
                "SELECT id, text, status, created_at, updated_at FROM task WHERE status = ?",
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String text = resultSet.getString("text");
                    Date createdAt = resultSet.getTimestamp("created_at");
                    Date updatedAt = resultSet.getTimestamp("updated_at");
                    return new Task(id, text, status, createdAt, updatedAt);
                }, status);
    }

    /**
     * Creates new task based on a given task.
     *
     * @param task - task.
     * @return new task.
     */
    @Override
    public Task create(final Task task) {
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                task.getId(), task.getText(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()
        );
        if (rows == 1) {
            return getTask(task.getId());
        } else {
            return null;
        }
    }

    /**
     * Returns task by id.
     *
     * @param id - task id.
     * @return task.
     */
    @Override
    public Task getTask(final UUID id) {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT  text, status, created_at, updated_at FROM task WHERE id = ?",
                    (resultSet, i) -> {
                        String text = resultSet.getString("text");
                        String status = resultSet.getString("status");
                        Date createdAt = resultSet.getTimestamp("created_at");
                        Date updatedAt = resultSet.getTimestamp("updated_at");
                        return new Task(id, text, status, createdAt, updatedAt);
                    }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Deletes task by id.
     *
     * @param id - task id.
     * @return true if removal was successful.
     */
    @Override
    public boolean deleteTask(final UUID id) {
        int rows = jdbcOperations.update("DELETE FROM task WHERE id = ?", id);
        return rows == 1;
    }

    /**
     * Updates task by id according to given task.
     *
     * @param id   - task id.
     * @param task - task.
     * @return updated task.
     */
    @Override
    public Task updateTask(final UUID id, final Task task) {
        int rows = jdbcOperations.update("UPDATE task SET text = ?, status = ?, updated_at = ? WHERE id = ?",
                task.getText(),
                task.getStatus(),
                new Date(),
                id);
        if (rows == 1) {
            return getTask(id);
        } else {
            return null;
        }
    }
}
