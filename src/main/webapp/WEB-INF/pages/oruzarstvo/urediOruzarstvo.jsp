<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/3/2021
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="../includes/header.jsp" %>

<div class="container">
    <div class="row bg-light mb-3">
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_dodajOpremu">Dodaj novi komad opreme</button>
    </div>
    <!-- Dodaj opremu Modal -->
    <div class="modal fade" id="modal_dodajOpremu" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="label_dodajOpremu" aria-hidden="true">
        <div class="modal-dialog modal-fullscreen-md-down">
            <div class="modal-content">
                <form action="<c:url value="/oruzarstvo/oruzar/dodaj_opremu"/>" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="label_dodajOpremu">Dodaj opremu</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <label for="vendor">Proizvođač: </label>
                        <select class="form-select mb-2" id="vendor" name="vendor" aria-label=".form-select example" required>
                            <c:forEach items="${vendors}" var="v">
                                <option value="${v.vendor_id}">${v.name}</option>
                            </c:forEach>
                        </select>
                        <label for="group">Grupa: </label>
                        <select class="form-select mb-2" id="group" name="group" aria-label=".form-select example" required>
                            <c:forEach items="${groups}" var="g">
                                <option value="${g.group_id}">${g.name}</option>
                            </c:forEach>
                        </select>
                        <label for="type">Tip opreme: </label>
                        <select class="form-select mb-1" id="type" name="type" aria-label=".form-select example" required>
                            <c:forEach items="${types}" var="t">
                                <option value="${t}">${t}</option>
                            </c:forEach>
                            <option value="none">Ništa od ponuđenog</option>
                        </select>
                        <div class="form-floating mb-2">
                            <input type="text" class="form-control" id="newType" name="newType" placeholder="Tip" disabled>
                            <label for="newType">Tip</label>
                        </div>
                        <div class="form-floating mb-2">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Ime" value="${i.name}" required>
                            <label for="name">Ime</label>
                        </div>
                        <div class="form-floating mb-2">
                            <input type="number" class="form-control" id="qty" name="quantity" placeholder="Količina" required>
                            <label for="qty">Količina</label>
                        </div>
                        <div class="form-floating mb-2">
                            <input type="text" class="form-control" id="desc" name="description" placeholder="Opis" >
                            <label for="desc">Opis</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                        <button type="submit" class="btn btn-success">Dodaj</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="row p-2 rounded bg-light">
        <input class="form-control" type="text" id="traziOpremu" onkeyup="pretraziOpremu()" placeholder="Pretraži">

        <table id="oprema" class="table table-striped">
            <thead>
            <tr>
                <th>Proizvođač</th>
                <th>Grupa</th>
                <th>Tip</th>
                <th>Ime</th>
                <th>Ukupno</th>
                <th>Dostupno</th>
                <th>Uredi</th>
                <th>Izbriši</th>
            </tr>
            </thead>
            <tbody id="oprema_body">
            <c:forEach items="${items}" var="oprema">
                <tr>
                    <td>${oprema.key.type_id.vendor_id.name}</td>
                    <td>${oprema.key.type_id.group_id.name}</td>
                    <td>${oprema.key.type_id.name}</td>
                    <td>${oprema.key.name}</td>
                    <td><div class="badge rounded-pill bg-info text-dark">${oprema.key.quantity}</div></td>
                    <td><div class="badge rounded-pill bg-secondary text-dark">${oprema.key.quantity - oprema.value}</div></td>
                    <td>
                        <form action="<c:url value="/oruzarstvo/oruzar/uredi_opremu"/>" method="get">
                            <input type="text" name="id" value="${oprema.key.item_id}" hidden>
                            <button type="submit">
                                <img src="<c:url value="/static/images/edit_icon.png"/>" class="me-1" width="20" height="25" alt="edit">
                            </button>
                        </form>
                    </td>
                    <td>
                        <button data-bs-toggle="modal" class="deleteBtn" id="${''.concat(oprema.key.item_id).concat('_').concat(oprema.key.name).concat('_deleteButton')}" data-bs-target="#izbrisiOpremuModal" type="button">
                            <img src="<c:url value="/static/images/delete_icon.png"/>" width="20" height="25" alt="delete">
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- Delete Modal -->
        <div class="modal fade" id="izbrisiOpremuModal" tabindex="-1" aria-labelledby="izbrisiOpremuLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
                <div class="modal-content">
                    <form action="<c:url value="/oruzarstvo/oruzar/izbrisi_opremu"/>" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="izbrisiOpremuLabel">Izbriši komad opreme</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="text" id="delete_id" name="id" hidden>
                            Jesi li siguran da zauvijek želiš izbrisati komad <span id="delete_text"></span>?
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

</div>

<%@include file="../includes/footer.jsp" %>

<script>
    $( document ).ready(function() {
        let modalBtns = document.getElementsByClassName('deleteBtn');
        for(let btn of modalBtns)
            btn.onclick = popuniModal;

        document.getElementById('type').onchange = enableTextInput;
    });

</script>
