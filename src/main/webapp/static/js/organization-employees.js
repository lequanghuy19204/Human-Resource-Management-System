const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

async function loadEmployees() {
    try {
        const organizationId = document.getElementById('organizationId').value;
        const response = await fetch(`${API_URL}/employees/organization/${organizationId}`);
        const employees = await response.json();
        
        const tbody = document.getElementById('employeesList');
        tbody.innerHTML = '';
        
        if (employees.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="4" class="text-center py-4">
                        <i class="bi bi-people display-4 text-muted d-block mb-3"></i>
                        <p class="text-muted">Chưa có nhân viên nào trong tổ chức này</p>
                    </td>
                </tr>
            `;
            return;
        }

        for (const emp of employees) {
            let managerName = 'Không có';
            if (emp.manager_id) {
                const managerResponse = await fetch(`${API_URL}/employees/${emp.manager_id}`);
                const manager = await managerResponse.json();
                if (manager) {
                    managerName = manager.name;
                }
            }

            tbody.innerHTML += `
                <tr>
                    <td>${emp.name}</td>
                    <td>${emp.position_name}</td>
                    <td>${emp.phone || 'Chưa cập nhật'}</td>
                    <td>${managerName}</td>
                </tr>
            `;
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhân viên');
    }
}

document.addEventListener('DOMContentLoaded', loadEmployees);