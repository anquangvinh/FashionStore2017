<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>User</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">List</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <table width="100%" class="table table-striped table-bordered table-hover" id="fs-user-dataTables">
                    <thead>
                        <tr>
                            <th align="center"></th>
                            <td align="center">No</td>
                            <td align="center">Status</td>
                            <td align="center">Role</td>
                            <td align="center">Email</td>
                            <!--                            <td align="center">First Name</td>
                                                        <td align="center">Last Name</td>-->
                            <td align="center">Gender</td>
                            <!--                            <td align="center">Birth Day</td>
                                                        <td align="center">Registration Date</td>-->

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${ulist}" var="users" varStatus="no">
                            <tr class="odd gradeX" >
                                <td align="center" class="details-control fs-user-dataTable-control-button" fs-userID="${users.userID}"></td>
                                <td class="center fs-detail-user text-center" fs-userID="${users.userID}" data-toggle="modal">${no.index + 1}</td>
                                <c:if test="${users.userID != 1}">
                                    <td align="center">
                                        <select name="status" fs-user="${users.userID}" class="fs-select-user-status form-control input-sm" id="fs-status-select">
                                            <option id="fs-status-1" value="1" <c:if test="${users.status == 1}">selected</c:if>>WORKING</option>
                                            <option id="fs-status-2" value="0" <c:if test="${users.status == 0}">selected</c:if>>BANNED</option>
                                            </select>
                                        </td>
                                </c:if>
                                <c:if test="${users.userID == 1}">
                                    <td class="center" align="center">
                                        <c:if test="${users.status == 1}">
                                            WORKING
                                        </c:if>
                                    </td>
                                </c:if>
                                <c:if test="${users.userID != 1}">
                                    <td class="center" align="center">
                                        <select class="fs-select-user-role form-control input-sm" fs-user="${users.userID}" >
                                            <c:forEach items="${roles}" var="role">
                                                <option <c:if test="${users.role.roleID == role.roleID}">selected</c:if> value="${role.roleID}">${role.roleName}</option>
                                            </c:forEach>
                                        </select>   
                                    </td>
                                </c:if>
                                <c:if test="${users.userID == 1}">
                                    <td class="center" align="center">
                                        ${users.role.roleName}
                                    </td>
                                </c:if>
                                <td class="center" align="center">${users.email}</td>
                                <!--                                <td class="center" align="center"></${users.firstName}</td>
                                                                <td class="center" align="center"></${users.lastName}</td>-->
                                <c:if test="${users.gender == 1}">
                                    <td class="center" align="center">MALE</td>
                                </c:if>
                                <c:if test="${users.gender == 0}">
                                    <td class="center" align="center">FEMALE</td>
                                </c:if>
                                <!--                                <td class="center" align="center">
                                <%--<fmt:formatDate pattern="dd/MM/yyyy" value="${users.birthday}"/>--%>
                            </td>
                            <td class="center" align="center">
                                <%--<fmt:formatDate pattern="dd/MM/yyyy" value="${users.registrationDate}"/>--%>
                            </td>-->

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
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->

<!--MODAL USER-->
<div id="fs-user-detail-info" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" id="close fs-close" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title text-center">TABLE USER</h3>
            </div>
            <div class="modal-body text-center">
                <table class="heavyTable" style="width: 100%;border: 1px solid #38678f;max-width: 500px; height: 30px; border-collapse: collapse;margin: 10px auto;background: white; text-align: center" >
                    <tr id="fs-tr" style="border-bottom: 1px solid #cccccc;">
                        <th class="text-center" id="fs-th" style="background: steelblue;height: 54px;width: 25%;font-weight: lighter;text-shadow: 0 1px 0 #38678f;color: white;border: 1px solid #38678f;box-shadow: inset 0px 1px 2px #568ebd;transition: all 0.2s;">First Name</th> 
                        <th class="text-center" id="fs-th" style="background: steelblue;height: 54px;width: 25%;font-weight: lighter;text-shadow: 0 1px 0 #38678f;color: white;border: 1px solid #38678f;box-shadow: inset 0px 1px 2px #568ebd;transition: all 0.2s;">Last Name</th>
                        <th class="text-center" id="fs-th" style="background: steelblue;height: 54px;width: 25%;font-weight: lighter;text-shadow: 0 1px 0 #38678f;color: white;border: 1px solid #38678f;box-shadow: inset 0px 1px 2px #568ebd;transition: all 0.2s;">Birth Day</th>
                        <th class="text-center" id="fs-th" style="background: steelblue;height: 54px;width: 25%;font-weight: lighter;text-shadow: 0 1px 0 #38678f;color: white;border: 1px solid #38678f;box-shadow: inset 0px 1px 2px #568ebd;transition: all 0.2s;">Resgistraion Date</th>
                    </tr>
                    <tbody id="fs-tbody-table-in-user-detail-info">
                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
