<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="Quản lý Tổ chức" />
    <jsp:param name="active" value="organization" />
    <jsp:param name="content" value="../organization/list-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/organization.js'></script>
    " />
</jsp:include> 