<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 8/18/2021
  Time: 3:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="includes/header.jsp" %>
<div class="container">
    <div class="row bg-secondary text-white bg-opacity-90 rounded-pill pt-1 mb-2">
        <h5 class="text-center">Alpinistička škola</h5>
    </div>
    <div class="row bg-light p-2 rounded">
        <h4 class=""><b>Ljetna alpinistička škola</b></h4>

        <ul class="text-muted px-4">
            <li>Ljetna alpinistička škola se održava u proljeće, s početkom u ožujku</li>
            <li>Škola je u trajanju od 2 mjeseca</li>
            <li>Sastoji se od teorijskog i praktičnog dijela</li>
            <li>Teorijski dio sastoji se od predavanja ponedjeljkom u prostorima društva te čvorologije poslije predavanja</li>
            <li>Praktični dio obuhvaća vikend izlete i ponavaljanja tehnika na Tunelu ili u prostorima stare Žičare Sljeme</li>
            <li>Po planu se odrađuje 7-8 vikend izleta</li>
            <li>Na izletima se poučavaju alpinističke tehnike koje se kroz samo penjanje usavršavaju</li>
        </ul>
        <p class="text-decoration-underline fw-bold">Koja je razlika između alpinističke i sportsko penjačke škole?</p>
        <p class="text-muted">Alpinizam i sportsko penjanje imaju dodirnih točaka, ali su u pravilu različite aktivnosti. Fokus
            alpinista je penjanje neosiguranih smjerova u suhoj stijeni te zimskih smjerova. Alpinistička škola obuhvaća
            osnove sportskog penjanja, no glavni fokus je u postavljanju vlastitih međuosiguranja te penjanju na ista.
        </p>
        <p class="text-decoration-underline fw-bold">Koja penjačka oprema je potrebna?</p>
        <p class="text-muted">Nikakva. Svu penjačku opremu (kacige, pojaseve, užeta...) osigurava odsjek.
            Preporuča se imati vlastitu planinarsku opremu (ruksak, tenisice, vreću za spavanje, karimat ili podlogu za ležanje...)
            te vlastite penjačice (cipele za penjanje).
        </p>
        <p class="text-decoration-underline fw-bold">Je li mi potrebno kakvo prijašnje iskustvo u penjanju?</p>
        <p class="text-muted">Nije potrebno prijašnje iskustvo, ali može pomoći pri savladavanju tehnika na način da usmjeri fokus
            pojedinca na tehnike, a ne na samo kretanje u stijeni.
        </p>
        <p class="text-decoration-underline fw-bold">Koja je cijena škole?</p>
        <p class="text-muted">Cijena škole je 2500kn za zaposlene te 2000kn za nezaposlene i studente.
        </p>

        <p class="text-decoration-underline fw-bold">Kad se mogu prijaviti?</p>
        <p class="text-muted">Prijave se otvaraju mjesec-dva prije početka škole. Pratite našu web i Instagram stranicu!</p>
    </div>
    <div class="row bg-light ">
        <div class="col-6 m-auto">
            <div id="carouselSkolica" class="carousel slide pb-3" data-bs-ride="carousel" ž>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="<c:url value="${mainSlika}"/>" class="d-block img-fluid" alt="main">
                    </div>
                    <c:forEach items="${skolaSlike}" var="slika">
                        <div class="carousel-item">
                            <img src="<c:url value="${slika}"/>" class="d-block img-fluid" alt="...">
                        </div>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselSkolica" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselSkolica" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
    </div>
    <div class="row mt-2 bg-light p-2 rounded">
        <h4 class=""><b>Zimska alpinistička škola</b></h4>

        <ul class="text-muted px-4">
            <li>Zimska alpinistička škola namijenjena je za članove odsjeka koji su završili ljetnu školu i žele
            proširiti svoje znanje o penjanju zimskih smjerova</li>
            <li>Uobičajeno trajanje je između mjesec dana i dva mjeseca</li>
            <li>Vikend izleti su ovisni o uvjetima</li>
        </ul>
    </div>


</div>

<%@include file="includes/footer.jsp" %>