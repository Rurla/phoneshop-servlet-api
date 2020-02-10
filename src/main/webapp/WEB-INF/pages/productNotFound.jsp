<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product not found">


    <br>
    <h1 style="text-align: center">404 Not Found</h1>
    <hr>
    <div style="text-align: center">${requestScope['javax.servlet.error.exception'].message}</div>


</tags:master>