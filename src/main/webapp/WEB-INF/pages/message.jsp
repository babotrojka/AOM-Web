<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/17/2021
  Time: 8:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="includes/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="card text-white text-center ${bg} mx-auto mb-3" style="max-width: 30rem;">
            <div class="card-header">${result}</div>
            <div class="card-body">
                <h5 class="card-title">${title}</h5>
                <p class="card-text">${message} Preusmjeravam...</p>
            </div>
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp" %>

