package it.sevenbits.courses.workshops.postgresql.web.controllers;

import it.sevenbits.courses.workshops.postgresql.core.model.Task;
import it.sevenbits.courses.workshops.postgresql.core.service.TasksService;
import it.sevenbits.courses.workshops.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.workshops.postgresql.web.model.UpdateTaskRequest;
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
import javax.validation.constraints.NotBlank;
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
    private TasksService tasksService;

    /**
     * Constructor.
     *
     * @param tasksService - tasks service to use.
     */
    public TasksController(final TasksService tasksService) {
        this.tasksService = tasksService;

    }

    /**
     * Returns all tasks.
     *
     * @return ResponseEntity with tasks in body.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(tasksService.getTasks());
    }

    /**
     * Returns all tasks by status.
     *
     * @param status - status.
     * @return ResponseEntity with tasks in body.
     */
    @RequestMapping(method = RequestMethod.GET, params = "status")
    @ResponseBody
    public ResponseEntity<List<Task>> getAllByStatus(@RequestParam("status") final String status) {
        if (!tasksService.isStatusLegit(status)) {
            return ResponseEntity.status(HttpStatus.OK).body(tasksService.getTasks());
        }
        return ResponseEntity.status(HttpStatus.OK).body(tasksService.getTasks(status));
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
        Task createdTask = tasksService.create(addTaskRequest);
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
        if (!tasksService.isTaskExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        Task task = tasksService.getTask(id);
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
        if (!tasksService.isTaskExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        tasksService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Updates task.
     *
     * @param id                - id.
     * @param updateTaskRequest - update task request.
     * @return ResponseEntity with status.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity patch(
            @Valid @NotNull @PathVariable("id") final UUID id,
            @Valid @NotBlank @RequestBody final UpdateTaskRequest updateTaskRequest) {
        if (!tasksService.isTaskExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (updateTaskRequest.getStatus() != null) {
            if (!tasksService.isStatusLegit(updateTaskRequest.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        if (updateTaskRequest.getStatus() == null && updateTaskRequest.getText() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (tasksService.updateTask(id, updateTaskRequest) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
