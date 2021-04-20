<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<%@ page import="by.epam.jwd.cyberbets.domain.ResultStatus" %>
<%@ page import="by.epam.jwd.cyberbets.domain.Bet.Upshot" %>


<div class="card-body p-3">
    <div class="outcomes">
        <c:forEach items="${eventResults}" var="eventResult">
            <c:forEach items="${eventCoefficients}" var="coefficientsDto">
                <c:if test="${coefficientsDto.eventResultId eq eventResult.id}">
                    <c:set var="outcomeCoefficients" value="${coefficientsDto}"/>
                </c:if>
            </c:forEach>

            <c:forEach items="${eventBets}" var="bet">
                <c:set var="outcomeBet" value=""/>
                <c:if test="${bet.eventResultId eq eventResult.id}">
                    <c:set var="outcomeBet" value="${bet}"/>
                </c:if>
            </c:forEach>


            <c:if test="${eventResult.resultStatus eq ResultStatus.UNBLOCKED}">
                <div class="flip-box custom-controls">
                    <div class="flip-box-inner">
                        <div class="flip-box-front">
                            <div class="bet-module bet-outcome d-flex justify-content-between align-items-center"
                                 data-id="${eventResult.id}" data-outcome="${eventResult.eventOutcomeType.id}">
                                <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                                    <span class="odds left-odds text-center d-flex m-2 order-sm-last">
                                        <i>x</i>
                                        <span>
                                            <fmt:formatNumber value="${outcomeCoefficients.firstUpshotOdds}"
                                                              minIntegerDigits="1"
                                                              minFractionDigits="2" groupingUsed="false"/>
                                        </span>
                                    </span>
                                    <c:choose>
                                        <c:when test="${empty outcomeBet}">
                                            <button type="button"
                                                    class="place-bet btn btn-sm btn-secondary text-nowrap ms-1" data-upshot="1">
                                                <fmt:message key="bet_modal.place_bet_btn"/>
                                            </button>
                                        </c:when>
                                        <c:when test="${(not empty outcomeBet) and (outcomeBet.upshot eq 'FIRST_UPSHOT')}">
                                            <button type="button"
                                                    class="place-bet btn btn-sm btn-secondary text-nowrap ms-1" data-upshot="1">
                                                <img src="/resources/assets/interface/cash-icon.png" class="mx-1" style="width: 15px">
                                                <span class="bet-sum">
                                                    <i class="fas fa-dollar-sign"></i>
                                                    <span class="sum">
                                                        ${outcomeBet.amount}
                                                    </span>
                                                 </span>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button"
                                                    class="btn btn-sm btn-secondary text-nowrap ms-1" disabled>
                                                <i class="fas fa-minus-circle"></i>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <span class="outcome-name text-center mx-1"></span>
                                <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                                    <span class="odds right-odds text-center d-flex m-2">
                                        <i>x</i>
                                        <span>
                                            <fmt:formatNumber value="${outcomeCoefficients.secondUpshotOdds}"
                                                              minIntegerDigits="1"
                                                              minFractionDigits="2" groupingUsed="false"/>
                                        </span>
                                    </span>
                                    <c:choose>
                                        <c:when test="${empty outcomeBet}">
                                            <button type="button"
                                                    class="place-bet btn btn-sm btn-secondary text-nowrap me-1" data-upshot="2">
                                                <fmt:message key="bet_modal.place_bet_btn"/>
                                            </button>
                                        </c:when>
                                        <c:when test="${(not empty outcomeBet) and (outcomeBet.upshot eq 'SECOND_UPSHOT')}">
                                            <button type="button"
                                                    class="place-bet btn btn-sm btn-secondary text-nowrap me-1" data-upshot="2">
                                                <img src="/resources/assets/interface/cash-icon.png" class="mx-1" style="width: 15px">
                                                <span class="bet-sum">
                                                    <i class="fas fa-dollar-sign"></i>
                                                    <span class="sum">
                                                            ${outcomeBet.amount}
                                                    </span>
                                                 </span>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button"
                                                    class="btn btn-sm btn-secondary text-nowrap me-1" disabled>
                                                <i class="fas fa-minus-circle"></i>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="flip-box-back">
                            <div class="bet-module bet-confirm d-flex align-items-center" data-upshot="">
                                <div class="d-flex flex-column flex-sm-row justify-content-center align-items-center">
                                    <img src="/resources/assets/interface/cash-icon.png" class="m-1" style="width: 30px">
                                    <input class="bet-amount text-center" style="width: 80px; padding: 5px; border-radius: 5px;" maxlength="6" placeholder="Amount">
                                </div>

                                <div class="d-flex flex-column align-items-center mx-auto">
                                    <div class="d-flex">
                                        <span class="me-2">
                                            <fmt:message key="bet_modal.prize_title"/>
                                        </span>
                                        <span class="odds outcome-odd text-center d-flex">
                                            <i>x</i>
                                            <span>

                                            </span>
                                        </span>
                                    </div>
                                    <span class="potential-prize">~</span>
                                </div>
                                    <c:choose>
                                        <c:when test="${empty outcomeBet}">
                                            <div class="d-flex flex-column flex-sm-row ms-auto">
                                                <button type="button"
                                                        class="cancel btn btn-sm btn-secondary text-nowrap m-1">
                                                    <fmt:message key="bet_modal.cancel_btn"/>
                                                </button>
                                                <button type="button" class="place-bet btn btn-sm btn-secondary text-nowrap m-1">
                                                    <fmt:message key="bet_modal.place_bet_btn"/>
                                                </button>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="d-flex ms-auto">
                                                <button type="button"
                                                        class="cancel btn btn-sm btn-secondary text-nowrap mx-1" style="width: 30px" title="Back">
                                                    <i class="fas fa-long-arrow-alt-left"></i>
                                                </button>
                                                <button type="button" class="refund-bet btn btn-sm btn-secondary text-nowrap me-1" style="width: 30px" title="Cancel Bet">
                                                    <i class="fas fa-times"></i>
                                                </button>
                                                <button type="button" class="edit-bet btn btn-sm btn-secondary text-nowrap me-1" style="width: 30px" title="Edit Bet">
                                                    <i class="fas fa-pen"></i>
                                                </button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${eventResult.resultStatus eq ResultStatus.BLOCKED}">
                <div class="bet-module bet-outcome d-flex justify-content-between align-items-center"
                     data-id="${eventResult.id}" data-outcome="${eventResult.eventOutcomeType.id}">
                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                        <span class="odds left-odds text-center d-flex m-2 order-sm-last">
                            <i>x</i>
                            <span>
                                <fmt:formatNumber value="${outcomeCoefficients.firstUpshotOdds}"
                                                  minIntegerDigits="1"
                                                  minFractionDigits="2" groupingUsed="false"/>
                            </span>
                        </span>
                        <button type="button"
                                class="btn btn-sm btn-secondary text-nowrap ms-1" disabled>
                            <i class="fas fa-lock"></i>
                            <c:if test="${(not empty outcomeBet) and (outcomeBet.upshot eq 'FIRST_UPSHOT')}">
                                <span class="bet-sum">
                                        <i class="fas fa-dollar-sign"></i>
                                        <span class="sum">
                                                ${outcomeBet.amount}
                                        </span>
                                </span>
                            </c:if>
                        </button>
                    </div>
                    <span class="outcome-name text-center mx-1"></span>
                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                        <span class="odds right-odds text-center d-flex m-2">
                            <i>x</i>
                            <span>
                               <fmt:formatNumber value="${outcomeCoefficients.secondUpshotOdds}"
                                                 minIntegerDigits="1"
                                                 minFractionDigits="2" groupingUsed="false"/>
                            </span>
                        </span>
                        <button type="button"
                                class="btn btn-sm btn-secondary text-nowrap me-1" disabled>
                            <i class="fas fa-lock"></i>
                            <c:if test="${(not empty outcomeBet) and (outcomeBet.upshot eq 'SECOND_UPSHOT')}">
                                <span class="bet-sum">
                                        <i class="fas fa-dollar-sign"></i>
                                        <span class="sum">
                                                ${outcomeBet.amount}
                                        </span>
                                </span>
                            </c:if>
                        </button>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>