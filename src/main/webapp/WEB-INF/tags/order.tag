<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="orderParam" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="products?query=${query}&orderParam=${orderParam}&order=${order}">${order}</a>