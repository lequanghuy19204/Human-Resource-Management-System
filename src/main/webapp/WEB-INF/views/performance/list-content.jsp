<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Danh sách hiệu suất</h2>
    <button
            class="btn btn-success"
            onclick="addPerformance()">
        + Thêm mới
    </button>
</div>

<table class="table table-striped">
    <thead>
    <tr>
        <th>#</th>
        <th>Nhân viên</th>
        <th>Điểm hiệu suất</th>
        <th>Tỷ lệ hoàn thành</th>
        <th>Tỷ lệ đúng hạn</th>
        <th>Điểm chất lượng</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody id="performanceTableBody">
    <c:forEach var="performance" items="${performances}" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${performance.employeeName}</td>
            <td>${performance.performance_score}</td>
            <td>${performance.complete_rate}%</td>
            <td>${performance.ontime_rate}%</td>
            <td>${performance.quality_score}</td>
            <td>
                <button
                        class="btn btn-primary btn-sm"
                        onclick="editPerformance('${performance._id}')">
                    Sửa
                </button>
                <button
                        class="btn btn-danger btn-sm"
                        onclick="deletePerformance('${performance._id}')">
                    Xóa
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
