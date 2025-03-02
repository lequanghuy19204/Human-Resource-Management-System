const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    setupFormValidation();
    loadOrganizations();
    loadManagers();
    loadEmployeeData();
});

async function loadOrganizations() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/organizations/company/${companyId}`);
        const organizations = await response.json();
        
        const select = document.getElementById('organization_id');
        organizations.forEach(org => {
            const option = document.createElement('option');
            option.value = org.id;
            option.textContent = org.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách tổ chức');
    }
}

async function loadManagers() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/employees/managers/${companyId}`);
        const managers = await response.json();
        
        const select = document.getElementById('manager_id');
        managers.forEach(manager => {
            const option = document.createElement('option');
            option.value = manager._id;
            option.textContent = manager.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách quản lý');
    }
}

async function loadEmployeeData() {
    const employeeId = document.getElementById('employeeId').value;
    if (!employeeId) return;

    try {
        const response = await fetch(`${API_URL}/employees/${employeeId}`);
        if (response.ok) {
            const employee = await response.json();
            
            // Điền thông tin nhân viên
            document.getElementById('name').value = employee.name;
            document.getElementById('position_name').value = employee.position_name;
            document.getElementById('organization_id').value = employee.organization_id;
            document.getElementById('manager_id').value = employee.manager_id || '';
            document.getElementById('phone').value = employee.phone || '';

            // Đánh dấu các quyền
            const checkboxes = document.querySelectorAll('.permission-checkbox');
            checkboxes.forEach(checkbox => {
                if (employee.permissions.includes(checkbox.value)) {
                    checkbox.checked = true;
                }
            });

            // Ẩn/disable trường tài khoản khi cập nhật
            document.getElementById('username').disabled = true;
            document.getElementById('password').disabled = true;
        } else {
            alert('Không thể tải thông tin nhân viên');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải thông tin nhân viên');
    }
}

function setupFormValidation() {
    const form = document.getElementById('employeeForm');
    
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        event.stopPropagation();
        
        // Kiểm tra quyền hạn
        const permissions = getSelectedPermissions();
        if (permissions.length === 0) {
            document.querySelector('.permission-checkbox').parentElement.classList.add('was-validated');
            return;
        }
        
        if (form.checkValidity()) {
            await saveEmployee();
        }
        
        form.classList.add('was-validated');
    });
}

function getSelectedPermissions() {
    const checkboxes = document.querySelectorAll('.permission-checkbox:checked');
    return Array.from(checkboxes).map(cb => cb.value);
}

async function saveEmployee() {
    const employeeId = document.getElementById('employeeId').value;
    const companyId = document.getElementById('companyId').value;

    const employeeData = {
        name: document.getElementById('name').value,
        position_name: document.getElementById('position_name').value,
        organization_id: document.getElementById('organization_id').value,
        manager_id: document.getElementById('manager_id').value || null,
        phone: document.getElementById('phone').value,
        permissions: getSelectedPermissions(),
        company_id: companyId,
        overtime_hours: 0,
        late_hours: 0,
        absent_days: 0
    };

    // Thêm thông tin tài khoản nếu là tạo mới
    if (!employeeId) {
        employeeData.account = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        };
    }

    try {
        const method = employeeId ? 'PUT' : 'POST';
        const url = employeeId 
            ? `${API_URL}/employees/${employeeId}`
            : `${API_URL}/employees`;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employeeData)
        });

        if (response.ok) {
            window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/employees';
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi lưu nhân viên');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu nhân viên');
    }
} 