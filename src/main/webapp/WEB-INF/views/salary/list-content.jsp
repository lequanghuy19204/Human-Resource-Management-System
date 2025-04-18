<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="mb-4">Danh sách lương nhân viên</h2>
</div>
<div class="d-flex form-group mb-4">
    <label for="monthFilter">Lọc theo tháng:</label>
    <input type="month" id="monthFilter" class="form-control" />
</div>
<div class="card">

    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>STT</th>
            <th>Tên nhân viên</th>
            <th>Số điện thoại</th>
            <th>Lương cơ bản</th>
            <th>Giờ tăng ca</th>
            <th>Giờ muộn</th>
            <th>Số ngày nghỉ</th>
            <th>Số ngày nghỉ có phép</th>
            <th>Tổng lương</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody id="salariesTableBody">
        <!-- Dữ liệu sẽ được thêm bởi JavaScript -->
        </tbody>
    </table>
</div>
