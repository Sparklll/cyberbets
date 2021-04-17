<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<%@ page import="by.epam.jwd.cyberbets.domain.ResultStatus" %>
<%@ page import="by.epam.jwd.cyberbets.domain.Bet.Upshot" %>

<div class="outcomes">

    <c:forEach items="${eventResults}" var="eventResult">
        <c:if test="${eventResult eq ResultStatus.UNBLOCKED}">

        </c:if>

        <c:if test="${eventResult eq ResultStatus.BLOCKED}">

            <c:forEach items="${eventCoefficients}" var="coefficientsDto">
                <c:if test="${coefficientsDto.eventResultId eq eventResult.id}">
                    <c:set var="outcomeCoefficients" value="${coefficientsDto}"/>
                </c:if>
            </c:forEach>

            <div class="bet-module bet-outcome d-flex justify-content-between align-items-center" data-id="${eventResult.id}">
                <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                    <span class="odds left-odds text-center m-2 order-sm-last"><i>x</i>${outcomeCoefficients.firstUpshotOdds}</span>
                    <button type="button"
                            class="btn btn-sm btn-secondary text-nowrap ms-1" disabled>
                        <i class="fas fa-lock"></i>
                    </button>
                </div>
                <span class="outcome-name text-center mx-1">[Карта #1] Второй пистолетный раунд</span>
                <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">
                    <span class="odds left-odds text-center m-2"><i>x</i>${outcomeCoefficients.secondUpshotOdds}</span>
                    <button type="button"
                            class="btn btn-sm btn-secondary text-nowrap me-1" disabled>
                        <i class="fas fa-lock"></i>
                    </button>
                </div>
            </div>
        </c:if>
    </c:forEach>


<%--    <div class="flip-box custom-controls">--%>
<%--        <div class="flip-box-inner">--%>
<%--            <div class="flip-box-front">--%>
<%--                <div class="bet-module bet-outcome d-flex justify-content-between align-items-center">--%>
<%--                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">--%>
<%--                        <span class="odds left-odds text-center m-2 order-sm-last"><i>x</i>1.80</span>--%>
<%--                        <button type="button"--%>
<%--                                class="place-bet btn btn-sm btn-secondary text-nowrap ms-1">Place Bet--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                    <span class="outcome-name text-center mx-1">[Карта #1] Второй пистолетный раунд</span>--%>
<%--                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">--%>
<%--                        <span class="odds left-odds text-center m-2"><i>x</i>1.80</span>--%>
<%--                        <button type="button"--%>
<%--                                class="place-bet btn btn-sm btn-secondary text-nowrap me-1">Place Bet--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--            <div class="flip-box-back">--%>
<%--                <div class="bet-module bet-confirm d-flex align-items-center">--%>
<%--                    <div class="d-flex flex-column flex-sm-row justify-content-center align-items-center">--%>
<%--                        <img src="assets/cash-icon.png" class="m-1" style="width: 30px">--%>
<%--                        <input class="text-center" style="width: 80px; padding: 5px; border-radius: 5px;" maxlength="6" placeholder="Amount">--%>
<%--                    </div>--%>

<%--                    <div class="d-flex flex-column align-items-center mx-auto">--%>
<%--                        <div class="d-flex">--%>
<%--                            <span class="me-2">Выигрыш</span>--%>
<%--                            <span class="odds outcome-odd text-center"><i>x</i>1.80</span>--%>
<%--                        </div>--%>
<%--                        <span class="potential-prize">~</span>--%>
<%--                    </div>--%>

<%--                    <div class="d-flex flex-column flex-sm-row ms-auto">--%>
<%--                        <button type="button"--%>
<%--                                class="cancel-bet btn btn-sm btn-secondary text-nowrap m-1">Cancel--%>
<%--                        </button>--%>
<%--                        <button type="button" class="btn btn-sm btn-secondary text-nowrap m-1">Place Bet--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    --%>
<%--    <!--Refund-->--%>
<%--    <div class="flip-box custom-controls">--%>
<%--        <div class="flip-box-inner">--%>
<%--            <div class="flip-box-front">--%>
<%--                <div class="bet-module bet-outcome d-flex justify-content-between align-items-center">--%>
<%--                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">--%>
<%--                        <span class="odds left-odds text-center m-2 order-sm-last"><i>x</i>1.80</span>--%>
<%--                        <button type="button"--%>
<%--                                class="place-bet btn btn-sm btn-secondary text-nowrap ms-1">--%>
<%--                            <img src="assets/cash-icon.png" class="mx-1" style="width: 15px">--%>

<%--                        </button>--%>
<%--                    </div>--%>
<%--                    <span class="outcome-name text-center mx-1">[Карта #1] Второй пистолетный раунд</span>--%>
<%--                    <div class="d-flex flex-column align-items-sm-center flex-sm-row mb-2 mb-sm-0">--%>
<%--                        <span class="odds left-odds text-center m-2"><i>x</i>1.80</span>--%>
<%--                        <button type="button"--%>
<%--                                class="btn btn-sm btn-secondary text-nowrap me-1" disabled>--%>
<%--                            <i class="fas fa-minus-circle"></i>--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--            <div class="flip-box-back">--%>
<%--                <div class="bet-module bet-edit d-flex align-items-center">--%>
<%--                    <div class="d-flex flex-column flex-sm-row justify-content-center align-items-center">--%>
<%--                        <img src="assets/cash-icon.png" class="m-1" style="width: 30px">--%>
<%--                        <input class="text-center" style="width: 80px; padding: 5px; border-radius: 5px;" maxlength="6" placeholder="Amount">--%>
<%--                    </div>--%>

<%--                    <div class="d-flex flex-column align-items-center mx-auto">--%>
<%--                        <div class="d-flex">--%>
<%--                            <span class="me-2">Выигрыш</span>--%>
<%--                            <span class="odds outcome-odd text-center"><i>x</i>1.80</span>--%>
<%--                        </div>--%>
<%--                        <span class="potential-prize">~</span>--%>
<%--                    </div>--%>

<%--                    <div class="d-flex ms-auto">--%>
<%--                        <button type="button"--%>
<%--                                class="cancel-bet btn btn-sm btn-secondary text-nowrap mx-1" style="width: 30px" title="Back">--%>
<%--                            <i class="fas fa-long-arrow-alt-left"></i>--%>
<%--                        </button>--%>
<%--                        <button type="button" class="btn btn-sm btn-secondary text-nowrap me-1" style="width: 30px" title="Cancel Bet">--%>
<%--                            <i class="fas fa-times"></i>--%>
<%--                        </button>--%>
<%--                        <button type="button" class="btn btn-sm btn-secondary text-nowrap me-1" style="width: 30px" title="Edit Bet">--%>
<%--                            <i class="fas fa-pen"></i>--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

</div>