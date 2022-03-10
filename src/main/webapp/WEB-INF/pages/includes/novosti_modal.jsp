<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/12/2021
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Novosti Modal-->
<div class="modal fade" id="dodajNovostModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticNovostiLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-fullscreen-md-down">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticNovostiLabel">Dodaj novost</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="<c:url value="/novosti/dodaj"/>" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <c:if test="${n != null}"><input type="text" hidden name="id" id="id" value="${n.id}"></c:if>
                    <div class="form-floating mb-1">
                        <input type="text" class="form-control" id="naslovNovost" placeholder="Naslov" name="naslov" <c:if test="${n != null}"> value="${n.title}" </c:if>  required>
                        <label for="naslovNovost">Naslov </label>
                    </div>
                    <div class="mb-1">
                        <textarea class="form-control" id="sadrzajNovost" rows="10" name="sadrzaj" <c:if test="${n != null}"> value="${n.body}" </c:if> required placeholder="Tekst..."></textarea>
                    </div>
                    <div class="d-flex mb-3">
                        <button type="button" id="paragrafN" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci paragraf">Paragraf</button>
                        <button type="button" id="boldN" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Bold">Bold</button>
                        <button type="button" id="kurzivN" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Kurziv">Kurziv</button>
                        <button type="button" id="linkN" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci link">Link</button>
                        <button type="button" id="slikaN" class="btn btn-outline-secondary" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci sliku">Slika</button>
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

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                    <button type="submit" class="btn btn-primary">Dodaj</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $( document ).ready(function() {
        $('#linkN').click(function(){
            let text = '<a href="upisi_link">upisi_tekst</a>';
            $("#sadrzajNovost").insertIntoTextArea(text);
        });
        $('#slikaN').click(function(){
            let text = '<img src="link_za_sliku" alt="ime_slike" class="img-thumbnail">';
            $("#sadrzajNovost").insertIntoTextArea(text);
        });
        $('#paragrafN').click(function(){
            let text = '<p>upisi_tekst</p>';
            $("#sadrzajNovost").insertIntoTextArea(text);
        });
        $('#boldN').click(function(){
            let text = '<b>upisi_tekst</b>';
            $("#sadrzajNovost").insertIntoTextArea(text);
        });
        $('#kurzivN').click(function(){
            let text = '<i>upisi_tekst</i>';
            $("#sadrzajNovost").insertIntoTextArea(text);
        });
    });
</script>
