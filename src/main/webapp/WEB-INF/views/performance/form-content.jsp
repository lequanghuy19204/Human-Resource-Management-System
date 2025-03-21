<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-4">
    <form id="performanceForm">
        <input type="hidden" id="performanceId" value="${performanceId}" />

        <!-- Chọn nhân viên -->
        <div class="mb-3">
            <label for="employeeId" class="form-label">Nhân viên</label>
            <select class="form-control" id="employeeId"
                    data-selected="${performance.employee_id}" required>
                <option value="">-- Chọn nhân viên --</option>
            </select>
        </div>

        <!-- Điểm hiệu suất -->
        <div class="mb-3">
            <label for="performanceScore" class="form-label">Điểm hiệu suất</label>
            <input type="number" class="form-control" id="performanceScore"
                   value="${performance.performance_score}" min="0" max="100" step="0.01" required />
        </div>

        <!-- Tỷ lệ hoàn thành -->
        <div class="mb-3">
            <label for="completeRate" class="form-label">Tỷ lệ hoàn thành (%)</label>
            <input type="number" class="form-control" id="completeRate"
                   value="${performance.complete_rate}" min="0" max="100" step="0.01" required />
        </div>

        <!-- Tỷ lệ đúng hạn -->
        <div class="mb-3">
            <label for="ontimeRate" class="form-label">Tỷ lệ đúng hạn (%)</label>
            <input type="number" class="form-control" id="ontimeRate"
                   value="${performance.ontime_rate}" min="0" max="100" step="0.01" required />
        </div>

        <!-- Điểm chất lượng -->
        <div class="mb-3">
            <label for="qualityScore" class="form-label">Điểm chất lượng</label>
            <input type="number" class="form-control" id="qualityScore"
                   value="${performance.quality_score}" min="0" max="100" required />
        </div>

        <!-- Nút Lưu -->
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a href="${pageContext.request.contextPath}/performance" class="btn btn-secondary">Hủy</a>
    </form>
</div>
