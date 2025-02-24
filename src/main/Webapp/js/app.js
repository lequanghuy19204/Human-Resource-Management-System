const API_URL = "http://localhost:8080/Human-Resource-Management-System-1.0-SNAPSHOT/api/tasks";

// Load và hiển thị danh sách Task
const loadTasks = async () => {
    const taskContainer = document.getElementById("tasks-container");
    try {
        const response = await fetch(API_URL);
        const tasks = await response.json();

        // Xóa danh sách cũ và kiểm tra nếu không có Task nào
        taskContainer.innerHTML = "";
        if (tasks.length === 0) {
            taskContainer.innerHTML = "<p>No tasks to display.</p>";
            return;
        }

        // Tạo giao diện cho từng Task
        tasks.forEach((task) => {
            const taskCard = document.createElement("div");
            taskCard.className = "task-card";

            taskCard.innerHTML = `
                <h3>${task.name}</h3>
                <p>${task.description}</p>
                <p class="status ${task.status.toLowerCase()}">
                    <strong>Status:</strong> ${task.status}
                </p>
                <button class="edit" onclick="editTask('${task._id}')">Edit</button>
                <button class="delete" onclick="deleteTask('${task._id}')">Delete</button>
            `;

            taskContainer.appendChild(taskCard);
        });
    } catch (error) {
        console.error("Error loading tasks:", error);
        taskContainer.innerHTML = `<p style="color: red;">Failed to load tasks. Try again later.</p>`;
    }
};

// Lưu hoặc cập nhật Task
const saveTask = async (task) => {
    try {
        const url = task._id ? `${API_URL}/${task._id}` : API_URL;
        const method = task._id ? "PUT" : "POST";

        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(task),
        });

        if (response.ok) {
            loadTasks();
            resetForm();
            alert(task._id ? "Task updated successfully!" : "Task created successfully!");
        } else {
            console.error("Failed to save task:", await response.text());
        }
    } catch (error) {
        console.error("Error saving task:", error);
    }
};

// Xóa Task
const deleteTask = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (response.ok) {
            loadTasks();
            alert("Task deleted successfully!");
        } else {
            console.error("Failed to delete task:", await response.text());
        }
    } catch (error) {
        console.error("Error deleting task:", error);
    }
};

// Sửa Task
const editTask = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const task = await response.json();

        document.getElementById("task-id").value = task._id;
        document.getElementById("task-name").value = task.name;
        document.getElementById("task-desc").value = task.description;
        document.getElementById("task-status").value = task.status;

        document.getElementById("form-title").innerText = "Edit Task";
        document.getElementById("cancel-edit").style.display = "inline-block";
    } catch (error) {
        console.error("Error fetching task for editing:", error);
    }
};

// Reset Form
const resetForm = () => {
    document.getElementById("task-id").value = "";
    document.getElementById("task-name").value = "";
    document.getElementById("task-desc").value = "";
    document.getElementById("task-status").value = "IN_PROGRESS";
    document.getElementById("form-title").innerText = "Create Task";
    document.getElementById("cancel-edit").style.display = "none";
};

// Xử lý sự kiện Submit form
document.getElementById("task-form-element").addEventListener("submit", (event) => {
    event.preventDefault();
    const task = {
        _id: document.getElementById("task-id").value || null,
        name: document.getElementById("task-name").value,
        description: document.getElementById("task-desc").value,
        status: document.getElementById("task-status").value,
        assigned_to: [], // Có thể thêm danh sách assign nếu cần
    };

    saveTask(task);
});

// Hủy chế độ chỉnh sửa
document.getElementById("cancel-edit").addEventListener("click", resetForm);

// Tải danh sách Task khi trang được load
window.onload = loadTasks;