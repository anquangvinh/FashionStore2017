<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid" id="fs-product-add-page">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Product</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Create New</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-xs-12 text-center">
                ${error}
            </div>
        </div>
        <div class="row text-center">
            <ul id="fs-product-add-progressbar">
                <li class="active">General Info</li>
                <li>Detail Info</li>
            </ul>
        </div>
        <div class="row">
            <form id="fs-form-product-create-new" method="POST" enctype="multipart/form-data">
                <fieldset class="form-group" id="fs-fieldset-general-info">
                    <div class="col-lg-12">
                        <legend class="text-center">General Information</legend>
                        <div class="col-xs-12" style="margin-bottom: 10px">
                            <span style="color: red; font-size: 13px">
                                <i>*: Required Field!</i>
                            </span>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group">
                                <label>Category <span class="fs-color-red">*</span></label>
                                <p class="help-block" id="fs-select-cate-error"></p>
                                <select class="form-control" name="category" id="fs-product-category">
                                    <option value="0">-- Please select category --</option>
                                    <c:forEach items="${categories}" var="cate">
                                        <option value="${cate.cateID}">${cate.cateName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Sub-Category <span class="fs-color-red">*</span></label>
                                <p class="help-block" id="fs-select-subcate-error"></p>
                                <select class="form-control" name="subCategory" id="fs-product-subCategory">
                                    <option value="0">-- Please select sub-category --</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Product <span class="fs-color-red">*</span></label>
                                <p class="help-block" id="fs-product-name-error"></p>
                                <input name="productName" id="fs-product-name" class="form-control" placeholder="Product Name">
                            </div>

                            <div class="form-group">
                                <label>Price <span class="fs-color-red">*</span></label>
                                <p class="help-block" id="fs-product-price-error"></p>
                                <span class="input-group">
                                    <input name="price" id="fs-product-price" class="form-control" placeholder="Price">
                                    <span class="input-group-addon"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                </span>
                            </div>

                            <div class="form-group">
                                <label>Discount</label>
                                <p class="help-block" id="fs-product-discount-error"></p>
                                <span class="input-group">
                                    <input name="discount" id="fs-product-discount" class="form-control" placeholder="Discount" value="0">
                                    <span class="input-group-addon"><i class="fa fa-percent" aria-hidden="true"></i></span>
                                </span>
                            </div>

                            <div class="form-group">
                                <label>Image <span class="fs-color-red">*</span></label>
                                <p id="fs-error-mess-product-main-img" class="help-block"></p>
                                <input name="urlImg" type="file" id="fs-product-main-img">

                            </div>
                        </div>

                        <div class="col-md-7">
                            <div class="form-group">
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea name="description" class="form-control" rows="5" id="fs-product-description"></textarea>
                                </div>

                                <div class="form-group text-right">
                                    <button type="button" class="btn btn-primary next" title="Go to next step">
                                        <i class="fa fa-arrow-circle-right" style="font-size: 18px"></i> <b>Next</b>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset><!-- /fieldset -->

                <!-- fieldset DETAIL INFORMATION -->
                <fieldset class="form-group" id="fs-fieldset-detail">
                    <legend class="text-center">Detail Information</legend>
                    <div class="col-xs-12" style="padding: 5px 0; border: 1px #CCC dashed; margin-bottom: 10px" fs-big-div-color="0">
                        <div class="col-md-6 fs-right-border">
                            <div class="form-group">
                                <label>Color 01 <span class="fs-color-red">*</span></label>
                                <p class="help-block" id="fs-product-color-name-error"></p>
                                <input name="color" class="form-control fs-product-color-name" id="fs-product-color-name" placeholder="Color">
                            </div>

                            <div class="form-group">
                                <label>Color Image <span class="fs-color-red">*</span></label>
                                <p id="fs-error-mess-color-img" class="help-block"></p>
                                <input type="file" name="colorImg[]" id="fs-product-color-img" class="colorImg">
                            </div>

                            <div class="col-xs-12" style="padding: 0;">
                                <div class="col-xs-12" style="padding-left: 0; border: 1px #CCC dashed; margin-bottom: 5px;">
                                    <div class="form-group col-xs-5">
                                        <label>Size <span class="fs-color-red">*</span></label>
                                        <input id="fs-product-size" name="size" class="form-control fs-product-size" placeholder="Size" style="text-transform:uppercase">
                                    </div>

                                    <div class="form-group col-xs-5">
                                        <label>Quantity <span class="fs-color-red">*</span></label>
                                        <input id="fs-product-quantity" name="quantity" class="form-control fs-product-quantity" placeholder="Quantity">
                                    </div>

                                    <div class="form-group col-xs-2">
                                    </div>

                                    <div class="col-xs-12 error">
                                        <p class="fs-error-mess-size" style="color: red; margin-left: 15px"></p>
                                        <p class="fs-error-mess-quantity" style="color: red; margin-left: 15px"></p>
                                    </div>
                                </div>

                                <span class="fs-more-size"></span>

                                <div class="form-group col-xs-12" style="margin-top: 20px">
                                    <button type="button" class="btn btn-warning fs-add-more-size" title="Add More Size">
                                        <i class="fa fa-plus" aria-hidden="true"></i> Add More Size
                                    </button>
                                </div>            
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Product Sub Image <span class="fs-color-red">*</span></label>
                                <p id="fs-error-mess-productSubImg" class="help-block fs-error-mes-productSubImg"></p>
                                <input name="productSubImg" class="fs-productSubImg" type="file" multiple="multiple">
                            </div>
                        </div>    
                    </div>

                    <span id="fs-more-color"></span>

                    <div class="col-xs-12 text-center">
                        <button type="button" class="btn btn-success"  title="Add More Color" id="fs-add-more-color">
                            <i class="fa fa-plus" aria-hidden="true"></i> Add More Color
                        </button>

                        <button type="button" class="btn btn-danger disabled"  title="Delete Color" id="fs-delete-color">
                            <i class="fa fa-trash-o" aria-hidden="true"></i> Delete Color
                        </button>
                    </div>

                    <div class="col-xs-12">
                        <hr>
                        <div class="form-group text-right">
                            <button type="button" class="btn btn-primary previous pull-left" title="Back to previous step">
                                <i class="fa fa-arrow-circle-left" style="font-size: 18px"></i> Prev
                            </button>

                            <button type="reset" class="btn btn-default" title="Reset to default">
                                <i class="fa fa-undo" aria-hidden="true"></i> Reset
                            </button>

                            <button type="submit" class="btn btn-success" id="fs-btn-create-new-product" title="Create New Product">
                                <i class="fa fa-plus-circle" aria-hidden="true"></i> Create
                            </button>                               
                        </div>   
                    </div>
                </fieldset> <!-- fieldset -->
            </form>
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->

<div class="modal fade" id="fs-confirm-delete-size" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel"><b>Confirm Delete SIZE</b></h3>
            </div>

            <div class="modal-body">
                <p>You are about to delete a <b>"Size"</b>, this procedure is irreversible.</p>
                <p>Do you want to proceed?</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="fs-confirm-delete-color" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel"><b>Confirm Delete COLOR</b></h3>
            </div>

            <div class="modal-body">
                <p>You are about to delete <b id="fs-modal-change-color-number">"COLOR "</b>
                <p>Do you want to proceed?</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-delete-color-ok">Delete</a>
            </div>
        </div>
    </div>
</div>
