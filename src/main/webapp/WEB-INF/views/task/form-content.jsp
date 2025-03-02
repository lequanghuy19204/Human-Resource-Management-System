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
                    <label for="assigned_to" class="form-label">Người được giao <span class="text-danger">*</span></label>
                    <select class="form-select" id="assigned_to" required>
                        <option value="">Chọn người được giao</option>
                    </select>
                    <div class="invalid-feedback">Vui lòng chọn người được giao</div>
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