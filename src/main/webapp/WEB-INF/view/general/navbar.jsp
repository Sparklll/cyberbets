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
                <li class="nav-item me-2"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item me-2"><a class="nav-link" href="/news/">News</a></li>
                <li class="nav-item me-2"><a class="nav-link" href="/rating/">Rating</a></li>
                <li class="nav-item text-nowrap"><a class="nav-link" href="/support/">Support</a></li>
            </ul>

            <ul class="navbar-nav ms-auto me-3">
                <li class="nav-item dropdown timezone-select">
                    <a class="nav-link dropdown-toggle" href="#" id="timezoneDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"><i class="far fa-clock me-2"></i></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="timezoneDropdown">
                        <li><a class="dropdown-item" href="#">UTC-0</a></li>
                        <li><a class="dropdown-item" href="#">UTC-1</a></li>
                        <li><a class="dropdown-item" href="#">UTC-2</a></li>
                        <li><a class="dropdown-item" href="#">UTC-3</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown lang-select">
                    <a class="nav-link dropdown-toggle" href="#" id="langDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="langDropdown">
                        <li><a class="dropdown-item" href="#"><i class="flag flag-de me-2"></i>De</a></li>
                        <li><a class="dropdown-item" href="#"><i class="flag flag-gb me-2"></i>En</a></li>
                        <li><a class="dropdown-item" href="#"><i class="flag flag-fr me-2"></i>Fr</a></li>
                        <li><a class="dropdown-item" href="#"><i class="flag flag-ru me-2"></i>Ru</a></li>
                    </ul>
                </li>
            </ul>

            <c:choose>
                <c:when test="${param.auth}">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link text-nowrap" href="#">
                                <img src="/resources/assets/wallet.png" class="balance">
                                <span>
                            <i class="fas fa-dollar-sign"></i>
                            <span>10000</span>
                        </span>
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false"><img
                                    src="/resources/assets/profile-avatar.png"
                                    class="profile-avatar rounded-circle"></a>
                            <ul class="dropdown-menu fade-down" aria-labelledby="profileDropdown">
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-dice me-2"></i>
                                        My Bets
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-plus-circle me-2"></i>
                                        Deposit
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-arrow-circle-up me-2"></i>
                                        Withdrawal
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-history me-2"></i>
                                        Transaction History
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-user-cog me-2"></i>
                                        Account Settings
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <c:if test="${param.role eq 'admin'}">
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-cogs me-2"></i>
                                        Admin Panel
                                    </a>
                                </li>
                                </c:if>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <i class="fas fa-sign-out-alt me-2"></i>
                                        Sign Out
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
                                    data-bs-target="#registerModal">Register
                            </button>
                        </li>
                        <li class="nav-item mt-2 mt-lg-0">
                            <button type="button" class="login btn btn-primary text-uppercase text-nowrap"
                                    data-bs-toggle="modal"
                                    data-bs-target="#loginModal">Log in
                            </button>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<div class="modal fade" id="registerModal" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="registerModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content">
            <div class="registration-form">
                <div class="card">
                    <div class="card-header d-inline-flex align-items-center">
                        <h5 class="mt-3 text-uppercase fw-bold">Register</h5>
                        <a class="close ms-auto" role="button"
                           data-bs-dismiss="modal"
                           data-bs-toggle="modal">
                            <i class="fas fa-times"></i>
                        </a>
                    </div>
                    <div class="card-body p-3">
                        <form action="" method="POST">
                            <div class="input-field mt-3">
                                <label for="registerEmail" class="col-sm-2 col-form-label">
                                    <i class="fas fa-user px-2"></i>
                                </label>
                                <input type="text" class="form-control" id="registerEmail" placeholder="Email"
                                       required>
                            </div>
                            <div class="input-field my-4">
                                <label for="registerPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <input type="password" class="form-control" id="registerPassword"
                                       placeholder="Password"
                                       required>
                            </div>
                            <div class="input-field my-4">
                                <label for="registerRepeatPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <input type="password" class="form-control" id="registerRepeatPassword"
                                       placeholder="Repeat password"
                                       required>
                            </div>
                            <div class="d-grid gap-1 mb-4">
                                <button type="submit" class="btn btn-primary btn-block text-uppercase mt-3">
                                    Register
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
                        <h5 class="mt-3 text-uppercase fw-bold">Log in</h5>
                        <a class="close ms-auto" role="button"
                           data-bs-dismiss="modal"
                           data-bs-toggle="modal">
                            <i class="fas fa-times"></i>
                        </a>
                    </div>
                    <div class="card-body p-3">
                        <form action="" method="POST">
                            <div class="input-field mt-3">
                                <label for="loginEmail" class="col-sm-2 col-form-label">
                                    <i class="fas fa-user px-2"></i>
                                </label>
                                <input type="text" class="form-control" id="loginEmail" placeholder="Email"
                                       required>
                            </div>
                            <div class="input-field my-4">
                                <label for="loginPassword" class="col-sm-2 col-form-label">
                                    <i class="fas fa-lock px-2"></i>
                                </label>
                                <input type="password" class="form-control" id="loginPassword"
                                       placeholder="Password"
                                       required>
                            </div>
                            <div class="d-grid gap-1">
                                <button type="submit" class="btn btn-primary btn-block text-uppercase mt-3">Log in
                                </button>
                            </div>
                            <div class="text-center mt-4">
                                <span>Don't have an account?</span>
                                <a class="fw-bold" role="button" data-bs-dismiss="modal"
                                   data-bs-toggle="modal"
                                   data-bs-target="#registerModal">Register
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>