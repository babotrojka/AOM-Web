<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/24/2021
  Time: 4:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Izvjestaj Modal -->
<div class="modal fade" id="dodajIzvjestajModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticIzvjestajiLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-fullscreen-md-down">
        <div class="modal-content">
            <form action="<c:url value="/izvjestaji/dodaj"/>" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticIzvjestajiLabel">Dodaj izvještaj</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="text" hidden name="id" id="id">
                    <div class="form-floating mb-1">
                        <input type="text" class="form-control" id="naslov" placeholder="Naslov" name="naslov" required>
                        <label for="naslov">Naslov </label>
                    </div>
                    <div class="mb-1">
                        <textarea class="form-control" id="sadrzaj" rows="20" required name="sadrzaj" placeholder="Tekst..."></textarea>
                    </div>
                    <div class="d-flex mb-3">
                        <button type="button" id="paragraf" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci paragraf">Paragraf</button>
                        <button type="button" id="bold" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Bold">Bold</button>
                        <button type="button" id="kurziv" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Kurziv">Kurziv</button>
                        <button type="button" id="link" class="btn btn-outline-secondary me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci link">Link</button>
                        <button type="button" id="slika" class="btn btn-outline-secondary" data-bs-toggle="tooltip" data-bs-placement="top" title="Ubaci sliku">Slika</button>
                    </div>
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" id="kljucneRijeci" name="kljucneRijeci" placeholder="Ključne riječi" required>
                        <label for="kljucneRijeci">Ključne riječi </label>
                    </div>
                    <div class="mb-3">
                        <label for="datum">Datum pisanja: </label>
                        <input type="date" id="datum" name="datum">
                    </div>

                    <label for="autorDatalist" class="form-label">Autor</label>
                    <input class="form-control" list="autorOptions" name="autor" id="autorDatalist" value="${sessionScope.currentUserNick}" onkeyup="check()" required placeholder="Pretraži po nadimku...">
                    <datalist id="autorOptions">
                        <c:forEach items="${korisnici}" var="korisnik">
                            <option value="${korisnik.nickname}" >
                        </c:forEach>
                    </datalist>
                    <div class="alert alert-danger text-end mb-1" id="wrongUser" hidden role="alert">
                        Molim odaberi korisnika iz padajuće liste!
                    </div>

                    <label for="format">Format: </label>
                    <select class="form-select mb-1" id="format" name="format" aria-label=".form-select example" required>
                        <c:forEach items="${formati}" var="format">
                            <option value="${format.id}" <c:if test="${format.id == 2}">selected</c:if>>${format.format}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                    <button type="submit" class="btn btn-success" id="dodajButton" >Dodaj</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $( document ).ready(function() {
        $('#link').click(function(){
            let text = '<a target="_blank" href="upisi_link">upisi_tekst</a>';
            $("#sadrzaj").insertIntoTextArea(text);
        });
        $('#slika').click(function(){
            let text = '<img src="link_za_sliku" alt="ime_slike" class="img-thumbnail">';
            $("#sadrzaj").insertIntoTextArea(text);
        });
        $('#paragraf').click(function(){
            let text = '<p>upisi_tekst</p>';
            $("#sadrzaj").insertIntoTextArea(text);
        });
        $('#bold').click(function(){
            let text = '<b>upisi_tekst</b>';
            $("#sadrzaj").insertIntoTextArea(text);
        });
        $('#kurziv').click(function(){
            let text = '<i>upisi_tekst</i>';
            $("#sadrzaj").insertIntoTextArea(text);
        });

    });
</script>

