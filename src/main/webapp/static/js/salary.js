const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    loadSalaries();

    // Bắt sự kiện khi thay đổi bộ lọc tháng
    document.querySelector('#monthFilter').addEventListener('change', loadSalaries);
});



// Tải dữ liệu lương + nhân viên + công ty song song
async function loadSalaries() {
    try {
        const monthFilter = document.querySelector('#monthFilter');
        if (!monthFilter.value) {
            const now = new Date();
            now.setMonth(now.getMonth() - 1);
            monthFilter.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
        }
        const companyId = document.getElementById('companyId').value;
        const filterDate = new Date(monthFilter.value);
        const response = await fetch(`${API_URL}/salaries/month/${filterDate.getMonth() + 1}/year/${filterDate.getFullYear()}/companyId/${companyId}`);

        if (!response.ok) {
            throw new Error('Không thể tải dữ liệu');
        }
        const data = await response.json();
        renderSalaries(data);
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách lương');
    }
}

// Hiển thị danh sách lương
function renderSalaries(salaries) {
    const tableBody = document.querySelector('#salariesTableBody');
    tableBody.innerHTML = '';

    const fragment = document.createDocumentFragment();

    salaries.forEach((salary, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${salary.name  || 'N/A'}</td>
            <td>${salary.phone || 'N/A'}</td>
            <td>${salary.base_salary}</td>
            <td><input type="number" value="${salary.overtime_hours}" class="form-control" id="overtime_hours_${index}" min="0"></td>
            <td><input type="number" value="${salary.late_hours}" class="form-control" id="late_hours_${index}" min="0"></td>
            <td><input type="number" value="${salary.absent_days}" class="form-control" id="absent_days_${index}" min="0"></td>
            <td><input type="number" value="${salary.approved_leave_days}" class="form-control" id="approved_leave_days_${index}" min="0"></td>
            <td>${salary.sumSalary}</td>
            <td>
                <button class="btn btn-success btn-sm" onclick="saveSalary(salary._id ? '${salary._id}' : null,${index + 1}), '${salary.employee_id}'">Sửa</button>
            </td>
        `;
        fragment.appendChild(row);
    });

    tableBody.appendChild(fragment);
}
async function saveSalary(index) {
    const overtime = document.getElementById(`overtime_hours_${index}`).value;
    const late = document.getElementById(`late_hours_${index}`).value;
    const absent = document.getElementById(`absent_days_${index}`).value;
    const leave = document.getElementById(`approved_leave_days_${index}`).value;
    const companyId = document.getElementById('companyId').value;
    const filterDate = new Date(document.querySelector('#monthFilter').value);

    const payload = {
        employee_id: employeeId,
        company_id: companyId,
        year: filterDate.getFullYear(),
        month: filterDate.getMonth() + 1,
        overtime_hours: parseFloat(overtime),
        late_hours: parseFloat(late),
        absent_days: parseFloat(absent),
        approved_leave_days: parseFloat(leave)
    };

    let method = 'POST';
    let url = `${API_URL}/salaries`;

    // Nếu có salaryId thì kiểm tra có tồn tại không
    if (salaryId) {
        try {
            const checkRes = await fetch(`${API_URL}/salaries/${salaryId}`, { method: 'HEAD' });

            if (checkRes.ok) {
                // Nếu tồn tại thì chuyển sang PATCH
                method = 'PATCH';
                url = `${API_URL}/salaries/${salaryId}`;
            } else {
                console.warn('Salary ID không tồn tại, sẽ tạo mới.');
            }
        } catch (err) {
            console.warn('Không kiểm tra được salaryId, fallback về POST');
        }
    }

    try {
        const response = await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message || 'Lỗi khi lưu lương');
        }

        alert(method === 'PATCH' ? 'Cập nhật thành công!' : 'Tạo mới thành công!');
        loadSalaries();
    } catch (error) {
        console.error('Save Error:', error);
        alert(`Lỗi: ${error.message}`);
    }
}
