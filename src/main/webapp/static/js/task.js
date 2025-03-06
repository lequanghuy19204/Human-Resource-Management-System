const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;
    console.log('Current path:', path); // In ra đường dẫn hiện tại

    if (path.endsWith('/tasks')) {
        loadTasks(); // Tải danh sách task nếu ở trang danh sách
    }
});

// Hàm tải danh sách task và hiển thị lên bảng
async function loadTasks() {
    try {
        const response = await fetch(`${API_URL}/tasks`);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách nhiệm vụ');
        }
        const tasks = await response.json();

        // Lấy danh sách nhân viên từ API
        const employeesResponse = await fetch(`${API_URL}/employees`);
        if (!employeesResponse.ok) {
            throw new Error('Không thể tải danh sách nhân viên');
        }
        const employees = await employeesResponse.json();

        // Tạo một map để lưu thông tin nhân viên theo ID
        const employeeMap = new Map();
        employees.forEach(employee => {
            employeeMap.set(employee._id, employee);
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
                        ${task.assigned_to && task.assigned_to.length > 0
                ? task.assigned_to.map(employeeId => {
                    const employee = employeeMap.get(employeeId);
                    return employee
                        ? `<div class="assigned-to-item">${employee.name} - ${employee.company_id} - ${employee.phone}</div>`
                        : '<div class="assigned-to-item">Không tìm thấy thông tin</div>';
                }).join('')
                : 'N/A'
            }
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
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhiệm vụ');
    }
}

// Hàm xóa task
async function deleteTask(taskId) {
    if (!taskId) {
        console.error('Task ID không hợp lệ');
        return;
    }

    // Hiển thị thông báo xác nhận
    const isConfirmed = confirm('Bạn có chắc chắn muốn xóa nhiệm vụ này không?');
    if (!isConfirmed) return;

    try {
        const response = await fetch(`${API_URL}/tasks/${taskId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Xóa nhiệm vụ thành công');
            loadTasks(); // Tải lại danh sách sau khi xóa
        } else {
            const errorData = await response.json();
            alert(`Không thể xóa nhiệm vụ: ${errorData.message || 'Lỗi không xác định'}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa nhiệm vụ');
    }
}