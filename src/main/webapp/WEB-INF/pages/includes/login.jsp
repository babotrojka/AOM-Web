<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/1/2021
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row bg-light mb-5 pt-2 rounded">
    <form action="<c:url value="/home/login"/>" method="post">
        <div class="form-floating mb-2">
            <input type="text" class="form-control" name="username" id="username" placeholder="Korisničko ime" required>
            <label for="username">Email ili nadimak</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" name="lozinka" id="lozinka" placeholder="Lozinka">
            <label for="lozinka">Lozinka</label>
        </div>
        <button type="submit" class="btn btn-dark">Prijavi se</button>
    </form>
    <c:if test="${messageLogin != null}">
        <div class="alert alert-danger" role="alert">
            ${messageLogin}
        </div>
    </c:if>
</div>