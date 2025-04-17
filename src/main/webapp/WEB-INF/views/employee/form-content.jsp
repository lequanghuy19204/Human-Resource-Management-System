<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card">
    <div class="card-header">
        <h4 class="mb-0">${empty param.id ? 'Thêm mới' : 'Cập nhật'} Nhân viên</h4>
    </div>
    <div class="card-body">
        <form id="employeeForm" class="needs-validation" novalidate>
            <div class="row">
                <!-- Thông tin tài khoản -->
                <div class="col-md-6 mb-3">
                    <label for="username" class="form-label">Tên đăng nhập <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="username" required>
                    <div class="invalid-feedback">Vui lòng nhập tên đăng nhập</div>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="password" required>
                    <div class="invalid-feedback">Vui lòng nhập mật khẩu</div>
                </div>

                <!-- Thông tin nhân viên -->
                <div class="col-md-6 mb-3">
                    <label for="name" class="form-label">Tên nhân viên <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="name" required>
                    <div class="invalid-feedback">Vui lòng nhập tên nhân viên</div>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="position_name" class="form-label">Chức vụ <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="position_name" required>
                    <div class="invalid-feedback">Vui lòng nhập chức vụ</div>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="organization_id" class="form-label">Tổ chức <span class="text-danger">*</span></label>
                    <select class="form-select" id="organization_id" required>
                        <option value="">Chọn tổ chức</option>
                    </select>
                    <div class="invalid-feedback">Vui lòng chọn tổ chức</div>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="manager_id" class="form-label">Quản lý</label>
                    <select class="form-select" id="manager_id">
                        <option value="">Chọn quản lý</option>
                    </select>
                </div>

                <div class="col-md-6 mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel" class="form-control" id="phone">
                </div>

                <!-- Thêm các trường thống kê mới -->
                <div class="col-md-4 mb-3">
                    <label for="base_salary" class="form-label">Lương cơ bản (VNĐ)</label>
                    <input type="number" class="form-control" id="base_salary" min="0" value="0">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="task_count" class="form-label">Số nhiệm vụ</label>
                    <input type="number" class="form-control" id="task_count" min="0" value="0">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="completed_tasks" class="form-label">Số nhiệm vụ hoàn thành</label>
                    <input type="number" class="form-control" id="completed_tasks" min="0" value="0">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="ontime_tasks" class="form-label">Số nhiệm vụ đúng hạn</label>
                    <input type="number" class="form-control" id="ontime_tasks" min="0" value="0">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="quality_score" class="form-label">Điểm chất lượng</label>
                    <input type="number" class="form-control" id="quality_score" min="0" max="10" step="0.1" value="0">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="performance_score" class="form-label">Điểm hiệu suất</label>
                    <input type="number" class="form-control" id="performance_score" min="0" max="100" step="0.1" value="0">
                </div>

                <div class="col-12 mb-3">
                    <label class="form-label">Quyền hạn <span class="text-danger">*</span></label>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_ORGANIZATION">
                        <label class="form-check-label">Quản lý tổ chức</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_EMPLOYEES">
                        <label class="form-check-label">Quản lý nhân viên</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_TASKS">
                        <label class="form-check-label">Quản lý nhiệm vụ</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_SALARY">
                        <label class="form-check-label">Quản lý lương thưởng</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_TRAINING">
                        <label class="form-check-label">Quản lý đào tạo</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="MANAGE_PERFORMANCE">
                        <label class="form-check-label">Quản lý hiệu suất</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input permission-checkbox" value="EMPLOYEES">
                        <label class="form-check-label">Nhân viên</label>
                    </div>
                    <div class="invalid-feedback">Vui lòng chọn ít nhất một quyền</div>
                </div>
            </div>

            <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-secondary" 
                        onclick="window.location.href='${pageContext.request.contextPath}/employees'">
                    Hủy
                </button>
                <button type="submit" class="btn btn-primary">
                    ${empty param.id ? 'Thêm mới' : 'Cập nhật'}
                </button>
            </div>
        </form>
    </div>
</div> 