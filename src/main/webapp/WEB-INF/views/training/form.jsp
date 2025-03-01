<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="companyId" value="${sessionScope.companyId}">
<input type="hidden" id="trainingId" value="${param.id}">

<jsp:include page="../shared/taskbar.jsp">
    <jsp:param name="title" value="${empty param.id ? 'Thêm mới' : 'Chỉnh sửa'} Chương trình Đào tạo" />
    <jsp:param name="active" value="training" />
    <jsp:param name="content" value="../training/form-content.jsp" />
    <jsp:param name="scripts" value="
        <script src='${pageContext.request.contextPath}/static/js/training-form.js'></script>
    " />
</jsp:include> 