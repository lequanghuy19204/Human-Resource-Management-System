<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">
<input type="hidden" id="employeeId" value="${param.id}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="${empty param.id ? 'Thêm' : 'Sửa'} Nhân viên" />
    <jsp:param name="active" value="employees" />
    <jsp:param name="content" value="../employee/form-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/employee-form.js'></script>
    " />
</jsp:include> 