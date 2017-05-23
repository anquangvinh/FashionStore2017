<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- PAGE HEADER -->
<%--<jsp:include page="../blocks/page-header.jsp" flush="true"/>--%>

<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true" />

<!-- SHOP CONTENT -->
<div class="shop-content" id="fs-shop-content-sub-category">

    <div class="container">
        <div class="row">
            <aside class="col-md-3 col-sm-4">

                <div class="side-widget">
                    <c:if test="${productsList != null}">
                        <h3><span>Shop by</span></h3>
                        <h5>Price Options ($)</h5>
                        <div class="col-xs-12"  style="padding: 0">
                            <div class="form-group col-xs-6" style="padding: 0; padding-right: 10px;">
                                <label for="fs-price-from">From:</label>
                                <input type="number" fs-subCategory="${subCateID}" class="form-control fs-product-price-filter" id="fs-price-from">
                            </div>
                            <div class="form-group col-xs-6"  style="padding: 0">
                                <label for="fs-price-to">To:</label>
                                <input type="number" class="form-control fs-product-price-filter" id="fs-price-to">
                            </div>
                        </div>
                        <p style="color: red" id="fs-filter-price-error"></p>
                        <p>
                            <button type="button" id="fs-btn-filter-price" class="btn-black pull-left">Filter Now</button>
                            <span class="pull-right fs-sc-range">
                                Price:<b> $<span fs-min-price="${minPrice}" id="fs-price-from-text">${minPrice}</span> - $<span fs-max-price="${maxPrice}" id="fs-price-to-text">${maxPrice}</span> </b>
                            </span>
                        </p>
                        <div class="clearfix space30"></div>

                        <h5 style="border-bottom: 1px solid #cccccc; padding-bottom: 10px">Color Options</h5>
                        <ul class="fs-ul-color">
                            <c:forEach items="${colorList}" var="color" varStatus="i">
                                <li style="margin-bottom: 15px;">
                                    <input id="fs-color-checkbox-${i.index + 1}" class="fs-color-checkbox" type="checkbox" value="${color}"/>
                                    <label style="font-weight: normal" for="fs-color-checkbox-${i.index + 1}">${color}</label>
                                </li>
                            </c:forEach>
                        </ul>
                        <div class="clearfix space20"></div>
                        <h5 style="border-bottom: 1px solid #cccccc; padding-bottom: 10px">Size Options</h5>
                        <ul class="fs-ul-size">
                            <c:forEach items="${sizeList}" var="size" varStatus="i">
                                <li style="margin-bottom: 10px;">
                                    <input id="fs-size-checkbox-${i.index + 1}" class="fs-size-checkbox" type="checkbox" value="${size.sizeLetter}"/>
                                    <label style="font-weight: normal" for="fs-size-checkbox-${i.index + 1}">${size.sizeLetter}</label>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
                <div class="clearfix space30"></div>
            </aside>
            <div class="col-md-9 col-sm-8">
                <c:if test="${productsList != null}">
                    <div class="filter-wrap">
                        <div class="row">
                            <div class="col-md-8 col-sm-8">
                                Sort by:
                                <select id="fs-sort-product-by" fs-subCategory="${subCateID}">
                                    <option value="1">Newest (Default)</option>
                                    <option value="2">Price (Low to High)</option>
                                    <option value="3">Price (High to Low)</option>
                                    <input type="hidden" name="findUsersID" value="${sessionScope.findUsersID}"/>
                                </select>
                            </div>
                            <div class="col-md-4 col-sm-4">
                                <span class="pull-right">
                                    Show:
                                    <select id="fs-number-of-item-on-page" fs-subCategory="${subCateID}">
                                        <option value="6">6 items</option>
                                        <option value="9">9 items</option>
                                        <input type="hidden" name="findUsersID" value="${sessionScope.findUsersID}"/>
                                    </select>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="pagenav-wrap">
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                Results: <span><span class="fs-change-currentProductPageInfo">${currentProductPageInfo}</span> of <span class="fs-number-of-products">${numberOfProducts}</span> items</span>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="pull-right">
                                    <em>Page:</em>
                                    <ul class="page_nav fs-ul-page-nav">
                                        <li><span class="fs-page-number fs-page-number-active" fs-page-number="1" fs-subCategory="${subCateID}">1</span></li>
                                            <c:forEach begin="2" end="${Math.ceil(numberOfProducts/6)}" var="p">
                                            <li><span class="fs-page-number" fs-page-number="${p}" fs-subCategory="${subCateID}">${p}</span></li>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="space50"></div>
                    <div class="row" style="position: relative;">
                        <div id="fs-ajax-loading"></div>
                        <div id="fs-change-data-here">
                            <c:forEach items="${productsList}" var="product">
                                <div class="col-md-4 col-sm-6">
                                    <div class="product-item">
                                        <div class="item-thumb">
                                            <c:if test="${product.productDiscount > 0}">
                                                <span class="badge offer">-${product.productDiscount}%</span>
                                            </c:if>
                                            <img src="assets/images/products/${product.urlImg}" 
                                                 class="img-responsive" 
                                                 alt="${product.urlImg}"
                                                 fs-product-for-img="${product.productID}"/>
                                            <div class="overlay-rmore fa fa-search quickview fs-product-modal" 
                                                 data-toggle="modal" 
                                                 fs-product="${product.productID}" 
                                                 fs-product-modal-color="${product.productColorListWorking[0].colorID}">
                                            </div>
                                            <div class="product-overlay">
<!--                                                 <a href="#" class="addcart fa fa-shopping-cart"></a> -->
                                                <a class="likeitem fa fa-heart-o fs-wl-add-sub"
                                                   fs-userID="${sessionScope.findUsersID}" fs-productID="${product.productID}"></a>
                                                <input type="hidden" name="emailUser" value="${sessionScope.emailUser}" />
                                            </div>
                                        </div>
                                        <div class="product-info">
                                            <h4 class="product-title">
                                                <a href="${product.productID}-${product.productColorListWorking[0].colorID}-${product.productNameNA}.html">
                                                    ${product.productName}
                                                </a>
                                            </h4>
                                            <span class="product-price">
                                                <c:if test="${product.productDiscount > 0}">
                                                    <small class="cutprice">$ ${product.price}0 </small>  $
                                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${product.price - (product.price*product.productDiscount/100)}" var="prodPrice"/>
                                                    ${fn:replace(prodPrice, ",", ".")}
                                                </c:if>
                                                <c:if test="${product.productDiscount == 0}">
                                                    $ ${product.price}0
                                                </c:if>
                                            </span>
                                            <div class="item-colors" style="height: 25px;">
                                                <c:if test="${product.productColorListWorking.size() > 1}">
                                                    <c:forEach items="${product.productColorListWorking}" var="color">
                                                        <img src="assets/images/products/colors/${color.urlColorImg}" 
                                                             class="img-responsive fs-index-color-img" 
                                                             fs-index-color-img="${color.colorID}" 
                                                             fs-product="${product.productID}" 
                                                             alt="${color.urlColorImg}" 
                                                             title="${color.color}"/>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="pagenav-wrap">
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                Results: <span><span class="fs-change-currentProductPageInfo">${currentProductPageInfo}</span> of <span id="fs-number-of-products" class="fs-number-of-products">${numberOfProducts}</span> items</span>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="pull-right">
                                    <em>Page:</em>
                                    <ul class="page_nav fs-ul-page-nav">
                                        <li><span class="fs-page-number fs-page-number-active" fs-page-number="1" fs-subCategory="${subCateID}">1</span></li>
                                            <c:forEach begin="2" end="${Math.ceil(numberOfProducts/6)}" var="p">
                                            <li><span class="fs-page-number" fs-page-number="${p}" fs-subCategory="${subCateID}">${p}</span></li>
                                              <input type="hidden" name="findUsersID" value="${sessionScope.findUsersID}"/>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${productsList == null}">
                    <h1>We are coming...</h1>
                </c:if>
            </div>
        </div>
        <div class="space50"></div>
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div style="border-top: 1px solid #CCC; padding-top: 17px; padding-bottom: 22px; min-height: 200px">
                    <h5 style="margin-bottom: 20px; font-family: Montserrat;
                        font-size: 14px; font-weight: bold;
                        text-transform: uppercase; color: #333;">
                        Recently Products
                    </h5>
                    <p id="fs-localStorage-result" ></p>
                    <div id="fs-recent-view-product" class="col-md-12 col-sm-12">

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="clearfix space20"></div>

<!-- MODAL -->
<jsp:include page="../blocks/modal.jsp" flush="true" />
<jsp:include page="../blocks/messmodal.jsp" flush="true"/>