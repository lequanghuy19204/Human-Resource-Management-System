const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteEmpId = null;
let allEmployees = []; // Lưu trữ tất cả nhân viên
let organizationsMap = new Map(); // Lưu trữ tất cả tổ chức
let managersMap = new Map(); // Lưu trữ tất cả quản lý
let currentOrganizationFilter = ''; // Lưu trữ bộ lọc tổ chức hiện tại
let currentSearchTerm = ''; // Lưu trữ từ khóa tìm kiếm hiện tại

async function loadEmployees() {
    try {
        const companyId = document.getElementById('companyId').value;
        const tableBody = document.getElementById('employeesTableBody');
        
        // Hiển thị trạng thái đang tải
        tableBody.innerHTML = `
            <tr>
                <td colspan="12" class="text-center py-4">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Đang tải...</span>
                    </div>
                    <p class="mt-2 text-muted mb-0">Đang tải dữ liệu...</p>
                </td>
            </tr>
        `;
        
        // Lấy danh sách nhân viên
        const response = await fetch(`${API_URL}/employees/company/${companyId}`);
        allEmployees = await response.json();
        
        if (allEmployees.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="12" class="text-center py-4">
                        <p class="text-muted mb-0">Chưa có nhân viên nào</p>
                    </td>
                </tr>
            `;
            document.getElementById('totalEmployees').textContent = '0';
            return;
        }

        // Tải dữ liệu tổ chức một lần
        organizationsMap = new Map();
        const orgIds = [...new Set(allEmployees.map(emp => emp.organization_id))];
        await Promise.all(orgIds.map(async (orgId) => {
            if (orgId) {
                const orgResponse = await fetch(`${API_URL}/organizations/${orgId}`);
                const org = await orgResponse.json();
                organizationsMap.set(orgId, org);
            }
        }));
        
        // Tải danh sách tổ chức cho bộ lọc
        loadOrganizationFilter();
        
        // Tải dữ liệu quản lý một lần
        managersMap = new Map();
        const managerIds = [...new Set(allEmployees.filter(emp => emp.manager_id).map(emp => emp.manager_id))];
        await Promise.all(managerIds.map(async (managerId) => {
            if (managerId) {
                const managerResponse = await fetch(`${API_URL}/employees/${managerId}`);
                if (managerResponse.ok) {
                    const manager = await managerResponse.json();
                    managersMap.set(managerId, manager);
                }
            }
        }));
        
        // Hiển thị dữ liệu
        renderEmployees(allEmployees);
        
    } catch (error) {
        console.error('Error:', error);
        const tableBody = document.getElementById('employeesTableBody');
        tableBody.innerHTML = `
            <tr>
                <td colspan="12" class="text-center py-4">
                    <p class="text-danger mb-0">Có lỗi xảy ra khi tải danh sách nhân viên</p>
                    <button class="btn btn-sm btn-outline-primary mt-2" onclick="loadEmployees()">
                        <i class="bi bi-arrow-clockwise"></i> Thử lại
                    </button>
                </td>
            </tr>
        `;
        document.getElementById('totalEmployees').textContent = '0';
    }
}

// Tải danh sách tổ chức cho bộ lọc
function loadOrganizationFilter() {
    const organizationFilter = document.getElementById('organizationFilter');
    organizationFilter.innerHTML = '<option value="">-- Tất cả tổ chức --</option>';
    
    // Tạo mảng các tổ chức để sắp xếp theo tên
    const organizations = [];
    for (const [orgId, org] of organizationsMap.entries()) {
        organizations.push({
            id: orgId,
            name: org.name
        });
    }
    
    // Sắp xếp tổ chức theo tên
    organizations.sort((a, b) => a.name.localeCompare(b.name));
    
    // Thêm các tổ chức vào dropdown
    for (const org of organizations) {
        const option = document.createElement('option');
        option.value = org.id;
        option.textContent = org.name;
        organizationFilter.appendChild(option);
    }
}

// Hàm hiển thị danh sách nhân viên
function renderEmployees(employees) {
    const tableBody = document.getElementById('employeesTableBody');
    document.getElementById('totalEmployees').textContent = employees.length;
    
    if (employees.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="12" class="text-center py-4">
                    <p class="text-muted mb-0">Không tìm thấy nhân viên nào</p>
                </td>
            </tr>
        `;
        return;
    }
    
    let html = '';
    
    for (const emp of employees) {
        const org = organizationsMap.get(emp.organization_id) || { name: 'Không có' };
        const manager = managersMap.get(emp.manager_id);
        const managerName = manager ? manager.name : '-';
        
        html += `
            <tr>
                <td>${emp.name}</td>
                <td>${emp.position_name}</td>
                <td>${org.name}</td>
                <td>${managerName}</td>
                <td>${emp.phone || '-'}</td>
                <td>${new Intl.NumberFormat('vi-VN').format(emp.base_salary || 0)} VNĐ</td>
                <td>${emp.task_count || 0}</td>
                <td>${emp.completed_tasks || 0}</td>
                <td>${emp.ontime_tasks || 0}</td>
                <td>${emp.quality_score?.toFixed(1) || '0.0'}</td>
                <td>${emp.performance_score?.toFixed(1) || '0.0'}</td>
                <td>
                    <div class="btn-group btn-group-sm">
                        <button class="btn btn-primary" onclick="window.location.href='employees/edit?id=${emp._id}'">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-danger" onclick="deleteEmployee('${emp._id}')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    }
    
    tableBody.innerHTML = html;
}

// Hàm áp dụng bộ lọc và tìm kiếm
function applyFilters() {
    let filteredEmployees = allEmployees;
    
    // Áp dụng tìm kiếm theo từ khóa
    if (currentSearchTerm) {
        filteredEmployees = filteredEmployees.filter(emp => {
            const org = organizationsMap.get(emp.organization_id) || { name: '' };
            const manager = managersMap.get(emp.manager_id) || { name: '' };
            
            return emp.name.toLowerCase().includes(currentSearchTerm) ||
                emp.position_name.toLowerCase().includes(currentSearchTerm) ||
                org.name.toLowerCase().includes(currentSearchTerm) ||
                manager.name.toLowerCase().includes(currentSearchTerm) ||
                (emp.phone && emp.phone.toLowerCase().includes(currentSearchTerm));
        });
    }
    
    // Áp dụng lọc theo tổ chức
    if (currentOrganizationFilter) {
        filteredEmployees = filteredEmployees.filter(emp => 
            emp.organization_id === currentOrganizationFilter
        );
    }
    
    renderEmployees(filteredEmployees);
}

// Hàm xử lý tìm kiếm
function searchEmployees() {
    currentSearchTerm = document.getElementById('searchInput').value.toLowerCase().trim();
    applyFilters();
}

// Hàm xử lý lọc theo tổ chức
function filterByOrganization() {
    currentOrganizationFilter = document.getElementById('organizationFilter').value;
    applyFilters();
}

// Hàm xóa bộ lọc
function resetFilters() {
    document.getElementById('searchInput').value = '';
    document.getElementById('organizationFilter').value = '';
    currentSearchTerm = '';
    currentOrganizationFilter = '';
    renderEmployees(allEmployees);
}

function deleteEmployee(id) {
    deleteEmpId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

// Xử lý sự kiện
document.addEventListener('DOMContentLoaded', function() {
    // Load danh sách ban đầu
    loadEmployees();
    
    // Sự kiện cho nút tìm kiếm
    document.getElementById('searchButton').addEventListener('click', searchEmployees);
    
    // Sự kiện cho ô input (tìm kiếm khi nhấn Enter)
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchEmployees();
        }
    });
    
    // Sự kiện cho bộ lọc tổ chức
    document.getElementById('organizationFilter').addEventListener('change', filterByOrganization);
    
    // Sự kiện cho nút xóa bộ lọc
    document.getElementById('resetFilterButton').addEventListener('click', resetFilters);
    
    // Sự kiện xác nhận xóa
    document.getElementById('confirmDelete').addEventListener('click', async () => {
        try {
            const response = await fetch(`${API_URL}/employees/${deleteEmpId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
                modal.hide();
                loadEmployees();
            } else {
                const data = await response.json();
                alert(data.message || 'Có lỗi xảy ra khi xóa nhân viên');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi xóa nhân viên');
        }
    });
}); 