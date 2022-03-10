<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/30/2021
  Time: 1:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="includes/header.jsp" %>
<div class="container">
    <form action="<c:url value="/novosti/uredi"/>" method="post" enctype="multipart/form-data" class="bg-light p-2">
        <c:if test="${n != null}"><input type="text" hidden name="id" id="id" value="${n.id}"></c:if>
        <div class="form-floating mb-1">
            <input type="text" class="form-control" id="naslovNovost" placeholder="Naslov" name="naslov" <c:if test="${n != null}"> value="${n.title}" </c:if>  required>
            <label for="naslovNovost">Naslov </label>
        </div>
        <div class="mb-2">
            <textarea class="form-control" id="sadrzajNovostUredi" rows="10" name="sadrzaj" required placeholder="Tekst..."><c:if test="${n != null}">${n.body} </c:if></textarea>
        </div>
        <div class="d-flex mb-3">
            <button type="button" id="paragrafNUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci paragraf">Paragraf</button>
            <button type="button" id="boldNUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Bold">Bold</button>
            <button type="button" id="kurzivNUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Kurziv">Kurziv</button>
            <button type="button" id="linkNUredi" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci link">Link</button>
            <button type="button" id="slikaNUredi" class="btn btn-outline-secondary" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci sliku">Slika</button>
        </div>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="kljucneRijeci" name="kljucneRijeci" <c:if test="${n != null}"> value="${n.keywords}" </c:if> placeholder="Ključne riječi">
            <label for="kljucneRijeci">Ključne riječi </label>
        </div>
        <div class="mb-3">
            <label for="datumNovost">Datum: </label>
            <input type="date" id="datumNovost" name="datum" <c:if test="${n != null}"> value="${''.concat(n.writetime).substring(0, 10)}" </c:if> required>
        </div>
        <div class="mb-2">
            <label for="photo" class="form-label">Dodaj fotografiju</label>
            <input class="form-control" type="file" id="photo" name="headerImg">
        </div>

        <label for="kategorija">Kategorija: </label>
        <select class="form-select mb-1" id="kategorija" name="kategorija" aria-label=".form-select example" required>
            <c:forEach items="${kategorije}" var="c">
                <option value="${c.id}" <c:if test="${n != null}"><c:if test="${c.id.equals(n.category.id)}">selected</c:if> </c:if>>${c.category}</option>
            </c:forEach>
        </select>

        <label for="format">Format: </label>
        <select class="form-select mb-3" id="format" name="format" aria-label=".form-select example" required>
            <c:forEach items="${formati}" var="format">
                <option value="${format.id}" <c:if test="${n != null}"><c:if test="${format.id.equals(n.format.id)}">selected</c:if> </c:if>>${format.format}</option>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-success w-100" id="dodajButton">Uredi</button>
    </form>

</div>

<%@include file="includes/footer.jsp" %>

<script>
    $( document ).ready(function() {
        $('#linkNUredi').click(function(){
            let text = '<a href="upisi_link">upisi_tekst</a>';
            $("#sadrzajNovostUredi").insertIntoTextArea(text);
        });
        $('#slikaNUredi').click(function(){
            let text = '<img src="link_za_sliku" alt="ime_slike" class="img-thumbnail">';
            $("#sadrzajNovostUredi").insertIntoTextArea(text);
        });
        $('#paragrafNUredi').click(function(){
            let text = '<p>upisi_tekst</p>';
            $("#sadrzajNovostUredi").insertIntoTextArea(text);
        });
        $('#boldNUredi').click(function(){
            let text = '<b>upisi_tekst</b>';
            $("#sadrzajNovostUredi").insertIntoTextArea(text);
        });
        $('#kurzivNUredi').click(function(){
            let text = '<i>upisi_tekst</i>';
            $("#sadrzajNovostUredi").insertIntoTextArea(text);
        });


    });
</script>
