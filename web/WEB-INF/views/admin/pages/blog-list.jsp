<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<base href="${pageContext.servletContext.contextPath}/"/> 
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Blog</strong> 
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
                <table width="100%" class="table table-striped table-bordered table-hover" id="tableBlogList">
                    <thead>
                        <tr>
                            <th class="text-center fs-valign-middle">No.</th>
                            <th class="text-center fs-valign-middle">Category</th>
                            <th class="text-center fs-valign-middle">Poster</th>
                            <th class="text-center fs-valign-middle">Title</th>
                            <th class="text-center fs-valign-middle">Summary</th>
                            <th class="text-center fs-valign-middle">Image</th>
                            <th class="text-center fs-valign-middle">Content</th>
                            <th class="text-center fs-valign-middle">Posted Date</th>
                            <th class="text-center fs-valign-middle">Views</th>
                            <th class="text-center fs-valign-middle">Status</th>
                            <th class="text-center fs-valign-middle">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${blogsList}" var="blogs">
                            <tr>
                                <td class="text-center fs-valign-middle">${blogs.blogID}</td>
                                <td class="text-center fs-valign-middle">
                                    ${blogs.blogCategory.blogCateName}
                                </td>  
                                <td class="text-center fs-valign-middle">${blogs.user.lastName} ${blogs.user.firstName}</td>
                                <td class="fs-valign-middle">${blogs.blogTitle}</td>
                                <td class="fs-valign-middle">${blogs.blogSummary}</td>
                                <td>
                                    <img class="responsive" style="width: 100px" src="assets/images/blog/1/${blogs.blogImg}" alt=""/>
                                </td>
                                <td class="text-center fs-valign-middle">${blogs.content}</td>
                                <td>  
                                    <fmt:formatDate value="${blogs.postedDate}" pattern="dd-MM-YYYY" />
                                </td>
                                <td >${blogs.blogViews}</td>
                                <td >
                                    <select name="status-blog" class="btn btn-info" 
                                            onchange="window.location = 'admin/blog/confirmStatusBlog/${blogs.blogID}/' + this.value + '.html';">
                                        <c:choose>
                                            <c:when test="${blogs.status == 0}">
                                                <option value="0" <c:out value="selected"/>>Enable</option>
                                                <option value="1">Disable</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="0">Enable</option>
                                                <option value="1" <c:out value="selected"/>>Disable</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </td>
                                <td>
                                    <a href="admin/blog/edit/${blogs.blogID}.html" class="btn btn-warning">Update  <i class="fa fa-edit"></i></a>
                                    <a class="btn btn-danger" 
                                       data-href="admin/blog/deleteBlog/${blogs.blogID}.html" 
                                       data-toggle="modal" 
                                       data-target="#confirm-blog-delete">Delete <i class="fa fa-remove"></i></a>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>
        <!-- /.row -->
    </div>
    <div class="modal fade" id="confirm-blog-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Blog Delete</h4>
                </div>
                <div class="modal-body">
                    <p>You are about to delete one blog, this procedure is irreversible.</p>
                    <p>Do you want to proceed?</p>
                    <p class="debug-url"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-blog-delete-ok">Delete</a>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->