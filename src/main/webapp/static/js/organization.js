const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteOrgId = null;

// Hàm load danh sách tổ chức
async function loadOrganizations() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/organizations/company/${companyId}`);
        const organizations = await response.json();
        
        const container = document.getElementById('organizationsContainer');
        container.innerHTML = '';
        
        organizations.forEach(org => {
            container.innerHTML += `
                <div class="col">
                    <div class="card org-card">
                        <div class="card-body d-flex flex-column">
                            <div class="d-flex justify-content-between align-items-start position-relative">
                                <h5 class="org-title mb-0">
                                    <i class="bi bi-diagram-3 me-2 text-primary"></i>
                                    ${org.name}
                                </h5>
                                <div class="dropdown position-static">
                                    <button class="btn btn-link text-dark p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="bi bi-three-dots-vertical"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end shadow">
                                        <li>
                                            <a class="dropdown-item" href="organizations/employees?id=${org.id}">
                                                <i class="bi bi-people me-2"></i>Xem nhân viên
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="organizations/edit?id=${org.id}">
                                                <i class="bi bi-pencil me-2"></i>Chỉnh sửa
                                            </a>
                                        </li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li>
                                            <a class="dropdown-item text-danger" href="#" onclick="deleteOrganization('${org.id}')">
                                                <i class="bi bi-trash me-2"></i>Xóa
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        });

        // Nếu không có tổ chức nào, hiển thị thông báo
        if (organizations.length === 0) {
            container.innerHTML = `
                <div class="col-12">
                    <div class="card">
                        <div class="card-body text-center py-5">
                            <i class="bi bi-diagram-3 display-4 text-muted mb-3"></i>
                            <h5>Chưa có tổ chức nào</h5>
                            <p class="text-muted">Hãy tạo tổ chức đầu tiên cho công ty của bạn</p>
                            <button class="btn btn-primary" onclick="window.location.href='organizations/create'">
                                <i class="bi bi-plus-circle me-2"></i>Thêm Tổ chức
                            </button>
                        </div>
                    </div>
                </div>
            `;
        }
    } catch (error) {
        console.error('Error loading organizations:', error);
        alert('Có lỗi xảy ra khi tải danh sách tổ chức');
    }
}

function deleteOrganization(id) {
    deleteOrgId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

document.getElementById('confirmDelete').addEventListener('click', async () => {
    try {
        const response = await fetch(`${API_URL}/organizations/${deleteOrgId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
            modal.hide();
            loadOrganizations(); // Tải lại danh sách sau khi xóa
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi xóa tổ chức');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa tổ chức');
    }
});

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', loadOrganizations); 