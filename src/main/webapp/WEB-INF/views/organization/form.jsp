<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty param.id ? 'Thêm' : 'Sửa'} Tổ chức - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">HRMS</a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h4 class="mb-0">${empty param.id ? 'Thêm mới' : 'Cập nhật'} Tổ chức</h4>
                    </div>
                    <div class="card-body">
                        <form id="organizationForm" class="needs-validation" novalidate>
                            <input type="hidden" id="organizationId" value="${param.id}">
                            
                            <div class="mb-3">
                                <label for="name" class="form-label">Tên tổ chức <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="name" required>
                                <div class="invalid-feedback">
                                    Vui lòng nhập tên tổ chức
                                </div>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-secondary" 
                                        onclick="window.location.href='${pageContext.request.contextPath}/organizations'">
                                    Hủy
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    ${empty param.id ? 'Thêm mới' : 'Cập nhật'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/organization-form.js"></script>
</body>
</html> 