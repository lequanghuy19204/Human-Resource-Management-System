// Constants
const API_URL = 'Human-Resource-Management-System-1.0-SNAPSHOT//api/employees';
const ORG_API_URL = 'Human-Resource-Management-System-1.0-SNAPSHOT//api/organizations';
let currentOrgId = null;

// Load employees when page loads
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    currentOrgId = urlParams.get('orgId');
    if (currentOrgId) {
        loadOrganizationName();
        loadEmployees();
    } else {
        alert('Không tìm thấy thông tin tổ chức');
        window.location.href = 'index.html';
    }
});

async function loadOrganizationName() {
    try {
        const response = await fetch(`${ORG_API_URL}/${currentOrgId}`);
        const organization = await response.json();
        document.getElementById('organizationName').textContent = organization.name;
    } catch (error) {
        console.error('Error loading organization:', error);
    }
}

async function loadEmployees() {
    try {
        const response = await fetch(`${API_URL}?orgId=${currentOrgId}`);
        const employees = await response.json();
        displayEmployees(employees);
    } catch (error) {
        console.error('Error loading employees:', error);
        alert('Không thể tải danh sách nhân viên');
    }
}

function displayEmployees(employees) {
    const tbody = document.getElementById('employeeList');
    tbody.innerHTML = '';

    employees.forEach(emp => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${emp.name}</td>
            <td>${emp.position ? emp.position.name : 'N/A'}</td>
            <td>${emp.phone}</td>
            <td>${emp.status === 'active' ? 'Đang làm việc' : 'Nghỉ việc'}</td>
            <td>
                <button class="btn btn-sm btn-warning" onclick="editEmployee('${emp._id}')">Sửa</button>
                <button class="btn btn-sm btn-danger" onclick="deleteEmployee('${emp._id}')">Xóa</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function editEmployee(id) {
    window.location.href = `employee-form.html?orgId=${currentOrgId}&empId=${id}`;
}

async function deleteEmployee(id) {
    if (!confirm('Bạn có chắc muốn xóa nhân viên này?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadEmployees();
        } else {
            throw new Error('Lỗi khi xóa nhân viên');
        }
    } catch (error) {
        console.error('Error deleting employee:', error);
        alert('Không thể xóa nhân viên');
    }
} 