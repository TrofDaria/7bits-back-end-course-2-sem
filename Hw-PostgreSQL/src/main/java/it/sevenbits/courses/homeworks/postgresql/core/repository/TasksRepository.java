package it.sevenbits.courses.homeworks.postgresql.core.repository;

import it.sevenbits.courses.homeworks.postgresql.core.model.Task;

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
     * Creates new task based on a given task.
     *
     * @param task - task.
     * @return new task.
     */
    Task create(final Task task);

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
     * Updates task by id according to given update task request.
     *
     * @param id   - task id.
     * @param task - task.
     * @return updated task.
     */
    Task updateTask(final UUID id, Task task);

    /**
     * Returns amount of tasks with given status.
     *
     * @param status - task status.
     * @return amount of tasks.
     */
    int getTasksAmount(final String status);

    /**
     * Returns task with demanded status, sorted by given order, with limit number and offset.
     *
     * @param status - task status.
     * @param order  - order to sort tasks.
     * @param limit  - maximum amount of tasks to return.
     * @param offset - offset.
     * @return tasks.
     */
    List<Task> getTasks(final String status, final String order, final int limit, final int offset);
}
