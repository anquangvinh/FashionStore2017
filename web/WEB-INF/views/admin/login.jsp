<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <base href="${pageContext.servletContext.contextPath}/" />
        <title>SB Admin 2 - Bootstrap Admin Theme</title>

        <!-- Bootstrap Core CSS -->
        <link href="assets/admin/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

        <!-- MetisMenu CSS -->
        <link href="assets/admin/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="assets/admin/dist/css/sb-admin-2.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="assets/admin/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Please Sign In</h3>
                        </div>
                        ${error} <!-- Ti sua mot thong bao loi hien thi cho dep --> 
                        <div class="panel-body">
                            <form method="POST" id="fs-form-login-admin" action="admin/login.html">
                                <fieldset>
                                    <div class="form-group fa-vali-email-admin">
                                        <input class="form-control" id="fs-email-login-admin" placeholder="E-mail" name="email" type="email" autofocus>
                                    </div>
                                    <p class="help-block" id="fs-email-login-admin-error"></p>
                                    <div class="form-group fa-vali-pass-admin">
                                        <input class="form-control" id="fs-pass-login-admin" placeholder="Password" name="password" type="password" value="">
                                    </div>
                                    <p class="help-block" id="fs-pass-login-admin-error"></p>
                                    <div class="checkbox">
                                        <label>
                                            <input name="remember" type="checkbox" value="1">Remember Me
                                        </label>
                                    </div>
                                    <button class="btn btn-lg btn-success btn-block fs-button-login-admin">Login</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="assets/admin/vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="assets/admin/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="assets/admin/vendor/metisMenu/metisMenu.min.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="assets/admin/dist/js/sb-admin-2.js"></script>

        <!--<script src="assets/admin/dist/js/fashion_shop_custom_JS.js" type="text/javascript"></script>-->
        <script>
            $(document).ready(function () {
                function checkEmailLogin(email) {
                    email = $("#fs-email-login-admin").val().trim();
                    var pattern = new RegExp(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/);
                    if (email == "") {
                        $("#fs-email-login-admin-error").text("Email cannot be empty!");
                        $("#fs-email-login-admin").focus();
                        var divemail = $("#fs-email-login-admin").closest("div.fa-vali-email-admin");
                        divemail.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divemail.addClass("has-error has-feedback");
                        divemail.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else if (!pattern.test(email)) {
                        $("#fs-email-login-admin-error").text("Please enter valid email!");
                        $("#fs-email-login-admin").focus();
                        var divemail = $("#fs-email-login-admin").closest("div.fa-vali-email-admin");
                        divemail.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divemail.addClass("has-error has-feedback");
                        divemail.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else {
                        $("#fs-email-login-admin-error").text("");
                        var divemail = $("#fs-email-login-admin").closest("div.fa-vali-email-admin");
                        divemail.removeClass("has-error");
                        divemail.addClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divemail.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-ok form-control-feedback"></span>');
                        return true;
                    }
                }

                $(".fs-button-login-admin").click(function (e) {
                    e.preventDefault();
                    var email = $("#fs-email-login-admin").val().trim();
                    var pass = $("#fs-pass-login-admin").val().trim();

                    if (!checkEmailLogin(email)) {
                        return false;
                    } else if (pass == "") {
                        $("#fs-pass-login-admin-error").text("Password cannot be empty!");
                        $("#fs-pass-login-admin").focus();
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.addClass("has-error has-feedback");
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else if (pass.length < 6 || pass.length > 100) {
                        $("#fs-pass-login-admin-error").text("Password has 6-100 characters!");
                        $("#fs-pass-login-admin").focus();
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.addClass("has-error has-feedback");
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else {
                        $("#fs-form-login-admin").submit();
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-error");
                        divpass.addClass("has-success has-feedback");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-ok form-control-feedback"></span>');
                        return true;
                    }
                });

                $("#fs-email-login-admin").keyup(function () {
                    var email = $("#fs-email-login-admin").val().trim();
                    if (!checkEmailLogin(email)) {
                        return false;
                    }
                });

                $("#fs-pass-login-admin").keyup(function () {
                    var pass = $("#fs-pass-login-admin").val().trim();

                    if (pass == "") {
                        $("#fs-pass-login-admin-error").text("Password cannot be empty!");
                        $("#fs-pass-login-admin").focus();
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.addClass("has-error has-feedback");
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else if (pass.length < 6 || pass.length > 100) {
                        $("#fs-pass-login-admin-error").text("Password has 6-100 characters!");
                        $("#fs-pass-login-admin").focus();
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-success");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.addClass("has-error has-feedback");
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-remove form-control-feedback"></span>');
                        return false;
                    } else {
                        $("#fs-pass-login-admin-error").text("");
                        var divpass = $("#fs-pass-login-admin").closest("div.fa-vali-pass-admin");
                        divpass.removeClass("has-error");
                        divpass.addClass("has-success has-feedback");
                        $("#glypcn-fs-login-admin").remove();
                        divpass.append('<span id="glypcn-fs-login-admin" class="glyphicon glyphicon-ok form-control-feedback"></span>');
                        return true;
                    }
                });
            });
        </script>
    </body>

</html>
