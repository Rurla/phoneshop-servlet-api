<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>

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


</tags:master>