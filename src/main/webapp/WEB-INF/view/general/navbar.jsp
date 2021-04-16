<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<nav class="navbar navbar-expand-lg navbar-dark smart-scroll">
    <div class="container">
        <a class="navbar-brand py-0" href="/"><img src="/resources/assets/logo.png" alt="logo"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse text-uppercase" id="navbarSupportedContent">

            <ul class="navbar-nav">
                <li class="nav-item me-2">
                    <a class="nav-link" href="/">
                        <fmt:message key="navbar.link.home"/>
                    </a>
                </li>
                <li class="nav-item me-2">
                    <a class="nav-link" href="/news/">
                        <fmt:message key="navbar.link.news"/>
                    </a>
                </li>
                <li class="nav-item me-2">
                    <a class="nav-link" href="/rating/">
                        <fmt:message key="navbar.link.rating"/>
                    </a>
                </li>
                <li class="nav-item text-nowrap">
                    <a class="nav-link" href="/support/">
                        <fmt:message key="navbar.link.support"/>
                    </a>
                </li>
            </ul>

            <ul class="navbar-nav ms-auto me-3">
                <li class="nav-item dropdown timezone-select">
                    <a class="nav-link dropdown-toggle" href="#" id="timezoneDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"><i class="far fa-clock me-2"></i></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="timezoneDropdown">
<%--                        <li><a class="dropdown-item" href="#">UTC-0</a></li>--%>
<%--                        <li><a class="dropdown-item" href="#">UTC-1</a></li>--%>
<%--                        <li><a class="dropdown-item" href="#">UTC-2</a></li>--%>
<%--                        <li><a class="dropdown-item" href="#">UTC-3</a></li>--%>
                    </ul>
                </li>
                <li class="nav-item dropdown lang-select">
                    <a class="nav-link dropdown-toggle" href="#" id="langDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="langDropdown">
                        <li><a class="dropdown-item" href="#" data-id="de"><i class="flag flag-de me-2"></i>De</a></li>
                        <li><a class="dropdown-item" href="#" data-id="en"><i class="flag flag-gb me-2"></i>En</a></li>
                        <li><a class="dropdown-item" href="#" data-id="fr"><i class="flag flag-fr me-2"></i>Fr</a></li>
                        <li><a class="dropdown-item" href="#" data-id="ru"><i class="flag flag-ru me-2"></i>Ru</a></li>
                    </ul>
                </li>
            </ul>

            <c:choose>
                <c:when test="${param.auth}">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link text-nowrap" href="#">
                                <img src="/resources/assets/interface/wallet.png" class="balance">
                                <span>
                            <i class="fas fa-dollar-sign"></i>
                            <span>
                                <fmt:formatNumber value="${param.balance}" minIntegerDigits="1" minFractionDigits="2" groupingUsed="false"/>
                            </span>
                        </span>
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false"><img
                                    src="/resources/assets/interface/profile-avatar.png"
                                    class="profile-avatar rounded-circle"></a>
                            <ul class="dropdown-menu fade-down" aria-labelledby="profileDropdown">
                                <li>
                                    <a class="dropdown-item" href="/mybets/">
                                        <i class="fas fa-dice fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.my_bets"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/deposit/">
                                        <i class="fas fa-plus-circle fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.deposit"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/withdrawal/">
                                        <i class="fas fa-arrow-circle-up fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.withdrawal"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/transactions/">
                                        <i class="fas fa-history fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.transaction_history"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/settings/">
                                        <i class="fas fa-user-cog fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.account_settings"/>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <c:if test="${param.role eq 'admin'}">
                                    <li>
                                        <a class="dropdown-item" href="/admin/">
                                            <i class="fas fa-cogs fa-fw me-2"></i>
                                            <fmt:message key="navbar.account.admin_panel"/>
                                        </a>
                                    </li>
                                </c:if>
                                <li>
                                    <a id="logout" class="dropdown-item" href="#">
                                        <i class="fas fa-sign-out-alt fa-fw me-2"></i>
                                        <fmt:message key="navbar.account.log_out"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="navbar-nav">
                        <li class="nav-item me-2 mt-2 mt-lg-0">
                            <button type="button" class="register btn btn-secondary text-uppercase"
                                    data-bs-toggle="modal"
                                    data-bs-target="#registerModal">
                                <fmt:message key="navbar.button.register"/>
                            </button>
                        </li>
                        <li class="nav-item mt-2 mt-lg-0">
                            <button type="button" class="login btn btn-primary text-uppercase text-nowrap"
                                    data-bs-toggle="modal"
                                    data-bs-target="#loginModal">
                                <fmt:message key="navbar.button.log_in"/>
                            </button>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<fmt:message key="navbar.modal.placeholder.email" var="email"/>
<fmt:message key="navbar.modal.placeholder.password" var="password"/>
<fmt:message key="navbar.modal.placeholder.repeat_password" var="repeat_password"/>
<div class="modal fade" id="registerModal" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="registerModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content">
            <div class="registration-form">
                <div class="card">
                    <div class="card-header d-inline-flex align-items-center">
                        <h5 class="mt-3 text-uppercase fw-bold">
                            <fmt:message key="navbar.register_modal.header"/>
                        </h5>
                        <a class="close ms-auto" role="button"
                           data-bs-dismiss="modal"
                           data-bs-toggle="modal">
                            <i class="fas fa-times"></i>
                        </a>
                    </div>
                    <div class="card-body p-3">
                        <form action="" method="POST" class="custom-controls" novalidate>
                            <div class="input-field mt-3">
                                <label for="registerEmail" class="col-sm-2 col-form-label">
                                    <i class="fas fa-user px-2"></i>
                                </label>
                                <div>
                                    <input type="email" class="form-control" id="registerEmail"
                                           placeholder="${email}"
                                           maxlength="50"
                                           required>
                                    <div class="break"></div>
                                    <div class="invalid-feedback">
                                        <fmt:message key="navbar.register_modal.invalid_email"/>
                                    </div>
                                </div>
                            </div>
                            <div class="input-field my-4">
                                <label for="registerPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <div>
                                    <input type="password" class="form-control" id="registerPassword"
                                           placeholder="${password}"
                                           maxlength="50"
                                           required>
                                    <div class="invalid-feedback">
                                        <fmt:message key="navbar.register_modal.invalid_password_length"/>
                                    </div>
                                </div>
                            </div>
                            <div class="input-field my-4">
                                <label for="registerRepeatedPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <div>
                                    <input type="password" class="form-control" id="registerRepeatedPassword"
                                           placeholder="${repeat_password}"
                                           maxlength="50"
                                           required>
                                    <div class="invalid-feedback">
                                        <fmt:message key="navbar.register_modal.invalid_repeated_password"/>
                                    </div>
                                </div>
                            </div>
                            <div id="emailAlreadyExist" class="text-center text-danger" style="display: none">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <fmt:message key="navbar.register_modal.email_already_exist"/>
                            </div>
                            <div class="d-grid gap-1 mb-4">
                                <button type="submit" class="register btn btn-primary btn-block text-uppercase mt-3">
                                    <fmt:message key="navbar.register_modal.button.register"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="loginModal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content">
            <div class="login-form">
                <div class="card">
                    <div class="card-header d-inline-flex align-items-center">
                        <h5 class="mt-3 text-uppercase fw-bold">
                            <fmt:message key="navbar.login_modal.header"/>
                        </h5>
                        <a class="close ms-auto" role="button"
                           data-bs-dismiss="modal"
                           data-bs-toggle="modal">
                            <i class="fas fa-times"></i>
                        </a>
                    </div>
                    <div class="card-body p-3">
                        <form action="" method="POST" class="custom-controls" novalidate>
                            <div class="input-field mt-3">
                                <label for="loginEmail" class="col-sm-2 col-form-label">
                                    <i class="fas fa-user px-2"></i>
                                </label>
                                <div>
                                    <input type="email" class="form-control" id="loginEmail" placeholder="${email}"
                                           maxlength="50"
                                           required>
                                </div>
                            </div>
                            <div class="input-field my-4">
                                <label for="loginPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <div>
                                    <input type="password" class="form-control" id="loginPassword"
                                           placeholder="${password}"
                                           maxlength="50"
                                           required>
                                </div>
                            </div>
                            <div id="wrongCredentials" class="text-center text-danger" style="display: none">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <fmt:message key="navbar.register_modal.wrong_credentials"/>
                            </div>
                            <div class="d-grid gap-1">
                                <button type="submit" class="login btn btn-primary btn-block text-uppercase mt-3">
                                    <fmt:message key="navbar.login_modal.button.log_in"/>
                                </button>
                            </div>
                            <div class="text-center mt-4">
                                <span>
                                    <fmt:message key="navbar.login_modal.dont_have_account"/>
                                </span>
                                <a class="fw-bold" role="button" data-bs-dismiss="modal"
                                   data-bs-toggle="modal"
                                   data-bs-target="#registerModal">
                                    <fmt:message key="navbar.login_modal.button.register"/>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>