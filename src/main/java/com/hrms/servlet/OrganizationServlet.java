package com.hrms.servlet;

import com.hrms.model.Organization;
import com.hrms.service.OrganizationService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

@WebServlet("/organizations/*")
public class OrganizationServlet extends HttpServlet {

    @EJB
    private OrganizationService organizationService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Hiển thị danh sách tổ chức
            List<Organization> organizations = organizationService.findAll();
            request.setAttribute("organizations", organizations);
            request.getRequestDispatcher("/WEB-INF/views/organization/list.jsp")
                    .forward(request, response);
        } else if (pathInfo.equals("/create")) {
            // Hiển thị form tạo mới
            request.getRequestDispatcher("/WEB-INF/views/organization/form.jsp")
                    .forward(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Hiển thị form chỉnh sửa
            String id = request.getParameter("id");
            Organization organization = organizationService.findById(new ObjectId(id));
            request.setAttribute("organization", organization);
            request.getRequestDispatcher("/WEB-INF/views/organization/form.jsp")
                    .forward(request, response);
        } else if (pathInfo.equals("/employees")) {
            // Chuyển đến trang danh sách nhân viên của tổ chức
            String orgId = request.getParameter("id");
            response.sendRedirect(request.getContextPath() + "/employees?organizationId=" + orgId);
        }
    }
}