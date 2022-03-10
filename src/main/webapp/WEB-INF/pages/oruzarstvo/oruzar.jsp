<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/14/2021
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="../includes/header.jsp" %>

<div class="container">
    <c:if test="${transactions.size() > 0}">
    <div class="row mb-5 bg-light">
        <table class="table table-hover table-dark table-striped">
            <thead>
            <tr>
                <th scope="col">Ime</th>
                <th scope="col">Prezime</th>
                <th scope="col">Datum povrata</th>
                <th scope="col">Pregled</th>
                <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                    <th scope="col">Uređivanje</th>
                    <th scope="col">Otkaz</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="today" class="java.util.Date"/>
            <c:forEach items="${transactions}" var="t">
                <tr class="${t.returnuntil.time lt today.time ? 'table-danger' : ''}">
                    <td>${t.borrower_id.firstname}</td>
                    <td>${t.borrower_id.lastname}</td>
                    <td>${''.concat(t.returnuntil).substring(0, 10)}</td>
                    <td>
                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="${'#modal_'.concat(t.transaction_id)}">
                            Pregledaj
                        </button>
                    </td>
                    <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                    <td>
                        <form action="<c:url value="/oruzarstvo/oruzar/uredi"/>">
                            <input type="text" name="id" value="${t.transaction_id}" hidden>
                            <button type="submit" class="btn btn-info">Uredi</button>
                        </form>
                    </td>
                    <td>
                        <form action="<c:url value="/oruzarstvo/oruzar/otkazi"/>" method="post">
                            <input type="text" name="id"  value="${t.transaction_id}" hidden>
                            <button type="submit" class="btn btn-danger">Otkaži</button>
                        </form>
                    </td>
                    </c:if>
                </tr>
                <div class="modal fade" id="${'modal_'.concat(t.transaction_id)}" tabindex="-1" aria-labelledby="${'label_'.concat(t.transaction_id)}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="${'label_'.concat(t.transaction_id)}">Sažetak posudbe</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <ul class="list-group">
                                    <c:forEach items="${t.entries}" var="e">
                                        <li class="list-group-item">
                                            <span>${''.concat(e.item_id.type_id.vendor_id.name).concat(' ').concat(e.item_id.type_id.group_id.name).concat(' ').concat(e.item_id.type_id.name).concat(' ').concat(e.item_id.name)}</span>
                                            <span class="badge bg-primary rounded-pill">${e.quantity}</span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>
    <div class="row bg-light">
        <div class="container">
        <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
        <form action="<c:url value="/oruzarstvo/oruzar/zaduzi"/>" method="post">
            <div class="row mb-2">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#zaduziModal" id="zaduzi">
                    Zaduži
                </button>
            </div>

            <div class="row" >
                <label for="alpinistDataList" class="form-label">Alpinist:</label>
                <input class="form-control mb-4" list="alpinistDataListOptions" id="alpinistDataList" name="borrower" placeholder="Kreni pisati..." required>
                <datalist id="alpinistDataListOptions">
                    <c:forEach items="${users}" var="u">
                        <option value="${u.nickname}">
                    </c:forEach>
                </datalist>
            </div>
            </c:if>
            <div class="row">
                <input class="form-control" type="text" id="traziOpremu" onkeyup="pretraziOpremu()" placeholder="Pretraži">

                <table id="oprema" class="table table-striped">
                    <thead>
                    <tr>
                    <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                        <th></th>
                    </c:if>
                        <th>Proizvođač</th>
                        <th>Grupa</th>
                        <th>Tip</th>
                        <th>Ime</th>
                        <th>Ukupno</th>
                        <th>Dostupno</th>
                        <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                            <th>Odabrano</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody id="oprema_body">
                    <c:forEach items="${items}" var="oprema">
                        <tr>
                            <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                            <td>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="oprema" id="${oprema.key.item_id}" value="${oprema.key.item_id}" ${oprema.key.quantity == 0 ? 'disabled' : ''}>
                                </div>
                            </td>
                            </c:if>
                            <td>${oprema.key.type_id.vendor_id.name}</td>
                            <td>${oprema.key.type_id.group_id.name}</td>
                            <td>${oprema.key.type_id.name}</td>
                            <td>${oprema.key.name}</td>

                            <td><div class="badge rounded-pill bg-info text-dark">${oprema.key.quantity}</div></td>
                            <td><div class="badge rounded-pill bg-secondary text-dark">${oprema.key.quantity - oprema.value}</div></td>
                            <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
                            <td>
                                <input type="number" name="${oprema.key.item_id}" class="form-control" style="width: 65px" value="${oprema.key.quantity - oprema.value > 0 ? 1 : 0}" min="${oprema.key.quantity - oprema.value > 0 ? 1 : 0}" max="${oprema.key.quantity - oprema.value}">
                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
            <div class="modal fade" id="zaduziModal" tabindex="-1" aria-labelledby="zaduziModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="zaduziModalLabel">Sažetak</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <ul class="list-group" id="listaZaduzenja">
                                <li class="list-group-item active d-flex justify-content-between align-items-center" id="alpinist">
                                    Nije odabran
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center" id="komadOpreme">
                                    Zeleno uze
                                    <span id = "pill" class="badge bg-primary rounded-pill">1</span>
                                </li>
                            </ul>

                            <label for="povratDatum" class="form-label" style="margin-top: 15px">Povrat (mm-dd-yyyy):</label>
                            <input type="date" class="form-control mb-2" id="povratDatum" name="returnUntil">
                            <div class="form-floating mb-2">
                                <input type="text" class="form-control" id="miscdata" name="miscdata" placeholder="Napomena">
                                <label for="miscdata">Napomena</label>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                            <button type="submit" class="btn btn-success">Zaduži</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
            </c:if>
        </div>
    </div>

    <c:if test="${sessionScope.currentUserPrivileges.equals('admin') || sessionScope.currentUserPrivileges.equals('equipmentofficer')}">
    <div class="row mt-5">
        <a href="<c:url value="/oruzarstvo/oruzar/uredi_oruzarstvo"/>" class="btn btn-info w-100">Uredi Oružarstvo</a>
    </div>

    <div class="row mt-3">
        <button class="btn btn-danger w-100" data-bs-toggle="modal" data-bs-target="#reset_modal">Resetiraj oružarstvo!</button>

        <div class="modal fade" id="reset_modal" tabindex="-1" aria-labelledby="reset_modal_label" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
                <form action="<c:url value="/oruzarstvo/oruzar/reset"/>" method="post" enctype="multipart/form-data">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            <h5 class="modal-title" id="reset_modal_label">Resetiraj oružarstvo!</h5>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="excel_file" class="form-label">Excel tablica oružarstva</label>
                                <input class="form-control" type="file" name="excel" id="excel_file" required>
                            </div>
                            Oprez! Navedenom akcijom se brišu sve posudbe dosada te sav sadržaj oružarstva! Oružarstvo će biti napunjeno sa podacima iz dobivene excel tablice
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
                            <button type="submit" class="btn btn-danger">Resetiraj</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </c:if>
</div>

<%@include file="../includes/footer.jsp" %>
<script>
    $( document ).ready(function() {
        $('#zaduzi').click(updateOpremaModal);
    });
</script>
