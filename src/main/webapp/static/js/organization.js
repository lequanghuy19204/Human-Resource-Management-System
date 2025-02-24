const API_URL = '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteOrgId = null;

// Xử lý xóa tổ chức
function deleteOrganization(id) {
    deleteOrgId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

// Xác nhận xóa
document.getElementById('confirmDelete').addEventListener('click', async () => {
    try {
        const response = await fetch(`${API_URL}/organizations/${deleteOrgId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            window.location.reload();
        } else {
            alert('Có lỗi xảy ra khi xóa tổ chức');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa tổ chức');
    }
}); 