<%-- 
    Document   : order-history
    Created on : Feb 28, 2017, 5:05:55 PM
    Author     : vinh.an
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true" />

<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <!-- HTML -->
                <div id="account-id">
                    <c:set value="${orderStatus}" var="orderStatus"/>
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Your Order History</h4>
                    <div class="order-history">
                        <table class="table table-striped table-bordered" width="100%">
                            <tr>
                                <th colspan="5" style="font-weight: 700;" class="text-center fs-valign-middle">SELECT ORDER STATUS OPTION</th>
                            </tr>
                            <tr>
                                <%
                                    int orderStatus = Integer.parseInt(pageContext.getAttribute("orderStatus").toString());
                                    String btnDefault = "class=\"btn btn-default\"";
                                    String btnPrimary = "class=\"btn btn-primary\"";
                                    String classOrder1 = btnDefault;
                                    String classOrder2 = btnDefault;
                                    String classOrder3 = btnDefault;
                                    String classOrder4 = btnDefault;
                                    String classOrder5 = btnDefault;
                                    if (orderStatus == 4) {
                                        classOrder1 = btnPrimary;
                                    } else if (orderStatus == 2) {
                                        classOrder2 = btnPrimary;
                                    } else if (orderStatus == 3) {
                                        classOrder3 = btnPrimary;
                                    } else if (orderStatus == 1) {
                                        classOrder4 = btnPrimary;
                                    } else if (orderStatus == 0) {
                                        classOrder5 = btnPrimary;
                                    }
                                %>
                                <!--class="btn btn-default"-->
                                <td class="text-center fs-valign-middle"><a href="orders/order-history.html" <%= classOrder1%>>ALL ORDER</a></td>
                                <td class="text-center fs-valign-middle"><a href="orders/order-history/2.html" <%= classOrder2%>>PENDING</a></td>
                                <td class="text-center fs-valign-middle"><a href="orders/order-history/3.html" <%= classOrder3%>>CONFIRMED</a></td>
                                <td class="text-center fs-valign-middle"><a href="orders/order-history/1.html" <%= classOrder4%>>COMPLETED</a></td>
                                <td class="text-center fs-valign-middle"><a href="orders/order-history/0.html" <%= classOrder5%>>CANCELED</a></td>
                            </tr>
                        </table>
                    </div>
                    <div style="padding: 10px;"></div>
                    <div class="order-history">
                        <table class="cart-table table-hover" id="table-order-history">
                            <thead>
                                <tr>
                                    <th style="font-weight: 700;">Order ID</th>
                                    <th style="font-weight: 700;">Product(s)</th>
                                    <th style="font-weight: 700;">Total</th>
                                    <th style="font-weight: 700;">Order Date</th>
                                    <th style="font-weight: 700;">Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${orderList.size() == 0}">
                                        <tr>
                                            <td colspan="5">
                                                <span style="font-weight: 700;">YOU HAVE NO ORDER</span><br/>
                                                <!--<a href="index.html" class="btn btn-success">SHOPPING MORE</a>-->
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${orderList}" var="order">
                                            <tr>
                                                <td align="center"><a href="orders/order-history-detail/${order.ordersID}.html">Order No.${order.ordersID}</a></td>
                                                <td align="center">
                                                    <c:choose>
                                                        <c:when test="${order.getOrderDetailForOrderHistoryPage() != null}">
                                                            ${order.getOrderDetailForOrderHistoryPage().product.productName}
                                                            &nbsp - &nbsp ${order.getOrderDetailForOrderHistoryPage().size.color.color}
                                                            &nbsp - &nbsp Size: &nbsp ${order.getOrderDetailForOrderHistoryPage().size.productSize}
                                                        </c:when>
                                                        <c:otherwise><span style="font-weight: 700px;">Your order is empty</span></c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td style="width: 100px;">
                                                    <div class="item-price">$${order.getPaymentTotal()}</div>
                                                </td>
                                                <td align="center">
                                                    <fmt:formatDate value="${order.ordersDate}" pattern="dd-MM-yyyy hh:mm:ss"/>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${order.status == 1}">Completed</c:when>
                                                        <c:when test="${order.status == 2}">Pending</c:when>
                                                        <c:when test="${order.status == 3}">Confirmed</c:when>
                                                        <c:otherwise>Canceled</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr> 
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>

                        <div class="table-btn">
                            <a href="user/myaccount.html" class="btn-black">Back To Account</a>
                        </div>
                    </div>                          
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clearfix space20"></div>