const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    loadPerformanceData();

    // Xử lý sự kiện nút "Thêm mới"
    document.querySelector('#addPerformanceBtn').addEventListener('click', () => {
        window.location.href = `${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/performance/create`;
    });
});

// Tải dữ liệu hiệu suất
async function loadPerformanceData() {
    try {
        const [performanceRes, employeesRes] = await Promise.all([
            fetch(`${API_URL}/performances`),
            fetch(`${API_URL}/employees`)
        ]);

        if (!performanceRes.ok || !employeesRes.ok) {
            throw new Error('Không thể tải dữ liệu');
        }

        const performances = await performanceRes.json();
        const employees = await employeesRes.json();

        // Tạo map cho dữ liệu nhân viên
        const employeeMap = new Map(employees.map(emp => [emp._id, emp]));

        renderPerformance(performances, employeeMap);
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu:', error);
        alert('Có lỗi xảy ra khi tải danh sách hiệu suất');
    }
}

// Hiển thị danh sách hiệu suất
function renderPerformance(performances, employeeMap) {
    const tableBody = document.querySelector('#performanceTableBody');
    tableBody.innerHTML = '';

    const fragment = document.createDocumentFragment();

    performances.forEach((performance, index) => {
        const employee = employeeMap.get(performance.employee_id) || {};

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${employee.name || 'Không tìm thấy nhân viên'}</td>
            <td>${performance.performance_score.toFixed(2)}</td>
            <td>${performance.complete_rate.toFixed(2)}%</td>
            <td>${performance.ontime_rate.toFixed(2)}%</td>
            <td>${performance.quality_score}</td>
            <td>
                <a href="${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/performance/edit?id=${performance._id}" class="btn btn-primary btn-sm">Sửa</a>
                <button onclick="deletePerformance('${performance._id}')" class="btn btn-danger btn-sm">Xóa</button>
            </td>
        `;
        fragment.appendChild(row);
    });

    tableBody.appendChild(fragment);
}

// Xóa hiệu suất
async function deletePerformance(performanceId) {
    if (!performanceId) return;

    const isConfirmed = confirm('Bạn có chắc chắn muốn xóa hiệu suất này không?');
    if (!isConfirmed) return;

    try {
        const response = await fetch(`${API_URL}/performance/${performanceId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Xóa hiệu suất thành công');
            loadPerformanceData(); // Tải lại dữ liệu
        } else {
            const errorData = await response.json();
            alert(`Không thể xóa hiệu suất: ${errorData.message || 'Lỗi không xác định'}`);
        }
    } catch (error) {
        console.error('Lỗi khi xóa hiệu suất:', error);
        alert('Có lỗi xảy ra khi xóa hiệu suất');
    }
}
