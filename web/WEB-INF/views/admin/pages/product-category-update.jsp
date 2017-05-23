<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid" id="fs-update-category-admin-page" fs-category-id="${targetCate.cateID}">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Product Category</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Edit Info</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div class="col-lg-6">
                    <div>
                        ${error}
                    </div>
                    <form:form id="fs-form-update-category" method="POST" action="" modelAttribute="targetCate">
                        <div class="form-group">
                            <label>Category</label>
                            <form:input path="cateName" cssClass="form-control" placeholder="Enter Product Category Name" />
                            <!--Error Message-->
                            <p class="help-block" id="fs-cate-name-err-mes"></p>
                        </div>

                        <button type="submit" class="btn btn-warning" id="fs-btn-update-category">Update</button>
                        <button type="reset" class="btn btn-default">Reset</button>
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