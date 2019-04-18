package it.sevenbits.courses.workshops.postgresql.core.repository;

import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DatabaseTasksRepositoryTest {

    @Before
    public void setup() {

    }

    @Test
    public void testGetTasksByStatus() {
        JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
        DatabaseTasksRepository databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);

        String status = "done";
        List<Task> mockListTasks = mock(List.class);

        when(mockJdbcOperations.query(anyString(), any(RowMapper.class), anyVararg())).thenReturn(mockListTasks);

        List<Task> expectedList = databaseTasksRepository.getTasksByStatus(status);
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, text, status, created_at, updated_at FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq(status)
        );
        Assert.assertSame(expectedList, mockListTasks);
    }


    @Test
    public void testGetTask() {
        JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
        DatabaseTasksRepository databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);

        UUID id = UUID.randomUUID();
        Task mockTask = mock(Task.class);

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(mockTask);

        Task expectedTask = databaseTasksRepository.getTask(id);
        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT  text, status, created_at, updated_at FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id)
        );
        Assert.assertSame(expectedTask, mockTask);
    }

    @Test
    public void testDeleteTask() {
        JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
        DatabaseTasksRepository databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);

        UUID id = UUID.randomUUID();
        boolean expectedResult = true;
        when(mockJdbcOperations.update(anyString(), (String) any())).thenReturn(1);
        boolean actualResult = databaseTasksRepository.deleteTask(id);
        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                eq(id)
        );
        Assert.assertSame(expectedResult, actualResult);
    }

    @Test
    public void testCreateTask() {
        JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
        DatabaseTasksRepository databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);

        Task mockTask = mock(Task.class);
        when(mockJdbcOperations.update(anyString(), (String) any())).thenReturn(1);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(mockTask);
        Task actualTask = databaseTasksRepository.create(mockTask);
        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?)"),
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                eq(null)
        );
        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT  text, status, created_at, updated_at FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(null)
        );
        Assert.assertSame(mockTask, actualTask);
    }

    @Test
    public void testUpdateTask() {
        JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
        DatabaseTasksRepository databaseTasksRepository = new DatabaseTasksRepository(mockJdbcOperations);

        Task mockTask = mock(Task.class);
        when(mockJdbcOperations.update(anyString(), (String) any())).thenReturn(1);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(mockTask);
        Task actualTask = databaseTasksRepository.updateTask(null, mockTask);

        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updated_at = ? WHERE id = ?"),
                eq(null),
                eq(null),
                any(Date.class),
                eq(null)
        );

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT  text, status, created_at, updated_at FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(null)
        );
        Assert.assertSame(mockTask, actualTask);
    }

}

