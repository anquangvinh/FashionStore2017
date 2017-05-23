<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Blog</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Create New</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <form:form id="fs-form-create-blog" name="blogForm" action="admin/blog/create.html" method="POST" modelAttribute="newBlogs" enctype="multipart/form-data">
                <div class="col-md-5">
                    ${sessionScope.tensession}
                    ${status}
                    <div class="form-group">
                        <label>Categories <span class="fs-color-red">*</span></label>
                        <p class="help-block" id="fs-select-box-blog-category-error"></p>
                        <form:select id="fs-select-box-blog-category" name="category" path="blogCategory.blogCateID" cssClass="form-control">
                            <form:option value="0">-- Please Select --</form:option>
                            <form:options items="${blogcategory}" itemValue="blogCateID" itemLabel="blogCateName" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>Title <span class="fs-color-red">*</span></label> 
                        <p class="help-block" id="fs-blog-title-error"></p>
                        <form:input path="blogTitle" id="fs-blog-line-title" placeholder="Title of blog" name="title" cssClass="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Summary <span class="fs-color-red">*</span></label>
                        <p class="help-block" id="fs-blog-summary-error"></p>
                        <form:input path="blogSummary" name="summary" id="fs-blog-line-summary" placeholder="Summarize the content of the article" cssClass="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Image <span class="fs-color-red">*</span></label>
                        <p id="fs-error-mess-blog-img" class="help-block"></p>
                        <input type="file" id="upImageBlog" name="upImageBlog" class="upImageBlog">
                    </div>
                </div>
                <!--                        <div class="form-group">
                                            <label>Date</label>
                <form:input path="postedDate" cssClass="form-control" />
                Error Message
                <div style="color:red; margin-top: 10px;">
                <form:errors path="postedDate"/>
            </div>
        </div>-->
                <div class="col-md-7">
                    <div class="form-group text-left">
                        <div class="form-group">
                            <label for="content">Content</label>
                            <p class="help-block" id="fs-blog-content-error"></p>
                            <!--                            <br/>-->
                            <!--CKEditor-->
                            <form action="getContent" method="get">
                                <textarea class="required"  cols="80" id="editor1" name="editor1"></textarea>
                                <script>
                                    CKEDITOR.replace('editor1');
                                </script>
                            </form>
                            <ckfinder:setupCKEditor basePath="assets/ckfinder/" editor="editor1" />
                            <ckeditor:replace replace="editor1" basePath="assets/ckeditor/" />
                            <!--Error Message-->
                            <div style="color:red; margin-top: 10px;">
                                <form:errors path="content"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Status</label>
                            <div>
                                <label class="radio-inline">
                                    <form:radiobutton path="status" checked="checked" value="0" id="rdoEnable" />
                                    <label for="rdoEnable">Enable</label>
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton path="status" value="1" id="rdoDisable" />
                                    <label for="rdoDisable">Disable</label>
                                </label>
                            </div>
                        </div>

                        <form:button type="submit" id="fs-button-create-blog" name="fs-button-create-blog" class="btn btn-success"  style="width: 20%">Create <i class="fa fa-check-circle"></i></form:button>
                        <form:button type="reset" class="btn btn-default"  style="width: 20%">&nbsp; &nbsp; Reset&nbsp; &nbsp;  <i class="fa fa-window-restore"></i></form:button>
                        </div>
                </form:form>
                <!-- /.col-lg-12 -->
            </div>

            <!-- /.row -->
            <!--        </div>-->
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->