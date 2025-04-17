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
        <!-- Bổ sung ô tìm kiếm và lọc theo tổ chức -->
        <div class="row mb-3">
            <div class="col-md-6 mb-2 mb-md-0">
                <div class="input-group">
                    <input type="text" class="form-control" id="searchInput" placeholder="Tìm kiếm theo tên, chức vụ, tổ chức, quản lý, số điện thoại..." aria-label="Tìm kiếm">
                    <button class="btn btn-outline-primary" type="button" id="searchButton">
                        <i class="bi bi-search"></i> Tìm kiếm
                    </button>
                </div>
            </div>
            <div class="col-md-4">
                <select class="form-select" id="organizationFilter">
                    <option value="">-- Tất cả tổ chức --</option>
                    <!-- Các tổ chức sẽ được thêm bởi JavaScript -->
                </select>
            </div>
            <div class="col-md-2 d-grid">
                <button class="btn btn-outline-secondary" type="button" id="resetFilterButton">
                    <i class="bi bi-x-circle"></i> Xóa lọc
                </button>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-hover">
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
        
        <!-- Hiển thị thông tin phân trang -->
        <div class="d-flex justify-content-between align-items-center mt-3" id="paginationInfo">
            <div>
                <span id="totalEmployees">0</span> nhân viên
            </div>
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