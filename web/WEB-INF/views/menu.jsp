<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="top-bar" data-topbar>
    <ul class="title-area">
        <%--<!-- Title Area -->--%>
        <li class="name">
            <h1><a href="#">GisGMP</a></h1>
        </li>
        <%--Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone--%>
        <li class="toggle-topbar menu-icon"><a href="#"><span>МЕНЮ</span></a></li>
    </ul>
    <section class="top-bar-section">
        <%--<!-- Left Nav Section -->--%>
        <ul class="left">
            <li class="divider"></li>

                <li class="has-dropdown"><a href="#">Справочники</a>
                    <ul class="dropdown">
                        <li><a href="<c:url value="/"/>">Платежи</a></li>
                        <li><a href="<c:url value="/"/>">Терминалы</a></li>
                        <li><a href="<c:url value="/"/>">ТехКомп</a></li>
                        <li><a href="<c:url value="/"/>">Процесинги</a></li>
                    </ul>
                </li>

                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <li class="has-dropdown"><a href="#">Администрирование</a>
                        <ul class="dropdown">
                            <li><a href="<c:url value="/"/>">Пользователи</a></li>
                        </ul>
                    </li>
                </sec:authorize>
        </ul>

        <!-- Right Nav Section -->
        <ul class="right">
            <li class="divider hide-for-small"></li>
            <li class="has-dropdown"><a href="#">Пользователь</a>
                <ul class="dropdown">
                    <li><a href="<c:url value="/account/userinfo"/>">Информация о пользователе</a></li>
                </ul>
            </li>
            <li class="divider"></li>
            <li class="divider show-for-small"></li>
            <li class="has-form"><a class="button" href="#" data-reveal-id="modalExit">Выйти</a></li>
        </ul>
    </section>
</nav>

<div id="modalExit" class="reveal-modal small" data-reveal>
    <h2>GisGMP</h2>

    <p>Вы дествительно хотите выйти?</p>

    <div class="row text-right">
        <div class="small-8 columns">
            <a href="<c:url value="/account/logout" />" class="medium button">Да</a>
        </div>
        <div class="small-4 columns">
            <a href="#" class="medium button" onclick="$('a.close-reveal-modal').trigger('click')">Нет</a>
        </div>
    </div>

    <a class="close-reveal-modal">&#215;</a>
</div>
