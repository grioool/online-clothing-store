<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<!doctype html>
<html>
<head>
    <title><fmt:message key="title.error"/></title>
    <c:import url="../page/header.jsp"/>
</head>
<body>
<c:import url="../page/navbar.jsp"/>

<div class="alert alert-warning mt-4 text-center" role="alert">
    <h2 class="alert-heading">404!</h2>
    <p><fmt:message key="title.pageNotFound"/></p>
</div>
<c:import url="../page/footer.jsp"/>
</body>
</html>
