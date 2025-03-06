<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Thêm CSS trực tiếp vào file JSP -->
<style>
    /* CSS để giới hạn chiều cao và thêm thanh cuộn */
    .assigned-to-container {
        max-height: 150px; /* Giới hạn chiều cao tối đa */
        overflow-y: auto;  /* Hiển thị thanh cuộn dọc khi cần */
        border: 1px solid #ddd; /* Thêm viền để phân biệt */
        padding: 5px; /* Thêm padding để đẹp hơn */
        border-radius: 4px; /* Bo góc */
    }

    .assigned-to-item {
        padding: 3px 0; /* Thêm khoảng cách giữa các mục */
        border-bottom: 1px solid #eee; /* Thêm đường kẻ ngăn cách */
    }

    .assigned-to-item:last-child {
        border-bottom: none; /* Bỏ đường kẻ cho mục cuối cùng */
    }
</style>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Danh sách Nhiệm vụ</h2>
    <button class="btn btn-primary" onclick="window.location.href='tasks/create'">
        <i class="bi bi-plus-circle"></i> Thêm Nhiệm vụ
    </button>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Tên</th>
                    <th>Mô tả</th>
                    <th>Người được giao</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody id="tasksTableBody">
                <!-- Dữ liệu sẽ được thêm bởi JavaScript -->
                </tbody>
            </table>
        </div>
    </div>
</div>