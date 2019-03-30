package it.sevenbits.courses.practices.postgresql.web.controllers;

import it.sevenbits.courses.practices.postgresql.core.model.AddTaskRequest;
import it.sevenbits.courses.practices.postgresql.core.model.Task;
import it.sevenbits.courses.practices.postgresql.core.repository.TasksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;


/**
 * Controller for requests.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final it.sevenbits.courses.practices.postgresql.core.repository.TasksRepository TasksRepository;

    /**
     * Constructor.
     *
     * @param TasksRepository - tasks repository to use.
     */
    public TasksController(final TasksRepository TasksRepository) {
        this.TasksRepository = TasksRepository;
    }

    /**
     * Returns all tasks.
     *
     * @return ResponseEntity with tasks in body.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getAllTasks());
    }

    /**
     * Returns all tasks by status.
     *
     * @param status - status.
     * @return ResponseEntity with tasks in body.
     */
    @RequestMapping(method = RequestMethod.GET, params = "status")
    @ResponseBody
    public ResponseEntity<List<Task>> getAllByStatus(@NotNull @RequestParam("status") final String status) {
        if (!TasksRepository.isStatusLegit(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getTasksByStatus(status));
    }

    /**
     * Creates new task.
     *
     * @param addTaskRequest - add task request to use for creation.
     * @return ResponseEntity with created task.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@Valid @NotNull @RequestBody final AddTaskRequest addTaskRequest) {

        Task createdTask = TasksRepository.create(addTaskRequest);
        URI location = UriComponentsBuilder.fromPath("/Tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    /**
     * Returns task by id.
     *
     * @param id - id.
     * @return ResponseEntity with task in body.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@Valid @PathVariable("id") final UUID id) {
        Task task = TasksRepository.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    /**
     * Deletes task by id.
     *
     * @param id - id.
     * @return ResponseEntity with status.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@NotNull @Valid @PathVariable("id") final UUID id) {
        if (!TasksRepository.deleteTask(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Updates task.
     *
     * @param id      - id.
     * @param newTask - task to use.
     * @return ResponseEntity with status.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity patch(
            @Valid @NotNull @PathVariable("id") final UUID id,
            @NotNull @RequestBody final Task newTask) {
        if (!TasksRepository.isStatusLegit(newTask.getStatus()) && newTask.getStatus() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Task task = TasksRepository.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (!TasksRepository.updateTask(id, newTask)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
