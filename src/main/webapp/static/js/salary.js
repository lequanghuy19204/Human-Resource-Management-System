const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    // Cài đặt tháng mặc định là tháng trước
    setDefaultMonth();

    // Load danh sách lương ban đầu
    loadSalaries();
});

// Hàm cài đặt tháng mặc định là tháng trước
function setDefaultMonth() {
    const now = new Date();
    now.setMonth(now.getMonth() - 1); // Lấy tháng trước
    document.querySelector('#monthFilter').value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
    console.log(document.querySelector('#monthFilter').value);
}

// Hàm tải danh sách lương và hiển thị lên bảng
async function loadSalaries() {
    try {
        // Lấy giá trị tháng từ bộ lọc
        const monthFilter = document.querySelector('#monthFilter').value;
        let selectedMonth = null;

        if (monthFilter) {
            selectedMonth = new Date(monthFilter + '-01').getTime(); // Chuyển tháng về timestamp
        }

        // Gọi API với tham số lọc theo tháng
        const url = selectedMonth
            ? `${API_URL}/salaries?month=${selectedMonth}`
            : `${API_URL}/salaries`;

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Không thể tải danh sách lương');
        }

        const salaries = await response.json();

        // Lấy dữ liệu nhân viên và công ty
        const employeesResponse = await fetch(`${API_URL}/employees`);
        const companiesResponse = await fetch(`${API_URL}/companies`);
        const rewardsResponse = await fetch(`${API_URL}/rewards`);

        if (!employeesResponse.ok || !companiesResponse.ok) {
            throw new Error('Không thể tải danh sách nhân viên hoặc công ty');
        }

        const employeesData = await employeesResponse.json();
        const companiesData = await companiesResponse.json();
        const rewardsData = await rewardsResponse.json();

        // Tạo map cho nhân viên, công ty và thưởng
        const employeeMap = new Map(employeesData.map(emp => [emp._id, emp]));
        const companiesMap = new Map(companiesData.map(company => [company._id, company]));
        const rewardsMap = new Map(rewardsData.map(reward => [reward.employee_id, reward.reward]));

        // Xóa dữ liệu cũ trước khi hiển thị dữ liệu mới
        const tableBody = document.querySelector('#salariesTableBody');
        tableBody.innerHTML = '';

        let Stt = 0;
        salaries.forEach(salary => {
            const employee = employeeMap.get(salary.employee_id) || {};
            const company = companiesMap.get(employee.company_id) || {};
            const reward = rewardsMap.get(salary.employee_id) || 0;

            Stt++;
            const row = `
                <tr>
                    <td>${Stt}</td>
                    <td>${employee.name || 'Không tìm thấy nhân viên'}</td>
                    <td>${company.name || 'Không tìm thấy công ty'}</td>
                    <td>${employee.phone || 'N/A'}</td>
                    <td>${salary.salary}</td>
                    <td>${reward}</td>
                    <td>${salary.working_days}</td>
                    <td>
                        <a href="${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/salaries/edit?id=${salary._id}" class="btn btn-primary btn-sm">Sửa</a>
                        <button onclick="deleteSalary('${salary._id}')" class="btn btn-danger btn-sm">Xóa</button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách lương');
    }
}

// Hàm xóa lương
async function deleteSalary(salaryId) {
    if (!salaryId) return;

    const isConfirmed = confirm('Bạn có chắc chắn muốn xóa lương này không?');
    if (!isConfirmed) return;

    try {
        const response = await fetch(`${API_URL}/salaries/${salaryId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Xóa lương thành công');
            loadSalaries(); // Tải lại dữ liệu
        } else {
            const errorData = await response.json();
            alert(`Không thể xóa lương: ${errorData.message || 'Lỗi không xác định'}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa lương');
    }
}
