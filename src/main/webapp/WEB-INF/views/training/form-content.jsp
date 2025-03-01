<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">${empty param.id ? 'Thêm mới' : 'Chỉnh sửa'} Chương trình Đào tạo</h5>
        <button class="btn btn-secondary" onclick="window.history.back()">
            <i class="bi bi-arrow-left"></i> Quay lại
        </button>
    </div>
    <div class="card-body">
        <form id="trainingForm" class="needs-validation" novalidate>
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">Tên chương trình <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="name" required>
                    <div class="invalid-feedback">Vui lòng nhập tên chương trình</div>
                </div>

                <div class="col-md-6">
                    <label for="location" class="form-label">Địa điểm <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="location" required>
                    <div class="invalid-feedback">Vui lòng nhập địa điểm</div>
                </div>

                <div class="col-md-6">
                    <label for="start" class="form-label">Ngày bắt đầu <span class="text-danger">*</span></label>
                    <input type="datetime-local" class="form-control" id="start" required>
                    <div class="invalid-feedback">Vui lòng chọn ngày bắt đầu</div>
                </div>

                <div class="col-md-6">
                    <label for="end" class="form-label">Ngày kết thúc <span class="text-danger">*</span></label>
                    <input type="datetime-local" class="form-control" id="end" required>
                    <div class="invalid-feedback">Vui lòng chọn ngày kết thúc</div>
                </div>

                <div class="col-md-12">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" rows="3"></textarea>
                </div>

                <div class="col-md-6">
                    <label for="trainer" class="form-label">Người đào tạo <span class="text-danger">*</span></label>
                    <select class="form-select" id="trainer" required>
                        <option value="">Chọn người đào tạo</option>
                    </select>
                    <div class="invalid-feedback">Vui lòng chọn người đào tạo</div>
                </div>

                <div class="col-md-12">
                    <label class="form-label">Người tham gia <span class="text-danger">*</span></label>
                    <div class="row">
                        <div class="col-md-5">
                            <div class="card">
                                <div class="card-header">
                                    Danh sách nhân viên
                                </div>
                                <div class="card-body">
                                    <div class="mb-2">
                                        <input type="text" class="form-control" id="searchAvailable" placeholder="Tìm kiếm...">
                                    </div>
                                    <select class="form-select" id="availableParticipants" multiple style="height: 200px">
                                    </select>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-2 d-flex align-items-center justify-content-center">
                            <div class="d-grid gap-2">
                                <button type="button" class="btn btn-primary" id="addParticipant">
                                    <i class="bi bi-arrow-right"></i>
                                </button>
                                <button type="button" class="btn btn-primary" id="addAllParticipants">
                                    <i class="bi bi-arrow-right"></i><i class="bi bi-arrow-right"></i>
                                </button>
                                <button type="button" class="btn btn-danger" id="removeParticipant">
                                    <i class="bi bi-arrow-left"></i>
                                </button>
                                <button type="button" class="btn btn-danger" id="removeAllParticipants">
                                    <i class="bi bi-arrow-left"></i><i class="bi bi-arrow-left"></i>
                                </button>
                            </div>
                        </div>
                        
                        <div class="col-md-5">
                            <div class="card">
                                <div class="card-header">
                                    Người tham gia được chọn
                                </div>
                                <div class="card-body">
                                    <div class="mb-2">
                                        <input type="text" class="form-control" id="searchSelected" placeholder="Tìm kiếm...">
                                    </div>
                                    <select class="form-select" id="selectedParticipants" multiple style="height: 200px" required>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="invalid-feedback">Vui lòng chọn ít nhất một người tham gia</div>
                </div>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Lưu
                </button>
                <button type="button" class="btn btn-secondary ms-2" onclick="window.history.back()">
                    <i class="bi bi-x"></i> Hủy
                </button>
            </div>
        </form>
    </div>
</div>

