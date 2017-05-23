<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid" id="fs-category-add-page">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Product Category</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Create New</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div class="col-lg-6">
                    <div id="fs-create-category-error-mess-server">
                        ${error}
                    </div>
                    <form:form method="POST" action="admin/product-category/create.html" modelAttribute="newCate">
                        <div class="form-group">
                            <label>Category</label>
                            <form:input path="cateName" cssClass="form-control" placeholder="Enter Product Category Name" />

                            <!--Error Message-->
                            <p class="help-block" id="fs-cate-name-err-mes"></p>
                        </div>

                            <button type="submit" class="btn btn-success" id="fs-btn-create-category">Create</button>
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
