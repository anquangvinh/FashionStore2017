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
                    <span style="font-size: 0.9em">List</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <c:set value="${orderStatus}" var="orderStatus"/>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <tr>
                        <th colspan="5" class="text-center fs-valign-middle">SELECT ORDER STATUS OPTION</th>
                    </tr>
                    <tr>
                        <%
                            int orderStatus = Integer.parseInt(pageContext.getAttribute("orderStatus").toString());
                            String btnPrimary = "class=\"btn btn-primary\"";
                            String btnWarning = "class=\"btn btn-warning\"";
                            String classOrder1 = btnPrimary;
                            String classOrder2 = btnPrimary;
                            String classOrder3 = btnPrimary;
                            String classOrder4 = btnPrimary;
                            String classOrder5 = btnPrimary;
                            if (orderStatus == 4) {
                                classOrder1 = btnWarning;
                            } else if (orderStatus == 2) {
                                classOrder2 = btnWarning;
                            } else if (orderStatus == 3) {
                                classOrder3 = btnWarning;
                            } else if (orderStatus == 1) {
                                classOrder4 = btnWarning;
                            } else if (orderStatus == 0) {
                                classOrder5 = btnWarning;
                            }
                        %>
                        <td class="text-center fs-valign-middle"><a href="admin/orders/list.html" <%= classOrder1%>>ALL ORDER</a></td>
                        <td class="text-center fs-valign-middle"><a href="admin/orders/list/2.html" <%= classOrder2%>>PENDING</a></td>
                        <td class="text-center fs-valign-middle"><a href="admin/orders/list/3.html" <%= classOrder3%>>CONFIRMED</a></td>
                        <td class="text-center fs-valign-middle"><a href="admin/orders/list/1.html" <%= classOrder4%>>COMPLETED</a></td>
                        <td class="text-center fs-valign-middle"><a href="admin/orders/list/0.html" <%= classOrder5%>>CANCELED</a></td>
                    </tr>
                </table>
            </div>
            <div class="col-lg-12">
                <!--id="dataTables-example"-->
                <table width="100%" class="table table-striped table-bordered table-hover" id="tableOrder">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">Order ID</th>
                            <th class="text-center fs-valign-middle">First Name</th>
                            <th class="text-center fs-valign-middle">Phone</th>
                            <th class="text-center fs-valign-middle">Address</th>
                            <th class="text-center fs-valign-middle">Date</th>
                            <th class="text-center fs-valign-middle">Status</th>
                            <!--<th>Action</th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderList}" var="order">
                            <tr class="odd gradeX">
                                <td class="text-center fs-valign-middle">
                                    <a href="admin/orders/orderlistdetail/${order.ordersID}.html" style="font-size: 20px; font-weight: 700;">${order.ordersID}</a>
                                </td>
                                <td class="text-center fs-valign-middle">${order.receiverFirstName}</td>
                                <td class="text-center fs-valign-middle">${order.phoneNumber}</td>
                                <td class="text-center fs-valign-middle">${order.deliveryAddress}</td>
                                <td class="text-center fs-valign-middle">
                                    <fmt:formatDate value="${order.ordersDate}" pattern="dd-MM-yyyy hh:mm:ss"/>
                                </td>
                                <td class="text-center fs-valign-middle">
                                    <c:choose>
                                        <c:when test="${order.status == 1}">
                                            <select name="status-order" id="id-status-order" class="form-control input-sm" style="color: #00cc66;"
                                                    onchange="window.location = 'admin/orders/confirmstatusOrder/${order.ordersID}/' + this.value + '.html';">
                                                <option value="1" <c:out value="selected"/>>Completed</option>
                                                <option value="2">Pending</option>
                                                <option value="3">Confirmed</option>
                                                <option value="0">Canceled</option>
                                            </select>
                                        </c:when>
                                        <c:when test="${order.status == 2}">
                                            <select name="status-order" id="id-status-order" class="form-control input-sm" 
                                                    onchange="window.location = 'admin/orders/confirmstatusOrder/${order.ordersID}/' + this.value + '.html';">
                                                <option value="1">Completed</option>
                                                <option value="2" <c:out value="selected"/>>Pending</option>
                                                <option value="3">Confirmed</option>
                                                <option value="0">Canceled</option>
                                            </select>
                                        </c:when>
                                        <c:when test="${order.status == 3}">
                                            <select name="status-order" id="id-status-order" class="form-control input-sm" style="color: blue;"
                                                    onchange="window.location = 'admin/orders/confirmstatusOrder/${order.ordersID}/' + this.value + '.html';">
                                                <option value="1">Completed</option>
                                                <option value="2">Pending</option>
                                                <option value="3" <c:out value="selected"/>>Confirmed</option>
                                                <option value="0">Canceled</option>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="status-order" id="id-status-order" class="form-control input-sm" style="color: red;" 
                                                    onchange="window.location = 'admin/orders/confirmstatusOrder/${order.ordersID}/' + this.value + '.html';">
                                                <option value="1">Completed</option>
                                                <option value="2">Pending</option>
                                                <option value="3">Confirmed</option>
                                                <option value="0"  <c:out value="selected"/>>Canceled</option>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- /.table-responsive -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->