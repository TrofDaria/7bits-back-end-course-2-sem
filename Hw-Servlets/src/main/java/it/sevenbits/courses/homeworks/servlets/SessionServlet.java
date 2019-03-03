package it.sevenbits.courses.homeworks.servlets;

import it.sevenbits.courses.homeworks.servlets.repository.SessionRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class SessionServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("name");
        if (param == null) {
            resp.setStatus(404, "Parameter \"name\" is not specified");
            return;
        }
        SessionRepository sessions = SessionRepository.getInstance();
        resp.addCookie(new Cookie((sessions.createSession(param)).toString(), param));
        resp.setStatus(201, "Created session");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.setStatus(404, "No cookies found");
            return;
        }
        UUID sessionId = null;
        for (Cookie cookie : cookies) {
            if ("sessionId".equals(cookie.getName())) {
                sessionId = UUID.fromString(cookie.getValue());
            }
        }
        if (sessionId == null) {
            resp.setStatus(404, "No id is specified");
            return;
        }
        SessionRepository sessions = SessionRepository.getInstance();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("<!DOCTYPE html>" +
                "<html>\n" +
                "<head><title>Current user</title></head>\n" +
                "<body>\n" +
                "<h1>" + "Current user is " + sessions.getSession(sessionId) + "</h1>\n" +
                "</body>" +
                "</html>"
        );
        resp.setStatus(200, "User name returned");

    }
}
