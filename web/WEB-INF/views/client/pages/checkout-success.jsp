<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>

<div class="space10"></div>

<!-- MAIN CONTENT -->
<div class="shop-single">
    <div class="text-center fs-valign-middle container">
        <h3>Congratulation! You have successfully order</h3>
        <h3>Your order No is <a href="orders/order-history-detail/${success_orderID}.html">${success_orderID}</a></h3>
        <h3><a href="index.html">Continue to shopping</a></h3>
    </div>
</div>

<div class="clearfix space20"></div>
