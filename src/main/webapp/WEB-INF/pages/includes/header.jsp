<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/17/2021
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<html>
<head>
    <title>${title == null ? 'AO Zagreb Matica' : title}</title>
    <link rel="icon" href="<c:url value="/static/images/logo_no_b.png"/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css" />">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="<c:url value="/static/js/main.js"/>"></script>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

    <style>
        body {
            background-image: url("<c:url value="/static/images/bg_2.jpg"/>");
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
    </style>
    <meta name="google-site-verification" content="FRDm-KSc_XZYWOezg35hUjN6poRvnZ2SaVJ2M0BGzqg"/>
</head>
<!--<body class="bg-secondary bg-opacity-50">-->
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    <div class="container-fluid">
        <a href="<c:url value="/"/>" class="text-decoration-none">
            <img src="<c:url value="/static/images/logo.jpg"/>" class="img-thumbnail" alt="Logo tekst" width="60px" height="40px">
        <span class="text-white-50 ms-1">AO Zagreb Matica</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link ${active.equals("novosti") ? 'active' : ''}" aria-current="page" href="<c:url value="/"/>">Novosti</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${active.equals("izvjestaji") ? 'active' : ''}" href="<c:url value="/izvjestaji/"/>">Izvještaji</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link ${active.equals("skola") ? 'active' : ''}" href="<c:url value="/skola/"/>">Alpinistička škola</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${active.equals("drustvo") ? 'active' : ''}"  href="<c:url value="/drustvo/"/> ">O društvu</a>
                </li>
            </ul>
            <c:choose>
                <c:when test="${sessionScope.currentUserID != null}">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="userBtn" data-bs-toggle="dropdown" aria-expanded="false">
                            Bok ${sessionScope.currentUserNick}
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="userBtn">
                            <%@include file="dashboard.jsp" %>
                        </ul>
                    </div>
                    <a href="<c:url value="/logout/"/>" class="btn btn-danger ms-2 d-none d-lg-block">Logout</a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-primary d-lg-none mb-3" data-bs-toggle="modal" data-bs-target="#loginModal" >Prijava</button>
                    <form action="<c:url value="/izvjestaji/search"/>" class="d-flex">
                        <input class="form-control me-2" name="filter" type="search" placeholder="Traži izvještaje" aria-label="Search">
                        <button class="btn btn-success" type="submit" >Traži</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div style="margin-bottom: 85px"></div>
<c:if test="${messageLogin != null}">
<div class="alert alert-danger d-lg-none" role="alert">
        ${messageLogin}
</div>
</c:if>

<c:choose>
<c:when test="${sessionScope.currentUserEmail != null}">
    <%@include file="novosti_modal.jsp" %>
    <%@include file="izvjestaj_modal.jsp" %>
    <%@include file="lozinka_modal.jsp" %>
</c:when>
<c:otherwise>
    <%@include file="login_modal.jsp" %>
</c:otherwise>
</c:choose>
