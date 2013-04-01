<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.List" %>
<%@ page import="org.w3c.dom.css.DocumentCSS" %>
<%--
  Created by IntelliJ IDEA.
  User: gmdayley
  Date: 7/29/11
  Time: 10:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="http://fonts.googleapis.com/css?family=Bowlby+One+SC:regular&v1" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Permanent+Marker:regular&v1" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Play:regular,bold&v1" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/css/game.css"/>
</head>
<body>

<div id="container">
    <h1>GPardy!</h1>

    <div class="sub-container">
        <div>Choose a spreadsheet that contains answers and questions:</div>
        <ul>
            <c:forEach var="doc" items="${docs}">
                <li><a href="/game.html#game:GTUG:${doc.key}" class="doc"><c:out value="${doc.title.plainText}"/></a></li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>