package com.hrms.servlet;

import com.hrms.model.Organization;
import com.hrms.service.OrganizationService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

@WebServlet("/organizations/*")
public class OrganizationServlet extends HttpServlet {

    @EJB
    private OrganizationService organizationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            req.getRequestDispatcher("/WEB-INF/views/organization/list.jsp").forward(req, resp);
        } else if (path.equals("/create")) {
            req.getRequestDispatcher("/WEB-INF/views/organization/form.jsp").forward(req, resp);
        } else if (path.equals("/edit")) {
            String id = req.getParameter("id");
            req.setAttribute("organizationId", id);
            req.getRequestDispatcher("/WEB-INF/views/organization/form.jsp").forward(req, resp);
        }
    }
}