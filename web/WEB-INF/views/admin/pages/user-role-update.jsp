<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>User Role</strong> 
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
                    <form:form role="form" id="fs-form-update-role" action="admin/user/role/edit/${roleupdate.roleID}.html" modelAttribute="roleupdate" method="post">
                        <p>${error}</p>
                        <div class="form-group">
                            <label>Role</label>
                            <div class="fa-ccc">
                            <form:input path="roleName" id="fs-roleName-update" cssClass="form-control" placeholder="Enter Role Name" />
                            </div>
                            <p class="help-block" id="fs-update-roleName-error"></p>
                            </div>
                            <button type="submit" id="fs-button-update-role" class="btn btn-warning"><i class="fa fa-edit"></i> Update</button>
                            <button type="reset" class="btn btn-default" ><i class="fa fa-repeat"></i> Reset</button>
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