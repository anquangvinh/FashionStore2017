<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid" id="fs-cate-subcate-admin-page" fs-error="${error}">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Product Category</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">List</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        
        <div class="row">
            <div class="col-lg-12">
                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th class="text-center">No</th>
                            <th class="text-center">Category</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cateList}" var="cate" varStatus="No">
                            <tr class="odd gradeX">
                                <td class="text-center fs-valign-middle">${No.index + 1}</td>
                                <td class="text-center fs-valign-middle fs-cate-subcate-name">${cate.cateName}</td>
                                <td class="text-center fs-valign-middle">
                                    <a href="admin/product-category/${cate.cateNameNA}-${cate.cateID}.html" class="btn btn-warning"><i class="fa fa-wrench" aria-hidden="true"></i> Update</a>
                                    <button
                                        fs-type="cate"
                                        fs-cate-subcate_id="${cate.cateID}" 
                                        class="btn btn-danger fs-btn-delete-confirm" <c:if test="${not empty cate.subCateList}">disabled</c:if>><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- /.table-responsive -->
                <div class="modal fade" id="fs-modal-confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h3 class="modal-title" id="myModalLabel"><b>Confirm Delete Category</b></h3>
                            </div>

                            <div class="modal-body">
                                <p>You are about to delete <b id="fs-modal-change-cate-subcate-name"></b> category!
                                <p>Do you want to proceed?</p>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <a id="fs-btn-delete-cate-subcate" class="btn btn-danger">Delete</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->
