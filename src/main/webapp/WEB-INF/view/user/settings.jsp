<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/head.html" %>
    <title>CYBERBETS | ACCOUNT SETTINGS</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="../general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>


<div class="container wrapper my-5">
    <div class="row">
        <div class="header col-12 d-flex justify-content-start align-items-center">
            <i class="fas fa-angle-double-right"></i>
            <span class="ms-2 text-uppercase fw-bold">
                <fmt:message key="page.user.settings.header"/>
            </span>
        </div>
        <div class="general-container col">
            <div id="settingsContainer">
                <div class="d-flex flex-column flex-md-row justify-content-around custom-controls">
                    <div class="profile-info d-flex flex-column justify-content-center align-items-center mb-5 mb-md-0 me-md-5">
                        <div class="profile-avatar">
                            <img src="${account.avatarResource.path}">
                        </div>
                        <div class="d-flex flex-column ">
                            <div class="text-nowrap m-1">
                                <i class="fas fa-fingerprint fa-lg fa-fw me-1"></i>
                                <span>
                                    ID ${account.id}
                                </span>
                            </div>
                            <div class="text-nowrap m-1">
                                <i class="fas fa-at fa-lg fa-fw me-1"></i>
                                <span>
                                    ${account.email}
                                </span>
                            </div>
                            <div class="text-nowrap m-1">
                                <i class="fas fa-calendar-day fa-lg fa-fw me-1"></i>
                                <span>
                                    <fmt:message key="settings_page.registration_date" />
                                    <datetime:format value="${account.registrationDate}" pattern="dd.MM.YYYY" />
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="edit-profile d-flex flex-column justify-content-between">
                        <div class="mb-3">
                            <label for="avatarInput" class="d-block mb-2 text-center fw-bold">
                                <fmt:message key="settings_page.select_new_avatar" />
                            </label>
                            <input type="file" class="form-control" id="avatarInput" accept=".png,.jpg">
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="currentPassword" placeholder="Password">
                            <label for="currentPassword">
                                <fmt:message key="settings_page.current_password" />
                            </label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="newPassword" placeholder="New password">
                            <label for="newPassword">
                                <fmt:message key="settings_page.new_password" />
                            </label>
                            <div class="invalid-feedback">
                                <fmt:message key="settings_page.invalid_password_length" />
                            </div>
                        </div>
                        <div class="form-floating">
                            <input type="password" class="form-control" id="repeatedNewPassword" placeholder="Repeat new password">
                            <label for="repeatedNewPassword">
                                <fmt:message key="settings_page.repeat_new_password" />
                            </label>
                            <div class="invalid-feedback">
                                <fmt:message key="settings_page.invalid_repeated_password" />
                            </div>
                        </div>
                    </div>
                    <div class="m-auto d-none d-xl-block">
                        <img src="/resources/assets/settings-logo.png" style="max-width: 400px">
                    </div>
                </div>
                <button id="updateProfile" type="button" class="btn btn-primary fw-bold text-uppercase mt-4 mb-1 col-12">
                    <fmt:message key="settings_page.update_btn" />
                </button>
                <div id="accountUpdateError" style="display: none;">
                    <div class="d-flex justify-content-center align-items-center mt-2 text-danger">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <span>
                            <fmt:message key="settings_page.update_error"/>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../general/footer.jsp"/>

<%@ include file="../general/html/scripts.html" %>
</body>
</html>
