<%@ page import="com.es.phoneshop.model.product.ArrayListProductDao" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="errors" type="java.util.HashMap" scope="request"/>
<jsp:useBean id="description" type="java.lang.String" scope="request"/>
<jsp:useBean id="type" type="java.lang.String" scope="request"/>
<jsp:useBean id="minPrice" type="java.lang.String" scope="request"/>
<jsp:useBean id="maxPrice" type="java.lang.String" scope="request"/>

<tags:master pageTitle="Advanced search">
    <h1>Advanced search</h1>
    <form action="search" method="get">
        <p><label>
            Description <input type="text" name="description" value="${description}">
        <select name="type" >
            <option value="allWords">all words</option>
            <option value="anyWord">any word</option>
        </select>
        </label>
        </p>
        <p><label>Min price <input type="text" name="minPrice" value="${minPrice}"></label></p>
            <c:if test='${errors.containsKey("minPrice")}'>
            <p>${errors.get("minPrice")}</p>
            </c:if>
        <p><label>Max price <input type="text" name="maxPrice" value="${maxPrice}"></label></p>
            <c:if test='${errors.containsKey("maxPrice")}'>
                <p>${errors.get("maxPrice")}</p>
            </c:if>
        <input type="submit" value="Search">
    </form>

    <c:if test="${products.size() != 0}">
    <table>
        <thead>
        <tr>

            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
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
    </c:if>
</tags:master>