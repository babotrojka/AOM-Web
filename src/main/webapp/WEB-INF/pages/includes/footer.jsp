<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/17/2021
  Time: 8:38 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mb-5"></div>
<div class="bg-light mt-auto">
    <div class="container">
        <footer id="footer" class="row row-cols-3 py-1 border-top bg-light" >
            <div class="col">
                <h5>Korisni linkovi</h5>
                <ul class="nav flex-column">
                    <li class="nav-item mb-1"><a href="https://www.hps.hr/" class="nav-link p-0 text-muted text-decoration-underline">Hrvatski planinarski savez</a></li>
                    <li class="nav-item mb-1"><a href="https://www.hgss.hr/" class="nav-link p-0 text-muted text-decoration-underline">HGSS</a></li>
                    <li class="nav-item mb-1"><a href="https://www.hps.hr/alpinizam/" class="nav-link p-0 text-muted text-decoration-underline" >Komisija za alpinizam</a></li>
                </ul>
            </div>

            <div class="col">
                <h5>Navigacija</h5>
                <ul class="nav flex-column">
                    <li class="nav-item mb-1"><a href="<c:url value="/"/>" class="nav-link p-0 text-muted text-decoration-underline">Novosti</a></li>
                    <li class="nav-item mb-1"><a href="<c:url value="/izvjestaji/" />" class="nav-link p-0 text-muted text-decoration-underline">Izvještaji</a></li>
                    <li class="nav-item mb-1"><a href="<c:url value="/skola/" />" class="nav-link p-0 text-muted text-decoration-underline">Alpinistička škola</a></li>
                </ul>
            </div>


            <div class="col mt-auto">
                <a href="<c:url value="/"/>" class="mb-3 link-dark text-decoration-none">
                    <img src="<c:url value="/static/images/logo_no_b.png" />" class="bi mx-auto" width="40" height="32"/>
                </a>
                <p class="text-muted">©AO Zagreb Matica 2021</p>
            </div>


        </footer>
    </div>
</div>
</body>
</html>
