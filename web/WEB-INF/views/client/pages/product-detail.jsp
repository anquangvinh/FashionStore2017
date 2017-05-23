<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true"/>

<div class="space10"></div>
<!-- MAIN CONTENT -->
<div class="shop-single" id="fs-product-detail-page">
    <div class="container">
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div id="error-product-detail">

                </div>
                <div class="row">
                    <div class="col-md-5 col-sm-6" id="fs-product-detail-slide-img">                                    
                        <div class="owl-carousel prod-slider sync1" >
                            <c:forEach items="${targetColor.productSubImgsList}" var="subImg">
                                <div class="item">
                                    <img src="assets/images/products/subImg/${subImg.urlImg}" alt="${subImg.urlImg}">
                                    <a href="assets/images/products/subImg/${subImg.urlImg}" rel="prettyPhoto[gallery2]" title="Product" class="caption-link"><i class="fa fa-arrows-alt"></i></a>
                                </div>
                            </c:forEach>
                        </div>

                        <div  class="owl-carousel sync2">
                            <c:forEach items="${targetColor.productSubImgsList}" var="subImg">
                                <div class="item">
                                    <img src="assets/images/products/subImg/${subImg.urlImg}" alt="${subImg.urlImg}">
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-md-7 col-sm-6">
                        <div class="product-single">
                            <div class="ps-header fs-product-detail-name" fs-product-id="${targetProduct.productID}">
                                <h3>${targetProduct.productName}</h3>
                                <div class="ps-price"><h1>$ ${targetProduct.price}0</h1></div>
                            </div>
                            <div class="ps-stock">
                                Availability: <span style="color: #d6644a" class="fs-quantity-in-stock">---</span>
                                <div class="fs-display-none" id="fs-show-quantity"></div>
                            </div>
                            <div class="sep"></div>
                            <div class="ps-color fs-product-color">
                                <p>Color<span>*</span></p>
                                <c:forEach items="${targetProduct.productColorListWorking}" var="color">
                                    <div class="fs-product-color-border <c:if test="${color.colorID == targetColor.colorID}">fs-product-selected</c:if>" >
                                        <img fs-color="${color.colorID}" src="assets/images/products/colors/${color.urlColorImg}" class="img-responsive" alt="${color.urlColorImg}" title="${color.color}"/>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="fs-clear-fix"></div>
                            <div class="space10"></div>
                            <div class="row select-wraps">
                                <div class="col-md-7 col-xs-7">
                                    <p style="margin-bottom: 8px">Size<span>*</span></p>

                                    <div id="fs-product-size">
                                        <c:forEach items="${targetColor.sizeList}" var="size">
                                            <c:if test="${size.status != 0}">
                                                <div class="fs-particular-size <c:if test="${size.quantity == 0}">fs-unselectable</c:if>" 
                                                     fs-product="${targetProduct.productID}" 
                                                     fs-size="${size.sizeID}">
                                                    ${size.productSize}
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="col-md-4 col-xs-5 col-lg-3">
                                    <p style="margin-bottom: 8px !important;">Quantity<span>*</span></p>
                                    <div class="input-group">
                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-danger fs-btn-number fs-btn-quantity-minus" data-type="minus" disabled>
                                                <span class="glyphicon glyphicon-minus"></span>
                                            </button>
                                        </span>

                                        <input type="text" name="quantity" id="quantity" class="form-control fs-input-number text-center" value="1" min="1" max="10" disabled>

                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-success fs-btn-number fs-btn-quantity-plus" data-type="plus" disabled>
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="space20"></div>
                            <div class="share">
                                <span>
                                    <a href="#" class="fa fa-heart-o"></a>
                                </span>
                                <div class="addthis_native_toolbox"></div>
                            </div>
                            <div class="space20"></div>
                            <div class="sep"></div>
                            <button class="btn addtobag" id="fs-product-detail-add-to-cart">Add to Bag</button>
                        </div>
                    </div>
                </div>
                <div class="clearfix space40"></div>
                <div role="tabpanel">
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="active">
                            <a href="#fs-menu-tab-1" data-toggle="tab">
                                Product Description
                            </a>
                        </li>
                        <li>
                            <a href="#fs-menu-tab-2" data-toggle="tab">
                                Customer Review
                            </a>
                        </li>
                    </ul>
                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div class="tab-pane active" id="fs-menu-tab-1">
                            ${targetProduct.productDescription}
                        </div>
                        <div class="tab-pane" id="fs-menu-tab-2">
                            <div class="reviews-tab">
                                <div class="row">
                                    <div class="col-xs-5 col-sm-3 col-sm-offset-3">
                                        <div class="rating-block text-center">
                                            <h4>Average user rating</h4>
                                            <h2 class="bold padding-bottom-7">${ratingAVR} <small>/ 5</small></h2>
                                            <select id="fs-rating-star-result" name="fs-rating-star-result" data-current-rating="${ratingAVR}">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-5">
                                        <h4>Rating breakdown</h4>
                                        <div class="pull-left">
                                            <div class="pull-left" style="width:35px; line-height:1;">
                                                <div style="height:9px; margin:5px 0;">5 <span class="glyphicon glyphicon-star"></span></div>
                                            </div>
                                            <div class="pull-left" style="width:180px;">
                                                <div class="progress" style="height:9px; margin:8px 0;">
                                                    <div class="progress-bar progress-bar-success" aria-valuenow="5" aria-valuemin="0" aria-valuemax="5" style="width: ${(ratingfor5/numberOfRating)*100}%">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pull-right" style="margin-left:10px;">${ratingfor5}</div>
                                        </div>
                                        <div class="pull-left">
                                            <div class="pull-left" style="width:35px; line-height:1;">
                                                <div style="height:9px; margin:5px 0;">4 <span class="glyphicon glyphicon-star"></span></div>
                                            </div>
                                            <div class="pull-left" style="width:180px;">
                                                <div class="progress" style="height:9px; margin:8px 0;">
                                                    <div class="progress-bar progress-bar-primary" aria-valuenow="4" aria-valuemin="0" aria-valuemax="5" style="width: ${(ratingfor4/numberOfRating)*100}%">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pull-right" style="margin-left:10px;">${ratingfor4}</div>
                                        </div>
                                        <div class="pull-left">
                                            <div class="pull-left" style="width:35px; line-height:1;">
                                                <div style="height:9px; margin:5px 0;">3 <span class="glyphicon glyphicon-star"></span></div>
                                            </div>
                                            <div class="pull-left" style="width:180px;">
                                                <div class="progress" style="height:9px; margin:8px 0;">
                                                    <div class="progress-bar progress-bar-info" aria-valuenow="3" aria-valuemin="0" aria-valuemax="5" style="width: ${(ratingfor3/numberOfRating)*100}%">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pull-right" style="margin-left:10px;">${ratingfor3}</div>
                                        </div>
                                        <div class="pull-left">
                                            <div class="pull-left" style="width:35px; line-height:1;">
                                                <div style="height:9px; margin:5px 0;">2 <span class="glyphicon glyphicon-star"></span></div>
                                            </div>
                                            <div class="pull-left" style="width:180px;">
                                                <div class="progress" style="height:9px; margin:8px 0;">
                                                    <div class="progress-bar progress-bar-warning" aria-valuenow="2" aria-valuemin="0" aria-valuemax="5" style="width: ${(ratingfor2/numberOfRating)*100}%">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pull-right" style="margin-left:10px;">${ratingfor2}</div>
                                        </div>
                                        <div class="pull-left">
                                            <div class="pull-left" style="width:35px; line-height:1;">
                                                <div style="height:9px; margin:5px 0;">1 <span class="glyphicon glyphicon-star"></span></div>
                                            </div>
                                            <div class="pull-left" style="width:180px;">
                                                <div class="progress" style="height:9px; margin:8px 0;">
                                                    <div class="progress-bar progress-bar-danger" aria-valuenow="1" aria-valuemin="0" aria-valuemax="5" style="width: ${(ratingfor1/numberOfRating)*100}%">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pull-right" style="margin-left:10px;">${ratingfor1}</div>
                                        </div>
                                    </div>			
                                </div>	
                                <div class="sep"></div>
                                <div class="fs-display-none" id="fs-number-of-rating" fs-nort="${numberOfRating}"></div>
                                <c:forEach items="${targetProduct.productRatingList}" var="review" varStatus="no">
                                    <p>
                                        <b>${review.user.firstName} ${review.user.lastName}</b>, <fmt:formatDate value="${review.ratingDate}" pattern="dd MMM, yyyy" timeZone="US"/>
                                    </p>
                                    <p style="margin-top: 7px; margin-bottom: 7px">
                                        ${review.review}
                                    </p>
                                    <select id="fs-rating-star-${no.index}" name="fs-rating-star-${no.index}" data-current-rating="${review.rating}">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                    <div class="sep"></div>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${sessionScope.findUsersID != null && checkUserRated == 1}">
                                        <h3>You already voted for this product! </h3>
                                    </c:when>
                                    <c:when test="${sessionScope.findUsersID != null && checkUserRated == 0}">
                                        <div id="fs-form-rating-review">
                                            <h2 style="font-weight: 400">Write a Review</h2>
                                            <form>
                                                <div>Rating *:
                                                    <span id="fs-div-vote-value">
                                                        <strong style="font-size: 20px; color: #d6644a">1 </strong>Star
                                                    </span>
                                                </div>
                                                <div class="clearfix space10"></div>
                                                <select id="fs-rating-star" name="fs-rating-star">
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>

                                                <div class="clearfix space20"></div>
                                                <label>Your Review *</label>
                                                <textarea id="fs-review-product"></textarea>
                                                <br>
                                                <div class="clearfix space20"></div>
                                                <button type="button" 
                                                        id="fs-btn-rating-review" 
                                                        fs-user-id="${sessionScope.findUsersID}" 
                                                        fs-product-id="${targetProduct.productID}"
                                                        class="btn-black pull-left">
                                                    Submit
                                                </button>
                                                <div style="position: relative; width: 50px; height: 50px; float: left">
                                                    <span id="fs-ajax-loading-2"></span>
                                                </div>   
                                                <div class="clearfix space10"></div>
                                            </form>
                                        </div>
                                    </c:when>    
                                    <c:otherwise>
                                        <button type="button" id="fs-btn-login-to-review">Login to Write a Review</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix space40"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <h5 class="heading space40"><span>Recently Products</span></h5>
                        <div class="product-carousel3">
                            <c:if test="${recentProductList != null}">
                                <c:forEach items="${recentProductList}" var="prod">
                                    <div class="pc-wrap">
                                        <div class="product-item">
                                            <div class="item-thumb">
                                                <c:if test="${prod.productDiscount > 0}">
                                                    <div class="badge offer">-${prod.productDiscount}%</div>
                                                </c:if>
                                                <img src="assets/images/products/${prod.urlImg}" 
                                                     class="img-responsive" 
                                                     alt="${prod.urlImg}" 
                                                     fs-product-for-img="${prod.productID}"/>
                                                <div class=" fs-product-modal" 
                                                     fs-product="${prod.productID}" 
                                                     fs-product-modal-color="${prod.productColorListWorking[0].colorID}" 
                                                     data-toggle="modal" ></div>
                                                <div class="product-overlay">
                                                    <a href="#" class="addcart fa fa-shopping-cart"></a>
                                                    <a class="likeitem fa fa-heart-o fs-wl-add-lsp"
                                                       fs-userID="${sessionScope.findUsersID}" fs-productID="${prod.productID}" ></a>
                                                    <input type="hidden" name="emailUser" value="${sessionScope.emailUser}" />
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <h4 class="product-title">
                                                    <a href="${prod.productID}-${prod.productColorListWorking[0].colorID}-${prod.productNameNA}.html">
                                                        ${prod.productName}
                                                    </a>
                                                </h4>

                                                <span class="product-price">
                                                    <c:if test="${prod.productDiscount > 0}">
                                                        <small class="cutprice">$ ${prod.price}0 </small>  $
                                                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.price - (prod.price*prod.productDiscount/100)}" var="prodPrice"/>
                                                        ${fn:replace(prodPrice, ",", ".")}

                                                    </c:if>
                                                    <c:if test="${prod.productDiscount == 0}">
                                                        $ ${prod.price}0
                                                    </c:if>
                                                </span>

                                                <div class="item-colors">
                                                    <c:if test="${prod.productColorListWorking.size() > 1}">
                                                        <c:forEach items="${prod.productColorListWorking}" var="color">
                                                            <img src="assets/images/products/colors/${color.urlColorImg}" 
                                                                 class="img-responsive fs-index-color-img" 
                                                                 fs-index-color-img="${color.colorID}" 
                                                                 fs-product="${prod.productID}" 
                                                                 alt="${color.urlColorImg}" 
                                                                 title="${color.color}"/>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="clearfix space20"></div>
            </div>
        </div>
    </div>
</div>

<div class="clearfix space20"></div>

<!-- BREADCRUMBS -->

<jsp:include page="../blocks/modal.jsp" flush="true"/>
<script type="text/javascript">
    var productObj = {
        productID: '${targetProduct.productID}',
        productColorID: '${targetColor.colorID}',
        productName: '${targetProduct.productName}',
        productNameNA: '${targetProduct.productNameNA}',
        productImg: '${targetProduct.urlImg}'
    };

    // Check browser support
    if (typeof (Storage) !== "undefined") {

        if (localStorage.getItem("productsArrLocal") == null) {
            var productArr = [];
            productArr.push(productObj);
            localStorage.setItem("productsArrLocal", JSON.stringify(productArr));
        } else {
            var productsArrLocal = JSON.parse(localStorage.getItem("productsArrLocal"));
            var count = 0;
            for (var lc = 0; lc < productsArrLocal.length; lc++) {
                if (productsArrLocal[lc].productID == productObj.productID) {
                    count++;
                    break;
                }
            }

            if (count == 0) {
                productsArrLocal.push(productObj);
                localStorage.setItem("productsArrLocal", JSON.stringify(productsArrLocal));
            }
        }
    } else {
        document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Storage...";
    }
</script>
