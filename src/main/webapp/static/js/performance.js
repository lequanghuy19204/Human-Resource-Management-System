const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    loadPerformanceData();
});

// Tải dữ liệu hiệu suất
async function loadPerformanceData() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/employees/company/${companyId}`);
        const performances = await response.json();
        console.log(performances);
        renderPerformance(performances);
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu:', error);
        alert('Có lỗi xảy ra khi tải danh sách hiệu suất');
    }
}

// Hiển thị danh sách hiệu suất
function renderPerformance(performances) {
    const tableBody = document.querySelector('#performanceTableBody');
    tableBody.innerHTML = '';

    const fragment = document.createDocumentFragment();

    performances.forEach((performance, index) => {
        const Task_Completion_Rate = performance.task_count ? performance.completed_tasks/performance.task_count : 0;
        const Ontime_Rate = performance.completed_tasks ? performance.ontime_tasks/performance.completed_tasks : 0;
        const EE_Score = performance.quality_score;
        const Performance_Score = 0.5*Task_Completion_Rate + 0.2*Ontime_Rate + 0.3*EE_Score;
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${performance.name || 'lỗi tên nhân viên'}</td>
            <td>${Performance_Score}%</td>
            <td>${Task_Completion_Rate}%</td>
            <td>${Ontime_Rate}%</td>
            <td><input type="number" step="0.01" min="0" max="1" class="form-control ee-score-input" data-id="${performance._id}" value="${EE_Score}" /></td>
            <td>
                <button class="btn btn-primary btn-sm save-btn" data-id="${performance._id}">Sửa</button>
            </td>
        `;
        fragment.appendChild(row);
    });

    tableBody.appendChild(fragment);

    document.querySelectorAll('.save-btn').forEach(button => {
        button.addEventListener('click', async (e) => {
            const employeeId = button.getAttribute('data-id');
            const input = document.querySelector(`.ee-score-input[data-id="${employeeId}"]`);
            const newScore = parseFloat(input.value);

            if (isNaN(newScore) || newScore < 0 || newScore > 100) {
                alert('Điểm EE phải nằm trong khoảng từ 0 đến 100');
                return;
            }

            try {
                const res = await fetch(`${API_URL}/employees/performance/${employeeId}`, {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ quality_score: newScore })
                });

                if (!res.ok) throw new Error('Lỗi khi cập nhật điểm');

                alert('Cập nhật thành công!');
                loadPerformanceData();
            } catch (err) {
                console.error(err);
                alert('Có lỗi xảy ra khi cập nhật điểm EE');
            }
        });
    });
}