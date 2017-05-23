<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true" />
<style>
    .ui-datepicker {
        height: auto !important;
        width: 19em;
    }
</style>


<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-9 col-sm-8">
                <!-- HTML -->
                <div id="account-id">
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Change Your Personal Details</h4>                                                                  
                    <div class="account-form">
                        <form:form id="fs-form-update-account" class="form-update-user" method="POST" action="user/account-information/${sessionScope.findUsersID}.html" modelAttribute="updateUser" enctype="multipart/form-data">      
                            ${error}
                            <ul class="form-list row">
                                <li class="col-md-12 col-sm-12">
                                    <label><i class="fa fa-envelope"></i> Email <em>*</em></label>
                                    <div class="fs-email-update">
                                        <form:input path="email" id="fs-update-email" cssClass="input-text"/>
                                        <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-email-update-user-error"></p>
                                    </div>
                                </li>
                                <li class="col-md-12 col-sm-6">
                                    <label>First Name <em>*</em></label>
                                    <div class="fs-firstname-update">
                                        <form:input path="firstName" id="fs-update-firstname" cssClass="input-text" />
                                        <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-firstname-update-user-error"></p>
                                    </div>
                                </li>
                                <li class="col-md-12 col-sm-6">
                                    <label>LastName <em>*</em></label>
                                    <div class="fs-lastname-update">
                                        <form:input path="lastName" id="fs-update-lastname" cssClass="input-text" />
                                        <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-lastname-update-user-error"></p>
                                    </div>
                                </li>
                                <li class="col-md-6 col-sm-12">  
                                    <label><i class="fa fa-venus-mars"></i> Gender</label>
                                    <br>
                                    <div class="text-center fs-login-gender">
                                        <label>
                                            <form:radiobutton path="gender" value="1" checked="checked" /><i class="fa fa-male"></i> Male 
                                        </label>
                                        &nbsp;&nbsp;&nbsp;
                                        <label>
                                            <form:radiobutton path="gender" value="0" /><i class="fa fa-female"></i> Female 
                                        </label>
                                    </div>
                                </li>
                                <li class="col-md-6 col-sm-12">  
                                    <label><i class="fa fa-birthday-cake"></i> Birthday</label>
                                    <div class="fs-birthday-update">
                                        <form:input path="birthday" id="txtbirthday" cssClass="input-text" />
                                        <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-birthday-update-user-error"></p>
                                    </div>
                                </li>
                                <li class="col-md-12 col-sm-12">
                                    <label>Avatar <em>*</em></label>
                                    <input type="file" id="fs-upImage-acc" name="upImage" class="input-text fs-login-file-input">
                                    <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-upfile-update-user-error"></p>
                                </li>
                            </ul>
                            <div class="buttons-set">
                                <button class="btn-black fs-button-update-user" type="submit"><span>Update Account</span></button>
                            </div>
                        </form:form>
                    </div>                                
                </div>
            </div>

            <div class="col-md-3 col-sm-4 checkout-steps">
                <!-- USER-RIGHT-MENU -->
                <jsp:include page="../blocks/user-right-menu.jsp" flush="true" />
            </div>
        </div>
    </div>
</div>
<div class="clearfix space20"></div>