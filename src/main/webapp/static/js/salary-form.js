$(document).ready(function () {
// Tải danh sách nhân viên khi trang được tải
loadEmployees();

// Thiết lập sự kiện tìm kiếm nhân viên
$('#employeeSearch').on('input', function () {
const searchTerm = $(this).val().toLowerCase();
$('#employeeList .employee-item').each(function () {
const text = $(this).text().toLowerCase();
$(this).toggle(text.includes(searchTerm));
});
});

// Thiết lập sự kiện khi chọn nhân viên
$('#employeeList').on('click', '.employee-item', function () {
const defaultSalary = parseFloat($(this).data('salary')) || 0;
$('#defaultSalary').val(defaultSalary);
calculateCurrentSalary();
});

// Tính toán lương tháng này khi thưởng thay đổi
$('#reward').on('input', calculateCurrentSalary);

// Gửi form lưu thay đổi
$('#salaryForm').on('submit', function (event) {
event.preventDefault();
saveSalary();
});

// Nếu đang ở chế độ chỉnh sửa, tải dữ liệu lương
const salaryId = $('#salaryId').val();
if (salaryId) {
loadSalaryData(salaryId);
}
});

// Hàm tải danh sách nhân viên
function loadEmployees() {
$.ajax({
url: '/Human-Resource-Management-System-1.0-SNAPSHOT/api/employees',
method: 'GET',
success: function (employees) {
const employeeList = $('#employeeList');
employeeList.empty();

employees.forEach(employee => {
const item = $(`
<div class="employee-item" data-id="${employee._id}" data-salary="${employee.defaultSalary}">
    ${employee.name} - ${employee.companyName} - ${employee.phone}
</div>
`);
employeeList.append(item);
});
},
error: function (xhr, status, error) {
alert('Có lỗi xảy ra khi tải danh sách nhân viên: ' + error);
}
});
}

// Hàm tính toán lương tháng này
function calculateCurrentSalary() {
const defaultSalary = parseFloat($('#defaultSalary').val()) || 0;
const reward = parseFloat($('#reward').val()) || 0;
const currentSalary = defaultSalary + reward;
$('#currentSalary').val(currentSalary);
}

// Hàm tải dữ liệu lương nếu đang chỉnh sửa
function loadSalaryData(salaryId) {
$.ajax({
url: `/Human-Resource-Management-System-1.0-SNAPSHOT/api/salaries/${salaryId}`,
method: 'GET',
success: function (salary) {
$('#defaultSalary').val(salary.previousMonthSalary);
$('#reward').val(salary.reward);
$('#currentSalary').val(salary.currentMonthSalary);

// Chọn nhân viên tương ứng
$(`.employee-item[data-id="${salary.employeeId}"]`).addClass('active');
},
error: function (xhr, status, error) {
alert('Có lỗi xảy ra khi tải dữ liệu lương: ' + error);
}
});
}

// Hàm lưu thay đổi lương
function saveSalary() {
const salaryId = $('#salaryId').val();
const employeeId = $('#employeeList .employee-item.active').data('id');
const reward = parseFloat($('#reward').val()) || 0;
const currentSalary = parseFloat($('#currentSalary').val()) || 0;

if (!employeeId) {
alert('Vui lòng chọn nhân viên');
return;
}

const method = salaryId ? 'PUT' : 'POST';
const url = salaryId
? `/Human-Resource-Management-System-1.0-SNAPSHOT/api/salaries/${salaryId}`
: '/Human-Resource-Management-System-1.0-SNAPSHOT/api/salaries';

$.ajax({
url: url,
method: method,
contentType: 'application/json',
data: JSON.stringify({
employeeId: employeeId,
reward: reward,
currentSalary: currentSalary
}),
success: function () {
alert('Lưu thành công');
window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/salaries';
},
error: function (xhr, status, error) {
alert('Có lỗi xảy ra khi lưu lương: ' + error);
}
});
}