<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand py-0" href="/admin/"><img src="/resources/assets/admin-logo.png" alt="logo"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse text-uppercase" id="navbarSupportedContent">

            <ul class="navbar-nav ms-auto me-3">
                <li class="nav-item dropdown timezone-select">
                    <a class="nav-link dropdown-toggle" href="#" id="timezoneDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"><i class="far fa-clock me-2"></i></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="timezoneDropdown">
                        <li><a class="dropdown-item active" href="#">UTC-0</a></li>
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
                        <li><a class="dropdown-item active" href="#"><i class="flag flag-gb me-2"></i>En</a></li>
                        <li><a class="dropdown-item" href="#"><i class="flag flag-fr me-2"></i>Fr</a></li>
                        <li><a class="dropdown-item" href="#"><i class="flag flag-ru me-2"></i>Ru</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"><img src="/resources/assets/profile-avatar.png"
                                                                            class="profile-avatar rounded-circle"></a>
                    <ul class="dropdown-menu fade-down" aria-labelledby="profileDropdown">
                        <li><a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt me-2"></i>Sign Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>