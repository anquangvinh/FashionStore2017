<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="loginModal.jsp"></jsp:include>
    <!-- TOPBAR -->
    <div class="top_bar">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <div class="tb_left pull-left">
                        <p>Welcome to Fashion Store !</p>
                    </div>
                    <div class="tb_center pull-left">
                        <ul>
                            <li><i class="fa fa-phone"></i> Hotline: <a href="#">0168 8866 424</a></li>
                            <li><i class="fa fa-envelope-o"></i> <a href="#">support@smile.com</a></li>
                        </ul>
                    </div>
                    <div class="tb_right pull-right">
                        <ul>
                            <li>
                                <div class="tbr-info">
                                    <c:if test="${empty sessionScope.emailUser}">
                                        <span class="fa fa-user">
                                            <a class="fs-login-page" href="#loginModal" data-toggle="modal" data-target="#loginModal">
                                                Login
                                            </a>
                                        </span>
                                    </c:if>
                                    <c:if test="${not empty sessionScope.emailUser}">
                                        <span>${sessionScope.USfirstname} <i class="fa fa-caret-down"></i></span>

                                        <div class="tbr-inner">
                                            <a href="user/myaccount.html">My Account</a>
                                            <a href="user/wishlist/${sessionScope.findUsersID}.html">My Wishlist</a>
                                            <a href="orders/order-history.html">Order History</a>
                                            <a href="logout.html">LogOut</a>
                                        </div>
                                    </c:if>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- HEADER -->
<header style="z-index: 1000 !important;">
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="row">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!-- Logo -->
                    <a class="navbar-brand" href="./index.html"><img src="assets/images/basic/logo.png" class="img-responsive" alt=""/></a>
                </div>
                <!-- Cart & Search -->
                <div class="header-xtra pull-right">
                    <div class="topcart">
                        <div id="cart">

                        </div>
                        <input id="order-emailUser" name="order-emailUser" type="hidden" value="${sessionScope.emailUser}"/>
                    </div>
                    <div class="topsearch">
                        <span>
                            <i class="fa fa-search"></i>
                        </span>
                        <form class="searchtop">
                            <input type="text" id="fs-search-top-input" placeholder="Search by Product Name...">
                            <div style="background-color: white">
                                <ul id="fs-on-search-result">
                                    
                                </ul>
                            </div>
                        </form>
                        
                    </div>
                    
                </div>
                <!-- Navmenu -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="index.html" class="active">Home</a>
                        </li>
                        <c:forEach items="${cateList}" var="category">
                            <li class="dropdown">
                                <a href="category/${category.cateID}-${category.cateNameNA}.html" 
                                   class="dropdown-toggle" 
                                   data-toggle="dropdown" role="button" 
                                   aria-expanded="false">${category.cateName}</a>
                                <ul class="dropdown-menu submenu" role="menu">
                                    <c:forEach items="${category.subCateList}" var="subCate">
                                        <li><a href="${category.cateNameNA}/${subCate.subCateID}-${subCate.subCateNameNA}.html">${subCate.subCateName}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                        <li class="dropdown">
                            <a href="blog.html" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Blog</a>
                            <ul class="dropdown-menu submenu" role="menu">                     
                                <c:forEach items="${blogCateListClient}" var="blogcateclient">
                                    <li><a href="blog-categories/${blogcateclient.blogCateID}.html">${blogcateclient.blogCateName}</a></li>    
                                    </c:forEach>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</header>
<script type="text/javascript">
    function deleteItem(productId, sizeId, colorId) {
        $(".topcart").remove("#cart");
        $(".topcart").add("<div id=\"cart\"></div>");
        var productid = productId;
        var sizeid = sizeId;
        var colorid = colorId;
        $.ajax({
            url: "orders/deleteitemCartInHeader.html",
            method: "POST",
            data: {productID: productid, sizeID: sizeid, colorID: colorid},
            dataType: 'html',
            success: function (response) {
                $("#cart").html(response);
            }
        });
    }
    ;
    function checkoutClick() {
        var email = $('input[name=order-emailUser]').val();
        if (email == "" || email == null) {
            $("#loginModal").modal("show");
        } else {
            window.location = "orders/checkout.html";
        }
    }
    ;
</script>