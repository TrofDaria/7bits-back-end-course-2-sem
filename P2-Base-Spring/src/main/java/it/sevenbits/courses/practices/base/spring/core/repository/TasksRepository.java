package it.sevenbits.courses.practices.base.spring.core.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TasksRepository implements ItemsRepository {
    private List<Item> tasks;

    public TasksRepository() {
        tasks = new ArrayList<>();
    }

    @Override
    public List<Item> getAllItems() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Item create(Item item) {
        Item newItem = new Item(getNextID(), item.getName());
        tasks.add(newItem);
        return newItem;
    }

    private UUID getNextID() {
        return UUID.randomUUID();
    }
}
