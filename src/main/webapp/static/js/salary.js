const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;
    console.log('Current path:', path); // In ra đường dẫn hiện tại

    if (path.endsWith('/salaries')) {
        loadSalaries(); // Tải danh sách lương nếu ở trang danh sách
    }
});

// Hàm tải danh sách lương và hiển thị lên bảng
async function loadSalaries() {
    try {
        const response = await fetch(`${API_URL}/salaries`);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách lương');
        }
        const salaries = await response.json();

        // Lấy danh sách nhân viên và công ty từ API
        const employeesResponse = await fetch(`${API_URL}/employees`);
        const companiesResponse = await fetch(`${API_URL}/companies`);
        if (!employeesResponse.ok || !companiesResponse.ok) {
            throw new Error('Không thể tải danh sách nhân viên hoặc công ty');
        }
        const employees = await employeesResponse.json();
        const companiesData = await companiesResponse.json();

        // Tạo một map để lưu thông tin nhân viên theo ID
        const employeeMap = new Map();
        employees.forEach(employee => {
            employeeMap.set(employee._id, employee);
        });

        // Tạo một map để lưu thông tin công ty theo ID
        const companies = new Map();
        companiesData.forEach(company => {
            companies.set(company._id, company);
        });

        const tableBody = document.querySelector('#salariesTableBody');
        tableBody.innerHTML = ''; // Xóa nội dung cũ

        let Stt = 0;
        salaries.forEach(salary => {
            const row = document.createElement('tr');
            const employee = employeeMap.get(salary.employeeId);
            const employeeName = employee ? employee.name : 'Không tìm thấy nhân viên';
            const companyName = employee ? companies.get(employee.company_id)?.name : 'Không tìm thấy công ty';
            const phone = employee ? employee.phone : 'N/A';

            Stt++;

            row.innerHTML = `
                <td>${Stt}</td>
                <td>${employeeName}</td>
                <td>${companyName}</td>
                <td>${phone}</td>
                <td>${salary.salary}</td>
                <td>${salary.reward}</td>
                <td>${salary.working_days}</td>
                <td>
                    <a href="${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/salaries/edit?id=${salary._id}" class="btn btn-primary btn-sm">Sửa</a>
                    <button onclick="deleteSalary('${salary._id}')" class="btn btn-danger btn-sm">Xóa</button>
                </td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách lương');
    }
}

// Hàm xóa lương
async function deleteSalary(salaryId) {
    if (!salaryId) {
        console.error('Salary ID không hợp lệ');
        return;
    }

    // Hiển thị thông báo xác nhận
    const isConfirmed = confirm('Bạn có chắc chắn muốn xóa lương này không?');
    if (!isConfirmed) return;

    try {
        const response = await fetch(`${API_URL}/salaries/${salaryId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Xóa lương thành công');
            loadSalaries(); // Tải lại danh sách sau khi xóa
        } else {
            const errorData = await response.json();
            alert(`Không thể xóa lương: ${errorData.message || 'Lỗi không xác định'}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa lương');
    }
}