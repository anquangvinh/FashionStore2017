<%-- 
    Document   : return
    Created on : Feb 28, 2017, 5:02:44 PM
    Author     : vinh.an
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>


<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <!-- HTML -->
                <div id="account-id">
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Return Requests</h4>                                                                
                    <div class="order-history">
                        <table class="cart-table">
                            <tr>                                                
                                <th>Image</th>                                                                                             
                                <th>Qty</th>  
                                <th>Product Name</th>  
                                <th>total</th>
                                <th>Order ID</th>                                                
                                <th>Status</th>
                            </tr>
                            <tr>                                              
                                <td><img src="assets/images/products/fashion/5.jpg" class="img-responsive" alt=""/></td>                                                                                               
                                <td>x3</td>
                                <td>
                                    <h4><a href="./single-product.html">Product fashion</a></h4>
                                    <p>Size: M</p>
                                    <p>Color: White</p>
                                </td>
                                <td>
                                    <div class="item-price">$ 99.00</div>
                                </td> 
                                <td> OD31207</td>                                               
                                <td> Return </td>
                            </tr>                                         
                        </table>

                        <div class="table-btn">
                            <a href="my-account.html" class="btn-black">Back To Account</a>
                        </div>
                    </div>                               
                </div>
            </div>                        
        </div>
    </div>
</div>
<div class="clearfix space20"></div>