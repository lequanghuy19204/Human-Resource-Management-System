const API_URL = "http://localhost:8080/Human-Resource-Management-System-1.0-SNAPSHOT/api/rewards";

// Load danh sách Reward
const loadRewards = async () => {
    const rewardContainer = document.getElementById("rewards-container");
    try {
        const response = await fetch(API_URL);
        const rewards = await response.json();

        rewardContainer.innerHTML = "";
        if (rewards.length === 0) {
            rewardContainer.innerHTML = "<p>No rewards to display.</p>";
            return;
        }

        rewards.forEach((reward) => {
            const rewardCard = document.createElement("div");
            rewardCard.className = "reward-card";

            rewardCard.innerHTML = `
                <p><strong>Employee ID:</strong> ${reward.employee_id}</p>
                <p><strong>Completed Tasks:</strong> ${reward.completed_tasks}</p>
                <p><strong>Performance Score:</strong> ${reward.performance_score}</p>
                <p><strong>Reward:</strong> ${reward.reward}</p>
                <p><strong>Type:</strong> ${reward.type}</p>
                <button class="edit" onclick="editReward('${reward._id}')">Edit</button>
                <button class="delete" onclick="deleteReward('${reward._id}')">Delete</button>
            `;

            rewardContainer.appendChild(rewardCard);
        });
    } catch (error) {
        console.error("Error loading rewards:", error);
        rewardContainer.innerHTML = `<p style="color: red;">Failed to load rewards. Try again later.</p>`;
    }
};

// Lưu hoặc cập nhật Reward
const saveReward = async (reward) => {
    try {
        const url = reward._id ? `${API_URL}/${reward._id}` : API_URL;
        const method = reward._id ? "PUT" : "POST";

        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(reward),
        });

        if (response.ok) {
            loadRewards();
            resetForm();
            alert(reward._id ? "Reward updated successfully!" : "Reward created successfully!");
        } else {
            console.error("Failed to save reward:", await response.text());
        }
    } catch (error) {
        console.error("Error saving reward:", error);
    }
};

// Xóa Reward
const deleteReward = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (response.ok) {
            loadRewards();
            alert("Reward deleted successfully!");
        } else {
            console.error("Failed to delete reward:", await response.text());
        }
    } catch (error) {
        console.error("Error deleting reward:", error);
    }
};

// Sửa Reward
const editReward = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const reward = await response.json();

        document.getElementById("reward-id").value = reward._id;
        document.getElementById("employee-id").value = reward.employee_id;
        document.getElementById("completed-tasks").value = reward.completed_tasks;
        document.getElementById("performance-score").value = reward.performance_score;
        document.getElementById("reward-amount").value = reward.reward;
        document.getElementById("reward-type").value = reward.type;

        document.getElementById("form-title").innerText = "Edit Reward";
        document.getElementById("cancel-edit").style.display = "inline-block";
    } catch (error) {
        console.error("Error fetching reward for editing:", error);
    }
};

// Reset Form
const resetForm = () => {
    document.getElementById("reward-id").value = "";
    document.getElementById("employee-id").value = "";
    document.getElementById("completed-tasks").value = "";
    document.getElementById("performance-score").value = "";
    document.getElementById("reward-amount").value = "";
    document.getElementById("reward-type").value = "MONEY";
    document.getElementById("form-title").innerText = "Create Reward";
    document.getElementById("cancel-edit").style.display = "none";
};

// Xử lý sự kiện Submit form
document.getElementById("reward-form-element").addEventListener("submit", (event) => {
    event.preventDefault();
    const reward = {
        _id: document.getElementById("reward-id").value || null,
        employee_id: document.getElementById("employee-id").value,
        completed_tasks: parseInt(document.getElementById("completed-tasks").value),
        performance_score: parseFloat(document.getElementById("performance-score").value),
        reward: parseFloat(document.getElementById("reward-amount").value),
        type: document.getElementById("reward-type").value,
    };

    saveReward(reward);
});

// Hủy chế độ chỉnh sửa
document.getElementById("cancel-edit").addEventListener("click", resetForm);

// Tải danh sách Reward khi trang được load
window.onload = loadRewards;
