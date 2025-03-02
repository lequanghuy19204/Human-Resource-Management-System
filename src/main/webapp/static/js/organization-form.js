const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', () => {
    setupFormValidation();
    loadOrganizationData();
});

async function loadOrganizationData() {
    const organizationId = document.getElementById('organizationId').value;
    if (organizationId) {
        try {
            const response = await fetch(`${API_URL}/organizations/${organizationId}`);
            if (response.ok) {
                const organization = await response.json();
                document.getElementById('name').value = organization.name;
            } else {
                alert('Không thể tải thông tin tổ chức');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi tải thông tin tổ chức');
        }
    }
}

function setupFormValidation() {
    const form = document.getElementById('organizationForm');
    
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        event.stopPropagation();
        
        if (form.checkValidity()) {
            await saveOrganization();
        }
        
        form.classList.add('was-validated');
    });
}

async function saveOrganization() {
    const organizationId = document.getElementById('organizationId').value;
    const organizationData = {
        name: document.getElementById('name').value
    };

    try {
        const method = organizationId ? 'PUT' : 'POST';
        const url = organizationId 
            ? `${API_URL}/organizations/${organizationId}`
            : `${API_URL}/organizations`;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(organizationData)
        });

        if (response.ok) {
            window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/organizations';
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi lưu tổ chức');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu tổ chức');
    }
} 