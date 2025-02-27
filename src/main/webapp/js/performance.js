const API_URL = "http://localhost:8080/Human-Resource-Management-System-1.0-SNAPSHOT/api/performances";

// Load danh sách Performance
const loadPerformances = async () => {
    const performanceContainer = document.getElementById("performances-container");
    try {
        const response = await fetch(API_URL);
        const performances = await response.json();

        performanceContainer.innerHTML = "";
        if (performances.length === 0) {
            performanceContainer.innerHTML = "<p>No performance records found.</p>";
            return;
        }

        performances.forEach((performance) => {
            const performanceCard = document.createElement("div");
            performanceCard.className = "performance-card";

            performanceCard.innerHTML = `
                <p><strong>Employee ID:</strong> ${performance.employee_id}</p>
                <p><strong>Performance Score:</strong> ${performance.performance_score}</p>
                <p><strong>Complete Rate:</strong> ${performance.complete_rate}%</p>
                <p><strong>On-Time Rate:</strong> ${performance.ontime_rate}%</p>
                <p><strong>Quality Score:</strong> ${performance.quality_score}</p>
                <button class="edit" onclick="editPerformance('${performance._id}')">Edit</button>
                <button class="delete" onclick="deletePerformance('${performance._id}')">Delete</button>
            `;

            performanceContainer.appendChild(performanceCard);
        });
    } catch (error) {
        console.error("Error loading performances:", error);
        performanceContainer.innerHTML = `<p style="color: red;">Failed to load performances. Try again later.</p>`;
    }
};

// Lưu hoặc cập nhật Performance
const savePerformance = async (performance) => {
    try {
        const url = performance._id ? `${API_URL}/${performance._id}` : API_URL;
        const method = performance._id ? "PUT" : "POST";

        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(performance),
        });

        if (response.ok) {
            loadPerformances();
            resetForm();
            alert(performance._id ? "Performance record updated successfully!" : "Performance record created successfully!");
        } else {
            console.error("Failed to save performance:", await response.text());
        }
    } catch (error) {
        console.error("Error saving performance:", error);
    }
};

// Xóa Performance
const deletePerformance = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (response.ok) {
            loadPerformances();
            alert("Performance record deleted successfully!");
        } else {
            console.error("Failed to delete performance:", await response.text());
        }
    } catch (error) {
        console.error("Error deleting performance:", error);
    }
};

// Sửa Performance
const editPerformance = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const performance = await response.json();

        document.getElementById("performance-id").value = performance._id;
        document.getElementById("employee-id").value = performance.employee_id;
        document.getElementById("performance-score").value = performance.performance_score;
        document.getElementById("complete-rate").value = performance.complete_rate;
        document.getElementById("ontime-rate").value = performance.ontime_rate;
        document.getElementById("quality-score").value = performance.quality_score;

        document.getElementById("form-title").innerText = "Edit Performance Record";
        document.getElementById("cancel-edit").style.display = "inline-block";
    } catch (error) {
        console.error("Error fetching performance for editing:", error);
    }
};

// Reset Form
const resetForm = () => {
    document.getElementById("performance-id").value = "";
    document.getElementById("employee-id").value = "";
    document.getElementById("performance-score").value = "";
    document.getElementById("complete-rate").value = "";
    document.getElementById("ontime-rate").value = "";
    document.getElementById("quality-score").value = "";
    document.getElementById("form-title").innerText = "Create Performance Record";
    document.getElementById("cancel-edit").style.display = "none";
};

// Xử lý sự kiện Submit form
document.getElementById("performance-form-element").addEventListener("submit", (event) => {
    event.preventDefault();
    const performance = {
        _id: document.getElementById("performance-id").value || null,
        employee_id: document.getElementById("employee-id").value,
        performance_score: parseFloat(document.getElementById("performance-score").value),
        complete_rate: parseFloat(document.getElementById("complete-rate").value),
        ontime_rate: parseFloat(document.getElementById("ontime-rate").value),
        quality_score: parseInt(document.getElementById("quality-score").value),
    };

    savePerformance(performance);
});

// Hủy chế độ chỉnh sửa
document.getElementById("cancel-edit").addEventListener("click", resetForm);

// Tải danh sách Performance khi trang được load
window.onload = loadPerformances;
