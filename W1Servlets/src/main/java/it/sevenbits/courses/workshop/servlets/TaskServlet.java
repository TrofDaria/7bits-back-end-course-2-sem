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
        String param = req.getParameter("taskId");
        if (param == null) {
            resp.setStatus(404, "Empty taskId parameter");
            return;
        }
        int taskId = Integer.parseInt(param);
        TaskRepository tasks = TaskRepository.getInstance();
        if (taskId >= tasks.size()) {
            resp.setStatus(404, "Task not found");
            return;
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(String.format(
                "{\"id\": %s, \"name\": \"%s\"}",
                taskId,
                tasks.getTask(taskId)));
        resp.setStatus(200, "Task returned");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("taskId");
        if (param == null) {
            resp.setStatus(404, "Empty taskId parameter");
            return;
        }
        int taskId = Integer.parseInt(param);
        TaskRepository tasks = TaskRepository.getInstance();
        if (taskId >= tasks.size()) {
            resp.setStatus(404, "Task not found");
            return;
        }
        tasks.deleteTask(taskId);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(String.format(
                "{\"id\": %s }",
                taskId,
                tasks.getTask(taskId)));
        resp.setStatus(200, "Task deleted");
    }

}
