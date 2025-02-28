package com.hrms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/training/*")
public class TrainingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            req.getRequestDispatcher("/WEB-INF/views/training/list.jsp").forward(req, resp);
        } else if (path.equals("/create")) {
            req.getRequestDispatcher("/WEB-INF/views/training/form.jsp").forward(req, resp);
        } else if (path.equals("/edit")) {
            String id = req.getParameter("id");
            if (id == null || id.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
                return;
            }
            req.setAttribute("trainingId", id);
            req.getRequestDispatcher("/WEB-INF/views/training/form.jsp").forward(req, resp);
        }
    }
}