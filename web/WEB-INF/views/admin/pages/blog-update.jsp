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
                    <span style="font-size: 0.9em">Edit Info</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <form:form action="" id="fs-form-update-blog" name="blogupdateForm" method="POST" modelAttribute="targetBlogs" enctype="multipart/form-data">
                <div class="col-md-5">
                    ${sessionScope.tensession}
                    ${status}
                    <div class="form-group">
                        <label>Categories <span class="fs-color-red">*</span></label>
                        <p class="help-block" id="fs-select-box-blog-category-error"></p>
                        <form:select id="fs-select-box-blog-category-update" path="blogCategory.blogCateID" cssClass="form-control">
                            <form:option value="0"> Please Select </form:option>
                            <form:options items="${blogcategory}" itemValue="blogCateID" itemLabel="blogCateName" />
                        </form:select>
                    </div>
                    <!--                        <div class="form-group">
                                                <label>User name</label>
                    <form:select path="user.userID" cssClass="form-control">
                        <form:option value="0"> Please Select </form:option>
                        <form:options items="${user}" itemValue="userID" itemLabel="firstName" />
                    </form:select>
                </div>-->
                    <div class="form-group">
                        <label>Title <span class="fs-color-red">*</span></label>  
                        <p class="help-block" id="fs-blog-update-title-error"></p>
                        <form:input path="blogTitle" id="fs-blog-update-line-title" cssClass="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Summary <span class="fs-color-red">*</span></label>
                        <p class="help-block" id="fs-blog-summary-error"></p>
                        <form:input path="blogSummary" id="fs-blog-update-line-summary" cssClass="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Image <span class="fs-color-red">*</span></label>
                        <p id="fs-error-mess-blog-img" class="help-block"></p>
                        <input type="file" id="upImageBlog" name="upImageBlog">
                        <img class="responsive" style="width: 280px" src="assets/images/blog/1/${targetBlogs.blogImg}" alt=""/>
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
                            <label>Content <span class="fs-color-red">*</span></label>
                            <br/>
                            <!--CKEditor-->
                            <form action="getContent" method="get">
                                <textarea cols="80" id="editor1" name="editor1" value="${editor1}"></textarea>				
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
                                    <form:radiobutton path="status" value="0" id="rdoEnable" label="Enable" />
                                </label>
                                <label class="radio-inline">
                                    <form:radiobutton path="status" value="1" id="rdoDisable" label="Disable" />
                                </label>
                            </div>
                        </div>
                        <form:button id="fs-button-update-blog" type="submit" class="btn btn-warning" style="width: 20%" >Update<i class="fa fa-edit"></i></form:button>
                        <form:button type="reset" class="btn btn-default" style="width: 20%"> Reset </form:button>
                        </div>
                </form:form>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->