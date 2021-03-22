<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<footer class="mt-auto">
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-md-3 item">
                <h3><fmt:message key="footer.header.resources"/></h3>
                <ul>
                    <li><a href="/news/"><fmt:message key="footer.link.resources.news"/></a></li>
                    <li><a href="/rating/"><fmt:message key="footer.link.resources.team_rating"/></a></li>
                </ul>
            </div>
            <div class="col-sm-6 col-md-3 item">
                <h3><fmt:message key="footer.header.support"/></h3>
                <ul>
                    <li><a href="/support/"><fmt:message key="footer.link.support.technical_support"/></a></li>
                    <li><a href="/contacts/"><fmt:message key="footer.link.support.contacts"/></a></li>
                    <li><a href="/privacy-policy/"><fmt:message key="footer.link.support.privacy_policy"/></a></li>
                </ul>
            </div>
            <div class="col-md-6 item text">
                <h3>CYBERBETS</h3>
                <p>
                    <fmt:message key="footer.item_text1"/>
                </p>
                <p>
                    <fmt:message key="footer.item_text2"/>
                </p>
                <p>
                    <fmt:message key="footer.item_text3"/>
                </p>
            </div>
            <div class="col item social">
                <a href="#"><i class="fab fa-vk"></i></a>
                <a href="#"><i class="fab fa-facebook-f"></i></a>
                <a href="#"><i class="fab fa-instagram"></i></a>
            </div>
        </div>
        <p class="copyright mt-4 text-uppercase">Cyberbets © 2021</p>
    </div>
</footer>