<%-- 
    Document   : review-rating
    Created on : Feb 28, 2017, 4:29:14 PM
    Author     : vinh.an
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>

<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-9 col-sm-8">
                <!-- HTML -->
                <div id="account-id">
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Your Reviews and Ratings</h4>                                                                 
                    <div class="order-history">
                        <table class="cart-table"> 
                            <tbody>
                                <tr>                                              
                                    <td>
                                        <div class="review-wrap">
                                            <p> Review Product -</p>
                                            <h4 class="product-title"><a href="./single-product.html">Product fashion</a></h4>
                                            <div class="ratings-wrap">
                                                <span class="ratings">
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                    <span class=" fa fa-star"></span>
                                                    <span class=" fa fa-star"></span>
                                                </span>
                                                <em>(6 reviews)</em>
                                            </div>  
                                            <p>James Fikaru</p>
                                            <p>Aug 25, 2014</p>
                                        </div>
                                    </td>                                                                                               
                                    <td>
                                        <h2>Not Good, Not Bad</h2>
                                        <p>It left colour at first wash. Got white at some places. Don't know how many times more I'll be able
                                            to wear it before it loses too much colour. </p>
                                    </td>                                                
                                </tr>
                                <tr>                                              
                                    <td>
                                        <div class="review-wrap">
                                            <p> Review Product -</p>
                                            <h4 class="product-title"><a href="./single-product.html">Product fashion</a></h4>
                                            <div class="ratings-wrap">
                                                <span class="ratings">
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                    <span class="act fa fa-star"></span>
                                                </span>
                                                <em>(6 reviews)</em>
                                            </div>  
                                            <p>cary aliya</p>
                                            <p>Sep 30, 2014</p>
                                        </div>
                                    </td>                                                                                               
                                    <td>
                                        <h2>Stylish</h2>
                                        <p>
                                            Top is very fashionable and trendy. looks good when u pair it with black jeans and have formal look if u tug in. i give 5 star for this.
                                            as it is my favorite top and whenever i wear this i get noticed my most and get good complements by people. </p>
                                    </td>                                                
                                </tr>
                            </tbody>
                        </table>
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