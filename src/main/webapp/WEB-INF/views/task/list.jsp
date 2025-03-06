<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="Quản lý Nhiệm vụ" />
    <jsp:param name="active" value="tasks" />
    <jsp:param name="content" value="../task/list-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/task.js'></script>
    " />
</jsp:include>