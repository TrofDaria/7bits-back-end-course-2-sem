package it.sevenbits.courses.homeworks.base.spring.web.controllers;

import it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository;
import it.sevenbits.courses.homeworks.base.spring.core.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
@RequestMapping("/tasks/{id}")
public class TaskController {
    private final TasksRepository TasksRepository;

    public TaskController(TasksRepository TasksRepository) {
        this.TasksRepository = TasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@Valid @PathVariable("id") UUID id) {
        Task task = TasksRepository.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTask(@NotNull @Valid @PathVariable("id") UUID id) {
        if (!TasksRepository.deleteTask(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity create(@Valid @NotNull @PathVariable("id") UUID id, @NotNull @RequestBody Task newTask) {
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