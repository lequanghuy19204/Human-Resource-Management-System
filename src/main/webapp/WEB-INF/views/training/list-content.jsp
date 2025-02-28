<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Danh sách Chương trình Đào tạo</h2>
    <button class="btn btn-primary" onclick="window.location.href='training/create'">
        <i class="bi bi-plus-circle"></i> Thêm Chương trình
    </button>
</div>

<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4" id="trainingContainer">
    <!-- Nội dung sẽ được thêm bởi JavaScript -->
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
                Bạn có chắc chắn muốn xóa chương trình đào tạo này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDelete">Xóa</button>
            </div>
        </div>
    </div>
</div>

<style>
.training-card {
    height: 100%;
    transition: transform 0.2s, box-shadow 0.2s;
}

.training-title {
    font-size: 1.25rem;
    font-weight: 500;
}

.training-info {
    color: #6c757d;
    font-size: 0.9rem;
}

.dropdown-menu {
    z-index: 1000;
}
</style> 