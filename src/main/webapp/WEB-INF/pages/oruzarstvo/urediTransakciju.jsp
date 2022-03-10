<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/21/2021
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="../includes/header.jsp" %>

<div class="container">
    <div class="row bg-light">
        <form action="<c:url value="/oruzarstvo/oruzar/uredi"/>" method="post">
            <input type="text" name="id"  value="${t.transaction_id}" hidden>
            <div class="mb-1">
                <label for="povratDatum" class="form-label">Povrat (mm-dd-yyyy):</label>
                <input type="date" class="form-control" id="povratDatum" name="returnUntil" value="${''.concat(t.returnuntil).substring(0, 10)}">
            </div>
            <div class="mb-3">
                <c:forEach items="${t.entries}" var="e">
                    <div class="d-flex justify-content-around">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="oprema" value="${e.item_id.item_id}" id="${e.item_id.item_id}" checked>
                            <label class="form-check-label" for="${e.item_id.item_id}">
                                ${''.concat(e.item_id.type_id.vendor_id.name).concat(' ').concat(e.item_id.type_id.group_id.name).concat(' ').concat(e.item_id.type_id.name).concat(' ').concat(e.item_id.name)}
                            </label>
                        </div>
                        <div>
                            <input type="number" class="form-control" name="${e.item_id.item_id}" value="${e.quantity}">
                        </div>
                    </div>

                </c:forEach>
            </div>
            <button type="submit" class="btn btn-success w-100">Uredi</button>
        </form>

    </div>
</div>



<%@include file="../includes/footer.jsp" %>