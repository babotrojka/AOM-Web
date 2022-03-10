<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/24/2021
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="includes/header.jsp" %>
<div class="container">
    <form action="<c:url value="/izvjestaji/uredi"/>" method="post" class="bg-light p-2">
        <input type="text" hidden name="id" id="id" value="${i.id}">
        <div class="form-floating mb-1">
            <input type="text" class="form-control" id="naslov" placeholder="Naslov" name="naslov" value="${i.title}" required>
            <label for="naslov">Naslov </label>
        </div>
        <div class="mb-1">
            <textarea class="form-control" id="sadrzajUredi" rows="20" required name="sadrzaj" placeholder="Tekst...">${i.body}</textarea>
        </div>
        <div class="d-flex mb-3">
            <button type="button" id="paragrafUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci paragraf">Paragraf</button>
            <button type="button" id="boldUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Bold">Bold</button>
            <button type="button" id="kurzivUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Kurziv">Kurziv</button>
            <button type="button" id="linkUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci link">Link</button>
            <button type="button" id="slikaUredi" class="btn btn-outline-secondary" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci sliku">Slika</button>
        </div>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="kljucneRijeci" name="kljucneRijeci" placeholder="Ključne riječi" value="${i.keywords}" required>
            <label for="kljucneRijeci">Ključne riječi </label>
        </div>
        <div class="mb-3">
            <label for="datum">Datum pisanja: </label>
            <input type="date" id="datum" name="datum" value="${''.concat(i.writetime).substring(0, 10)}">
        </div>

        <label for="autorDatalist" class="form-label">Autor</label>
        <input class="form-control" list="autorOptions" name="autor" id="autorDatalist" onkeyup="check()" value="${i.writtenBy.nickname}" required placeholder="Pretraži po nadimku...">
        <datalist id="autorOptions">
            <c:forEach items="${korisnici}" var="korisnik">
            <option value="${korisnik.nickname}" <c:if test="${korisnik.nickname.equals(sessionScope.currentUserNick)}">selected</c:if>>
                </c:forEach>
        </datalist>
        <div class="alert alert-danger text-end mb-1" id="wrongUser" hidden role="alert">
            Molim odaberi korisnika iz padajuće liste!
        </div>

        <label for="format">Format: </label>
        <select class="form-select mb-3" id="format" name="format" aria-label=".form-select example" required>
            <c:forEach items="${formati}" var="format">
                <option value="${format.id}" <c:if test="${format.id.equals(i.format.id)}">selected</c:if>>${format.format}</option>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-success w-100" id="dodajButton">Uredi</button>
    </form>

</div>


<%@include file="includes/footer.jsp" %>
<script>
    $( document ).ready(function() {
        $('#linkUredi').click(function(){
            let text = '<a target="_blank" href="upisi_link">upisi_tekst</a>';
            $("#sadrzajUredi").insertIntoTextArea(text);
        });
        $('#slikaUredi').click(function(){
            let text = '<img src="link_za_jednu_sliku" alt="ime_slike" class="img-thumbnail">';
            $("#sadrzajUredi").insertIntoTextArea(text);
        });
        $('#paragrafUredi').click(function(){
            let text = '<p>upisi_tekst</p>';
            $("#sadrzajUredi").insertIntoTextArea(text);
        });
        $('#boldUredi').click(function(){
            let text = '<b>upisi_tekst</b>';
            $("#sadrzajUredi").insertIntoTextArea(text);
        });
        $('#kurzivUredi').click(function(){
            let text = '<i>upisi_tekst</i>';
            $("#sadrzajUredi").insertIntoTextArea(text);
        });

    });
</script>
