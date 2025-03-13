package com.hrms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/salaries/*")
public class SalaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            // Hiển thị danh sách lương
            req.getRequestDispatcher("/WEB-INF/views/salary/list.jsp").forward(req, resp);
        } else if (path.equals("/create")) {
            // Hiển thị form thêm mới lương
            req.getRequestDispatcher("/WEB-INF/views/salary/form.jsp").forward(req, resp);
        } else if (path.equals("/edit")) {
            // Hiển thị form chỉnh sửa lương
            String id = req.getParameter("id");
            if (id == null || id.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID lương không hợp lệ");
                return;
            }
            req.setAttribute("salaryId", id);
            req.getRequestDispatcher("/WEB-INF/views/salary/form.jsp").forward(req, resp);
        } else {
            // Trả về lỗi 404 nếu đường dẫn không hợp lệ
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Chuyển hướng tất cả các request POST đến API
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Vui lòng sử dụng API endpoints cho các thao tác POST");
    }
}