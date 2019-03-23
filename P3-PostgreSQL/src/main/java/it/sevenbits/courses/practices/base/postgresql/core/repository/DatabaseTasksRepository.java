package it.sevenbits.courses.practices.base.postgresql.core.repository;

import it.sevenbits.courses.practices.base.postgresql.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DatabaseTasksRepository implements TasksRepository {
    private JdbcOperations jdbcOperations;

    public DatabaseTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> getAllTasks() {
        return jdbcOperations.query(
                "SELECT id, text, status, createdAt FROM task",
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString(1));
                    String text = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    Date createdAt = resultSet.getTimestamp(4);
                    return new Task(id, text, status, createdAt);
                });
    }

    @Override
    public Task create(Task newTask) {
        UUID id = getNextId();
        String text = newTask.getText();
        String status = newTask.getStatus();
        Date createdAt = new Date();
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, text, status, createdAt) VALUES (?, ?, ?, ?)",
                id, text, status, createdAt
        );
        return new Task(id, text, status, createdAt);
    }

    private UUID getNextId() {
        return UUID.randomUUID();
    }

    @Override
    public Task getTask(UUID uuid) {
        return null;
    }

    @Override
    public List<Task> getTasksByStatus(String status) {
        return null;
    }


    @Override
    public boolean deleteTask(UUID uuid) {
        return false;
    }

    @Override
    public boolean updateTask(UUID id, Task task) {
        return false;
    }
}
