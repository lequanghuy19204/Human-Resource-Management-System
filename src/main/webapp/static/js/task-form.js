const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    // Tải danh sách nhân viên và thiết lập tìm kiếm
    loadEmployees();
    setupEmployeeSearch();

    // Thiết lập validation cho form
    setupFormValidation();

    // Kiểm tra xem đang ở trang chỉnh sửa hay thêm mới
    if (window.location.pathname.includes('/tasks/edit')) {
        loadTaskData();
    }
});

// Hàm tải danh sách nhân viên từ API
async function loadEmployees() {
    try {
        const response = await fetch(`${API_URL}/employees`);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách nhân viên');
        }
        const employees = await response.json();

        const employeeListDiv = document.getElementById('employeeList');
        employeeListDiv.innerHTML = ''; // Xóa nội dung cũ

        // Thêm từng nhân viên vào danh sách checkbox
        employees.forEach(employee => {
            const employeeDiv = document.createElement('div');
            employeeDiv.className = 'form-check';
            employeeDiv.innerHTML = `
                <input class="form-check-input" type="checkbox" value="${employee._id}" id="employee_${employee._id}">
                <label class="form-check-label" for="employee_${employee._id}">
                    ${employee.name} - ${employee.company_id} - ${employee.phone}
                </label>
            `;
            employeeListDiv.appendChild(employeeDiv);
        });

        // Thiết lập sự kiện khi checkbox thay đổi
        setupCheckboxEvents();
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhân viên');
    }
}

// Hàm thiết lập sự kiện khi checkbox thay đổi
function setupCheckboxEvents() {
    const checkboxes = document.querySelectorAll('#employeeList .form-check-input');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', renderSelectedEmployees);
    });
}

// Hàm hiển thị thành viên đã chọn
function renderSelectedEmployees() {
    const selectedEmployeesDiv = document.getElementById('selectedEmployees');
    const checkboxes = document.querySelectorAll('#employeeList .form-check-input:checked');

    selectedEmployeesDiv.innerHTML = ''; // Xóa nội dung cũ

    checkboxes.forEach(checkbox => {
        const employeeDiv = document.createElement('div');
        employeeDiv.className = 'd-flex justify-content-between align-items-center mb-2';
        employeeDiv.innerHTML = `
            <span>${checkbox.nextElementSibling.textContent}</span>
            <button class="btn btn-danger btn-sm" onclick="removeSelectedEmployee('${checkbox.value}')">Xóa</button>
        `;
        selectedEmployeesDiv.appendChild(employeeDiv);
    });
}

// Hàm xóa thành viên đã chọn
function removeSelectedEmployee(employeeId) {
    const checkbox = document.querySelector(`#employee_${employeeId}`);
    if (checkbox) {
        checkbox.checked = false; // Bỏ chọn
        renderSelectedEmployees(); // Cập nhật lại danh sách hiển thị
    }
}

// Hàm thiết lập tìm kiếm nhân viên
function setupEmployeeSearch() {
    const searchInput = document.getElementById('employeeSearch');
    const employeeListDiv = document.getElementById('employeeList');

    searchInput.addEventListener('input', () => {
        const searchTerm = searchInput.value.toLowerCase();

        Array.from(employeeListDiv.children).forEach(employeeDiv => {
            const label = employeeDiv.querySelector('.form-check-label');
            const text = label.textContent.toLowerCase();
            employeeDiv.style.display = text.includes(searchTerm) ? 'block' : 'none';
        });
    });
}

// Hàm tải dữ liệu task nếu đang chỉnh sửa
async function loadTaskData() {
    const taskId = new URLSearchParams(window.location.search).get('id');
    if (!taskId) return;

    try {
        const response = await fetch(`${API_URL}/tasks/${taskId}`);
        if (response.ok) {
            const task = await response.json();

            // Điền thông tin task vào form
            document.getElementById('name').value = task.name;
            document.getElementById('description').value = task.description || '';

            // Điền thông tin người được giao (assigned_to)
            if (task.assigned_to && task.assigned_to.length > 0) {
                task.assigned_to.forEach(employeeId => {
                    const checkbox = document.querySelector(`#employee_${employeeId}`);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                });
            }

            // Điền trạng thái (status)
            document.getElementById('status').value = task.status || '';

            // Hiển thị thành viên đã chọn
            renderSelectedEmployees();
        } else {
            alert('Không thể tải thông tin nhiệm vụ');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải thông tin nhiệm vụ');
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
        assigned_to: Array.from(document.querySelectorAll('#employeeList .form-check-input:checked'))
            .map(checkbox => checkbox.value), // Lấy giá trị từ các checkbox đã chọn
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
            window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/tasks'; // Chuyển hướng về trang danh sách
        } else {
            alert('Có lỗi xảy ra khi lưu nhiệm vụ');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu nhiệm vụ');
    }
}