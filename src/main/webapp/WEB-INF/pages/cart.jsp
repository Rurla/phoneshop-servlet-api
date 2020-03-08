<%@ page import="com.es.phoneshop.model.product.ArrayListProductDao" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cartItems" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Cart">
    <br>
    <form id="update" action="cart/update" method="post">
    <table>
        <thead>
        <tr>

            <td>Image</td>
            <td>Description</td>
            <td>Amount</td>
            <td class="price">Price</td>
            <td>Action</td>
        </tr>
        </thead>
        <c:set var="productDao" value="<%=ArrayListProductDao.getInstance()%>"/>
        <c:forEach var="cartItem" items="${cartItems}">
            <c:set var="productId" value="${cartItem.productId}"/>
            <c:set var="product" value="${productDao.getProduct(productId)}"/>
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
                <td><input form="update" type="number" name="${productId}" value="${cartItem.quantity}"></td>
                <td class="price">
                    <a href="price-history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
                <td>
                    <button formaction="cart/deleteCartItem" formmethod="post" name="productId" value="${productId}">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
        <br>
    <input form="update" type="submit" value="Update">
    </form>
</tags:master>