const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteEmpId = null;

async function loadEmployees() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/employees/company/${companyId}`);
        const employees = await response.json();
        
        const tableBody = document.getElementById('employeesTableBody');
        tableBody.innerHTML = '';
        
        for (const emp of employees) {
            // Lấy tên tổ chức
            const orgResponse = await fetch(`${API_URL}/organizations/${emp.organization_id}`);
            const org = await orgResponse.json();
            
            // Lấy thông tin quản lý
            let managerName = '-';
            if (emp.manager_id) {
                const managerResponse = await fetch(`${API_URL}/employees/${emp.manager_id}`);
                if (managerResponse.ok) {
                    const manager = await managerResponse.json();
                    managerName = manager.name;
                }
            }
            
            tableBody.innerHTML += `
                <tr>
                    <td>${emp.name}</td>
                    <td>${emp.position_name}</td>
                    <td>${org.name}</td>
                    <td>${managerName}</td>
                    <td>${emp.phone || '-'}</td>
                    <td>${new Intl.NumberFormat('vi-VN').format(emp.base_salary || 0)} VNĐ</td>
                    <td>${emp.overtime_hours || 0}</td>
                    <td>${emp.late_hours || 0}</td>
                    <td>${emp.absent_days || 0}</td>
                    <td>${emp.working_days || 0}</td>
                    <td>${emp.approved_leave_days || 0}</td>
                    <td>
                        <div class="btn-group btn-group-sm">
                            <button class="btn btn-primary" onclick="window.location.href='employees/edit?id=${emp._id}'">
                                <i class="bi bi-pencil"></i>
                            </button>
                            <button class="btn btn-danger" onclick="deleteEmployee('${emp._id}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }
        
        if (employees.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="12" class="text-center py-4">
                        <p class="text-muted mb-0">Chưa có nhân viên nào</p>
                    </td>
                </tr>
            `;
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhân viên');
    }
}

function deleteEmployee(id) {
    deleteEmpId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

document.getElementById('confirmDelete').addEventListener('click', async () => {
    try {
        const response = await fetch(`${API_URL}/employees/${deleteEmpId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
            modal.hide();
            loadEmployees();
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi xóa nhân viên');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa nhân viên');
    }
});

// Load danh sách khi trang được tải
document.addEventListener('DOMContentLoaded', loadEmployees); 