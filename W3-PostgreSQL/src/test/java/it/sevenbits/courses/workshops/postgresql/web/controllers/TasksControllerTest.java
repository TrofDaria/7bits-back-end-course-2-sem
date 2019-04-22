package it.sevenbits.courses.workshops.postgresql.web.controllers;

import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import it.sevenbits.courses.workshops.postgresql.core.service.TasksService;
import it.sevenbits.courses.workshops.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.workshops.postgresql.web.model.UpdateTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;


public class TasksControllerTest {
    private TasksService mockTasksService;
    private TasksController tasksController;

    @Before
    public void setup() {
        mockTasksService = mock(TasksService.class);
        tasksController = new TasksController(mockTasksService);
    }

    @Test
    public void testGetAll() {
        List<Task> mockTasks = mock(List.class);
        when(mockTasksService.getTasks()).thenReturn(mockTasks);
        ResponseEntity<List<Task>> answer = tasksController.getAll();
        verify(mockTasksService, times(1)).getTasks();
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTasks, answer.getBody());
    }

    @Test
    public void testGetAllByStatus() {
        List<Task> mockTasks = mock(List.class);
        when(mockTasksService.getTasks(anyString())).thenReturn(mockTasks);
        when(mockTasksService.isStatusLegit(anyString())).thenReturn(true);
        ResponseEntity<List<Task>> answer = tasksController.getAllByStatus(anyString());
        verify(mockTasksService, times(1)).getTasks(anyString());
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTasks, answer.getBody());
    }

    @Test
    public void testGetTaskById() {
        Task mockTask = mock(Task.class);
        when(mockTasksService.getTask(any())).thenReturn(mockTask);
        when(mockTasksService.isTaskExists(any())).thenReturn(true);
        ResponseEntity<Task> answer = tasksController.getTaskById(any());
        verify(mockTasksService, times(1)).getTask(any());
        verify(mockTasksService, times(1)).isTaskExists(any());
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testDeleteTask() {
        when(mockTasksService.deleteTask(any())).thenReturn(true);
        when(mockTasksService.isTaskExists(any())).thenReturn(true);
        ResponseEntity answer = tasksController.deleteTask(any());
        verify(mockTasksService, times(1)).deleteTask(any());
        verify(mockTasksService, times(1)).isTaskExists(any());
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(null, answer.getBody());
    }

    @Test
    public void testCreate() {
        Task mockTask = mock(Task.class);
        when(mockTasksService.create(any())).thenReturn(mockTask);

        ResponseEntity answer = tasksController.create(any(AddTaskRequest.class));

        verify(mockTasksService, times(1)).create(any());
        assertEquals(HttpStatus.CREATED, answer.getStatusCode());
    }

    @Test
    public void testPatch() {
        Task mockTask = mock(Task.class);
        UpdateTaskRequest mockUpdateTaskRequest = mock(UpdateTaskRequest.class);

        when(mockTasksService.updateTask(any(UUID.class), any(UpdateTaskRequest.class))).thenReturn(mockTask);
        when(mockTasksService.isTaskExists(any())).thenReturn(true);
        when(mockTasksService.isUpdateTaskRequestValid(any())).thenReturn(true);
        ResponseEntity answer = tasksController.patch(UUID.randomUUID(), mockUpdateTaskRequest);
        verify(mockTasksService, times(1)).updateTask(any(UUID.class), any(UpdateTaskRequest.class));
        verify(mockTasksService, times(1)).isTaskExists(any());
        verify(mockTasksService, times(1)).isUpdateTaskRequestValid(mockUpdateTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }

}
