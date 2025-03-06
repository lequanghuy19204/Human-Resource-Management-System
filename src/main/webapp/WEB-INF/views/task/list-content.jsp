<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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