<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Danh sách Nhân viên</h2>
    <button class="btn btn-primary" onclick="window.location.href='employees/create'">
        <i class="bi bi-plus-circle"></i> Thêm Nhân viên
    </button>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Tên</th>
                        <th>Chức vụ</th>
                        <th>Tổ chức</th>
                        <th>Quản lý</th>
                        <th>Số điện thoại</th>
                        <th>Lương cơ bản</th>
                        <th>Số nhiệm vụ</th>
                        <th>Hoàn thành</th>
                        <th>Đúng hạn</th>
                        <th>Chất lượng</th>
                        <th>Hiệu suất</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody id="employeesTableBody">
                    <!-- Dữ liệu sẽ được thêm bởi JavaScript -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Xác nhận xóa -->
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa nhân viên này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDelete">Xóa</button>
            </div>
        </div>
    </div>
</div> 