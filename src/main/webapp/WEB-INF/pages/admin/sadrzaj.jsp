<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/1/2021
  Time: 5:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="../includes/header.jsp" %>

<div class="container">
    <c:choose>
        <c:when test="${imaSadrzaja}">
        <div class="d-flex justify-content-evenly bg-light mb-2 p-2 rounded">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="izvjestaj" onclick="izvjestajiChecked()" checked>
                <label class="form-check-label" for="izvjestaj">
                    Izvještaji
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="novost" onclick="novostiChecked()" checked>
                <label class="form-check-label" for="novost">
                    Novosti
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="nepotvrden" onclick="nepotvrdeniChecked()" checked>
                <label class="form-check-label" for="nepotvrden">
                    Nepotvrđeni sadržaj
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="nevidljiv" onclick="nevidljiviChecked()" checked>
                <label class="form-check-label" for="nevidljiv">
                    Nevidljvi sadržaj
                </label>
            </div>
        </div>
        <div class="row bg-light">
            <table class="table table-warning table-striped table-hover" id="tableKorisnici">
                <thead class="table-success">
                <tr>
                    <th>Naslov</th>
                    <th>Autor</th>
                    <th>Datum</th>
                    <th>Vidljiv</th>
                    <th>Potvrđen</th>
                    <th>Vidljiv</th>
                    <th>Potvrdi</th>
                    <th>Izbriši</th>
                </tr>
                </thead>
                <c:forEach items="${izvjestaji}" var="i">
                    <tr class="izvjestaji <c:if test="${i.approvedBy == null}">nepotvrdeni</c:if> <c:if test="${i.visible == 0}">nevidljivi</c:if>">
                        <td>
                            <a class="text-reset" href="<c:url value="/izvjestaji/${i.id}"/>">
                                ${i.title}
                            </a>
                        </td>
                        <td>${i.writtenBy.nickname}</td>
                        <td>${i.writetime}</td>
                        <td>
                            <c:choose>
                                <c:when test="${i.visible == 1}">Da</c:when>
                                <c:otherwise>Ne</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${i.approvedBy != null}">Da</c:when>
                                <c:otherwise>Ne</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${i.visible == 0}">
                                <form action="<c:url value="/admin/sadrzaj/vidljivIzvjestaj"/>" method="post">
                                    <input type="text" name="id" value="${i.id}" hidden>
                                    <button type="submit" class="btn btn-primary">Vidljiv</button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${i.approvedBy == null}">
                                <form action="<c:url value="/admin/sadrzaj/potvrdiIzvjestaj"/>" method="post">
                                    <input type="text" name="id" value="${i.id}" hidden>
                                    <button type="submit" class="btn btn-success">Potvrdi</button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <form action="<c:url value="/admin/sadrzaj/izbrisiIzvjestaj"/>" method="post">
                                <input type="text" name="id" value="${i.id}" hidden>
                                <button type="submit" class="btn btn-danger">Izbriši</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:forEach items="${novosti}" var="n">
                    <tr class="novosti <c:if test="${n.approvedBy == null}">nepotvrdeni</c:if> <c:if test="${n.visible == 0}">nevidljivi</c:if>">
                        <td>
                            <a class="text-reset" href="<c:url value="/novosti/${n.id}"/>">
                                ${n.title}
                            </a>
                        </td>
                        <td>${n.writtenBy.nickname}</td>
                        <td>${n.writetime}</td>
                        <td>
                            <c:choose>
                                <c:when test="${n.visible == 1}">Da</c:when>
                                <c:otherwise>Ne</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${n.approvedBy != null}">Da</c:when>
                                <c:otherwise>Ne</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="<c:url value="/admin/sadrzaj/vidljivaNovost"/>" method="post">
                                <input type="text" name="id" value="${n.id}" hidden>
                                <button type="submit" class="btn btn-primary">Vidljiv</button>
                            </form>
                        </td>
                        <td>
                            <form action="<c:url value="/admin/sadrzaj/potvrdiNovost"/>" method="post">
                                <input type="text" name="id" value="${n.id}" hidden>
                                <button type="submit" class="btn btn-success">Potvrdi</button>
                            </form>
                        </td>
                        <td>
                            <form action="<c:url value="/admin/sadrzaj/izbrisiNovost"/>" method="post">
                                <input type="text" name="id" value="${n.id}" hidden>
                                <button type="submit" class="btn btn-danger">Izbriši</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info" role="alert">
                <h4 class="alert-heading">Nema novih izvještaja!</h4>
                <p>Trenutno nema novih izvještaja. Čekamo izlete da ljudi krenu penjati i pisati o tome!</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="../includes/footer.jsp" %>

<script>
    function izvjestajiChecked() {
        update("izvjestaji", document.getElementById("izvjestaj").checked)
    }

    function novostiChecked() {
        update("novosti", document.getElementById("novost").checked)
    }

    function nepotvrdeniChecked() {
        update("nepotvrdeni", document.getElementById("nepotvrden").checked);
    }

    function nevidljiviChecked() {
        update("nevidljivi", document.getElementById("nevidljiv").checked);
    }

    function update(classToUpdate, checked) {
        let rows = document.getElementsByClassName(classToUpdate);

        for(let r of rows)
            r.hidden = !checked;
    }
</script>

