package it.sevenbits.courses.workshop.servlets;

import it.sevenbits.courses.workshop.servlets.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskRepository tasks = TaskRepository.getInstance();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String taskId = req.getParameter("taskId");
        try {
            resp.getWriter().write(String.format("{\"id\": %s, \"name\": \"%s\"}",
                    Integer.parseInt(taskId),
                    tasks.getTask(Integer.parseInt(taskId))));
            resp.setStatus(200, "Task returned");
        } catch (Exception e) {
            resp.setStatus(404, "Task not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskRepository tasks = TaskRepository.getInstance();
        String taskId = req.getParameter("taskId");
        try {
            resp.getWriter().write(String.format("{\"id\": %s }",
                    Integer.parseInt(taskId)));
            tasks.deleteTask(Integer.parseInt(taskId));
        } catch (Exception e) {
            resp.sendError(404, "Task not found");
        }
    }

}
