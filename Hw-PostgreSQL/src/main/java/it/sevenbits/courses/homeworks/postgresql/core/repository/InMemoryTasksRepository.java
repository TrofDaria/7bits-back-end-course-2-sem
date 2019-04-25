package it.sevenbits.courses.homeworks.postgresql.core.repository;

import it.sevenbits.courses.homeworks.postgresql.core.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository for task stored in memory.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
@Deprecated
public class InMemoryTasksRepository implements TasksRepository {
    private Map<UUID, Task> tasks;

    /**
     * Constructor.
     */
    public InMemoryTasksRepository() {
        tasks = new HashMap<>();
    }


    /**
     * Returns task by id.
     *
     * @param id - task id.
     * @return task.
     */
    @Override
    public Task getTask(final UUID id) {
        return tasks.get(id);
    }

    /**
     * Deletes task by id.
     *
     * @param id - task id.
     * @return true if removal was successful.
     */
    @Override
    public boolean deleteTask(final UUID id) {
        return tasks.remove(id) != null;
    }

    /**
     * Creates new task based on a given task.
     *
     * @param task - task.
     * @return new task.
     */
    @Override
    public Task create(final Task task) {
        Date createdAt = new Date();
        Task newTask = new Task(getNextID(), task.getText(), "inbox", createdAt, createdAt);
        tasks.put(newTask.getId(), task);
        return newTask;

    }

    /**
     * Returns id for new task.
     *
     * @return id.
     */
    private UUID getNextID() {
        return UUID.randomUUID();
    }


    /**
     * Returns all task with given status.
     *
     * @param status - task status.
     * @return tasks.
     */
    @Override
    public List<Task> getTasksByStatus(final String status) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if ((task.getStatus()).equals(status)) {
                foundTasks.add(task);
            }
        }
        return Collections.unmodifiableList(foundTasks);
    }

    /**
     * Updates task by id according to given task.
     *
     * @param id   - task id.
     * @param task - update task request.
     * @return updated task.
     */
    @Override
    public Task updateTask(final UUID id, final Task task) {
        Task oldTask = tasks.get(id);
        String newText = task.getText();
        boolean modified = false;
        if (newText != null) {
            oldTask.setText(newText);
            modified = true;
        }
        String newStatus = task.getStatus();
        if (newStatus != null) {
            oldTask.setStatus(newStatus);
            modified = true;
        }
        return null;
    }

    /**
     * Returns amount of tasks with given status.
     *
     * @param status - task status.
     * @return amount of tasks.
     */
    @Override
    public int getTasksAmount(final String status) {
        return 0;
    }

    @Override
    public List<Task> getTasks(final String status, final String order, final int limit, final int offset) {
        return null;
    }

}
