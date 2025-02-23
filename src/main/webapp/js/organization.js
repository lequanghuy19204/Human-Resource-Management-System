// Constants
const API_URL = 'Human-Resource-Management-System-1.0-SNAPSHOT//api/organizations';
let currentOrganizationId = null;

// Load organizations when page loads
document.addEventListener('DOMContentLoaded', loadOrganizations);

async function loadOrganizations() {
    try {
        const response = await fetch(API_URL);
        const organizations = await response.json();
        displayOrganizations(organizations);
    } catch (error) {
        console.error('Error loading organizations:', error);
        alert('Không thể tải danh sách tổ chức');
    }
}

function displayOrganizations(organizations) {
    const tbody = document.getElementById('organizationList');
    tbody.innerHTML = '';

    organizations.forEach(org => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${org.name}</td>
            <td>${org.employeeCount || 0}</td>
            <td>
                <button class="btn btn-sm btn-info" onclick="viewEmployees('${org._id}')">Xem nhân viên</button>
                <button class="btn btn-sm btn-warning" onclick="editOrganization('${org._id}', '${org.name}')">Sửa</button>
                <button class="btn btn-sm btn-danger" onclick="deleteOrganization('${org._id}')">Xóa</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function editOrganization(id, name) {
    currentOrganizationId = id;
    document.getElementById('organizationId').value = id;
    document.getElementById('organizationName').value = name;
    new bootstrap.Modal(document.getElementById('organizationModal')).show();
}

async function saveOrganization() {
    const id = document.getElementById('organizationId').value;
    const name = document.getElementById('organizationName').value;

    if (!name.trim()) {
        alert('Vui lòng nhập tên tổ chức');
        return;
    }

    try {
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/${id}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name: name })
        });

        if (response.ok) {
            bootstrap.Modal.getInstance(document.getElementById('organizationModal')).hide();
            loadOrganizations();
            resetForm();
        } else {
            throw new Error('Lỗi khi lưu tổ chức');
        }
    } catch (error) {
        console.error('Error saving organization:', error);
        alert('Không thể lưu tổ chức');
    }
}

async function deleteOrganization(id) {
    if (!confirm('Bạn có chắc muốn xóa tổ chức này?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadOrganizations();
        } else {
            throw new Error('Lỗi khi xóa tổ chức');
        }
    } catch (error) {
        console.error('Error deleting organization:', error);
        alert('Không thể xóa tổ chức');
    }
}

function viewEmployees(orgId) {
    window.location.href = `employees.html?orgId=${orgId}`;
}

function resetForm() {
    document.getElementById('organizationForm').reset();
    document.getElementById('organizationId').value = '';
    currentOrganizationId = null;
} 