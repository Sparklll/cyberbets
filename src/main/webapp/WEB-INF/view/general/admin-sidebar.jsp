<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<nav id="sidebarMenu" class="sidebar d-lg-block bg-dark text-white collapse" data-simplebar>
    <div class="sidebar-inner px-4 pt-3">
        <ul class="nav flex-column pt-3 pt-md-0">
            <li class="nav-item d-flex justify-content-center">
                <a href="/admin/" class="nav-link">
                    <img class="sidebar-logo" src="/resources/assets/settings.png">
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-chart-pie"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.dashboard"/></span>
                </a>
            </li>
            <li class="nav-item">
                <a href="/admin/events/" class="nav-link d-flex justify-content-between">
                    <span>
                        <span class="sidebar-icon"><span class="fas fa-calendar-week"></span></span>
                        <span class="sidebar-text"><fmt:message key="admin_panel.navigation.events"/></span>
                    </span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/teams/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-users"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.teams"/></span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/leagues/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-trophy"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.leagues"/></span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/transactions/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-money-check-alt"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.transactions"/></span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/news/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-newspaper"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.news"/></span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/accounts/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-user-shield"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.accounts"/></span>
                </a>
            </li>
            <li class="nav-item ">
                <a href="/admin/support/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-question-circle"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.support"/></span>
                </a>
            <li class="nav-item ">
                <a href="/admin/logs/" class="nav-link">
                    <span class="sidebar-icon"><span class="fas fa-cog"></span></span>
                    <span class="sidebar-text"><fmt:message key="admin_panel.navigation.logs"/></span>
                </a>
            </li>
            <li role="separator" class="dropdown-divider mt-4 mb-3 border-black"></li>
        </ul>
    </div>
</nav>