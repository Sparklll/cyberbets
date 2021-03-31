<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/resources/assets/favicon.ico">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.css"/>
    <link type="text/css" rel="stylesheet"
          href="/resources/css/jsgrid-theme.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/notify.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
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
            <div class="login-form">
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
</div>

<script src="https://kit.fontawesome.com/4968ce6a0b.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.3/jsgrid.min.js"></script>
<script src="/resources/js/notify.js"></script>
<script src="/resources/js/script.js"></script>
</body>
</html>
