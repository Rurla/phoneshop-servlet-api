<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form action="products" method="get">
        <input type="text" name="query" value="${param.getOrDefault("query", "")}"/>
        <input type="submit" value="Search"/>
    </form>
    <table>
        <thead>
        <tr>

            <td>Image</td>
            <td>Description
                <tags:order query="${param.query}" orderParam="description" order="asc"/>
                <tags:order query="${param.query}" orderParam="description" order="desc"/>
            </td>
            <td class="price">Price
                <tags:order query="${param.query}" orderParam="price" order="asc"/>
                <tags:order query="${param.query}" orderParam="price" order="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">
                        ${product.description}
                    </a>
                </td>
                <td class="price">
                    <a href="products/price-history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>