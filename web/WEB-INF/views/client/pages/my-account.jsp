<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true" />


<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-9 col-sm-8">
                <!-- HTML -->
                <div id="accordion">
                    <h4 class="accordion-toggle"><span>01</span>Account</h4>
                    <div class="accordion-content default">                                    
                        <div class="details-box">
                            <ul>
                                <li>                                                
                                    <a href="user/account-information/${sessionScope.findUsersID}.html"> <i class="fa fa-edit"></i> Edit your account information</a>
                                </li>
                                <li>                                               
                                    <a href="user/change-password/${sessionScope.findUsersID}.html"> <i class="fa fa-edit"></i> Change your password</a>
                                </li>
                                <li>                                              
                                    <a href="user/address-list/${sessionScope.findUsersID}.html"> <i class="fa fa-edit"></i> Manage address book entries</a>
                                </li>
                            </ul>
                        </div>                                    
                    </div>
                    <div class="clearfix"></div>
                    <h4 class="accordion-toggle"><span>02</span>order and review</h4>
                    <div class="accordion-content">                                    
                        <div class="details-box">
                            <ul>
                                <li>
                                    <a href="orders/order-history.html"> <i class="fa fa-edit"></i> View your order history</a>
                                </li>
                            </ul>
                        </div>                                    
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