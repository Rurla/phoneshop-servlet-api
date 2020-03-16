<%@ page import="com.es.phoneshop.model.product.ArrayListProductDao" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cartItems" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="deliveryCosts" type="java.math.BigDecimal" scope="request"/>
<tags:master pageTitle="Checkout">
    <h1>Checkout</h1>
    <h2>Cart information:</h2>
    ${errors.getOrDefault("cart", "")}
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
                    <td>${cartItem.quantity}</td>
                    <td class="price">
                        <a href="price-history/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <p>Products price: ${sessionScope.get("cart").totalPrice}</p>
    <p>Delivery costs ${deliveryCosts}</p>
    <p>Total: ${sessionScope.get("cart").totalPrice + deliveryCosts}</p>
    <form action="checkout" method="post">
        <h2>Contact details:</h2>
        <p><input type="text" name="firstName" placeholder="First name"> ${errors.getOrDefault("firstName", "")}</p>
        <p><input type="text" name="lastName" placeholder="Last name"> ${errors.getOrDefault("lastName", "")}</p>
        <p>+<input type="text" name="phone" placeholder="Phone number"> ${errors.getOrDefault("phone", "")}</p>
        <h2>Delivery information:</h2>
        <p>Delivery date: <input type="date" name="date"> ${errors.getOrDefault("date", "")}</p>
        <p><input type="text" name="address" placeholder="Delivery address">${errors.getOrDefault("address", "")}</p>
        <h2>Payment method:</h2>
            ${errors.getOrDefault("paymentMethod", "")}
        <p><input type="radio" name="paymentMethod" value="cash"> Cash </p>
        <p><input type="radio" name="paymentMethod" value="card"> Card </p>
        <input type="submit" value="Place order">
    </form>

</tags:master>