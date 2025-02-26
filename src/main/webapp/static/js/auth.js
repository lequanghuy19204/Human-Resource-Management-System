document.addEventListener('DOMContentLoaded', function() {
    // Xử lý form đăng ký
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const username = document.getElementById('registerUsername').value;
            const password = document.getElementById('registerPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const companyName = document.getElementById('companyName').value;
            
            // Kiểm tra mật khẩu xác nhận
            if (password !== confirmPassword) {
                showAlert('Mật khẩu xác nhận không khớp!', 'danger');
                return;
            }
            
            // Gửi request đăng ký
            fetch(`${registerForm.action}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                    companyName: companyName
                })
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(data => {
                        throw new Error(data.message || 'Đăng ký thất bại');
                    });
                }
                return response.json();
            })
            .then(data => {
                showAlert('Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.', 'success');
                registerForm.reset();
                // Chuyển sang tab đăng nhập
                document.getElementById('login-tab').click();
            })
            .catch(error => {
                showAlert(error.message, 'danger');
            });
        });
    }
    
    // Xử lý form đăng nhập
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const username = document.getElementById('loginUsername').value;
            const password = document.getElementById('loginPassword').value;
            
            // Gửi request đăng nhập
            fetch(`${loginForm.action}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(data => {
                        throw new Error(data.message || 'Đăng nhập thất bại');
                    });
                }
                return response.json();
            })
            .then(data => {
                // Chuyển hướng đến trang chính sau khi đăng nhập thành công
                window.location.href = '/Human-Resource-Management-System-1.0-SNAPSHOT/organizations';
            })
            .catch(error => {
                showAlert(error.message, 'danger');
            });
        });
    }
    
    // Kiểm tra trạng thái hệ thống và chuyển tab nếu cần
    const initialized = document.getElementById('systemInitialized').value === 'false';
    if (!initialized) {
        document.querySelector('a[href="#register-tab"]').click();
    }
});

// Hiển thị thông báo
function showAlert(message, type) {
    const alertContainer = document.createElement('div');
    alertContainer.className = `alert alert-${type} alert-dismissible fade show`;
    alertContainer.role = 'alert';
    
    alertContainer.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    // Thêm alert vào đầu container
    const container = document.querySelector('.auth-container');
    const existingAlert = container.querySelector('.alert');
    
    if (existingAlert) {
        container.replaceChild(alertContainer, existingAlert);
    } else {
        container.insertBefore(alertContainer, container.firstChild.nextSibling);
    }
    
    // Tự động ẩn sau 5 giây
    setTimeout(() => {
        const alert = document.querySelector('.alert');
        if (alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }
    }, 5000);
}