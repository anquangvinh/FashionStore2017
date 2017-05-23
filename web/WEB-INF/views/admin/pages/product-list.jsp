<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Product</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">List</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row fs-div-product-list-select-box" >
            <div class="col-lg-12">
                <table width="100%" class="table table-striped table-bordered table-hover" id="productList_dataTable">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">No</th>
                            <th class="text-center fs-valign-middle">Status</th>
                            <th class="text-center fs-valign-middle">Category - SubCategory</th>
                            <th class="text-center fs-valign-middle">Product</th>
                            <th class="text-center fs-valign-middle">Image</th>
                            <th class="text-center fs-valign-middle">Posted Date</th>
                            <th class="text-center fs-valign-middle">Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${productList}" var="product" varStatus="No">
                            <tr class="odd gradeX">
                                <td class="text-center fs-valign-middle">${No.index + 1}</td>
                                <td class="text-center fs-valign-middle" style="position: relative">
                                    <div class="fs-trigger"></div>
                                    <div class="fs-success-icon">
                                        <svg id="tick" x="0px" y="0px"
                                             viewBox="0 0 37 37"
                                             style="enable-background:new 0 0 37 37;">
                                        <path class="circ path" 
                                              style="fill:none; stroke:#31B131; stroke-width:3; stroke-linejoin:round; stroke-miterlimit:10; width:30px"
                                              d="M30.5,6.5L30.5,6.5c6.6,6.6,6.6,17.4,0,24l0,0c-6.6,6.6-17.4,6.6-24,0l0,0c-6.6-6.6-6.6-17.4,0-24l0,0C13.1-0.2,23.9-0.2,30.5,6.5z"/>
                                        <polyline class="tick path" 
                                                  style="fill:none; stroke:#31B131; stroke-width:3; stroke-linejoin:round; stroke-miterlimit:10;"
                                                  points="11.6,20 15.9,24.2 26.4,13.8 "/>
                                        </svg>
                                    </div>

                                    <div class="form-group" style="position: relative">
                                        <c:choose>
                                            <c:when test="${product.status == 0}">
                                                <div class="fs-stopworking-icon">
                                                    <i class="fa fa-minus-circle" aria-hidden="true"></i>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="fs-stopworking-icon fs-display-none">
                                                    <i class="fa fa-minus-circle" aria-hidden="true"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <select class="form-control fs-product-status-select-box" fs-product="${product.productID}">
                                            <option value="1" <c:if test="${product.status == 1}">selected</c:if>>
                                                    Working
                                                </option>
                                                <option value="0" <c:if test="${product.status == 0}">selected</c:if>>
                                                    Stopped 
                                                </option>
                                            </select>
                                        </div>
                                    </td>
                                <td class="text-center fs-valign-middle">${product.subCate.category.cateName} - ${product.subCate.subCateName}</td>
                                <td class="text-center fs-valign-middle">${product.productName}</td>
                                <td class="text-center fs-valign-middle">
                                    <img class="responsive" style="width: 100px" src="assets/images/products/${product.urlImg}" alt="${product.urlImg}"/>
                                </td>
                                <td class="text-center fs-valign-middle">
                                    <fmt:formatDate pattern="dd/MM/yyyy" value="${product.postedDate}"/>
                                </td>

                                <td class="text-center fs-valign-middle">
                                    <a href="${product.productID}-${product.productColorList[0].colorID}-${product.productNameNA}.html" class="btn btn-info" style="margin-top: 4px;">
                                        <i class="fa fa-info-circle" aria-hidden="true"></i> Detail
                                    </a>
                                    <a href="admin/product/edit-${product.productID}.html" class="btn btn-warning" style="margin-top: 4px;">
                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Update
                                    </a>
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
