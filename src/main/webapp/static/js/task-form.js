const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    loadEmployees(); // Tải danh sách nhân viên khi trang được tải
    setupFormValidation(); // Thiết lập validation cho form
});

// Hàm tải danh sách nhân viên từ API
async function loadEmployees() {
    try {
        const response = await fetch(`${API_URL}/employees`);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách nhân viên');
        }
        const employees = await response.json();

        const select = document.getElementById('assigned_to');
        select.innerHTML = ''; // Xóa các option cũ

        // Thêm option mặc định
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.textContent = 'Chọn người được giao';
        select.appendChild(defaultOption);

        // Thêm từng nhân viên vào dropdown
        employees.forEach(employee => {
            const option = document.createElement('option');
            option.value = employee._id; // Sử dụng ObjectID của nhân viên
            option.textContent = employee.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhân viên');
    }
}

// Thiết lập validation và xử lý submit form
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

// Hàm lưu nhiệm vụ
async function saveTask() {
    const taskId = new URLSearchParams(window.location.search).get('id');
    const taskData = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        assigned_to: Array.from(document.getElementById('assigned_to').selectedOptions)
            .map(option => option.value)
            .filter(id => id !== ''), // Loại bỏ giá trị rỗng
        status: document.getElementById('status').value
    };

    try {
        const method = taskId ? 'PUT' : 'POST';
        const url = taskId ? `${window.location.origin}/api/tasks/${taskId}` : `${window.location.origin}/api/tasks`;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskData)
        });

        if (response.ok) {
            window.location.href = `${window.location.origin}/tasks`; // Chuyển hướng về trang danh sách
        } else {
            alert('Có lỗi xảy ra khi lưu nhiệm vụ');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu nhiệm vụ');
    }
}