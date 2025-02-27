const API_URL = "http://localhost:8080/Human-Resource-Management-System-1.0-SNAPSHOT/api/salaries";

// Load danh sách lương
const loadSalaries = async () => {
    const salaryContainer = document.getElementById("salaries-container");
    try {
        const response = await fetch(API_URL);
        const salaries = await response.json();

        salaryContainer.innerHTML = "";
        if (salaries.length === 0) {
            salaryContainer.innerHTML = "<p>No salaries to display.</p>";
            return;
        }

        salaries.forEach((salary) => {
            const salaryCard = document.createElement("div");
            salaryCard.className = "salary-card";

            salaryCard.innerHTML = `
                <p><strong>Employee ID:</strong> ${salary.employee_id}</p>
                <p><strong>Working Days:</strong> ${salary.working_days}</p>
                <p><strong>Salary:</strong> $${salary.salary.toFixed(2)}</p>
                <p><strong>Payment Date:</strong> ${new Date(salary.payment_date).toLocaleDateString()}</p>
                <button class="edit" onclick="editSalary('${salary._id}')">Edit</button>
                <button class="delete" onclick="deleteSalary('${salary._id}')">Delete</button>
            `;

            salaryContainer.appendChild(salaryCard);
        });
    } catch (error) {
        console.error("Error loading salaries:", error);
        salaryContainer.innerHTML = "<p style='color: red;'>Failed to load salaries. Try again later.</p>";
    }
};

// Lưu hoặc cập nhật Salary
const saveSalary = async (salary) => {
    try {
        const url = salary._id ? `${API_URL}/${salary._id}` : API_URL;
        const method = salary._id ? "PUT" : "POST";

        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(salary),
        });

        if (response.ok) {
            loadSalaries();
            resetForm();
            alert(salary._id ? "Salary updated successfully!" : "Salary added successfully!");
        } else {
            console.error("Failed to save salary:", await response.text());
        }
    } catch (error) {
        console.error("Error saving salary:", error);
    }
};

// Xóa Salary
const deleteSalary = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (response.ok) {
            loadSalaries();
            alert("Salary deleted successfully!");
        } else {
            console.error("Failed to delete salary:", await response.text());
        }
    } catch (error) {
        console.error("Error deleting salary:", error);
    }
};

// Sửa Salary
const editSalary = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const salary = await response.json();

        document.getElementById("salary-id").value = salary._id;
        document.getElementById("employee-id").value = salary.employee_id;
        document.getElementById("working-days").value = salary.working_days;
        document.getElementById("salary-amount").value = salary.salary;
        document.getElementById("payment-date").value = salary.payment_date.split("T")[0];

        document.getElementById("form-title").innerText = "Edit Salary";
        document.getElementById("cancel-edit").style.display = "inline-block";
    } catch (error) {
        console.error("Error fetching salary for editing:", error);
    }
};

// Reset Form
const resetForm = () => {
    document.getElementById("salary-id").value = "";
    document.getElementById("employee-id").value = "";
    document.getElementById("working-days").value = "";
    document.getElementById("salary-amount").value = "";
    document.getElementById("payment-date").value = "";
    document.getElementById("form-title").innerText = "Add Salary";
    document.getElementById("cancel-edit").style.display = "none";
};

// Xử lý sự kiện Submit form
document.getElementById("salary-form-element").addEventListener("submit", (event) => {
    event.preventDefault();
    const salary = {
        _id: document.getElementById("salary-id").value || null,
        employee_id: document.getElementById("employee-id").value,
        working_days: parseInt(document.getElementById("working-days").value),
        salary: parseFloat(document.getElementById("salary-amount").value),
        payment_date: document.getElementById("payment-date").value,
    };

    saveSalary(salary);
});

// Hủy chế độ chỉnh sửa
document.getElementById("cancel-edit").addEventListener("click", resetForm);

// Tải danh sách Salary khi trang được load
window.onload = loadSalaries;
