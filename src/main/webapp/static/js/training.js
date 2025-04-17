const API_URL = window.location.origin + '/Human-Resource-Management-System-1.0-SNAPSHOT/api';
let deleteTrainingId = null;

async function loadTrainingPrograms() {
    try {
        // Sử dụng company_id từ session
        const companyId = document.getElementById('companyId').value;
        // Sử dụng endpoint mới để lấy danh sách theo company_id
        const response = await fetch(`${API_URL}/training-programs/company/${companyId}`);
        const programs = await response.json();
        
        const container = document.getElementById('trainingContainer');
        container.innerHTML = '';
        
        if (programs.length === 0) {
            container.innerHTML = `
                <div class="col-12">
                    <div class="card">
                        <div class="card-body text-center py-5">
                            <i class="bi bi-mortarboard display-4 text-muted mb-3"></i>
                            <h5>Chưa có chương trình đào tạo nào</h5>
                            <p class="text-muted">Hãy tạo chương trình đào tạo đầu tiên</p>
                            <button class="btn btn-primary" onclick="window.location.href='training/create'">
                                <i class="bi bi-plus-circle me-2"></i>Thêm Chương trình
                            </button>
                        </div>
                    </div>
                </div>
            `;
            return;
        }

        for (const program of programs) {
            // Lấy thông tin trainer
            let trainerName = 'Không xác định';
            if (program.trainer_id) {
                const trainerResponse = await fetch(`${API_URL}/training-programs/trainer/${program.trainer_id}`);
                if (trainerResponse.ok) {
                    const trainer = await trainerResponse.json();
                    trainerName = trainer.name;
                }
            }

            const startDate = new Date(program.start).toLocaleDateString('vi-VN');
            const endDate = new Date(program.end).toLocaleDateString('vi-VN');

            container.innerHTML += `
                <div class="col">
                    <div class="card training-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <h5 class="training-title mb-3">
                                    <i class="bi bi-mortarboard me-2 text-primary"></i>
                                    ${program.name}
                                </h5>
                                <div class="dropdown">
                                    <button class="btn btn-link text-dark p-0" data-bs-toggle="dropdown">
                                        <i class="bi bi-three-dots-vertical"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li>
                                            <a class="dropdown-item" href="training/edit?id=${program._id}">
                                                <i class="bi bi-pencil me-2"></i>Chỉnh sửa
                                            </a>
                                        </li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li>
                                            <a class="dropdown-item text-danger" href="#" onclick="deleteTraining('${program._id}')">
                                                <i class="bi bi-trash me-2"></i>Xóa
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="training-info mb-2">
                                <i class="bi bi-geo-alt me-2"></i>${program.location}
                            </div>
                            <div class="training-info mb-2">
                                <i class="bi bi-person me-2"></i>${trainerName}
                            </div>
                            <div class="training-info mb-2">
                                <i class="bi bi-calendar-event me-2"></i>${startDate} - ${endDate}
                            </div>
                        </div>
                    </div>
                </div>
            `;
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi tải danh sách chương trình đào tạo');
    }
}

function deleteTraining(id) {
    deleteTrainingId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

document.getElementById('confirmDelete').addEventListener('click', async () => {
    try {
        const response = await fetch(`${API_URL}/training-programs/${deleteTrainingId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
            modal.hide();
            loadTrainingPrograms();
        } else {
            const data = await response.json();
            alert(data.message || 'Có lỗi xảy ra khi xóa chương trình đào tạo');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra khi xóa chương trình đào tạo');
    }
});

document.addEventListener('DOMContentLoaded', loadTrainingPrograms);