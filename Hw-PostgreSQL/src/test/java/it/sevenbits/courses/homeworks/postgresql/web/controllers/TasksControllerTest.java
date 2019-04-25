package it.sevenbits.courses.homeworks.postgresql.web.controllers;

import it.sevenbits.courses.homeworks.postgresql.core.model.Task;
import it.sevenbits.courses.homeworks.postgresql.core.service.TasksService;
import it.sevenbits.courses.homeworks.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.homeworks.postgresql.web.model.GetTasksRequest;
import it.sevenbits.courses.homeworks.postgresql.web.model.GetTasksResponse;
import it.sevenbits.courses.homeworks.postgresql.web.model.UpdateTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public void testGetTasks() {
        GetTasksResponse mockResponse = mock(GetTasksResponse.class);
        when(mockTasksService.createGetTasksResponse(any(GetTasksRequest.class))).thenReturn(mockResponse);
        ResponseEntity<GetTasksResponse> answer = tasksController.getTasks("", "",
                0, 0);

        verify(mockTasksService, times(1)).createGetTasksResponse(any(GetTasksRequest.class));
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockResponse, answer.getBody());
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
