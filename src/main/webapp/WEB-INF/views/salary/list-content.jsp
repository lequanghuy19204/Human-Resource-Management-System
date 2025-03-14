<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-5">
    <h2 class="mb-4">Danh sách lương nhân viên</h2>

    <!-- Bộ lọc theo tháng -->
    <div class="row mb-3">
        <div class="col-md-4">
            <label for="monthFilter">Chọn tháng:</label>
            <input type="month" id="monthFilter" class="form-control" />
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button class="btn btn-primary" onclick="loadSalaries()">Lọc</button>
        </div>
    </div>

    <!-- Bảng danh sách lương -->
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>STT</th>
            <th>Tên nhân viên</th>
            <th>Công ty</th>
            <th>Số điện thoại</th>
            <th>Lương cơ bản</th>
            <th>Thưởng (nếu có)</th>
            <th>Ngày làm tháng</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody id="salariesTableBody">
        <!-- Dữ liệu sẽ được thêm bởi JavaScript -->
        </tbody>
    </table>
</div>