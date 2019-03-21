package it.sevenbits.courses.homeworks.base.spring.web.controllers;

import it.sevenbits.courses.homeworks.base.spring.core.model.Task;
import it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final it.sevenbits.courses.homeworks.base.spring.core.repository.TasksRepository TasksRepository;

    public TasksController(TasksRepository TasksRepository) {
        this.TasksRepository = TasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getAll(@Nullable @RequestBody String status) {
        if(status==null){
            return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getAllTasks());
        }
        return ResponseEntity.status(HttpStatus.OK).body(TasksRepository.getTasksByStatus(status));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@NotNull @NotEmpty @RequestBody Task newTask) {
        Task createdTask = TasksRepository.create(newTask);
        URI location = UriComponentsBuilder.fromPath("/Tasks/")
                .path(String.valueOf(createdTask.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdTask);
    }
}
