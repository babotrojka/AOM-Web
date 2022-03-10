<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/18/2021
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="includes/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col">
            <div class="container">
                <div class="row bg-secondary text-white bg-opacity-90 ps-1 mb-2 rounded-pill">
                    <h5 class="pt-2 text-center">${izvjestaj.title}</h5>
                </div>
                <div class="row bg-light p-3 mb-2 rounded">
                    <div class="clearfix">
                        <c:if test="${headerImg != null}">
                            <img src="<c:url value="/backblaze/${headerImg}"/>" class="img-thumbnail float-start w-50 me-2 mb-2" alt="">
                        </c:if>
                        <p class="news-body">${izvjestaj.body}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-2 badge bg-secondary text-white">
                        ${izvjestaj.writtenBy.nickname}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg mt-lg-0 mt-5">
            <div class="list-group">
                <c:forEach items="${izvjestajiBar}" var="i">
                    <a href="<c:url value="/izvjestaji/${i.id}"/>" class="list-group-item list-group-item-action bg-light" aria-current="true">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${i.title}</h5>
                            <small class="badge bg-secondary text-white" style="max-height: 25px">${''.concat(i.writetime).substring(0, 10)}</small>
                        </div>
                        <!--<p class="mb-1">${''.concat(i.body).substring(0, 30).concat('...')}</p>-->
                        <small class="badge bg-secondary text-white">${i.writtenBy.nickname}</small>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp" %>

