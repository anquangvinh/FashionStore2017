<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Discount</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">List</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div>
                    ${error}
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover" id="tableDiscountList">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">Voucher Code</th>
                            <th class="text-center fs-valign-middle">Discount</th>
                            <th class="text-center fs-valign-middle">Quantity</th>
                            <th class="text-center fs-valign-middle">Begin Date</th>
                            <th class="text-center fs-valign-middle">End Date</th>
                            <th class="text-center fs-valign-middle">Description</th>
                            <th class="text-center fs-valign-middle">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${discountList}" var="discount">
                            <tr class="odd gradeX">
                                <td class="text-center fs-valign-middle">${discount.voucherID}</td>
                                <td class="text-center fs-valign-middle">${discount.discount}</td>
                                <td class="text-center fs-valign-middle">${discount.quantity}</td>
                                <td class="text-center fs-valign-middle">
                                    <c:choose>
                                        <c:when test="${discount.beginDate == null}">
                                            --
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate value="${discount.beginDate}" pattern="dd-MM-yyyy"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center fs-valign-middle">
                                    <c:choose>
                                        <c:when test="${discount.beginDate == null}">
                                            --
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate value="${discount.endDate}" pattern="dd-MM-yyyy"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center fs-valign-middle">${discount.description}</td>
                                <td class="text-center fs-valign-middle">
                                    <a href="admin/orders/discountupdate/${discount.voucherID}.html" type="button" class="btn btn-primary">UPDATE</a>
                                    <c:choose>
                                        <c:when test="${discount.ordersList.size() == 0}">
                                            <a data-href="admin/orders/discountdelete/${discount.voucherID}.html" 
                                               type="button" 
                                               class="btn btn-danger" 
                                               data-toggle="modal" 
                                               data-target="#confirm-discount-delete">DELETE</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a type="button" class="btn btn-danger" disabled>DELETE</a>
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
    <div class="modal fade" id="confirm-discount-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Discount Delete</h4>
                </div>

                <div class="modal-body">
                    <p>Do you want to proceed?</p>
                    <p class="debug-url"></p>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-discount-delete-ok">Delete</a>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->