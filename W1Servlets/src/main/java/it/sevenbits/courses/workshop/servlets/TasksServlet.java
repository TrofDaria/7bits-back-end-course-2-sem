package it.sevenbits.courses.workshop.servlets;

import it.sevenbits.courses.workshop.servlets.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TasksServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((req.getParameterMap()).size() == 0) {
            resp.setStatus(404, "Incorrect number of parameters");
            return;
        }
        TaskRepository tasks = TaskRepository.getInstance();
        String[] array = new String[tasks.size() + 1];

        for (int i = 0; i < tasks.size(); i++) {
            array[i] = String.format("{\"id\": %s, \"name\": \"%s\"}", i, tasks.getTask(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{[");
        for (int i = 0; i < tasks.size(); i++) {
            stringBuilder.append(array[i]);
            if ((i + 1) != tasks.size()) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(stringBuilder.toString());
        resp.setStatus(200, "The array of tasks");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String task = req.getParameter("name");
        if (task == null) {
            resp.setStatus(404, "Empty name parameter");
            return;
        }
        TaskRepository tasks = TaskRepository.getInstance();
        tasks.addTask(task);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(String.format("{\"id\": %s, \"name\": \"%s\"}",
                (tasks.size() - 1),
                tasks.getTask(tasks.size() - 1)));
        resp.setStatus(201, "Created task");
    }
}
