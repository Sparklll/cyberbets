<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/admin-head.html" %>
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
                <span class="ms-2 text-uppercase fw-bold">Teams</span>
            </div>

            <div class="team-control-panel">
                <button type="button" class="add-team btn btn-primary btn-block text-uppercase mt-3"
                        data-bs-toggle="modal"
                        data-bs-target="#teamModal">
                    <i class="fas fa-plus me-1"></i>
                    Add Team
                </button>
            </div>

            <div class="teams mt-2">
                <div id="teamsGrid"></div>
            </div>
        </div>
    </div>

    <jsp:include page="../general/admin-footer.jsp"/>
</main>

<div class="modal fade" id="teamModal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="teamModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content">
            <div class="card">
                <div class="card-header d-inline-flex align-items-center">
                    <h5 class="mt-3 text-uppercase fw-bold">Add Team</h5>
                    <a class="close ms-auto" role="button"
                       data-bs-dismiss="modal"
                       data-bs-toggle="modal">
                        <i class="fas fa-times"></i>
                    </a>
                </div>
                <div class="card-body p-3">
                    <form action="" method="POST" class="custom-controls">
                        <div class="mb-3 d-flex justify-content-center align-items-center">
                            <img id="teamLogoPreview" src="" width="80" style="display: none">
                        </div>
                        <div class="mb-3">
                            <label for="teamDisciplineSelect" class="form-label">Discipline</label>
                            <select id="teamDisciplineSelect" class="form-select" aria-label="teamNameLabel">
                                <option selected></option>
                                <option value="1">CS:GO</option>
                                <option value="2">DOTA2</option>
                                <option value="3">LEAGUE OF LEGENDS</option>
                                <option value="4">VALORANT</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="teamName" class="form-label">Name</label>
                            <input type="text" class="form-control" id="teamName" maxlength="30">
                        </div>
                        <div class="mb-3">
                            <label for="teamRating" class="form-label">Rating</label>
                            <input type="number" class="form-control" id="teamRating" value="0" min="0"
                                   max="1000000" pattern="([0-9]{6})">
                        </div>
                        <div class="mb-3">
                            <label for="teamLogo" class="form-label">Logo</label>
                            <input class="form-control" type="file" id="teamLogo" accept=".png,.jpg">
                        </div>
                        <div class="d-grid gap-1">
                            <button type="submit" id="teamModalSubmit"
                                    class="btn btn-primary btn-block text-uppercase mt-3">
                                Save
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../general/html/admin-scripts.html" %>
</body>
</html>
