<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">

                <h1 class="page-header"> 
                    <strong>Discount</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Create New</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div id="error-discount-add">
                    ${error}
                </div>
                <div class="col-lg-6">
                    <form:form id="fs-form-create-discount" name="discount-add-form" action="admin/orders/discountadd.html" method="POST" modelAttribute="discountVoucher">
                        <div class="form-group">
                            <label>Voucher Code <span style="color: red;">*</span></label>
                            <form:input placeholder="VOU00" cssClass="form-control" path="voucherID"/>
                            <!--onchange="this.value = this.value.toUpperCase();"-->
                        </div>
                        <div class="form-group">
                            <label>Discount Percent (0-100) <span style="color: red;">*</span></label>
                            <form:input cssClass="form-control" path="discount"/>
                        </div>
                        <div class="form-group">
                            <label>Discount Quantity <span style="color: red;">*</span></label>
                            <form:input cssClass="form-control" path="quantity"/>
                        </div>
                        <div class="form-group">
                            <label>Begin Date</label>
                            <form:input cssClass="form-control" path="beginDate" readonly="true"/>
                        </div>
                        <div class="form-group">
                            <label>End Date</label>
                            <form:input cssClass="form-control" path="endDate" readonly="true"/>
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <form:input cssClass="form-control" path="description"/>
                        </div>
                        <button type="submit" id="btn-create-discount" class="btn btn-success">CREATE</button>
                        <button type="reset" class="btn btn-default">RESET</button>
                        <button type="button" onclick="window.location = 'admin/orders/discountlist.html';" class="btn btn-default">CANCEL - BACK TO DISCOUNT LIST</button>
                    </form:form>
                </div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->
<script type="text/javascript">
//    $('#vouId').val($(this).val().toUpperCase());
</script>