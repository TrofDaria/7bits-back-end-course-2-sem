package it.sevenbits.courses.practices.postgresql.core.repository;

import it.sevenbits.courses.practices.postgresql.core.model.AddTaskRequest;
import it.sevenbits.courses.practices.postgresql.core.model.Task;

import java.util.List;
import java.util.UUID;

/**
 * Interface for task repositories.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public interface TasksRepository {

    /**
     * Returns all tasks.
     *
     * @return tasks.
     */
    List<Task> getAllTasks();

    /**
     * Creates new task based on a given task.
     *
     * @param addTaskRequest - add task request.
     * @return new task.
     */
    Task create(final AddTaskRequest addTaskRequest);

    /**
     * Returns task by id.
     *
     * @param id - task id.
     * @return task.
     */
    Task getTask(final UUID id);

    /**
     * Returns all task with given status.
     *
     * @param status - task status.
     * @return tasks.
     */
    List<Task> getTasksByStatus(final String status);

    /**
     * Deletes task by id.
     *
     * @param id - task id.
     * @return true if removal was successful.
     */
    boolean deleteTask(final UUID id);

    /**
     * Updates task by id according to given task.
     *
     * @param id   - task id.
     * @param task - task.
     * @return true if update was successful.
     */
    boolean updateTask(final UUID id, final Task task);

    /**
     * Determines whether status is legit or not.
     *
     * @param status - status.
     * @return true if status legit, false otherwise.
     */
    boolean isStatusLegit(final String status);

}
