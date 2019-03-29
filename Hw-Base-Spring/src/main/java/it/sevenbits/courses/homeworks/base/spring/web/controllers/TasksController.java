package it.sevenbits.courses.homeworks.base.spring.web.controllers;

import it.sevenbits.courses.homeworks.base.spring.core.model.Task;
import it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository TasksRepository;

    public TasksController(TasksRepository TasksRepository) {
        this.TasksRepository = TasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, params = "status")
    @ResponseBody
    public ResponseEntity<List<Task>> getAllByStatus(@NotNull @RequestParam("status") String status) {
        if (!TasksRepository.isStatusLegit(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getTasksByStatus(status));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@NotNull @Valid @RequestBody Task newTask) {
        if (!TasksRepository.isStatusLegit(newTask.getStatus()) && newTask.getStatus() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Task createdTask = TasksRepository.create(newTask);
        URI location = UriComponentsBuilder.fromPath("/Tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@Valid @PathVariable("id") UUID id) {
        Task task = TasksRepository.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@NotNull @Valid @PathVariable("id") UUID id) {
        if (!TasksRepository.deleteTask(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity patch(@Valid @NotNull @PathVariable("id") UUID id, @NotNull @RequestBody Task newTask) {
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
