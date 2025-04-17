const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;

    if (path.endsWith('/tasks')) {
        loadTasks();
    }
});

async function loadTasks() {
    try {
        const companyId = document.getElementById('companyId').value;
        const tasksResponse = await fetch(`${API_URL}/tasks/company/${companyId}`);
        if (!tasksResponse.ok) {
            throw new Error('Không thể tải danh sách nhiệm vụ');
        }
        const tasks = await tasksResponse.json();

        const employeesResponse = await fetch(`${API_URL}/employees`);
        const companiesResponse = await fetch(`${API_URL}/companies`);
        if (!employeesResponse.ok || !companiesResponse.ok) {
            throw new Error('Không thể tải danh sách nhân viên hoặc công ty');
        }
        const employees = await employeesResponse.json();
        const companiesData = await companiesResponse.json();

        const employeeMap = new Map();
        employees.forEach(employee => {
            employeeMap.set(employee._id, employee);
        });

        const companies = new Map();
        companiesData.forEach(company => {
            companies.set(company._id, company);
        });

        const tableBody = document.querySelector('#tasksTableBody');
        tableBody.innerHTML = ''; // Xóa nội dung cũ

        tasks.forEach(task => {
            const row = document.createElement('tr');
            // Tạo các cột dữ liệu
            row.innerHTML = `
                <td>${task.name}</td>
                <td>${task.description || 'N/A'}</td>
                <td>
                    <div class="assigned-to-container">
                        ${task.assigned_to && task.assigned_to.length > 0 ? task.assigned_to.map(employeeId => {
                            const employee = employeeMap.get(employeeId);
                            if (employee) {
                                const company = companies.get(employee.company_id);
                                const companyName = company ? company.name : 'N/A';
                                return `<div class="assigned-to-item">${employee.name} - ${companyName} - ${employee.phone}</div>`;
                            } else {
                                return '<div class="assigned-to-item">Không tìm thấy thông tin nhân viên</div>';
                            }
                        }).join('') : 'N/A'}
                    </div>
                </td>
                <td>${task.status || 'N/A'}</td>
                <td>
                    <a href="${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/tasks/edit?id=${task._id}" class="btn btn-primary btn-sm">Sửa</a>
                    <button onclick="deleteTask('${task._id}')" class="btn btn-danger btn-sm">Xóa</button>
                </td>
            `;

            tableBody.appendChild(row);
        });
    } catch (error) {
        alert('Có lỗi xảy ra khi tải danh sách nhiệm vụ');
    }
}

async function deleteTask(taskId) {
    if (!taskId) {
        return;
    }

    const isConfirmed = confirm('Bạn có chắc chắn muốn xóa nhiệm vụ này không?');
    if (!isConfirmed) return;

    try {
        const taskResponse = await fetch(`${API_URL}/tasks/${taskId}`, {
            method: 'DELETE'
        });

        if (taskResponse.ok) {
            alert('Xóa nhiệm vụ thành công');
            loadTasks();
        } else {
            const errorData = await taskResponse.json();
            alert(`Không thể xóa nhiệm vụ: ${errorData.message || 'Lỗi không xác định'}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa nhiệm vụ');
    }
}