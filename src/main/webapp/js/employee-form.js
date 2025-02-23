// Constants
const API_URL = 'Human-Resource-Management-System-1.0-SNAPSHOT//api/employees';
const POSITION_API_URL = 'Human-Resource-Management-System-1.0-SNAPSHOT//api/positions';
let currentOrgId = null;
let currentEmpId = null;

// Load data when page loads
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    currentOrgId = urlParams.get('orgId');
    currentEmpId = urlParams.get('empId');

    if (!currentOrgId) {
        alert('Không tìm thấy thông tin tổ chức');
        window.location.href = 'index.html';
        return;
    }

    if (currentEmpId) {
        document.getElementById('formTitle').textContent = 'Sửa Thông Tin Nhân Viên';
        loadEmployeeData();
    }

    // Add form submit handler
    document.getElementById('employeeForm').addEventListener('submit', handleSubmit);
});

async function loadEmployeeData() {
    try {
        const response = await fetch(`${API_URL}/${currentEmpId}`);
        const employee = await response.json();
        
        // Fill employee data
        document.getElementById('employeeName').value = employee.name;
        document.getElementById('employeePhone').value = employee.phone;
        document.getElementById('employeeStatus').value = employee.status;

        // Fill position data if exists
        if (employee.position) {
            document.getElementById('positionName').value = employee.position.name;
            document.getElementById('positionDescription').value = employee.position.description;
            document.getElementById('baseSalary').value = employee.position.baseSalary;
        }
    } catch (error) {
        console.error('Error loading employee data:', error);
        alert('Không thể tải thông tin nhân viên');
    }
}

async function handleSubmit(event) {
    event.preventDefault();

    const employeeData = {
        name: document.getElementById('employeeName').value,
        phone: document.getElementById('employeePhone').value,
        status: document.getElementById('employeeStatus').value,
        organizationId: currentOrgId,
        position: {
            name: document.getElementById('positionName').value,
            description: document.getElementById('positionDescription').value,
            baseSalary: parseFloat(document.getElementById('baseSalary').value)
        }
    };

    try {
        const method = currentEmpId ? 'PUT' : 'POST';
        const url = currentEmpId ? `${API_URL}/${currentEmpId}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employeeData)
        });

        if (response.ok) {
            window.location.href = `employees.html?orgId=${currentOrgId}`;
        } else {
            throw new Error('Lỗi khi lưu thông tin nhân viên');
        }
    } catch (error) {
        console.error('Error saving employee:', error);
        alert('Không thể lưu thông tin nhân viên');
    }
} 