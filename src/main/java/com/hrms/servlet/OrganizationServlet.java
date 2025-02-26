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

        // Kiểm tra session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("companyId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getPathInfo();
        String companyId = (String) session.getAttribute("companyId");

        if (path == null || path.equals("/")) {
            showAllOrganizations(req, resp, companyId);
        } else if (path.equals("/create")) {
            showCreateForm(req, resp);
        } else if (path.equals("/edit")) {
            showEditForm(req, resp);
        } else if (path.equals("/employees")) {
            redirectToEmployees(req, resp);
        }
    }

    private void showAllOrganizations(HttpServletRequest req, HttpServletResponse resp, String companyId)
            throws ServletException, IOException {
        List<Organization> organizations = organizationService.findByCompanyId(new ObjectId(companyId));
        req.setAttribute("organizations", organizations);
        req.getRequestDispatcher("/WEB-INF/views/organization/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/organization/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Organization organization = organizationService.findById(new ObjectId(id));
        req.setAttribute("organization", organization);
        req.getRequestDispatcher("/WEB-INF/views/organization/form.jsp").forward(req, resp);
    }

    private void redirectToEmployees(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String orgId = req.getParameter("id");
        resp.sendRedirect(req.getContextPath() + "/employees?organizationId=" + orgId);
    }
}