package it.sevenbits.courses.homeworks.base.spring.core.repository;

import it.sevenbits.courses.homeworks.base.spring.core.model.Task;

import java.util.*;


public class InMemoryTasksRepository implements TasksRepository {
    private Map<UUID, Task> tasks;
    private Set<String> avaliableStatuses;
    private final String defaultStatus = "inbox";

    public InMemoryTasksRepository() {
        avaliableStatuses = new HashSet<>();
        avaliableStatuses.add("done");
        avaliableStatuses.add("inbox");
        tasks = new HashMap<>();
    }

    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }

    @Override
    public Task getTask(UUID id) {
        return tasks.get(id);
    }

    @Override
    public boolean deleteTask(UUID id) {
        return tasks.remove(id) != null;
    }

    @Override
    public Task create(Task Task) {
        String status = Task.getStatus();
        if (status == null) {
            status = defaultStatus;
        }
        Task newTask = new Task(getNextID(), Task.getText(), status);
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    private UUID getNextID() {
        return UUID.randomUUID();
    }

    public boolean isStatusValid(String status) {
        return avaliableStatuses.contains(status);
    }

    @Override
    public List<Task> getTasksByStatus(String status) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if ((task.getStatus()).equals(status)) {
                foundTasks.add(task);
            }
        }
        return Collections.unmodifiableList(foundTasks);
    }

    public boolean updateTask(UUID id, Task task) {
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
        return modified;
    }

    @Override
    public boolean isStatusLegit(String status) {
        return avaliableStatuses.contains(status);
    }
}
