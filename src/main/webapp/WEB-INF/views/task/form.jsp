<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">
<input type="hidden" id="taskId" value="${param.id}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="${empty param.id ? 'Thêm mới' : 'Cập nhật'} Nhiệm vụ" />
    <jsp:param name="active" value="tasks" />
    <jsp:param name="content" value="../task/form-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/task.js'></script>
    " />
</jsp:include>