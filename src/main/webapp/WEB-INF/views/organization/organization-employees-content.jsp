<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Danh sách Nhân viên</h5>
        <button class="btn btn-secondary" onclick="window.history.back()">
            <i class="bi bi-arrow-left"></i> Quay lại
        </button>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Họ và Tên</th>
                        <th>Chức vụ</th>
                        <th>Số điện thoại</th>
                        <th>Quản lý</th>
                    </tr>
                </thead>
                <tbody id="employeesList">
                    <!-- Dữ liệu sẽ được thêm bởi JavaScript -->
                </tbody>
            </table>
        </div>
    </div>
</div>