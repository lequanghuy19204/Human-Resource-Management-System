<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5">
    <h2 class="mb-4">Thay đổi mức lương và thưởng</h2>
    <form id="salaryForm">
        <div class="form-group">
            <label for="employeeSearch">Tìm kiếm nhân viên:</label>
            <input type="text" class="form-control" id="employeeSearch" placeholder="Nhập tên, công ty hoặc số điện thoại">
        </div>
        <div id="employeeList" class="mb-4">
            <!-- Danh sách nhân viên sẽ được tải ở đây -->
        </div>
        <div class="form-group">
            <label for="defaultSalary">Lương mặc định (tháng trước):</label>
            <input type="number" class="form-control" id="defaultSalary" readonly>
        </div>
        <div class="form-group">
            <label for="reward">Thưởng (nếu có):</label>
            <input type="number" class="form-control" id="reward" placeholder="Nhập số tiền thưởng">
        </div>
        <div class="form-group">
            <label for="currentSalary">Lương tháng này:</label>
            <input type="number" class="form-control" id="currentSalary" readonly>
        </div>
        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
    </form>
</div>