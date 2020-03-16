<%@ page import="com.es.phoneshop.model.product.ArrayListProductDao" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>

<tags:master pageTitle="Order overview">
    <h1>Order overview</h1>
    <h2>Cart information:</h2>
    <table>
        <thead>
        <tr>

            <td>Image</td>
            <td>Description</td>
            <td>Amount</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:set var="productDao" value="<%=ArrayListProductDao.getInstance()%>"/>
        <c:forEach var="orderItem" items="${order.items}">
            <c:set var="productId" value="${orderItem.productId}"/>
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
                <td>
                    ${orderItem.quantity}
                <td class="price">
                        <fmt:formatNumber value="${orderItem.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>

    <p>Products price: ${order.totalPrice}</p>
    <p>Delivery costs: ${order.deliveryCosts}</p>
    <p>Total: ${order.totalPrice + order.deliveryCosts}</p>
    <p>Payment method: ${order.paymentMethod.toString().toLowerCase()}</p>

    <h2>Contact details:</h2>
    <p>First name: ${order.firstName}</p>
    <p>Last name: ${order.lastName}</p>
    <p>Phone number: +${order.phoneNumber}</p>

    <h2>Delivery information:</h2>
    <p>Delivery date: ${order.deliveryDate}</p>
    <p>Delivery address: ${order.deliveryAddress}</p>



</tags:master>