package it.sevenbits.courses.workshops.postgresql.core.service;

import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import it.sevenbits.courses.workshops.postgresql.core.repository.TasksRepository;
import it.sevenbits.courses.workshops.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.workshops.postgresql.web.model.UpdateTaskRequest;

import java.util.*;


/**
 * Service for tasks.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class TasksService {
    private Set<String> availableStatuses;

    private final String defaultStatus = "inbox";
    private TasksRepository tasksRepository;

    /**
     * Constructor.
     *
     * @param tasksRepository - tasks repository to use.
     */
    public TasksService(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        availableStatuses = new HashSet<>();
        availableStatuses.add("inbox");
        availableStatuses.add("done");
    }

    /**
     * Creates task based on add task request.
     *
     * @param addTaskRequest - add task request.
     * @return created task.
     */
    private Task formTask(final AddTaskRequest addTaskRequest) {
        UUID id = getNextId();
        String text = addTaskRequest.getText();
        String status = defaultStatus;
        Date createdAt = new Date();
        return new Task(id, text, status, createdAt, createdAt);
    }

    /**
     * Creates task based on updated task request and existing task.
     *
     * @param updateTaskRequest - update task request.
     * @return created task.
     */
    private Task formUpdatedTask(final Task task, final UpdateTaskRequest updateTaskRequest) {
        String text = task.getText();
        String status = task.getStatus();
        if (updateTaskRequest.getText() != null) {
            text = updateTaskRequest.getText();
        }
        if (updateTaskRequest.getStatus() != null) {
            status = updateTaskRequest.getStatus();
        }
        return new Task(task.getId(), text, status, task.getCreatedAt(), task.getUpdatedAt());
    }

    /**
     * Returns id for new task.
     *
     * @return id.
     */
    private UUID getNextId() {
        return UUID.randomUUID();
    }

    /**
     * Determines whether status is legit or not.
     *
     * @param status - status.
     * @return true if status legit, false otherwise.
     */
    public boolean isStatusLegit(final String status) {
        return availableStatuses.contains(status);
    }

    /**
     * Determines whether task with id exists or not.
     *
     * @param id - id.
     * @return true if status legit, false otherwise.
     */
    public boolean isTaskExists(final UUID id) {
        return tasksRepository.getTask(id) != null;
    }

    /**
     * Returns all task with given status.
     *
     * @return tasks.
     */
    public List<Task> getTasks() {
        return tasksRepository.getTasksByStatus(defaultStatus);
    }

    /**
     * Returns all task with given status.
     *
     * @param status - task status.
     * @return tasks.
     */
    public List<Task> getTasks(final String status) {
        return tasksRepository.getTasksByStatus(status);
    }

    /**
     * Creates new task based on a given update task request.
     *
     * @param addTaskRequest - update task request.
     * @return new task.
     */
    public Task create(final AddTaskRequest addTaskRequest) {
        return tasksRepository.create(formTask(addTaskRequest));
    }

    /**
     * Returns task by id.
     *
     * @param id - task id.
     * @return task.
     */
    public Task getTask(final UUID id) {
        return tasksRepository.getTask(id);
    }

    /**
     * Deletes task by id.
     *
     * @param id - task id.
     * @return true if removal was successful.
     */
    public boolean deleteTask(final UUID id) {
        return tasksRepository.deleteTask(id);
    }

    /**
     * Updates task by id according to given update task request.
     *
     * @param id                - task id.
     * @param updateTaskRequest - update task request.
     * @return updated task.
     */
    public Task updateTask(final UUID id, final UpdateTaskRequest updateTaskRequest) {
        return tasksRepository.updateTask(
                id,
                formUpdatedTask(tasksRepository.getTask(id), updateTaskRequest));
    }
}
