<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/3/2021
  Time: 11:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@include file="../includes/header.jsp" %>

<div class="container">
    <form class="row bg-light p-2 rounded" action="<c:url value="/oruzarstvo/oruzar/uredi_opremu" />" method="post">
        <input type="text" name="id" value="${i.item_id}" hidden>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="vendor" name="vendor" placeholder="Proizvođač" value="${i.type_id.vendor_id.name}" disabled>
            <label for="vendor">Proizvođač</label>
        </div>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="group" name="group" placeholder="Grupa" value="${i.type_id.group_id.name}" disabled>
            <label for="group">Grupa</label>
        </div>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="type" name="type" placeholder="Tip" value="${i.type_id.name}" disabled>
            <label for="type">Tip</label>
        </div>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="name" name="name" placeholder="Ime" value="${i.name}" required>
            <label for="name">Ime</label>
        </div>
        <div class="form-floating mb-2">
            <input type="number" class="form-control" id="qty" name="quantity" placeholder="Količina" value="${i.quantity}" required>
            <label for="qty">Količina</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="desc" name="description" placeholder="Opis" value="${i.description}">
            <label for="desc">Opis</label>
        </div>
        <button type="submit" class="btn btn-success w-100">Uredi</button>
    </form>
</div>

<%@include file="../includes/footer.jsp" %>

<script>
    $( document ).ready(function() {
        document.getElementById('type').onchange = enableTextInput('-1', 'newType');
    });

</script>
