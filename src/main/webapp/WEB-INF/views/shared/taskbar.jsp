<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            z-index: 100;
            padding: 48px 0 0;
            box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
            width: 280px;
            background-color: #343a40;
        }

        .sidebar-sticky {
            position: relative;
            top: 0;
            height: calc(100vh - 48px);
            padding-top: .5rem;
            overflow-x: hidden;
            overflow-y: auto;
        }

        .sidebar .nav-link {
            font-weight: 500;
            color: #fff;
            padding: 1rem 1.5rem;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .sidebar .nav-link:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }

        .sidebar .nav-link.active {
            background-color: rgba(255, 255, 255, 0.2);
        }

        .main-content {
            margin-left: 280px;
            padding: 20px;
        }

        .user-section {
            border-top: 1px solid rgba(255, 255, 255, 0.1);
            padding: 1rem;
            position: absolute;
            bottom: 0;
            width: 100%;
            color: #fff;
        }

        .user-section .dropdown-menu {
            min-width: 100%;
        }
    </style>
    ${param.styles}
</head>
<body>
    <!-- Sidebar -->
    <nav class="sidebar">
        <div class="sidebar-sticky">
            <div class="d-flex align-items-center px-3 py-2 mb-3">
                <span class="ms-2 text-white h4 mb-0">HRMS</span>
            </div>

            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'organization' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/organizations">
                        <i class="bi bi-diagram-3"></i>
                        Quản lý Tổ chức
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'tasks' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/tasks">
                        <i class="bi bi-list-task"></i>
                        Quản lý Nhiệm vụ
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'payroll' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/payroll">
                        <i class="bi bi-cash-stack"></i>
                        Quản lý Lương thưởng
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'performance' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/performance">
                        <i class="bi bi-graph-up"></i>
                        Đánh giá Hiệu xuất
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'training' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/training">
                        <i class="bi bi-mortarboard"></i>
                        Quản lý Đào tạo
                    </a>
                </li>
                <li class="nav-item mt-3">
                    <a class="nav-link ${param.active == 'settings' ? 'active' : ''}" 
                       href="${pageContext.request.contextPath}/settings">
                        <i class="bi bi-gear"></i>
                        Cài đặt
                    </a>
                </li>
            </ul>

            <!-- User Section -->
            <div class="user-section">
                <div class="dropdown">
                    <a href="#" class="d-flex align-items-center text-decoration-none dropdown-toggle" 
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <div style="width: 32px; height: 32px; background: white; border-radius: 50%;" class="me-2"></div>
                        <strong>Lưu Trần Hoàng Phúc</strong>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                            <i class="bi bi-person"></i> Hồ sơ
                        </a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right"></i> Đăng xuất
                        </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="main-content">
        <jsp:include page="${param.content}" />
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    ${param.scripts}
</body>
</html> 