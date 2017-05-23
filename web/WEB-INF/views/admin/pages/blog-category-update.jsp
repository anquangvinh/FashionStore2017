<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Blog Category</strong> 
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
                    <form:form name="cateupdateForm" action="" method="POST" modelAttribute="targetBlogCategories">
                        <div class="form-group">
                            <label>Category</label>
                            <p class="help-block" id="fs-blog-category-error"></p>                      
                            <form:input id="fs-blog-category-update" class="form-control" path="blogCateName"/>
                        </div>
                             ${status}

                                    <form:button type="submit" id="fs-button-update-blog-category" class="btn btn-warning">Update  <i class="fa fa-edit"></i></form:button>
                                    <form:button type="reset" class="btn btn-default">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Reset&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </form:button>
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