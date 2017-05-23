<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="spring.entity.Orders"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Orders</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Details Order NO.${order.ordersID}</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <table class="table table-striped table-bordered table-hover">
                    <tr>
                        <th class="text-center" style="font-weight: 700;">Order Date</th>
                        <td class="text-center"><fmt:formatDate value="${order.ordersDate}" pattern="dd-MM-yyyy"/></td>
                        <td class="text-center"><fmt:formatDate value="${order.ordersDate}" pattern="hh:mm:ss"/></td>
                    </tr>
                    <tr>
                        <th class="text-center" style="font-weight: 700;">Ship to</th>
                        <td colspan="2" style="padding-left: 20px;">${order.receiverFirstName} ${order.receiverLastName}</td>
                    </tr>
                    <tr>
                        <th class="text-center" style="font-weight: 700;">Address</th>
                        <td colspan="2" style="padding-left: 20px;">${order.deliveryAddress}</td>
                    </tr>
                    <tr>
                        <th class="text-center" style="font-weight: 700;">Phone</th>
                        <td colspan="2" style="padding-left: 20px;">${order.phoneNumber}</td>
                    </tr>
                    <tr>
                        <th class="text-center" style="width: 200px;">Discount Code</th>
                        <td colspan="2" style="padding-left: 20px;">
                            <c:choose>
                                <c:when test="${order.voucher.voucherID == null}">
                                    --
                                </c:when>
                                <c:otherwise>
                                    ${order.voucher.voucherID}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <th class="text-center" style="width: 200px;">Order Note</th>
                        <td colspan="2" style="padding-left: 20px;">
                            <c:choose>
                                <c:when test="${order.note == null}">
                                    --
                                </c:when>
                                <c:otherwise>
                                    ${order.note}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-lg-12">
                <table width="100%" class="table table-striped table-bordered table-hover" id="tableOrderDetails">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">Product ID</th>
                            <th class="text-center fs-valign-middle">Product Name</th>
                            <th class="text-center fs-valign-middle">Color</th>
                            <th class="text-center fs-valign-middle">Size</th>
                            <th class="text-center fs-valign-middle">Quantity</th>
                            <th class="text-center fs-valign-middle">Price for one</th>
                            <th class="text-center fs-valign-middle">Product discount</th>
                            <th class="text-center fs-valign-middle">Total</th>
                            <th class="text-center fs-valign-middle">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderdetailList}" var="orderdetail">
                            <tr class="odd gradeX">
                                <td class="text-center fs-valign-middle">${orderdetail.getProduct().productID}</td>
                                <td class="text-center fs-valign-middle">${orderdetail.getProduct().getProductName()}</td>
                                <td class="text-center fs-valign-middle">${orderdetail.getSize().getColor().getColor()}</td>
                                <td class="text-center fs-valign-middle">${orderdetail.getSize().productSize}</td>
                                <td class="text-center fs-valign-middle">${orderdetail.quantity}</td>
                                <td class="text-center fs-valign-middle">$${orderdetail.price}</td>
                                <td class="text-center fs-valign-middle">-$${orderdetail.product.getProductDiscountPrice()}</td>
                                <td class="text-center fs-valign-middle">$${orderdetail.getTotalPrice()}</td>
                                <td class="text-center fs-valign-middle">
                                    <c:choose>
                                        <c:when test="${orderdetail.status == 1}">
                                            <select name="status-orderDetail" style="color: red;"
                                                    id="id-status-orderdetail" 
                                                    class="form-control input-sm" 
                                                    onchange="window.location = 'admin/orders/confirmstatusOrderDetail/${orderdetail.ordersDetailID}/' + this.value + '.html';">
                                                <option value="0">Not Change</option>
                                                <option value="1" <c:out value="selected"/>>Canceled</option>
                                                <option value="2">New</option>
                                            </select>
                                        </c:when>
                                        <c:when test="${orderdetail.status == 2}">
                                            <select name="status-orderDetail" style="color: #00cc66;"
                                                    id="id-status-orderdetail"
                                                    class="form-control input-sm" 
                                                    onchange="window.location = 'admin/orders/confirmstatusOrderDetail/${orderdetail.ordersDetailID}/' + this.value + '.html';">
                                                <option value="0">Not Change</option>
                                                <option value="1">Canceled</option>
                                                <option value="2" <c:out value="selected"/>>New</option>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="status-orderDetail" 
                                                    id="id-status-orderdetail" 
                                                    class="form-control input-sm" 
                                                    onchange="window.location = 'admin/orders/confirmstatusOrderDetail/${orderdetail.ordersDetailID}/' + this.value + '.html';">
                                                <option value="0" <c:out value="selected"/>>Not Change</option>
                                                <option value="1">Canceled</option>
                                                <option value="2">New</option>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <c:if test="${order.status == 2}">
                            <tr>
                                <td colspan="9" align="center"><a style="width: 100%;" href="admin/orders/ordersdetailadd/${order.ordersID}.html" type="button" class="btn btn-primary"><b>ADD</b></a></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td colspan="7" align="right"><b>Order Discount</b></td>
                            <td class="text-center fs-valign-middle">
                                <c:set value="${order}" var="or"/>
                                <%
                                    Orders orders = (Orders) pageContext.getAttribute("or");
                                    DecimalFormat df = new DecimalFormat("#.#");
                                    df.setRoundingMode(RoundingMode.FLOOR);
                                %>
                                -$<%= df.format(orders.getOrderDiscountPrice())%>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="7" align="right"><b>Order Total</b></td>
                            <td class="text-center fs-valign-middle">$${order.getPaymentTotal()}</td>
                            <td></td>
                        </tr>
                    </tfoot>
                </table>
                <!-- /.table-responsive -->
            </div>

            <div class="col-lg-12" align="right">
                <button onclick="window.location = 'admin/orders/invoice/${order.ordersID}.html';" class="btn btn-primary">INVOICE</button>
                <button onclick="window.location = 'admin/orders/list.html'" class="btn btn-primary">BACK TO ORDER LIST</button>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->