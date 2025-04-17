<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container py-3">
    <div class="row">
        <!-- Thông tin cá nhân -->
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body text-center">
                    <div class="mb-3">
                        <div class="profile-image rounded-circle bg-primary text-white d-flex align-items-center justify-content-center mx-auto" style="width: 120px; height: 120px;">
                            <i class="bi bi-person" style="font-size: 3rem;"></i>
                        </div>
                    </div>
                    <h4 id="profileName" class="mb-2">Đang tải...</h4>
                    <p id="profilePosition" class="text-muted mb-1">Đang tải...</p>
                    <p id="profilePhone" class="mb-2">
                        <i class="bi bi-telephone"></i> <span>Đang tải...</span>
                    </p>
                    <p id="profileDepartment" class="mb-0">
                        <i class="bi bi-building"></i> <span>Đang tải...</span>
                    </p>
                </div>
            </div>
            <div class="card mt-3">
                <div class="card-header">
                    <h5 class="mb-0">Thông tin tài khoản</h5>
                </div>
                <div class="card-body">
                    <div class="mb-0">
                        <label class="text-muted">Tên đăng nhập</label>
                        <p id="profileUsername" class="mb-0">Đang tải...</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Thông tin chi tiết -->
        <div class="col-md-8">
            <div class="card mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Thông tin chi tiết</h5>
                    <button id="editProfileBtn" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-pencil"></i> Chỉnh sửa
                    </button>
                </div>
                <div class="card-body">
                    <form id="profileForm" class="needs-validation" novalidate>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="name" class="form-label">Họ và tên</label>
                                <input type="text" class="form-control" id="name" disabled required>
                            </div>
                            <div class="col-md-6">
                                <label for="position_name" class="form-label">Chức vụ</label>
                                <input type="text" class="form-control" id="position_name" disabled>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="phone" class="form-label">Số điện thoại</label>
                                <input type="tel" class="form-control" id="phone" disabled>
                            </div>
                            <div class="col-md-6">
                                <label for="organization" class="form-label">Tổ chức</label>
                                <input type="text" class="form-control" id="organization" disabled>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="manager" class="form-label">Quản lý</label>
                                <input type="text" class="form-control" id="manager" disabled>
                            </div>
                        </div>
                        
                        <div id="formActions" class="text-end" style="display: none;">
                            <button type="button" class="btn btn-secondary" id="cancelEdit">Hủy</button>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Hiệu suất làm việc -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">Hiệu suất làm việc</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h6 class="text-muted">Tổng số nhiệm vụ</h6>
                                    <h2 id="taskCount" class="mb-0">0</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h6 class="text-muted">Nhiệm vụ hoàn thành</h6>
                                    <h2 id="completedTasks" class="mb-0">0</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h6 class="text-muted">Nhiệm vụ đúng hạn</h6>
                                    <h2 id="ontimeTasks" class="mb-0">0</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h6 class="text-muted">Điểm hiệu suất</h6>
                                    <h2 id="performanceScore" class="mb-0">0.0</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Thông tin lương -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Thông tin lương</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <label class="text-muted">Lương cơ bản</label>
                            <h4 id="baseSalary" class="mb-0">0 VNĐ</h4>
                        </div>
                        <div class="col-md-6">
                            <label class="text-muted">Điểm chất lượng</label>
                            <h4 id="qualityScore" class="mb-0">0.0</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 