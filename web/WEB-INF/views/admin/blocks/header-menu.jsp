<%-- 
    Document   : menu
    Created on : Mar 3, 2017, 9:25:38 AM
    Author     : vinh.an
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">Smile - Fashion Shop</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li><a href="admin/logout.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
                    </div>
                    <!-- /input-group -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-key fa-fw"></i> User Role<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/user/role.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/user/role/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-users fa-fw"></i> User<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/user/list.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>

                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-cube fa-fw"></i> Product Category<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/product-category.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/product-category/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-cubes fa-fw"></i> Product SubCategory<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/product-subcategory.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/product-subcategory/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-diamond fa-fw"></i> Product<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/product.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/product/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-shopping-cart fa-fw"></i> Orders<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/orders/list.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/orders/orderchart.html"><i class="fa fa-list" aria-hidden="true"></i> Statistics</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-shopping-cart fa-fw"></i> Discount<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/orders/discountlist.html"><i class="fa fa-wpforms" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/orders/discountadd.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-thumb-tack fa-fw"></i> Blog Category<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/blog/category.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/blog/category/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

                <li>
                    <a href="#"><i class="fa fa-newspaper-o fa-fw"></i> Blog<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/blog/list.html"><i class="fa fa-list" aria-hidden="true"></i> List</a>
                        </li>
                        <li>
                            <a href="admin/blog/create.html"><i class="fa fa-plus" aria-hidden="true"></i> New</a>
                        </li>
                        <li>
                            <a href="admin/blog/listchartblog.html"><i class="fa fa-list" aria-hidden="true"></i> Statistics</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>