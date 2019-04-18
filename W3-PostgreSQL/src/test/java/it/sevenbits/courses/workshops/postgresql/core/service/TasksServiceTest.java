package it.sevenbits.courses.workshops.postgresql.core.service;

import it.sevenbits.courses.workshops.postgresql.core.repository.TasksRepository;
import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import it.sevenbits.courses.workshops.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.workshops.postgresql.web.model.UpdateTaskRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TasksServiceTest {
    private TasksRepository mockTasksRepository;
    private TasksService tasksService;

    @Before
    public void setup() {
        mockTasksRepository = mock(TasksRepository.class);
        tasksService = new TasksService(mockTasksRepository);
    }

    @Test
    public void testGetAllTasks(){
        List<Task> mockTasks = mock(List.class);
        when(mockTasksRepository.getTasksByStatus(anyString())).thenReturn(mockTasks);
        List<Task> tasks = tasksService.getTasks();
        verify(mockTasksRepository, times(1)).getTasksByStatus(anyString());

        assertSame(mockTasks, tasks);
    }

    @Test
    public void testGetTasksByStatus(){
        List<Task> mockTasks = mock(List.class);
        when(mockTasksRepository.getTasksByStatus(anyString())).thenReturn(mockTasks);
        List<Task> tasks = tasksService.getTasks();
        verify(mockTasksRepository, times(1)).getTasksByStatus(anyString());

        assertSame(mockTasks, tasks);
    }

    @Test
    public void testGetTask(){
        Task mockTask = mock(Task.class);
        when(mockTasksRepository.getTask(any())).thenReturn(mockTask);
        Task task = tasksService.getTask(any());
        verify(mockTasksRepository, times(1)).getTask(any());

        assertSame(mockTask, task);
    }

    @Test
    public void testDeleteTask(){
        boolean expected = true;
        when(mockTasksRepository.deleteTask(any())).thenReturn(expected);
        boolean actual = tasksService.deleteTask(any());
        verify(mockTasksRepository, times(1)).deleteTask(any());

        assertSame(expected, actual);
    }

    @Test
    public void testCreateTask(){
        Task mockTask = mock(Task.class);
        AddTaskRequest mockAddTaskRequest = mock(AddTaskRequest.class);
        when(mockAddTaskRequest.getText()).thenReturn("");
        when(mockTasksRepository.create(any())).thenReturn(mockTask);

        Task task = tasksService.create(mockAddTaskRequest);
        verify(mockTasksRepository, times(1)).create(any(Task.class));

    }

    @Test
    public void testUpdateTask(){
        Task mockTask = mock(Task.class);
        UpdateTaskRequest mockUpdateTaskRequest = mock(UpdateTaskRequest.class);
        when(mockUpdateTaskRequest.getStatus()).thenReturn("");
        when(mockUpdateTaskRequest.getText()).thenReturn("");
        when(mockTasksRepository.updateTask(any(), any())).thenReturn(mockTask);
        when(mockTasksRepository.getTask(any())).thenReturn(mockTask);

        Task task = tasksService.updateTask(any(), mockUpdateTaskRequest);

        verify(mockTasksRepository, times(1)).updateTask(any(), any());

        assertSame(mockTask, task);
    }

    @Test
    public void testIsTaskExists(){
        Task mockTask = mock(Task.class);
        when(mockTasksRepository.getTask(any())).thenReturn(mockTask);
        assertSame(true, tasksService.isTaskExists(any()));

    }

    @Test
    public void testIsStatusLegit(){
        assertSame(true, tasksService.isStatusLegit("done"));
    }
}
