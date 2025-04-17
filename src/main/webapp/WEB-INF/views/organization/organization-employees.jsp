<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="organizationId" value="${param.id}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="Danh sách Nhân viên - Phòng ban" />
    <jsp:param name="active" value="organizations" />
    <jsp:param name="content" value="../organization/organization-employees-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/organization-employees.js'></script>
    " />
</jsp:include>