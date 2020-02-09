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
          <a href=<c:url value="products?query=${param.query.replace(' ', '+')}&orderParam=description&order=asc"/> >asc</a>
          <a href=<c:url value="products?query=${param.query.replace(' ', '+')}&orderParam=description&order=desc"/> >desc</a></td>
        <td class="price">Price
            <a href=<c:url value="products?query=${param.query.replace(' ', '+')}&orderParam=price&order=asc"/> >asc</a>
            <a href=<c:url value="products?query=${param.query.replace(' ', '+')}&orderParam=price&order=desc"/> >desc</a>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>${product.description}</td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>