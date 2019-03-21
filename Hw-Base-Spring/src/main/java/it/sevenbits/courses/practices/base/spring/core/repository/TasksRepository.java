package it.sevenbits.courses.practices.base.spring.core.repository;


import it.sevenbits.courses.practices.base.spring.core.model.Task;

import java.util.List;
import java.util.UUID;

public interface TasksRepository {
    List<Task> getAllTasks();
    Task create(Task Task);
    Task getTask(UUID id);
    List<Task> getTasksByStatus(String status);
    boolean deleteTask(UUID id);
    boolean updateTask(UUID id, Task task);

}
