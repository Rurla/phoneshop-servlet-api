<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="totalPrice" type="java.math.BigDecimal" scope="request"/>
<a href="<%=request.getContextPath() + "/cart"%>"> Cart: ${totalPrice} </a>
