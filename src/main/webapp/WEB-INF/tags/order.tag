<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="orderParam" required="true" %>
<%@ attribute name="order" required="true" %>

<c:set var="style" value=""/>

<c:if test="${orderParam == param.orderParam}">
    <c:if test="${order == param.order}" >
        <c:set var="style" value="style='font-weight: bold'"/>
        </c:if>
</c:if>
<a ${style} href="products?query=${query}&orderParam=${orderParam}&order=${order}">${order}</a>