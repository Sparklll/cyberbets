<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="general/html/head.html" %>
    <title>CYBERBETS | ERROR404</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>

<div class="error container wrapper my-5">
    <div class="row">
        <div class="col-12 d-flex justify-content-center">
            <img src="/resources/assets/error404.png">
        </div>
        <div class="col-12 d-flex justify-content-center mt-5">
            <a class="login btn btn-primary btn-lg text-uppercase" href="/">
                <fmt:message key="page.error404.button.return_to_main_page"/>
            </a>
        </div>
    </div>
</div>

<jsp:include page="general/footer.jsp"/>

<%@ include file="general/html/scripts.html" %>
</body>
</html>