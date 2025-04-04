const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    setDefaultMonth();
    loadSalaries();

    // Bắt sự kiện khi thay đổi bộ lọc tháng
    document.querySelector('#monthFilter').addEventListener('change', loadSalaries);
});

function setDefaultMonth() {
    const monthFilter = document.querySelector('#monthFilter');
    if (!monthFilter.value) {
        const now = new Date();
        now.setMonth(now.getMonth() - 1);
        monthFilter.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
    }
}

// Tải dữ liệu lương + nhân viên + công ty song song
async function loadSalaries() {
    try {
        const [salariesRes, employeesRes, companiesRes, rewardsRes] = await Promise.all([
            fetch(`${API_URL}/salaries`),
            fetch(`${API_URL}/employees`),
            fetch(`${API_URL}/companies`),
            fetch(`${API_URL}/rewards`)
        ]);

        if (!salariesRes.ok || !employeesRes.ok || !companiesRes.ok || !rewardsRes.ok) {
            throw new Error('Không thể tải dữ liệu');
        }

        const salaries = await salariesRes.json();
        const employees = await employeesRes.json();
        const companies = await companiesRes.json();
        const rewards = await rewardsRes.json();

        // Tạo map cho dữ liệu
        const employeeMap = new Map(employees.map(emp => [emp._id, emp]));
        const companiesMap = new Map(companies.map(company => [company._id, company]));
        const rewardsMap = new Map(rewards.map(reward => [reward.employee_id, reward.reward || 0]));

        // Lọc theo tháng từ ô tìm kiếm
        const monthFilter = document.querySelector('#monthFilter').value;
        const filteredSalaries = monthFilter
            ? salaries.filter(salary => {
                const paymentDate = new Date(salary.payment_date);
                const filterDate = new Date(monthFilter + '-01');
                return (
                    paymentDate.getMonth() === filterDate.getMonth() &&
                    paymentDate.getFullYear() === filterDate.getFullYear()
                );
            })
            : salaries;

        renderSalaries(filteredSalaries, employeeMap, companiesMap, rewardsMap);
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách lương');
    }
}

// Hiển thị danh sách lương
function renderSalaries(salaries, employeeMap, companiesMap, rewardsMap) {
    const tableBody = document.querySelector('#salariesTableBody');
    tableBody.innerHTML = '';

    const fragment = document.createDocumentFragment();

    salaries.forEach((salary, index) => {
        const employee = employeeMap.get(salary.employee_id) || {};
        const company = companiesMap.get(employee.company_id) || {};
        const reward = rewardsMap.get(salary.employee_id) || 0;

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
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
        `;
        fragment.appendChild(row);
    });

    tableBody.appendChild(fragment);
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
