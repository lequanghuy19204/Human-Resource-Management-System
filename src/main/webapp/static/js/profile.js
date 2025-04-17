const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let currentEmployee = null;
let currentAccount = null;
let currentOrganization = null;
let currentManager = null;

document.addEventListener('DOMContentLoaded', function() {
    loadProfileData();
    setupEventListeners();
});

async function loadProfileData() {
    try {
        const employeeId = document.getElementById('employeeId').value;
        
        // Lấy thông tin nhân viên
        const employeeResponse = await fetch(`${API_URL}/employees/${employeeId}`);
        if (!employeeResponse.ok) {
            throw new Error('Không thể tải thông tin nhân viên');
        }
        
        currentEmployee = await employeeResponse.json();
        
        // Lấy thông tin tổ chức
        if (currentEmployee.organization_id) {
            const orgResponse = await fetch(`${API_URL}/organizations/${currentEmployee.organization_id}`);
            if (orgResponse.ok) {
                currentOrganization = await orgResponse.json();
            }
        }
        
        // Lấy thông tin quản lý
        if (currentEmployee.manager_id) {
            const managerResponse = await fetch(`${API_URL}/employees/${currentEmployee.manager_id}`);
            if (managerResponse.ok) {
                currentManager = await managerResponse.json();
            }
        }
        
        // Lấy thông tin tài khoản
        if (currentEmployee.account_id) {
            const accountResponse = await fetch(`${API_URL}/accounts/${currentEmployee.account_id}`);
            if (accountResponse.ok) {
                currentAccount = await accountResponse.json();
            }
        }
        
        // Hiển thị thông tin
        displayProfileData();
        
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải thông tin hồ sơ: ' + error.message);
    }
}

function displayProfileData() {
    if (!currentEmployee) return;
    
    // Hiển thị thông tin cơ bản
    document.getElementById('profileName').textContent = currentEmployee.name || 'Chưa có thông tin';
    document.getElementById('profilePosition').textContent = currentEmployee.position_name || 'Chưa có thông tin';
    document.getElementById('profilePhone').querySelector('span').textContent = currentEmployee.phone || 'Chưa có thông tin';
    document.getElementById('profileDepartment').querySelector('span').textContent = currentOrganization ? currentOrganization.name : 'Chưa có thông tin';
    
    // Hiển thị thông tin tài khoản
    document.getElementById('profileUsername').textContent = currentAccount ? currentAccount.username : 'Chưa có thông tin';
    
    // Hiển thị thông tin trong form
    document.getElementById('name').value = currentEmployee.name || '';
    document.getElementById('position_name').value = currentEmployee.position_name || '';
    document.getElementById('phone').value = currentEmployee.phone || '';
    document.getElementById('organization').value = currentOrganization ? currentOrganization.name : '';
    document.getElementById('manager').value = currentManager ? currentManager.name : 'Không có';
    
    // Hiển thị hiệu suất
    document.getElementById('taskCount').textContent = currentEmployee.task_count || 0;
    document.getElementById('completedTasks').textContent = currentEmployee.completed_tasks || 0;
    document.getElementById('ontimeTasks').textContent = currentEmployee.ontime_tasks || 0;
    document.getElementById('performanceScore').textContent = (currentEmployee.performance_score || 0).toFixed(1);
    document.getElementById('qualityScore').textContent = (currentEmployee.quality_score || 0).toFixed(1);
    
    // Hiển thị lương
    const formattedSalary = new Intl.NumberFormat('vi-VN').format(currentEmployee.base_salary || 0);
    document.getElementById('baseSalary').textContent = `${formattedSalary} VNĐ`;
}

function setupEventListeners() {
    // Sự kiện nút chỉnh sửa
    document.getElementById('editProfileBtn').addEventListener('click', function() {
        toggleEditMode(true);
    });
    
    // Sự kiện nút hủy
    document.getElementById('cancelEdit').addEventListener('click', function() {
        toggleEditMode(false);
        // Reset lại giá trị ban đầu
        document.getElementById('name').value = currentEmployee.name || '';
        document.getElementById('phone').value = currentEmployee.phone || '';
    });
    
    // Sự kiện submit form
    document.getElementById('profileForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        if (this.checkValidity()) {
            await updateProfile();
        }
        
        this.classList.add('was-validated');
    });
}

function toggleEditMode(isEdit) {
    // Chỉ cho phép chỉnh sửa một số trường
    document.getElementById('name').disabled = !isEdit;
    document.getElementById('phone').disabled = !isEdit;
    
    // Hiển thị/ẩn nút lưu và hủy
    document.getElementById('formActions').style.display = isEdit ? 'block' : 'none';
    document.getElementById('editProfileBtn').style.display = isEdit ? 'none' : 'block';
}

async function updateProfile() {
    try {
        const employeeId = currentEmployee._id;
        
        // Chỉ cập nhật các trường được phép thay đổi
        const updatedData = {
            ...currentEmployee,
            name: document.getElementById('name').value,
            phone: document.getElementById('phone').value
        };
        
        const response = await fetch(`${API_URL}/employees/${employeeId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedData)
        });
        
        if (response.ok) {
            // Cập nhật thông tin hiện tại
            currentEmployee = await response.json();
            displayProfileData();
            toggleEditMode(false);
            alert('Cập nhật thông tin thành công');
        } else {
            const error = await response.json();
            alert(error.message || 'Có lỗi xảy ra khi cập nhật thông tin');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi cập nhật thông tin: ' + error.message);
    }
} 