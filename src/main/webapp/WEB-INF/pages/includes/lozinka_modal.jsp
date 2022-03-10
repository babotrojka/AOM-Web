<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/5/2021
  Time: 8:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Lozinka Modal -->
<div class="modal fade" id="promijeniLozinkuModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="promijeniLozinkuLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen-md-down">
        <div class="modal-content">
            <form action="<c:url value="/home/promijeni_lozinku"/>" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="promijeniLozinkuLabel">Promijeni lozinku</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="text" name="id" hidden value="${sessionScope.currentUserID}">
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="old_pwd" placeholder="Stara lozinka" name="old_pwd" required>
                        <label for="old_pwd">Stara lozinka </label>
                    </div>
                    <div class="form-floating mb-1">
                        <input type="password" class="form-control" id="new_pwd" minlength="6" placeholder="Nova lozinka" name="new_pwd" required>
                        <label for="new_pwd">Nova lozinka </label>
                    </div>
                    <div class="form-floating mb-1">
                        <input type="password" class="form-control" id="rep_new_pwd" minlength="6" placeholder="Ponovljena nova lozinka" name="rep_new_pwd" required>
                        <label for="rep_new_pwd">Ponovljena nova lozinka </label>
                    </div>
                    <div class="alert alert-danger" role="alert" id="same_pwd_alert" hidden>
                        Lozinke se moraju podudarati
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Odustani</button>
                    <button type="submit" class="btn btn-success" id="dodajButton" >Promijeni</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $( document ).ready(function() {
        $('#rep_new_pwd').on('input', function () {
            document.getElementById('same_pwd_alert').hidden = $('#rep_new_pwd').val() === $('#new_pwd').val();
        });
    });
</script>

