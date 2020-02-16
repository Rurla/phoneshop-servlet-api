<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="productDao" type="com.es.phoneshop.model.product.ArrayListProductDao" scope="request"/>

<tags:master pageTitle="Product">
    <br>
    <p>${sessionScope.message}</p>
    <c:remove var="message"/>
    <form action="${product.id}" method="post">
        <label>
            Quantity
            <input type="number" name="quantity" value="1">
        </label>
        <input type="submit" value="Add to cart">
    </form>
    <table>
        <tr>
            <th rowspan="4">
                <img src="${product.imageUrl}" alt="Product image"/>
            </th>
        </tr>
        <tr>
            <td>
                Description:
            </td>
            <td>
                    ${product.description}
            </td>
        </tr>
        <tr>
            <td>
                Price:
            </td>
            <td>
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
        <tr>
            <td>Stock:</td>
            <td>${product.stock}</td>
        </tr>
    </table>

    <h2>Recently viewed</h2>
    <table>
        <thead>
        <tr>

            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</tr>
        </thead>
        <c:forEach var="productId" items="${sessionScope.recently.productIds}">
            <c:set var="product" value="${productDao.getProduct(productId)}"/>
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${product.id}">
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