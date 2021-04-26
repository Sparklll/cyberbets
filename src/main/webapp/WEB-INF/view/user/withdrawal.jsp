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
    <title>CYBERBETS | WITHDRAWAL</title>
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
                <fmt:message key="page.user.withdrawal.header"/>
            </span>
        </div>
        <div id="general-container" class="col">
            <div id="withdrawContainer">
                <div class="card">
                    <div class="d-flex flex-column flex-md-row" id="wrapper">
                        <!-- Sidebar -->
                        <div class="border-right" id="sidebar-wrapper">
                            <div class="sidebar-heading text-uppercase py-4">
                                <fmt:message key="transaction.withdrawal_methods" />
                            </div>
                            <div class="list-group list-group-flush d-flex flex-row flex-md-column justify-content-center row col-12 mx-0">
                                    <a href="#" class="tabs list-group-item" data-bs-toggle="tab" data-bs-target="#" disabled="">
                                        <div class="list-div my-2">
                                            <i class="fas fa-university fa-lg fa-fw me-2"></i>
                                            <fmt:message key="transaction.bank_method" />
                                            <span class="badge bg-secondary float-end">
                                                 <fmt:message key="transaction.unavailable" />
                                            </span>
                                        </div>
                                    </a>
                                    <a href="#" class="tabs list-group-item active" data-bs-toggle="tab"
                                       data-bs-target="#cardTab">
                                        <div class="list-div my-2">
                                            <i class="fa fa-credit-card fa-lg fa-fw me-2"></i>
                                            <fmt:message key="transaction.card_method" />
                                        </div>
                                    </a>
                                    <a href="#" class="tabs list-group-item" data-bs-toggle="tab" data-bs-target="#" disabled>
                                        <div class="list-div my-2">
                                            <i class="fab fa-paypal fa-lg fa-fw me-2"></i>
                                            PayPal
                                            <span class="badge bg-secondary float-end">
                                            <fmt:message key="transaction.unavailable" />
                                        </span>
                                        </div>
                                    </a>
                                    <a href="#" class="tabs list-group-item" data-bs-toggle="tab" data-bs-target="#" disabled>
                                        <div class="list-div my-2">
                                            <i class="fab fa-btc fa-lg fa-fw me-2"></i>
                                            Bitcoin
                                            <span class="badge bg-secondary float-end">
                                            <fmt:message key="transaction.unavailable" />
                                        </span>
                                        </div>
                                    </a>
                                    <a href="#" class="tabs list-group-item" data-bs-toggle="tab" data-bs-target="#" disabled>
                                        <div class="list-div my-2">
                                            <i class="fab fa-stripe fa-lg fa-fw me-2"></i>
                                            Stripe
                                            <span class="badge bg-secondary float-end">
                                            <fmt:message key="transaction.unavailable" />
                                        </span>
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
                                <div class="col-8">
                                    <div class="d-flex justify-content-end align-items-center flex-row">
                                        <img src="/resources/assets/interface/email.png" width="48">
                                        <span class="m-2 fw-bold text-end text-truncate">${accountEmail}</span>
                                    </div>
                                </div>

                                <div class="pay-input-group input-group custom-controls mt-3">
                                    <span class="input-group-text fw-bold">
                                        <span class="badge bg-success fs-6"><fmt:message key="transaction.amount" /> $</span>
                                    </span>
                                    <input type="text" class="form-control" maxlength="7">
                                </div>
                                <span id="checkEnteredData" class="text-danger text-center mt-3" style="display: none">
                                    <i class="fas fa-exclamation-triangle"></i>
                                    <fmt:message key="transaction.check_entered_data" />
                                </span>
                                <span id="incorrectPaySum" class="text-danger text-center mt-3" style="display: none">
                                    <i class="fas fa-exclamation-triangle"></i>
                                    <fmt:message key="transaction.incorrect_pay_sum" />
                                </span>
                                <span id="paymentError" class="text-danger text-center mt-3" style="display: none">
                                    <i class="fas fa-exclamation-triangle"></i>
                                    <fmt:message key="transaction.payment_error" />
                                </span>
                            </div>
                            <div class="tab-content mt-4">
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
                                                    <fmt:message key="transaction.enter_card_details" />
                                                </h3>
                                                <div class="custom-controls">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="input-group">
                                                                <input type="text"
                                                                       id="cp_cardHolder"
                                                                       placeholder="JOHN WICK"
                                                                       maxlength="64">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="transaction.card_holder" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <div class="input-group"><input type="text"
                                                                                            id="cp_cardNumber"
                                                                                            class="form-control"
                                                                                            placeholder="0000 0000 0000 0000"
                                                                                            minlength="19"
                                                                                            maxlength="19">
                                                                <label class="text-uppercase">
                                                                    <fmt:message key="transaction.card_number" />
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <button class="payment-button btn btn-lg btn-primary">
                                                            <i class="fa fa-lock fa-lg"></i>&nbsp;
                                                            <span class="payment-button-amount">
                                                                <span>
                                                                   <fmt:message key="transaction.withdraw" /> $
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
