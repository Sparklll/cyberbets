<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/head.html" %>
    <title>CYBERBETS | DEPOSIT</title>
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
                <fmt:message key="page.user.deposit.header"/>
            </span>
        </div>
        <div id="general-container" class="col">
            <div id="depositContainer">
                <div class="card">
                    <div class="d-flex flex-column flex-md-row" id="wrapper">
                        <!-- Sidebar -->
                        <div class="border-right" id="sidebar-wrapper">
                            <div class="sidebar-heading text-uppercase py-4">
                                <fmt:message key="deposit_page.pay_with" />
                            </div>
                            <div class="list-group list-group-flush d-flex flex-row flex-md-column justify-content-center">
                                <a href="#" class="tabs list-group-item" data-bs-toggle="tab" data-bs-target="#bankTab">
                                    <div class="list-div my-2">
                                        <div class="fa fa-home fa-sm me-2"></div>
                                        <fmt:message key="deposit_page.bank_method" />
                                    </div>
                                </a>

                                <a href="#" class="tabs list-group-item active" data-bs-toggle="tab"
                                   data-bs-target="#cardTab">
                                    <div class="list-div my-2">
                                        <div class="fa fa-credit-card fa-sm me-2"></div>
                                        <fmt:message key="deposit_page.card_method" />
                                    </div>
                                </a>

                                <a href="#" class="tabs list-group-item" data-bs-toggle="tab"
                                   data-bs-target="#visaQrTab">
                                    <div class="list-div my-2">
                                        <i class="fa fa-qrcode fa-sm me-2"></i>
                                        <span><fmt:message key="deposit_page.visaqr_method" /></span>
                                        <span class="badge bg-danger">NEW</span>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <!-- Page Content -->
                        <div id="page-content-wrapper">
                            <div class="page-content-wrapper-header row pt-3">
                                <div class="col-4">
                                    <button class="btn btn-primary mt-3" id="menu-toggle">
                                        <div class="bar4"></div>
                                        <div class="bar4"></div>
                                        <div class="bar4"></div>
                                    </button>
                                </div>
                                <div class="col-8 justify-content-center">
                                    <div class="d-flex flex-column">
                                        <span class="m-2 fw-bold text-end text-truncate">${accountEmail}</span>
                                    </div>
                                </div>

                                <div class="pay-input-group input-group custom-controls mt-3">
                                    <span class="input-group-text fw-bold">
                                        <span class="badge bg-success fs-6"><fmt:message key="deposit_page.amount" /> $</span>
                                    </span>
                                    <input type="text" class="form-control" maxlength="7">
                                </div>
                            </div>
                            <div class="tab-content mt-4">
                                <div id="bankTab" class="tab-pane">
                                    <div class="row justify-content-center">
                                        <div class="col-11">
                                            <div class="form-card">
                                                <h3 class="mt-0 mb-4 text-center">
                                                    <fmt:message key="deposit_page.enter_bank_detaild" />
                                                </h3>
                                                <div class="custom-controls">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="input-group"><input type="text"
                                                                                            id="bp_bankName"
                                                                                            class="form-control"
                                                                                            placeholder="BBB Bank">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="deposit_page.bank_name" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="input-group"><input type="text"
                                                                                            id="bp_beneficiaryName"
                                                                                            class="form-control"
                                                                                            placeholder="John Smith">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="deposit_page.beneficiary_name" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="input-group"><input type="text"
                                                                                            id="bp_swiftCode"
                                                                                            class="form-control"
                                                                                            placeholder="ABCDAB1S"
                                                                                            class="placeicon"
                                                                                            minlength="8"
                                                                                            maxlength="11">
                                                                <label class="text-uppercase">
                                                                      <fmt:message key="deposit_page.swift_code" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <button class="payment-button btn btn-lg btn-primary">
                                                            <i class="fa fa-lock fa-lg"></i>&nbsp;
                                                            <span class="payment-button-amount">
                                                                <span>
                                                                    <fmt:message key="deposit_page.pay" /> $
                                                                </span>
                                                                <span class="sum"></span>
                                                            </span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="cardTab" class="tab-pane in active">
                                    <div class="row justify-content-center">
                                        <div class="col-11">
                                            <div class="form-card">
                                                <div class="text-center">
                                                    <ul class="list-inline">
                                                        <li class="list-inline-item"><i
                                                                class="fab fa-cc-visa fa-3x"></i></li>
                                                        <li class="list-inline-item"><i
                                                                class="fa fa-cc-mastercard fa-3x"></i></li>
                                                        <li class="list-inline-item"><i class="fa fa-cc-amex fa-3x"></i>
                                                        </li>
                                                        <li class="list-inline-item"><i
                                                                class="fa fa-cc-discover fa-3x"></i></li>
                                                    </ul>
                                                </div>
                                                <h3 class="mt-0 mb-4 text-center">
                                                    <fmt:message key="deposit_page.enter_card_details" />
                                                </h3>
                                                <div class="custom-controls">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="input-group"><input type="text"
                                                                                            id="cp_cardNumber"
                                                                                            class="form-control"
                                                                                            placeholder="0000 0000 0000 0000"
                                                                                            minlength="19"
                                                                                            maxlength="19">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="deposit_page.card_number" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="input-group"><input type="text"
                                                                                            id="cp_cardExpiry"
                                                                                            class="form-control"
                                                                                            placeholder="MM/YY"
                                                                                            minlength="7" maxlength="7">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="deposit_page.card_expiry" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="input-group"><input type="password"
                                                                                            id="cp_cvv"
                                                                                            class="form-control"
                                                                                            placeholder="&#9679;&#9679;&#9679;"
                                                                                            minlength="3" maxlength="3">
                                                                <label>CVV</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <button class="payment-button btn btn-lg btn-primary">
                                                            <i class="fa fa-lock fa-lg"></i>&nbsp;
                                                            <span class="payment-button-amount">
                                                                <span>
                                                                   <fmt:message key="deposit_page.pay" /> $
                                                                </span>
                                                                <span class="sum"></span>
                                                            </span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="visaQrTab" class="tab-pane">
                                    <div class="d-flex flex-column justify-content-center align-items-center">
                                        <h3 class="mb-4 text-center">
                                            <fmt:message key="deposit_page.scan_qr" />
                                        </h3>
                                        <img src='/resources/assets/interface/qr-code.png' width="300px">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../general/footer.jsp"/>

<%@ include file="../general/html/scripts.html" %>
<script src="/resources/js/payform.js"></script>
</body>
</html>
