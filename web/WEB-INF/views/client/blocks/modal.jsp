<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="productModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-ku">
        <div class="modal-content">
            <button type="button" class="close fs-modal-close" data-dismiss="modal" aria-hidden="true">
                <i class="fa fa-times"></i>
            </button>
            <div class="row">
                <div class="col-md-5 col-sm-6" id="fs-product-modal-slide-img">
                    <div class="owl-carousel sync1 fs-main-product-img">

                    </div>

                    <div class="owl-carousel sync2 fs-main-product-img">

                    </div>
                </div>
                <div class="col-md-7 col-sm-6">
                    <div class="product-single fs-modal-product">
                        <div id="error-cart-product-modal">
                        </div>
                        <div class="ps-header">
                            <h3 class="fs-product-name"></h3>
                            <div class="ps-price fs-product-price"></div>
                        </div>

                        <div class="ps-stock">
                            Available: <span style="color: #d6644a" class="fs-quantity-in-stock">---</span>
                            <div class="fs-display-none" id="fs-show-quantity"></div>
                        </div>
                        <div class="sep"></div>
                        <div class="ps-color fs-product-modal-color">
                            <p>Color<span>*</span></p>                         
                            <div class="fs-product-modal-color-border">

                            </div>
                        </div>
                        <div class="fs-clear-fix"></div>
                        <div class="space10"></div>
                        <div class="row select-wraps">
                            <div class="col-md-7 col-xs-7">
                                <p>Size<span>*</span></p>
                                <div id="fs-product-modal-size">

                                </div>
                            </div>

                            <div class="col-md-4 col-xs-5 col-lg-4">
                                <p style="margin-bottom: 8px !important;">Quantity<span>*</span></p>
                                <div class="input-group">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger fs-modal-btn-number fs-modal-btn-quantity-minus" data-type="minus" disabled>
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
                                    </span>

                                    <input type="text" name="" class="form-control fs-modal-input-number text-center" value="1" min="1" max="10" disabled>

                                    <span class="input-group-btn">
                                        <button type="button" disabled class="btn btn-success fs-modal-btn-number fs-modal-btn-quantity-plus" data-type="plus">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="space20"></div>
                        <div class="share">
                            <span>
                                <a class="fa fa-heart-o fs-wl-add-detail"
                                   fs-userID="${sessionScope.findUsersID}" ></a>
                                   <input type="hidden" name="emailUser" value="${sessionScope.emailUser}" />
                                <a href="#" class="fa fa-signal" onclick="return false;"></a>
                                <a href="#" class="fa fa-envelope-o" onclick="return false;"></a>
                            </span>
                            <div class="addthis_native_toolbox"></div>
                        </div>
                        <div class="space20"></div>
                        <div class="sep"></div>
                        <!--<form method="POST" action="">-->
                        <!--<a class="fs-modal-btn-addtobag" href="#">Add to Bag</a>-->
                        <button class="fs-modal-btn-addtobag">Add to Bag</button>
                        <!--</form>-->
                        <a class="fs-product-modal-link-to-detail" href="#">Go to Details</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>