<%@ page import="com.es.phoneshop.model.product.ArrayListProductDao" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced search">
    <h1>Advanced search</h1>
    <form action="search" method="get">
        <p><label>
            Description <input type="text" name="description">
        <select name="type">
            <option value="allWords">all words</option>
            <option value="anyWord">any word</option>
        </select>
        </label>
        </p>
        <p><label>Min price <input type="text" name="minPrice"></label></p>
        <p><label>Max price <input type="text" name="maxPrice"></label></p>
        <input type="submit" value="Search">
    </form>
</tags:master>