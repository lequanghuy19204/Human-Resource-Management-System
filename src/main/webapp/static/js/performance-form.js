const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
const urlParams = new URLSearchParams(window.location.search);
const performanceId = urlParams.get('id');
let employeeMap = new Map(); // Map để lưu thông tin nhân viên

document.addEventListener('DOMContentLoaded', () => {
    loadEmployeeList();
    if (performanceId) {
        loadPerformanceData(performanceId); // Nếu có `id` thì là chế độ chỉnh sửa
    }
});

// Tải danh sách nhân viên
async function loadEmployeeList() {
    try {
        const response = await fetch(`${API_URL}/employees`);
        if (!response.ok) throw new Error('Không thể tải danh sách nhân viên');
        const employees = await response.json();

        const employeeSelect = document.querySelector('#employeeId');
        employeeSelect.innerHTML = '<option value="">-- Chọn nhân viên --</option>';

        employees.forEach(employee => {
            const option = document.createElement('option');
            option.value = employee._id;
            option.textContent = employee.name;
            employeeSelect.appendChild(option);

            // Lưu thông tin vào Map để tra cứu nhanh khi chỉnh sửa
            employeeMap.set(employee._id, employee.name);
        });

        // Nếu đang trong chế độ chỉnh sửa, chọn đúng nhân viên trong dropdown
        if (performanceId) {
            selectEmployeeInDropdown();
        }
    } catch (error) {
        console.error('Lỗi khi tải danh sách nhân viên:', error);
    }
}

// Tải dữ liệu hiệu suất (chỉnh sửa)
async function loadPerformanceData(id) {
    try {
        const response = await fetch(`${API_URL}/performances/${id}`);
        if (!response.ok) throw new Error('Không thể tải dữ liệu hiệu suất');
        const performance = await response.json();

        document.querySelector('#performanceScore').value = performance.performance_score;
        document.querySelector('#completeRate').value = performance.complete_rate;
        document.querySelector('#ontimeRate').value = performance.ontime_rate;
        document.querySelector('#qualityScore').value = performance.quality_score;

        // Chọn đúng nhân viên trong dropdown
        if (employeeMap.size > 0) {
            document.querySelector('#employeeId').value = performance.employee_id;
        }
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu:', error);
    }
}

// Chọn đúng nhân viên trong dropdown khi chỉnh sửa
function selectEmployeeInDropdown() {
    const employeeId = document.querySelector('#employeeId').value;
    if (employeeId && employeeMap.has(employeeId)) {
        document.querySelector('#employeeId').value = employeeId;
    }
}

// Xử lý lưu dữ liệu (thêm hoặc cập nhật)
async function handleSavePerformance(event) {
    event.preventDefault();

    const performanceData = {
        employee_id: document.querySelector('#employeeId').value,
        performance_score: parseFloat(document.querySelector('#performanceScore').value),
        complete_rate: parseFloat(document.querySelector('#completeRate').value),
        ontime_rate: parseFloat(document.querySelector('#ontimeRate').value),
        quality_score: parseInt(document.querySelector('#qualityScore').value)
    };

    try {
        const url = performanceId
            ? `${API_URL}/performances/${performanceId}`  // Gửi đúng `id` khi cập nhật
            : `${API_URL}/performances`;
        const method = performanceId ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(performanceData)
        });

        if (response.ok) {
            alert(performanceId ? 'Cập nhật thành công' : 'Thêm mới thành công');
            window.location.href = `${window.location.origin}/Human-Resource-Management-System-1.0-SNAPSHOT/performance`;
        } else {
            const errorData = await response.json();
            alert(`Lỗi: ${errorData.message || 'Không xác định'}`);
        }
    } catch (error) {
        console.error('Lỗi khi lưu dữ liệu:', error);
        alert('Có lỗi xảy ra khi lưu dữ liệu');
    }
}

// Bắt sự kiện khi submit form
document.querySelector('#performanceForm').addEventListener('submit', handleSavePerformance);
