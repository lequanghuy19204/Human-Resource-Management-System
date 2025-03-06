const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname.includes('list')) {
        loadTasks();
    } else if (window.location.pathname.includes('form')) {
        setupFormValidation();
        loadEmployeesForAssignment();
        loadTaskData();
    }
});

async function loadTasks() {
    try {
        const response = await fetch(`${API_URL}/tasks`);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách nhiệm vụ');
        }
        const tasks = await response.json();

        const tableBody = document.querySelector('#tasksTableBody');
        tableBody.innerHTML = ''; // Xóa nội dung cũ

        tasks.forEach(task => {
            const row = document.createElement('tr');
            // Tạo các cột dữ liệu
            row.innerHTML = `
                <td>${task.name}</td>
                <td>${task.description || 'N/A'}</td>
                <td>${task.assigned_to ? task.assigned_to.join(', ') : 'N/A'}</td>
                <td>${task.status || 'N/A'}</td>
                <td>
                    <a href="${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/tasks/form?id=${task._id}" class="btn btn-primary btn-sm">Sửa</a>
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

// Xóa nhiệm vụ
async function deleteTask(taskId) {
    if (confirm('Bạn có chắc chắn muốn xóa nhiệm vụ này không?')) {
        try {
            const response = await fetch(`${API_URL}/tasks/${taskId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Xóa nhiệm vụ thành công');
                loadTasks(); // Tải lại danh sách sau khi xóa
            } else {
                alert('Không thể xóa nhiệm vụ');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi xóa nhiệm vụ');
        }
    }
}

// Tải danh sách nhân viên để gán nhiệm vụ
async function loadEmployeesForAssignment() {
    try {
        const response = await fetch(`${API_URL}/employees`);
        const employees = await response.json();

        const select = document.getElementById('assigned_to');
        employees.forEach(employee => {
            const option = document.createElement('option');
            option.value = employee._id;
            option.textContent = employee.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhân viên');
    }
}

// Tải dữ liệu Task nếu đang chỉnh sửa
async function loadTaskData() {
    const taskId = new URLSearchParams(window.location.search).get('id');
    if (!taskId) return;

    try {
        const response = await fetch(`${API_URL}/tasks/${taskId}`);
        if (response.ok) {
            const task = await response.json();

            // Điền thông tin Task vào form
            document.getElementById('name').value = task.name;
            document.getElementById('description').value = task.description || '';
            document.getElementById('assigned_to').value = task.assigned_to || '';
            document.getElementById('status').value = task.status || '';
        } else {
            alert('Không thể tải thông tin nhiệm vụ');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải thông tin nhiệm vụ');
    }
}

// Thiết lập validation cho form
function setupFormValidation() {
    const form = document.getElementById('taskForm');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        event.stopPropagation();

        if (form.checkValidity()) {
            await saveTask();
        }

        form.classList.add('was-validated');
    });
}

// Lưu Task
async function saveTask() {
    const taskId = new URLSearchParams(window.location.search).get('id');
    const taskData = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        assigned_to: Array.from(document.getElementById('assigned_to').selectedOptions).map(opt => opt.value),
        status: document.getElementById('status').value
    };

    try {
        const method = taskId ? 'PUT' : 'POST';
        const url = taskId ? `${API_URL}/tasks/${taskId}` : `${API_URL}/tasks`;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskData)
        });

        if (response.ok) {
            window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/tasks';
        } else {
            alert('Có lỗi xảy ra khi lưu nhiệm vụ');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu nhiệm vụ');
    }
}

document.addEventListener('DOMContentLoaded', loadTasks);