<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 12/16/2021
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Login Modal -->
<div class="modal fade d-lg-none" id="loginModal" tabindex="-1" aria-labelledby="loginLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm modal-fullscreen-sm-down">
        <div class="modal-content">
            <form action="<c:url value="/home/login"/>" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginLabel">Izbriši novost</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating mb-2">
                        <input type="text" class="form-control" name="username" id="username" placeholder="Korisničko ime" required>
                        <label for="username">Email ili nadimak</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" name="lozinka" id="lozinka" placeholder="Lozinka">
                        <label for="lozinka">Lozinka</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
                    <button type="submit" class="btn btn-primary">Prijavi se</button>
                </div>
            </form>
        </div>
    </div>
</div>
