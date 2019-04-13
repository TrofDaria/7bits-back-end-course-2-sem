package it.sevenbits.courses.practices.postgresql.core.service;

import it.sevenbits.courses.practices.postgresql.core.model.Task;
import it.sevenbits.courses.practices.postgresql.web.model.AddTaskRequest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


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

    /**
     * Constructor.
     */
    public TasksService() {
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
    public Task formTask(final AddTaskRequest addTaskRequest) {
        UUID id = getNextId();
        String text = addTaskRequest.getText();
        String status = defaultStatus;
        Date createdAt = new Date();
        return new Task(id, text, status, createdAt);
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
}
