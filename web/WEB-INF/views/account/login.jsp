<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="columns">

        <h1>GisGMP</h1>

        <p>Используйте форму ниже, чтобы войти в свою учетную запись.</p>

        <c:if test="${not empty error}">
            <div data-alert="" class="alert-box alert">
                Невозможно войти по следующей причине ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                <a href="" class="close">×</a>
            </div>
        </c:if>

        <form class="custom" action="<c:url value="/j_spring_security_check" />" method="post">
            <div class="row">
                <div class="large-offset-1 large-3 small-10 small-centered small-offset-1">
                    <div class="row">
                        <label for="j_username">Логин:</label>
                        <input id="j_username"
                               name="j_username" maxlength="50" type="text"/>
                    </div>
                    <div class="row">
                        <label for="j_password">Пароль:</label>
                        <input id="j_password"
                               name="j_password" maxlength="50" type="password"/>
                    </div>
                    <div class="row">
                        <label for="_remember_me">
                            <input type="checkbox" id="_remember_me" name="_remember_me" style="display: none;"><span
                                class="custom checkbox checked"></span>Запомнить меня</label>
                    </div>
                    <input type="submit" class="small button" value="Войти"/>
                </div>
            </div>
        </form>
        <script>
            $(document).foundation();
        </script>
    </div>
</div>