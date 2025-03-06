<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card">
    <div class="card-header">
        <h4 class="mb-0">${empty param.id ? 'Thêm mới' : 'Cập nhật'} Nhiệm vụ</h4>
    </div>
    <div class="card-body">
        <form id="taskForm" class="needs-validation" novalidate>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="name" class="form-label">Tên <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="name" required>
                    <div class="invalid-feedback">Vui lòng nhập tên</div>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <input type="text" class="form-control" id="description">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">Người được giao <span class="text-danger">*</span></label>
                    <!-- Ô tìm kiếm nhanh -->
                    <input type="text" class="form-control mb-2" id="employeeSearch" placeholder="Tìm kiếm nhân viên...">
                    <!-- Danh sách nhân viên dưới dạng checkbox -->
                    <div id="employeeList" class="border p-2" style="max-height: 200px; overflow-y: auto;">
                        <!-- Các checkbox sẽ được thêm vào đây bằng JavaScript -->
                    </div>
                    <div class="invalid-feedback">Vui lòng chọn người được giao</div>

                    <!-- Ô hiển thị thành viên đã chọn -->
                    <div class="mt-3">
                        <label class="form-label">Thành viên đã chọn:</label>
                        <div id="selectedEmployees" class="border p-2"></div>
                    </div>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="status" class="form-label">Trạng thái <span class="text-danger">*</span></label>
                    <select class="form-select" id="status" required>
                        <option value="">Chọn trạng thái</option>
                        <option value="IN_PROGRESS">Đang thực hiện</option>
                        <option value="COMPLETED">Hoàn thành</option>
                    </select>
                    <div class="invalid-feedback">Vui lòng chọn trạng thái</div>
                </div>
            </div>

            <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-secondary"
                        onclick="window.location.href='${pageContext.request.contextPath}/tasks'">
                    Hủy
                </button>
                <button type="submit" class="btn btn-primary">
                    ${empty param.id ? 'Thêm mới' : 'Cập nhật'}
                </button>
            </div>
        </form>
    </div>
</div>