<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 9/10/2021
  Time: 7:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="../includes/header.jsp" %>

<div class="container">
    <div class="row bg-light mb-3">
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_dodajNovogKorisnika">Dodaj novog korisnika</button>
    </div>
    <!-- Dodaj korisnika Modal -->
    <div class="modal fade" id="modal_dodajNovogKorisnika" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="label_dodajNovogKorisnika" aria-hidden="true">
        <div class="modal-dialog modal-fullscreen-md-down">
            <div class="modal-content">
                <form action="<c:url value="/admin/dodajKorisnika"/>" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="label_dodajNovogKorisnika">Dodaj novog korisnika</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-floating mb-1">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                            <label for="email">Email</label>
                        </div>
                        <div class="form-floating mb-2">
                            <input type="password" class="form-control" id="lozinka" name="lozinka" placeholder="Lozinka" required>
                            <label for="lozinka">Lozinka</label>
                        </div>
                        <div class="form-floating mb-1">
                            <input type="text" class="form-control" id="ime" name="ime" placeholder="Ime" required>
                            <label for="ime">Ime</label>
                        </div>
                        <div class="form-floating mb-1">
                            <input type="text" class="form-control" id="prezime" name="prezime" placeholder="Prezime" required>
                            <label for="prezime">Prezime</label>
                        </div>
                        <div class="form-floating mb-1">
                            <input type="text" class="form-control" id="nadimak" name="nadimak" placeholder="Nadimak">
                            <label for="nadimak">Nadimak</label>
                        </div>
                        <div class="form-check mb-1">
                            <input type="checkbox" class="form-check-input" name="zakljucan" id="zakljucan">
                            <label class="form-check-label" for="zakljucan">Zaključan</label>
                        </div>
                        <label for="group">Grupa: </label>
                        <select class="form-select mb-3" id="group" name="group" aria-label=".form-select example" required>
                            <c:forEach items="${ugroups}" var="ugroup">
                                <option value="${ugroup.name}" <c:if test="${ugroup.name.equals('member')}">selected</c:if>>${ugroup.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                        <button type="submit" class="btn btn-success">Dodaj</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row mb-2">
        <input class="form-control" type="text" id="traziKorisnika" onkeyup="pretrazi()" placeholder="Pretraži">
    </div>

    <div class="row bg-light">
        <table class="table table-warning table-striped table-hover" id="tableKorisnici">
            <thead class="table-success">
                <tr>
                    <th>Email</th>
                    <th>Ime</th>
                    <th>Prezime</th>
                    <th>Nadimak</th>
                    <th>Uloga</th>
                    <th>Zaključan</th>
                    <th>Uredi</th>
                    <th>Izbriši</th>
                </tr>
            </thead>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.email}</td>
                    <td>${user.firstname}</td>
                    <td>${user.lastname}</td>
                    <td>${user.nickname}</td>
                    <td>${user.privileges}</td>
                    <td>${user.locked}</td>
                    <td>
                        <button data-bs-toggle="modal" data-bs-target="${'#modal_'.concat(user.id)}">
                            <img src="<c:url value="/static/images/edit_icon.png"/>" class="me-1" width="20" height="25" alt="edit">
                        </button>
                    </td>
                    <td>
                        <button data-bs-toggle="modal" data-bs-target="${'#deleteModal_'.concat(user.id)}">
                            <img src="<c:url value="/static/images/delete_icon.png"/>" width="20" height="25" alt="delete">
                        </button>
                    </td>
                </tr>
                <!-- Uredi korisnika Modal -->
                <div class="modal fade" id="${'modal_'.concat(user.id)}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="${'label_'.concat(user.id)}" aria-hidden="true">
                    <div class="modal-dialog modal-fullscreen-md-down">
                        <div class="modal-content">
                            <form action="<c:url value="/admin/urediKorisnika"/>" method="post">
                                <input type="text" name="id" value="${user.id}" hidden>
                                <div class="modal-header">
                                    <h5 class="modal-title" id="${'label_'.concat(user.id)}">${user.firstname.concat(' ').concat(user.lastname)}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="form-floating mb-1">
                                        <input type="email" class="form-control" id="${'email_'.concat(user.id)}" name="email" placeholder="Email" value="${user.email}" required>
                                        <label for="${'email_'.concat(user.id)}">Email</label>
                                    </div>
                                    <div class="form-floating mb-2">
                                        <input type="password" class="form-control" id="${'lozinka_'.concat(user.id)}" name="lozinka" placeholder="Lozinka">
                                        <label for="${'lozinka_'.concat(user.id)}">Upisati novu lozinku ili ostaviti prazno za nepromijenjenu</label>
                                    </div>
                                    <div class="form-floating mb-1">
                                        <input type="text" class="form-control" id="${'ime_'.concat(user.id)}" name="ime" value="${user.firstname}" placeholder="Ime" required>
                                        <label for="${'ime_'.concat(user.id)}">Ime</label>
                                    </div>
                                    <div class="form-floating mb-1">
                                        <input type="text" class="form-control" id="${'prezime_'.concat(user.id)}" name="prezime" value="${user.lastname}" placeholder="Prezime" required>
                                        <label for="${'prezime_'.concat(user.id)}">Prezime</label>
                                    </div>
                                    <div class="form-floating mb-1">
                                        <input type="text" class="form-control" id="${'nadimak_'.concat(user.id)}" name="nadimak" value="${user.nickname}" placeholder="Nadimak">
                                        <label for="${'nadimak_'.concat(user.id)}">Nadimak</label>
                                    </div>
                                    <div class="form-check mb-1">
                                        <input type="checkbox" class="form-check-input" name="zakljucan" id="${'zakljucan_'.concat(user.id)}">
                                        <label class="form-check-label" for="${'zakljucan_'.concat(user.id)}">Zaključan</label>
                                    </div>
                                    <label for="group">Grupa: </label>
                                    <select class="form-select mb-3" id="${'group_'.concat(user.id)}" name="group" aria-label=".form-select example" required>
                                        <c:forEach items="${ugroups}" var="ugroup">
                                            <option value="${ugroup.name}" <c:if test="${ugroup.name.equals(user.privileges)}">selected</c:if>>${ugroup.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                                    <button type="submit" class="btn btn-success">Uredi</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- Izbrisi korisnika Modal -->
                <div class="modal fade" id="${'deleteModal_'.concat(user.id)}" tabindex="-1" aria-labelledby="${'deleteModalLabel_'.concat(user.id)}" aria-hidden="true">
                    <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
                        <form action="<c:url value="/admin/izbrisiKorisnika"/>" method="post">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    <h5 class="modal-title" id="${'deleteModalLabel_'.concat(user.id)}">Izbriši člana ${user.firstname.concat(' ').concat(user.lastname)}</h5>
                                </div>
                                <div class="modal-body">
                                    <input type="text" name="id" value="${user.id}" hidden>
                                    Jeste li sigurni da zauvijek želite izbrisati člana ${user.firstname.concat(' ').concat(user.lastname)}?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-danger">Izbriši</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </table>
    </div>


</div>

<%@include file="../includes/footer.jsp" %>
