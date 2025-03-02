const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api/tasks';

document.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname.includes('list')) {
        loadTasks();
    } else if (window.location.pathname.includes('form')) {
        setupFormValidation();
        loadEmployeesForAssignment();
        loadTaskData();
    }
});

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
    const taskId = document.getElementById('taskId').value;
    if (!taskId) return;

    try {
        const response = await fetch(`${API_URL}/${taskId}`);
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
    const taskId = document.getElementById('taskId')?.value;
    const taskData = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        assigned_to: Array.from(document.getElementById('assigned_to').selectedOptions).map(opt => opt.value),
        status: document.getElementById('status').value
    };

    try {
        const method = taskId ? 'PUT' : 'POST';
        const url = taskId ? `${API_URL}/${taskId}` : API_URL;

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