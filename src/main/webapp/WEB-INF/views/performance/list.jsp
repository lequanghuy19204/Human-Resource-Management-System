<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="Quản lý Hiệu suất" />
    <jsp:param name="active" value="performance" />
    <jsp:param name="content" value="../performance/list-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/performance.js'></script>
    " />
</jsp:include>