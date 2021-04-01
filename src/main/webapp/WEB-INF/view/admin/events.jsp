<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/admin-head.html" %>
    <title>CYBERBETS | ADMIN PANEL</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="../general/admin-sidebar.jsp"/>

<main class="d-flex flex-column min-vh-100">
    <jsp:include page="../general/admin-navbar.jsp"/>

    <div class="container-fluid wrapper my-3">
        <div class="row">
            <div class="header col-12 d-flex justify-content-start align-items-center">
                <i class="fas fa-angle-double-right"></i>
                <span class="ms-2 text-uppercase fw-bold">Events</span>
            </div>

        </div>
    </div>

    <jsp:include page="../general/admin-footer.jsp"/>
</main>

<%@ include file="../general/admin-scripts.html" %>
</body>
</html>
