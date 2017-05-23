<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@include file="../client/blocks/loginModal.jsp" %>--%>
<!DOCTYPE html>
<!--[if IE 8]>			<html class="ie ie8"> <![endif]-->
<!--[if IE 9]>			<html class="ie ie9"> <![endif]-->
<!--[if gt IE 9]><!-->	<html> <!--<![endif]-->
    <head>

        <!-- Meta -->
        <meta charset="utf-8">
        <meta name="keywords" content="HTML5 Template" />
        <meta name="description" content="">
        <meta name="author" content="">
        <base href="${pageContext.servletContext.contextPath}/" />
        <title>Smile | Responsive Bootstrap Ecommerce Template</title>

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Google Webfont -->
        <link href='assets/css/google-font/raleway.css' rel='stylesheet' type='text/css'>
        <link href='assets/css/google-font/lato.css' rel='stylesheet' type='text/css'>
        <link href='assets/css/google-font/montserrat.css' rel='stylesheet' type='text/css'>

        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/font-awesome/css/font-awesome.css" type='text/css'>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/js/vendors/isotope/isotope.css">
        <link rel="stylesheet" href="assets/js/vendors/slick/slick.css">
        <link rel="stylesheet" href="assets/js/vendors/rs-plugin/css/settings.css">
        <link rel="stylesheet" href="assets/js/vendors/select/jquery.selectBoxIt.css">
        <link rel="stylesheet" href="assets/plugin/owl-carousel/owl.carousel.css">
        <link rel="stylesheet" href="assets/plugin/owl-carousel/owl.theme.css">
        <link rel="stylesheet" href="assets/plugin/prettyphoto/css/prettyPhoto.css">  
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/js/jquery-ui-1.12.1/jquery-ui.min.css"/>
        <link rel="stylesheet" href="assets/css/fashion_shop_custom_CSS.css">
        <link href="assets/css/fontawesome-stars.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/fontawesome-stars-o.css" rel="stylesheet" type="text/css"/>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
                <![endif]-->

        <!--Thông báo css cập nhật thành công-->
        <link href="assets/css/sweetalert.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <!-- PRELOADER -->
        <div id="loader"></div>

        <div class="body">
            <!-- HEADER -->
            <tiles:insertAttribute name="header" />

            <!-- CONTENT -->
            <tiles:insertAttribute name="content" />

            <!-- FOOTER -->
            <tiles:insertAttribute name="footer" />

        </div>

        <div id="backtotop"><i class="fa fa-chevron-up"></i></div>

        <!-- Javascript -->
        <script src="assets/js/jquery-ui-1.12.1/external/jquery/jquery.js"></script>
        <script src="assets/js/jquery.barrating.min.js" type="text/javascript"></script>
        <script src="assets/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>        
        <script src="assets/plugin/owl-carousel/owl.carousel.min.js"></script>
        <script src="assets/plugin/prettyphoto/js/jquery.prettyPhoto.js"></script>
        <script src="assets/js/bs-navbar.js"></script>
        <script src="assets/js/vendors/isotope/isotope.pkgd.js"></script>
        <script src="assets/js/vendors/slick/slick.min.js"></script>
        <script src="assets/js/vendors/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
        <script src="assets/js/vendors/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
        <script src="assets/js/jquery.sticky.js"></script>
        <script src="assets/js/royalslider/jquery.royalslider.min.js"></script>
        <script src="assets/js/vendors/select/jquery.selectBoxIt.min.js"></script>
        <script src="assets/js/sweetalert.min.js"></script>
        <script src="assets/js/main.js"></script>
        <script src="assets/js/bootstrap-notify.js" type="text/javascript"></script>

        <script src="assets/js/fashion_shop_custom_JS.js" type="text/javascript"></script>
    </body>
</html>
