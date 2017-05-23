<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid" id="fs-create-sub-category-admin-page">
        <div class="row">
            <div class="col-lg-12">

                <h1 class="page-header"> 
                    <strong>Product Sub-Category</strong> 
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
                    <div>
                        ${error}
                    </div>
                    <form:form method="POST" action="" modelAttribute="subCategory">
                        <div class="form-group">
                            <label>Category</label>
                            <form:select id="fs-select-category" path="category.cateID" cssClass="form-control">
                                <form:option value="0">--- Please Select ---</form:option>
                                <form:options items="${categories}"  itemValue="cateID" itemLabel="cateName" />
                            </form:select>
                            <p class="help-block" id="fs-sub-cate-err-mes"></p>    
                        </div>
                        <div class="form-group">
                            <label>Sub-Category</label>
                            <form:input disabled="true" path="subCateName" cssClass="form-control" placeholder="Choose Category first!"/>

                            <!--Error Message-->
                            <p class="help-block" id="fs-sub-cate-name-err-mes"></p>
                        </div>

                        <button type="submit" class="btn btn-success" id="fs-btn-create-subCategory">Create</button>
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
