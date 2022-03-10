<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/17/2021
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<%@include file="includes/header.jsp" %>
<div class="container">
    <form action="<c:url value="/izvjestaji/search"/>" method="get" class="row mb-3">
        <div class="col-9">
            <input class="form-control" type="text" id="traziIzvjestaj" name="filter" placeholder="Traži izvještaje">
        </div>
        <button class="col-2 btn btn-success" type="submit">Traži</button>
    </form>
    <div class="row mb-3">
        <div class="list-group">
            <c:forEach items="${izvjestaji}" var="i">
                <c:if test="${i.visible == 1}">
                    <div class="list-group-item list-group-item-action bg-light" aria-current="true">
                        <div class="d-flex w-100 justify-content-between">
                            <a href="<c:url value="/izvjestaji/${i.id}"/>" class="text-reset text-decoration-none"><h5 class="mb-1"><i>${i.title}</i></h5></a>
                            <small>${''.concat(i.writetime).substring(0, 10)}</small>
                        </div>
                        <p class="mb-1">${''.concat(i.body).substring(0, Math.min(fn:length(i.body), cLength)).concat('...')}</p>
                        <div class="d-flex justify-content-between"  style="max-height: 28px">
                            <small class="badge bg-secondary text-white">${i.writtenBy.nickname}</small>
                            <c:if test="${sessionScope.currentUserPrivileges.equals('admin')  || sessionScope.currentUserPrivileges.equals('editor') || sessionScope.currentUserNick.equals(i.writtenBy.nickname)}">
                                <form action="<c:url value="/izvjestaji/uredi"/>" method="get">
                                    <span>
                                        <input type="text" name="id" value="${i.id}" hidden>
                                        <button type="submit">
                                            <img src="<c:url value="/static/images/edit_icon.png"/>" class="me-1" width="20" height="25" alt="edit">
                                        </button>
                                        <button data-bs-toggle="modal" class="deleteBtn" id="${''.concat(i.id).concat('_').concat(i.title).concat('_deleteButton')}" data-bs-target="#izbrisiIzvjestajModal" type="button">
                                            <img src="<c:url value="/static/images/delete_icon.png"/>" width="20" height="25" alt="delete">
                                        </button>
                                    </span>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <!-- Delete Modal -->
    <div class="modal fade" id="izbrisiIzvjestajModal" tabindex="-1" aria-labelledby="izbrisiIzvjestajLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
            <div class="modal-content">
                <form action="<c:url value="/izvjestaji/izbrisi"/>" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="izbrisiIzvjestajLabel">Izbriši izvještaj</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" id="delete_id" name="id" hidden>
                        Jesi li siguran da zauvijek želiš izbrisati izvještaj <span id="delete_text"></span>?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
                        <button type="submit" class="btn btn-danger">Izbriši</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <nav>
            <ul class="pagination pagination-lg justify-content-center">
                <c:forEach begin="1" end="${brojStranica}" varStatus="loop">
                    <c:choose>
                        <c:when test="${loop.index == trenutnaStranica}">
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${loop.index}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <form action="<c:url value="/izvjestaji/"/>" method="get">
                                <li class="page-item">
                                    <button type="submit" name="stranica" value="${loop.index}" class="page-link">${loop.index}</button>
                                </li>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>

<%@include file="includes/footer.jsp" %>

<script>
    $( document ).ready(function() {
        let modalBtns = document.getElementsByClassName('deleteBtn');
        for(let btn of modalBtns)
            btn.onclick = popuniModal;
    });

</script>
