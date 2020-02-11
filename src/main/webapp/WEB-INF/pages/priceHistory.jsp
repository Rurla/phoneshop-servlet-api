<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>

<jsp:useBean id="history" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Price History">
<h1>Price history</h1>
<h2>${product.description}</h2>

    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Price</th>
        </tr>
        </thead>
        <c:forEach var="record" items="${history}">
            <tr>
                <td>${record.date}</td>
                <td>
                        <fmt:formatNumber value="${record.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>