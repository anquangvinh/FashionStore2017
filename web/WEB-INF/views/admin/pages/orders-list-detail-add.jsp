<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">

                <h1 class="page-header"> 
                    <strong>Orders</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em" id="productOrDetailAddHeader" fs-Order-ID="${orderID}">Details Order NO.${orderID} - Create New</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div id="error-orderDetail-add">

            </div>
            <div class="col-lg-12 row">
                <!--<p class="help-block" id="order-detail-add-error" style="color: red; font-weight: 700;"></p>-->
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-4">
                    <select class="form-control input-md" id="searchType" name="searchType">
                        <option value="1">Search By Product Name</option>
                        <option value="2">Search By Product ID</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-4">
                    <input class="form-control" type="text" id="searchText" name="searchText" value=""/>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <button id="btnSearchProduct" class="btn btn-primary">SEARCH</button>
                </div>
            </div>
            <div class="col-lg-12 divTableProductSearchOrderDetailAdd" style="padding-top: 30px;">
                <table class="table table-striped table-bordered table-hover tableProductOrderDetailAdd" id="tableProductOrderDetailAdd" style="width: 922px;">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">ID</th>
                            <th class="text-center fs-valign-middle">Product Name</th>
                            <th class="text-center fs-valign-middle">Category</th>
                            <th class="text-center fs-valign-middle">Product Discount</th>
                            <th class="text-center fs-valign-middle">Price</th>
                        </tr>
                    </thead>
                    <tbody class="bodyProductOrDetailAdd">

                    </tbody>
                </table>
                <div class="form-group row">
                    <!--<p class="help-block" id="order-detail-add-color-error" style="color: red; font-weight: 700;"></p>-->
                    <div class="col-lg-3">
                        <label>Product Color</label>
                    </div>
                    <div class="col-lg-3">
                        <select class="form-control input-sm" id="productOrDetailAddColor" name="productOrDetailAddColor">
                        </select>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-group row">
                    <!--<p class="help-block" id="order-detail-add-size-error" style="color: red; font-weight: 700;"></p>-->
                    <div class="col-lg-3">
                        <label>Product Size</label>
                    </div>
                    <div class="col-lg-3">
                        <select class="form-control input-sm" id="productOrDetailAddSize" name="productOrDetailAddSize">
                        </select>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-group">
                    <!--<p class="help-block" id="order-detail-add-quantity-error" style="color: red; font-weight: 700;"></p>-->
                    <div class="col-lg-3">
                        <label>Quantity</label>
                    </div>
                    <div class="col-lg-3">
                        <input class="form-control" type="text" id="productOrDetailAddQuantity" name="productOrDetailAddQuantity" value=""/>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="form-group" style="padding-top: 20px;">
                    <button onclick="window.location = 'admin/orders/orderlistdetail/${orderID}.html'" class="btn btn-primary">BACK TO ORDER DETAILS</button>
                    <button id="btnAddOrderDetail" class="btn btn-primary">ADD ORDER DETAIL</button>
                </div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->