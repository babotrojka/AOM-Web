<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/16/2021
  Time: 3:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="includes/header.jsp" %>

<div class="container">

    <div class="row">
        <div class="col-lg-8 col">
            <div class="container">
                <c:forEach items="${news}" var="n">
                    <c:if test="${n.visible == 1}">
                        <div class="row mb-3">
                            <div class="container rounded bg-light">
                                <div class="row pt-1">
                                    <div class="col-2 ms-2 badge bg-dark">${''.concat(n.writetime).substring(0, 10)}</div>
                                    <div class="col-1 ms-1 badge bg-dark">${n.writtenBy.nickname}</div>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="<c:url value="/novosti/${n.id}"/>" class="text-reset text-decoration-none"><h4><i><b>${n.title}</b></i></h4></a>
                                    <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('editor')}">
                                        <div>
                                            <form action="<c:url value="/novosti/uredi"/>" method="get">
                                                <input type="text" name="id" value="${n.id}" hidden>
                                                <button type="submit">
                                                    <img src="<c:url value="/static/images/edit_icon.png"/>" class="me-1" width="20" height="25" alt="edit">
                                                </button>
                                                <button data-bs-toggle="modal" class="deleteBtn" id="${''.concat(n.id).concat('_').concat(n.title).concat('_deleteButton')}" data-bs-target="#izbrisiNovostModal" type="button">
                                                    <img src="<c:url value="/static/images/delete_icon.png"/>" width="20" height="25" alt="delete">
                                                </button>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="row pb-2">
                                    <div class="clearfix">
                                        <c:if test="${n.headerImg != null}">
                                            <img src="<c:url value="/backblaze/${n.headerImg}"/>" class="img-thumbnail float-start w-25 me-2 mb-2" alt="">
                                        </c:if>
                                        <p class="news-body">${n.body}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
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
                                        <form action="<c:url value="/home/"/>" method="get">
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
            <!-- Delete Modal -->
            <div class="modal fade" id="izbrisiNovostModal" tabindex="-1" aria-labelledby="izbrisiNovostLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
                    <div class="modal-content">
                        <form action="<c:url value="/novosti/izbrisi"/>" method="post">
                            <div class="modal-header">
                                <h5 class="modal-title" id="izbrisiNovostLabel">Izbriši novost</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <input type="text" id="delete_id" name="id" hidden>
                                Jesi li siguran da zauvijek želiš izbrisati novost <span id="delete_text"></span>?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
                                <button type="submit" class="btn btn-danger">Izbriši</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col d-none d-lg-block">
            <div class="container">
                <c:choose>
                    <c:when test="${sessionScope.currentUserEmail != null}">
                        <%@include file="includes/dashboard.jsp" %>
                    </c:when>
                    <c:otherwise>
                        <%@include file="includes/login.jsp" %>
                    </c:otherwise>
                </c:choose>

                <div class="row mt-5">
                    <div id="carouselBestImages" class="carousel slide carousel-fade" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="<c:url value="${mainSlika}"/>" class="d-block w-75 border border-light border-3 rounded" alt="main">
                            </div>
                            <c:forEach items="${homeSlike}" var="slika">
                                <div class="carousel-item">
                                    <img src="<c:url value="${slika}"/>" class="d-block w-75 border border-light border-3 rounded" alt="...">
                                </div>
                            </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
