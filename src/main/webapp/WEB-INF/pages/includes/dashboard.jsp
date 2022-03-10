<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/1/2021
  Time: 9:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<div class="row bg-light py-2 rounded">
    <div class="btn-group-vertical" role="group" aria-label="Basic outlined example">
        <button type="button" class="btn btn-dark" disabled>Dashboard</button>
        <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal" id="dodajNovostButton" data-bs-target="#dodajNovostModal">Dodaj novost</button>
        <button type="button" class="btn btn-outline-dark" data-bs-toggle="modal" id="dodajIzvjestajButton" data-bs-target="#dodajIzvjestajModal">Dodaj izvještaj</button>
        <a href="<c:url value="/izvjestaji/osobni"/>" class="btn btn-outline-dark mb-2">Moji izvještaji</a>
        <!--<button type="button" class="btn btn-outline-dark mt-2">Uredi karton</button>-->
        <a href="<c:url value="/oruzarstvo/"/>" class="btn btn-outline-dark">Idi u oružarstvo</a>
        <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('editor')}">
            <a href="<c:url value="/admin/sadrzaj/"/>" class="btn btn-outline-dark mt-2">
                Upravljaj sadržajem
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">${nonConfirmed}</span>
            </a>
        </c:if>
        <c:if test="${sessionScope.currentUserPrivileges.equals('admin')}">
            <a href="<c:url value="/admin/korisnici"/>" class="btn btn-outline-dark">Korisnici</a>
        </c:if>
        <button type="button" class="btn btn-outline-dark mt-3" data-bs-toggle="modal" id="promijeniLozinkuButton" data-bs-target="#promijeniLozinkuModal">Promijeni lozinku</button>
        <a href="<c:url value="/logout/"/>" class="btn d-lg-none btn-danger">Logout</a>
    </div>
</div>
