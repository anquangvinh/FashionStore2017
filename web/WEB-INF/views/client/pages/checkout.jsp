<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>

<div class="space10"></div>

<!-- MAIN CONTENT -->
<div class="shop-single">
    <div class="container">
        <div class="row">
            <div class="col-md-9 col-sm-8">
                <!-- HTML -->
                <div>
                    <div id="error-checkout">
                        ${error}
                    </div>
                    <input type="hidden" name="emailUser" value="${sessionScope.emailUser}"/>
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Checkout Method</h4>
                    <div class="account-form">
                        <form id="checkout-form" method="POST" action="orders/checkout.html">
                            <c:choose>
                                <c:when test="${userAddressList.size() == 0}">
                                    <input type="hidden" name="addressSize" value="0"/>
                                    <ul class="form-list row">
                                        <li class="col-md-12 col-sm-12" style="padding-bottom: 10px;">
                                            <span style="font-weight: 900; font-size: 16px;">Save address to your address list</span>
                                        </li>
                                        <li class="col-md-12 col-sm-12" style="padding-bottom: 20px;">
                                            <div class="buttons-set">
                                                <a href="user/address-list/${emailU.userID}.html" style="width: 250px;" class="btn btn-black">ADD ADDRESS</a>
                                            </div>
                                        </li>
                                        <li class="col-md-12 col-sm-12" style="padding-bottom: 10px;">
                                            <span style="font-weight: 900; font-size: 16px;">Please enter your information below</span>
                                        </li>
                                        <li class="col-md-12 col-sm-12 shipping-address" style="display: list-item;">
                                            <ul class="form-list row">
                                                <li class="col-md-8 col-sm-6">
                                                    <label>First Name <em>*</em></label>
                                                    <p id="error-checkout-firstname" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffFirstname" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-6">
                                                    <label>Last Name <em>*</em></label>
                                                    <p id="error-checkout-lastname" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffLastname" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-6">
                                                    <label>Phone Number <em>*</em></label>
                                                    <p id="error-checkout-phone" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffPhone" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-12">
                                                    <label >Address <em>*</em></label>
                                                    <p id="error-checkout-address" style="color: red; font-weight: 700;"></p>
                                                    <input type="text"  class="input-text" name="diffAddress" value=""/>
                                                </li>
                                                <li class="clearfix"></li>
                                            </ul>
                                        </li>
                                        <li class="col-md-12 col-sm-12 discount-code">
                                            <p class="help-block" id="fs-checkout-discountvou-error" style="color: red;"></p>
                                            <ul class="form-list row discount-ul">
                                                <li class="col-md-6 col-sm-6 discount-inputs">
                                                    <label><span style="font-weight: 900; font-size: 16px;">Your Discount Code</span></label>
                                                    <div class="input-box">
                                                        <p class="help-block" id="fs-checkout-discountvou-error" style="color: red;"></p>
                                                        <input class="input-text" id="coupon_code" name="coupon_code" value=""/>
                                                    </div>
                                                </li>
                                                <li class="col-md-6 col-sm-6 discount-buttons" style="padding-top: 30px;">
                                                    <div class="buttons-set">
                                                        <button style="height: 40px;" type="button" title="Apply Discount Code" class="btn-black" id="discount-order" ><span><span>Apply Discount Code</span></span></button>
                                                    </div>
                                                </li>
                                                <li class="col-md-8 col-sm-6 discount-show">

                                                </li>
                                            </ul>
                                        </li>
                                        <li class="col-md-12 col-sm-12">
                                            <span style="font-weight: 900; font-size: 16px;">Note (Eg: deliver during office hours)</span>
                                            <p id="error-checkout-note" style="color: red; font-weight: 700;"></p>
                                            <input type="text" class="input-text" name="note"/>
                                        </li>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <ul class="form-list row">
                                        <input type="hidden" name="addressSize" value="1"/>
                                        <li class="col-md-12 col-sm-12" style="padding-bottom: 10px;">
                                            <span style="font-weight: 900; font-size: 16px;">Please choose your address method below</span>
                                        </li>
                                        <c:forEach items="${userAddressList}" var="userAddress">
                                            <li class="col-md-12 col-sm-12">
                                                <input type="radio" id="diff-address" name="address-chose" value="${userAddress.addressID}"/>
                                                <label style="font-weight: normal;"><span style="font-weight: 700;">Ship to:</span> &nbsp ${userAddress.getUser().firstName} &nbsp ${userAddress.getUser().lastName}<br/>
                                                    <span style="font-weight: 700;">Address:</span> ${userAddress.address}<br/>
                                                    <span style="font-weight: 700;">Phone:</span> &nbsp ${userAddress.phoneNumber}
                                                </label>
                                            </li>
                                        </c:forEach>
                                        <li class="col-md-12 col-sm-12" style="padding-bottom: 20px;">
                                            <div class="buttons-set">
                                                <a href="user/address-list/${emailU.userID}.html" style="width: 250px;" class="btn btn-black">ADD MORE ADDRESS</a>
                                            </div>
                                        </li>
                                        <li class="col-md-12 col-sm-12">
                                            <input id="diff-address" name="address-chose" value="difference" type="radio" class="input-chkbox"/>
                                            <label style="font-weight: normal;"><span style="font-weight: 700;"> Ship to a different address?</span></label>
                                        </li>
                                        <li class="col-md-12 col-sm-12 shipping-address">
                                            <ul class="form-list row">
                                                <li class="col-md-8 col-sm-6">
                                                    <label>First Name <em>*</em></label>
                                                    <p id="error-checkout-firstname" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffFirstname" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-6">
                                                    <label>Last Name <em>*</em></label>
                                                    <p id="error-checkout-lastname" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffLastname" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-6">
                                                    <label>Phone Number <em>*</em></label>
                                                    <p id="error-checkout-phone" style="color: red; font-weight: 700;"></p>
                                                    <input type="text" class="input-text" name="diffPhone" value=""/>
                                                </li>
                                                <li class="col-md-8 col-sm-12">
                                                    <label >Address <em>*</em></label>
                                                    <p id="error-checkout-address" style="color: red; font-weight: 700;"></p>
                                                    <input type="text"  class="input-text" name="diffAddress" value=""/>
                                                </li>
                                                <li class="clearfix"></li>
                                            </ul>
                                        </li>
                                        <li class="col-md-12 col-sm-12 discount-code">
                                            <p class="help-block" id="fs-checkout-discountvou-error" style="color: red;"></p>
                                            <ul class="form-list row discount-ul">
                                                <li class="col-md-6 col-sm-6 discount-inputs">
                                                    <label><span style="font-weight: 900; font-size: 16px;">Your Discount Code</span></label>
                                                    <div class="input-box">
                                                        <p class="help-block" id="fs-checkout-discountvou-error" style="color: red;"></p>
                                                        <input class="input-text" id="coupon_code" name="coupon_code" value=""/>
                                                    </div>
                                                </li>
                                                <li class="col-md-6 col-sm-6 discount-buttons" style="padding-top: 30px;">
                                                    <div class="buttons-set">
                                                        <button style="height: 40px;" type="button" title="Apply Discount Code" class="btn-black" id="discount-order" ><span><span>Apply Discount Code</span></span></button>
                                                    </div>
                                                </li>
                                                <li class="col-md-8 col-sm-6 discount-show">

                                                </li>
                                            </ul>
                                        </li>
                                        <li class="col-md-12 col-sm-12">
                                            <span style="font-weight: 900; font-size: 16px;">Note (Eg: deliver during office hours)</span>
                                            <p id="error-checkout-note" style="color: red; font-weight: 700;"></p>
                                            <input type="text" class="input-text" name="note"/>
                                        </li>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                            <div class="buttons-set">
                                <input id="btnCheckoutPlaceOrder" class="btn-black" type="submit" value="Place Order"/>
                            </div>
                        </form>
                    </div>
                    <div class="clearfix"></div>   
                </div>
            </div>
            <div class="col-md-3 col-sm-4">
                <div class="side-widget space50">
                    <h3> <span> Your order </span></h3>
                    <div>
                        <table class="cart-table"> 
                            <tbody></tbody>
                            <c:forEach items="${cartList}" var="item">
                                <tr>                                              
                                    <td colspan="2">
                                        <div class="item-img col-md-5 col-sm-5">
                                            <a href="${item.getProduct().productID}-${item.getProduct().productColorList[0].colorID}-${item.getProduct().productNameNA}.html">
                                                <img src="assets/images/products/${item.getProduct().getUrlImg()}" class="img-responsive" alt=""/>
                                            </a>
                                        </div>
                                        <div class="item-info col-md-7 col-sm-7">
                                            <h4>${item.getProduct().productName}</h4>
                                            <p>
                                                &nbsp Size: ${item.getSizesByColor().getProductSize()} 
                                                <img fs-color="${item.getSizesByColor().getColor().colorID}" 
                                                     src="assets/images/products/colors/${item.getSizesByColor().getColor().getUrlColorImg()}" 
                                                     class="img-responsive" 
                                                     alt="${item.getSizesByColor().getColor().urlColorImg}" 
                                                     title="${item.getSizesByColor().getColor().getColor()}"
                                                     style="width: 20px; height: 20px; border: 2px;"/> 
                                            </p>
                                            <p>&nbsp ${item.quantity} &nbsp x $${item.getProduct().getPrice()}</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tfoot class="foot">
                                <tr>
                                    <th>Discount</th>
                                    <td>
                                        <div class="">-$0.0</div>
                                    </td> 
                                </tr>
                                <tr>
                                    <th>Order Total</th>
                                    <td>
                                        <div class="grandTotal">$${grandTotal}</div>
                                    </td> 
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix space20"></div>
<script type="text/javascript">
    function enterDiscountAgain() {
        $("#discountShow").remove();
        $.get("orders/ajax/nodiscount.html", function (responsenodiscount) {
            $(".foot").html(responsenodiscount);
        });
    };
</script>
