package it.sevenbits.courses.practices.base.spring.web.controllers;

import it.sevenbits.courses.practices.base.spring.core.repository.Item;
import it.sevenbits.courses.practices.base.spring.core.repository.ItemsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final ItemsRepository itemsRepository;

    public TasksController(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getAll() {
        return itemsRepository.getAllItems();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Item> create(@RequestBody Item newItem) {
        if (newItem.getName() == null) {
            throw new InvalidInputException();
        }
        Item createdItem = itemsRepository.create(newItem);
        URI location = UriComponentsBuilder.fromPath("/items/")
                .path(String.valueOf(createdItem.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdItem);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input")
    public class InvalidInputException extends RuntimeException {
    }
}
