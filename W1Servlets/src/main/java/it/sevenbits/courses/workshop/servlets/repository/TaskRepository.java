package it.sevenbits.courses.workshop.servlets.repository;

import java.util.concurrent.CopyOnWriteArrayList;

public class TaskRepository {
    private static TaskRepository instance;
    private CopyOnWriteArrayList<String> tasks;

    private TaskRepository() {
        tasks = new CopyOnWriteArrayList<String>();

    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public String getTask(int id) {
        return tasks.get(id);
    }

    public int size(){
        return tasks.size();
    }

    public void deleteTask(int id){
        tasks.remove(id);
    }

}
