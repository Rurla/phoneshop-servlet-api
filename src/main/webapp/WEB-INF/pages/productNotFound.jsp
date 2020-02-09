<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="exception" type="com.es.phoneshop.exception.ProductNotFoundException" scope="request"/>

<tags:master pageTitle="Product not found">


    <br>
    <h1 style="text-align: center">404 Not Found</h1>
    <hr>
    <div style="text-align: center">${exception.message}</div>


</tags:master>