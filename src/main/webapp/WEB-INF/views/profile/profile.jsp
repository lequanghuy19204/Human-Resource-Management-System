<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="employeeId" value="${sessionScope.employeeId}">
<input type="hidden" id="companyId" value="${sessionScope.companyId}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="Hồ sơ cá nhân" />
    <jsp:param name="active" value="profile" />
    <jsp:param name="content" value="../profile/profile-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/profile.js'></script>
    " />
</jsp:include> 