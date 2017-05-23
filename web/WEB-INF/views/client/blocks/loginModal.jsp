<%-- 
    Document   : loginModal
    Created on : May 6, 2017, 4:02:07 PM
    Author     : hoang
--%>

<!-- BREADCRUMBS -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    .ui-datepicker {
        height: auto !important;
        width: 19em;
    }
</style>


<div id="loginModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content login-modal">
            <div class="modal-header login-modal-header">
                <button type="button" class="close" style="color: black" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title text-center" id="loginModalLabel"><i class="fa fa-user" style="font: 20px;"></i> USER AUTHENTICATION</h4>
            </div>
            <div class="modal-body">
                <div class="">
                    <div role="tabpanel" class="login-tab">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a id="signin-taba" href="#home" aria-controls="home" role="tab" data-toggle="tab"><i class="fa fa-sign-in"></i> LOGIN</a></li>
                            <li role="presentation"><a id="signup-taba" href="#profile" aria-controls="profile" role="tab" data-toggle="tab"><i class="fa fa-user-plus"></i> REGISTER</a></li>
                        </ul>
                    </div>

                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active text-center" id="home">
                            &nbsp;&nbsp;
                            <span id="login_fail" class="response_error" style="display: none;">Loggin failed, please try again.</span>
                            <span style="color: red; font-size: 20px;" id="fs-error-show"></span>
                            <div class="clearfix"></div>
                            <form name="fs-form-login-user" action="user/login.html" method="post" class="form-login fs-login-modal" id="fs-form-login-user"> 
                                <!--action="user/login.html"--> 


                                <ul class="form-list row">
                                    <li class="col-md-12 col-sm-12" style="text-align: left">
                                        <label ><i class="fa fa-envelope"></i> Email <em>*</em></label>
                                        <div class="fs-email-user">
                                            <input type="email" id="fs-email-login-user" name="email" class="input-text" autofocus >
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-email-login-user-error"></p>
                                        </div>

                                    </li>
                                    <li class="col-md-12 col-sm-12" style="text-align: left">
                                        <label ><i class="fa fa-key"></i> Your password <em>*</em></label>
                                        <div class="fs-pass-user">
                                            <input type="password" id="fs-pass-login-user" name="password" class="input-text" >
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-pass-login-user-error"></p>
                                        </div>

                                    </li> 
                                    <li class="col-md-6 col-sm-12">                                                
                                        <input name="checkremember" id="fs-check-remember" class="input-chkbox" type="checkbox" value="1">
                                        <label for="fs-check-remember" >Remember me</label>
                                    </li>
                                </ul>
                                <div class="buttons-set">
                                    <button class="btn-black fs-button-login-user" type="submit"><span>Login</span></button>
                                </div>
                            </form>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="profile">
                            &nbsp;&nbsp;
                            <span id="registration_fail" class="response_error" style="display: none;">Registration failed, please try again.</span>
                            <span id="fs-error-show-register"></span>
                            <div class="clearfix" ></div>
                            <form name="fs-form-create-user" class="form-login" method="post" action="user/register.html" id="fs-form-create-user" enctype="multipart/form-data">    
                                <ul class="form-list row">
                                    <li class="col-md-12 col-sm-12">
                                        <label><i class="fa fa-envelope"></i> Email <em>*</em></label>
                                        <div class="fs-email-create">
                                            <input name="email" id="fs-create-email" class="input-text" type="email" autofocus=""/>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-email-create-user-error"></p>

                                        </div>
                                    </li>
                                    <li class="col-md-6 col-sm-12">
                                        <label><i class="fa fa-key"></i> Password <em>*</em></label>
                                        <div class="fs-password-create">
                                            <input type="password" id="password" name="password" class="input-text"  />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-pass-create-user-error"></p>
                                        </div>
                                    </li> 
                                    <li class="col-md-6 col-sm-12">
                                        <label><i class="fa fa-key"></i> Retype Password <em>*</em></label>
                                        <div class="fs-repassword-create">
                                            <input type="password" id="Repassword" name="Repassword" class="input-text" />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-repass-create-user-error"></p>
                                        </div>
                                    </li>
                                    <li class="col-md-12 col-sm-6">
                                        <label>First Name <em>*</em></label>
                                        <div class="fs-firstname-create">
                                            <input name="firstName" id="fs-create-firstname" class="input-text" />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-fname-create-user-error"></p>
                                        </div>
                                    </li>
                                    <li class="col-md-12 col-sm-6">
                                        <label>LastName <em>*</em></label>
                                        <div class="fs-lastname-create">
                                            <input name="lastName" id="fs-create-lastname" class="input-text" />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-lname-create-user-error"></p>
                                        </div>
                                    </li>
                                    <li class="col-md-6 col-sm-12">  
                                        <label><i class="fa fa-venus-mars"></i> Gender</label>
                                        <br>
                                        <div class="text-center fs-login-gender">
                                            <label>
                                                <input type="radio" name="gender"  value="1" checked="checked" /><i class="fa fa-male"></i> Male 
                                            </label>
                                            &nbsp;&nbsp;&nbsp;
                                            <label>
                                                <input type="radio" name="gender" value="0" /><i class="fa fa-female"></i> Female 
                                            </label>
                                        </div>
                                    </li>
                                    <li class="col-md-6 col-sm-12">  
                                        <label><i class="fa fa-birthday-cake"></i> Birthday</label>
                                        <div class="fs-birthday-create" >
                                            <span id="fs-birthday-create"><input name="birthday" id="txtBirthday" class="input-text" /></span>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-bday-create-user-error"></p>
                                        </div>
                                    </li>

                                    <li class="col-md-12 col-sm-12">
                                        <label><i class="fa fa-phone"></i> Phone <em>*</em></label>
                                        <div class="fs-phone-create">
                                            <input type="text" id="fs-create-phone" name="phoneNumber" />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-phone-create-user-error"></p>
                                        </div>
                                    </li>

                                    <li class="col-md-12 col-sm-12">
                                        <label>Address <em>*</em></label>
                                        <div class="fs-address-create">
                                            <input type="text" id="fs-create-address" name="address" />
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-address-create-user-error"></p>
                                        </div>
                                    </li>

                                    <li class="col-md-12 col-sm-12">
                                        <label>Avatar <em>*</em></label>
                                        <input type="file" id="fs-upImage" name="upImage" class="input-text fs-login-file-input">
                                        <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-upfile-create-user-error"></p>
                                    </li>
                                </ul>
                                <div class="buttons-set text-center">
                                    <button class="btn-black btn-custom fs-button-create-user" id="fs-button-create-user" type="submit"><span>Create Account</span></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



