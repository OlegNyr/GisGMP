<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE-8"/>

    <%--Берем локаль и ставим в переменную--%>
    <c:set var="userLocale">
        <c:set var="plocale">${pageContext.response.locale}</c:set>
        <c:out value="${fn:replace(plocale, '_', '-')}" default="ru"/>
    </c:set>

    <c:url value="/" var="root"/>

    <!-- Set the viewport width to device width for mobile -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%--
    Included CSS Files (Compressed)                             --%>
    <link rel="stylesheet" href="${root}res/css/foundation.css">
    <link rel="stylesheet" href="${root}res/css/app.v1.css">
    <link rel="stylesheet" href="${root}res/css/normalize.css">
    <%--<link rel="stylesheet" href="${cssUrl}/ie8-grid-foundation-4.css">--%>

    <link rel="stylesheet" href="${root}res/kendo/styles/kendo.common.min.css">
    <link rel="stylesheet" href="${root}res/kendo/styles/kendo.default.min.css">

    <script src="${root}res/js/jquery.js"></script>
</head>
<body>
<script src="${root}res/js/modernizr.js"></script>
<script src="${root}res/js/foundation.min.js"></script>
<script src="${root}res/kendo/js/kendo.web.min.js"></script>
<script src="${root}res/kendo/js/cultures/kendo.culture.ru-RU.min.js"></script>

<tiles:insertAttribute name="header" ignore="true"/>

<tiles:insertAttribute name="menu" ignore="true"/>

<script type="text/javascript">
        function getDataSource(url, func) {
            return new kendo.data.DataSource({"schema": {"total": "total", "data": "data"},
                "serverFiltering": true,
                "transport": {"parameterMap": function parameterMap(options, type) {
                    if(func){
                      options = func(options, type);
                    }
                    return JSON.stringify(options);
                },
                    "read": {
                        "contentType": "application/json",
                        "type": "POST",
                        "url": url}
                }
            });
        }
</script>

<div class="context">
    <tiles:insertAttribute name="body" ignore="true"/>
</div>

<tiles:insertAttribute name="footer" ignore="true"/>

<script>
    $(document).foundation('joyride', 'start');
    $(document).foundation();

    kendo.culture("ru-RU");
    $(document).ready(function () {
        $(".date").kendoDatePicker({ format: "dd.MM.yy"});
        $(".datetime").kendoDateTimePicker({format: "dd.MM.yy HH:mm"});
        $("form .dropdown").kendoDropDownList();
        $("form .combobox").kendoComboBox();
        $(".grid").kendoGrid({sortable: {
            mode: "single",
            allowUnsort: false
        },
            filterable: true,
            scrollable: false});
    })

</script>
<style>
    .date {
        width: 100%;
    }

    .datetime {
        width: 100%;
    }
</style>
</body>
