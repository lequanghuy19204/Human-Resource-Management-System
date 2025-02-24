const API_URL = '/Human-Resource-Management-System-1.0-SNAPSHOT/api';

document.addEventListener('DOMContentLoaded', async () => {
    // Khởi tạo Select2
    $('#companyId, #positionIds').select2({
        theme: 'bootstrap-5'
    });

    // Load dữ liệu
    await Promise.all([
        loadCompanies(),
        loadPositions()
    ]);

    // Setup form validation
    setupFormValidation();
});

async function loadCompanies() {
    try {
        const response = await fetch(`${API_URL}/companies`);
        const companies = await response.json();
        const select = document.getElementById('companyId');
        
        companies.forEach(company => {
            const option = new Option(company.name, company._id);
            select.add(option);
        });

        // Set giá trị nếu đang edit
        const organization = document.getElementById('organizationId').value;
        if (organization) {
            select.value = organization.companyId;
        }
    } catch (error) {
        console.error('Error loading companies:', error);
    }
}

async function loadPositions() {
    try {
        const response = await fetch(`${API_URL}/positions`);
        const positions = await response.json();
        const select = document.getElementById('positionIds');
        
        positions.forEach(position => {
            const option = new Option(position.name, position._id);
            select.add(option);
        });

        // Set giá trị nếu đang edit
        const organization = document.getElementById('organizationId').value;
        if (organization && organization.positionIds) {
            organization.positionIds.forEach(id => {
                Array.from(select.options).forEach(option => {
                    if (option.value === id) option.selected = true;
                });
            });
        }
    } catch (error) {
        console.error('Error loading positions:', error);
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
        name: document.getElementById('name').value,
        companyId: document.getElementById('companyId').value,
        positionIds: Array.from(document.getElementById('positionIds').selectedOptions)
                         .map(option => option.value)
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
            alert('Có lỗi xảy ra khi lưu tổ chức');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu tổ chức');
    }
} 