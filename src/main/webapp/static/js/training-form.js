const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let allEmployees = [];

document.addEventListener('DOMContentLoaded', () => {
    initializeForm();
    setupParticipantControls();
    document.getElementById('trainingForm').addEventListener('submit', handleSubmit);
});

async function initializeForm() {
    await Promise.all([
        loadTrainers(),
        initializeParticipants()
    ]);

    const trainingId = document.getElementById('trainingId').value;
    if (trainingId) {
        await loadTrainingData();
    }
}

async function loadTrainers() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/employees/company/${companyId}/details`);
        const employees = await response.json();
        
        const select = document.getElementById('trainer');
        employees.forEach(emp => {
            const option = document.createElement('option');
            option.value = emp._id;
            option.textContent = `${emp.name} - ${emp.position_name} - ${emp.organization_name || 'Không có tổ chức'}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách người đào tạo');
    }
}

async function initializeParticipants() {
    try {
        const companyId = document.getElementById('companyId').value;
        const response = await fetch(`${API_URL}/employees/company/${companyId}/details`);
        allEmployees = await response.json();
        
        const availableSelect = document.getElementById('availableParticipants');
        allEmployees.forEach(emp => {
            const option = document.createElement('option');
            option.value = emp._id;
            option.textContent = `${emp.name} - ${emp.position_name} - ${emp.organization_name || 'Không có tổ chức'}`;
            availableSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách người tham gia');
    }
}

function setupParticipantControls() {
    // Nút thêm người tham gia được chọn
    document.getElementById('addParticipant').addEventListener('click', () => {
        moveSelectedOptions('availableParticipants', 'selectedParticipants');
    });

    // Nút thêm tất cả
    document.getElementById('addAllParticipants').addEventListener('click', () => {
        moveAllOptions('availableParticipants', 'selectedParticipants');
    });

    // Nút xóa người tham gia được chọn
    document.getElementById('removeParticipant').addEventListener('click', () => {
        moveSelectedOptions('selectedParticipants', 'availableParticipants');
    });

    // Nút xóa tất cả
    document.getElementById('removeAllParticipants').addEventListener('click', () => {
        moveAllOptions('selectedParticipants', 'availableParticipants');
    });

    // Tìm kiếm trong danh sách có sẵn
    document.getElementById('searchAvailable').addEventListener('input', (e) => {
        filterOptions('availableParticipants', e.target.value);
    });

    // Tìm kiếm trong danh sách đã chọn
    document.getElementById('searchSelected').addEventListener('input', (e) => {
        filterOptions('selectedParticipants', e.target.value);
    });
}

function moveSelectedOptions(fromId, toId) {
    const fromSelect = document.getElementById(fromId);
    const toSelect = document.getElementById(toId);
    
    Array.from(fromSelect.selectedOptions).forEach(option => {
        toSelect.appendChild(option.cloneNode(true));
        fromSelect.remove(option.index);
    });
}

function moveAllOptions(fromId, toId) {
    const fromSelect = document.getElementById(fromId);
    const toSelect = document.getElementById(toId);
    
    Array.from(fromSelect.options).forEach(option => {
        toSelect.appendChild(option.cloneNode(true));
    });
    fromSelect.innerHTML = '';
}

function filterOptions(selectId, searchText) {
    const select = document.getElementById(selectId);
    const options = Array.from(select.options);
    
    options.forEach(option => {
        const text = option.text.toLowerCase();
        const search = searchText.toLowerCase();
        option.style.display = text.includes(search) ? '' : 'none';
    });
}

async function loadTrainingData() {
    try {
        const trainingId = document.getElementById('trainingId').value;
        const response = await fetch(`${API_URL}/training-programs/${trainingId}`);
        const training = await response.json();
        
        // Điền thông tin chương trình
        document.getElementById('name').value = training.name;
        document.getElementById('location').value = training.location;
        document.getElementById('description').value = training.description;
        document.getElementById('start').value = formatDateTimeLocal(training.start);
        document.getElementById('end').value = formatDateTimeLocal(training.end);
        document.getElementById('trainer').value = training.trainer_id;

        // Set participants
        if (training.participant_ids && training.participant_ids.length > 0) {
            const availableSelect = document.getElementById('availableParticipants');
            const selectedSelect = document.getElementById('selectedParticipants');
            
            training.participant_ids.forEach(id => {
                const option = availableSelect.querySelector(`option[value="${id}"]`);
                if (option) {
                    selectedSelect.appendChild(option.cloneNode(true));
                    availableSelect.removeChild(option);
                }
            });
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải thông tin chương trình đào tạo');
    }
}

function formatDateTimeLocal(dateString) {
    const date = new Date(dateString);
    return date.toISOString().slice(0, 16);
}

async function handleSubmit(event) {
    event.preventDefault();
    
    const form = event.target;
    if (!form.checkValidity()) {
        event.stopPropagation();
        form.classList.add('was-validated');
        return;
    }

    const selectedParticipants = Array.from(document.getElementById('selectedParticipants').options)
        .map(option => option.value);

    if (selectedParticipants.length === 0) {
        alert('Vui lòng chọn ít nhất một người tham gia');
        return;
    }

    const companyId = document.getElementById('companyId').value;
    const trainingData = {
        name: document.getElementById('name').value,
        location: document.getElementById('location').value,
        description: document.getElementById('description').value,
        start: new Date(document.getElementById('start').value),
        end: new Date(document.getElementById('end').value),
        trainer_id: document.getElementById('trainer').value,
        participant_ids: selectedParticipants,
        company_id: companyId
    };

    try {
        const trainingId = document.getElementById('trainingId').value;
        const url = trainingId ? 
            `${API_URL}/training-programs/${trainingId}` : 
            `${API_URL}/training-programs`;
        
        const response = await fetch(url, {
            method: trainingId ? 'PUT' : 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(trainingData)
        });

        if (response.ok) {
            window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/training';
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi lưu chương trình đào tạo');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi lưu chương trình đào tạo');
    }
} 