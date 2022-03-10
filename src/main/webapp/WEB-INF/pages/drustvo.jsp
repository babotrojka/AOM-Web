<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/4/2021
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="includes/header.jsp" %>

<div class="container">
    <div class="row bg-secondary text-white rounded-pill pt-1 mb-2">
        <h5 class="text-center">O društvu</h5>
    </div>
    <div class="row bg-light p-2 rounded">
        <h5 class="text-decoration-underline">${onama.title}</h5>
        <div class="text-muted mb-4">
            ${onama.body}
        </div>
        <h5 class="text-decoration-underline">${povijest.title}</h5>
        <div class="text-muted mb-4">
            ${povijest.body}
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp" %>