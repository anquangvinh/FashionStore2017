<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Blog Category</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">List</span>

                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div>
                    ${error}
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover" id="tableBlogCategory">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle" >No.</th>
                            <th class="text-center fs-valign-middle" >Category</th>
                            <th class="text-center fs-valign-middle" >Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${blogCategoriesList}" var="blogscate">
                            <tr>
                                <td class="text-center fs-valign-middle" >${blogscate.blogCateID}</td>
                                <td class="text-center fs-valign-middle" >
                                    <a href="admin/blog/list/${blogscate.blogCateID}.html">${blogscate.blogCateName}</a>
                                </td>  
                                <td class="text-center fs-valign-middle" >
                                    <a href="admin/blog/category/edit/${blogscate.blogCateID}.html" class="btn btn-warning">Update <i class="fa fa-edit"></i></a>
                                    <a class="btn btn-danger" 
                                       data-href="admin/blog/delete/${blogscate.blogCateID}.html" 
                                       data-toggle="modal" 
                                       data-target="#confirm-blog-category-delete">Delete <i class="fa fa-remove"></i></a>
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
    <div class="modal fade" id="confirm-blog-category-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Blog Category Delete</h4>
                </div>
                <div class="modal-body">
                    <p>You are about to delete one blog category, this procedure is irreversible.</p>
                    <p>Do you want to proceed?</p>
                    <p class="debug-url"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-blog-cate-delete-ok">Delete</a>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->