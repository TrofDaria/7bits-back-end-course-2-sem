package it.sevenbits.courses.homeworks.servlets;

import it.sevenbits.courses.homeworks.servlets.repository.SessionRepository;
import it.sevenbits.courses.homeworks.servlets.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isUserAuthorized(req, resp)) {
            resp.setStatus(401, "User is not specified");
            return;
        }
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
        if (!isUserAuthorized(req, resp)) {
            resp.setStatus(401, "User is not specified");
            return;
        }
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
                "{\"id\": %s , \"name\": %s}",
                taskId,
                tasks.getTask(taskId)));
        resp.setStatus(200, "Task deleted");
    }

    private boolean isUserAuthorized(HttpServletRequest req, HttpServletResponse resp) {
        req.getAuthType();
        String header = req.getHeader("Authorization");
        if (header == null) {
            return false;
        }
        SessionRepository sessions = SessionRepository.getInstance();
        return sessions.getSession(UUID.fromString(header)) != null;
    }
}
