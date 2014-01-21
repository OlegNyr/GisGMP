<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="userInfo" type="ru.nyrk.procdt.util.security.MyUserDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row column">
    <h2>Информация пользователя</h2>

    <div class="row">
        <label>Имя пользователя:</label>

        <p>${userInfo.username}</p>
    </div>
    <div class="row">
        <label>Имя пользователя:</label>

        <p>${userInfo.family}</p>
    </div>
    <div class="row">
        <label>Email</label>

        <p>${userInfo.email}</p>
    </div>
    <div class="row">
        <label>Телефон:</label>

        <p>${userInfo.phone}</p>
    </div>

    <div class="row">
        <h3>Смена пароля</h3>

        <%--<c:url value="/account/userinfo" var="userinfo"/>--%>
        <%--<form:form  action="${userinfo}" method="post">--%>
            <%--<div class="row">--%>
                <%--<form:label path="oldPassword">Старый пароль:</form:label>--%>
                <%--<form:input path="oldPassword" cssClass="small-3"/>--%>
            <%--</div>--%>

            <%--<div class="row">--%>
                <%--<div class="small-8 columns">--%>
                    <%--<button type="submit">Сменить пароль</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</form:form>--%>

    </div>

</div>