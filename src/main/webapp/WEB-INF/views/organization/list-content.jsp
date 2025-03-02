<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Danh sách Tổ chức</h2>
    <button class="btn btn-primary" onclick="window.location.href='organizations/create'">
        <i class="bi bi-plus-circle"></i> Thêm Tổ chức
    </button>
</div>

<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4" id="organizationsContainer">
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
                Bạn có chắc chắn muốn xóa tổ chức này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDelete">Xóa</button>
            </div>
        </div>
    </div>
</div>

<style>
.org-card {
    height: 100%;
    transition: transform 0.2s, box-shadow 0.2s;
    position: relative;
}

.org-title {
    font-size: 1.25rem;
    font-weight: 500;
    margin-bottom: 1rem;
}

.org-info {
    color: #6c757d;
    font-size: 0.9rem;
}

.org-actions {
    margin-top: auto;
    padding-top: 1rem;
    border-top: 1px solid rgba(0,0,0,0.1);
}

.org-action-btn {
    padding: 0.375rem 0.75rem;
    border-radius: 0.25rem;
    transition: all 0.2s;
}

.org-action-btn i {
    font-size: 1.1rem;
}

.dropdown-menu {
    z-index: 1000;
}
.dropdown .btn-link {
    z-index: 2;
    position: relative;
}

.dropdown {
    position: static;
}

.card-body {
    position: static;
}

.dropdown-menu {
    position: absolute;
    z-index: 9999 !important;
}

#organizationsContainer {
    position: relative;
}
</style> 