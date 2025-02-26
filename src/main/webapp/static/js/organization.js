const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteOrgId = null;

// Hàm load danh sách tổ chức
async function loadOrganizations() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/organizations/company/${companyId}`);
        const organizations = await response.json();
        
        const tbody = document.querySelector('table tbody');
        tbody.innerHTML = '';
        
        organizations.forEach(org => {
            tbody.innerHTML += `
                <tr>
                    <td>
                        <a href="organizations/employees?id=${org.id}" class="text-decoration-none">
                            ${org.name}
                        </a>
                    </td>
                    <td>${org.employeeCount || 0}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <button class="btn btn-sm btn-outline-primary" 
                                    onclick="window.location.href='organizations/edit?id=${org.id}'">
                                <i class="bi bi-pencil"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" 
                                    onclick="deleteOrganization('${org.id}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        });
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