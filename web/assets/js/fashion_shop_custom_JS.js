$(document).ready(function() {
    /* USER JS AREA */
    /* REGISTER FORM */
    $("#txtBirthday").datepicker({
        dateFormat: "dd/mm/yy",
        showAnim: "drop",
        changeMonth: true,
        changeYear: true,
        defaultDate: '01/01/1960',
        yearRange: "1960:1999"
    });

    $("#loginModal").on("change", "#fs-upImage", function() {
        var type = $(this)[0].files[0].type;
        var arrayMimeType = ['image/jpeg', 'image/png'];

        if (arrayMimeType.indexOf(type) == -1) { //Khong co trong danh sach mime hinh
            $(this).val("");
            $("#fs-upfile-create-user-error").text("Select 'jpeg' , 'png'");
        } else {
            $("#fs-upfile-create-user-error").text("");
        }
    });

    $("#fs-form-update-account").on("change", "#fs-upImage-acc", function() {
        var type = $(this)[0].files[0].type;
        var arrayMimeType = ['image/jpeg', 'image/png'];

        if (arrayMimeType.indexOf(type) == -1) { //Khong co trong danh sach mime hinh
            $(this).val("");
            $("#fs-upfile-update-user-error").text("Select 'jpeg' , 'png'");
        } else {
            $("#fs-upfile-update-user-error").text("");
        }
    });


    /* --------------- SEARCH PRODUCT BY NAME -------------------- */
    $("body").on("keyup", "#fs-search-top-input", function() {
        var kw = $(this).val();
        $.ajax({
            url: "ajax/searchProductByKeyWord.html",
            method: "POST",
            data: {
                keyword: kw
            },
            success: function(response) {
                $("#fs-on-search-result").html(response);
            }
        });
    });


    /* --------------- PRODUCT INDEX -------------------- */
    /* LOAD IMG TO RECENT VIEW FROM LOCALSTORAGE */
    // Check browser support
    if (typeof(Storage) != "undefined") {
        if (localStorage.getItem("productsArrLocal") != null) {
            var productsArrLocal = JSON.parse(localStorage.getItem("productsArrLocal"));
            productsArrLocal.reverse();
            var liProdStr = "";
            $.each(productsArrLocal, function(i, prod) {
                liProdStr += "<div>\n\
                                <a href=\"" + prod.productID + "-" + prod.productColorID + "-" + prod.productNameNA + ".html\">\n\
                                    <img style=\"width: 150px\" src=\"assets/images/products/" + prod.productImg + "\" class=\"img-responsive\" alt=\"" + prod.productImg + "\"/>\n\
                                </a>\n\
                            </div>";
            });
            $("#fs-recent-view-product").html(liProdStr);
        }
    } else {
        $("#fs-localStorage-result").text("Sorry, your browser does not support Web Storage...");
    }

    $("#fs-recent-view-product").owlCarousel({
        items: 6,
        margin: 35,
        loop: true,
        navigation: true,
        autoPlay: 2500,
        stopOnHover: true
    });

    /* Rating */
    for (var i = 0; i < 3; i++) {
        var rating = $('#fs-index-top-rating-result-' + i).data('current-rating');
        $('#fs-index-top-rating-result-' + i).barrating({
            theme: 'fontawesome-stars-o',
            initialRating: rating,
            showSelectedRating: false,
            readonly: true
        });
    }
    /* SLIDE PRODUCTS IN INDEX */
    $("#isotope").isotope({
        filter: '.isotope_to_all',
        sortBy: 'random'
    });

    /* INDEX - CHANGE IMG WHEN CHOOSE COLOR */
    $("body").on("click", ".fs-index-color-img", function() {
        var colorID = $(this).attr("fs-index-color-img");
        var productID = $(this).attr("fs-product");
        //Change link of product Title to productDetail
        var elementProductLink = $(this).parent().siblings("h4").find("a");
        var productLink = elementProductLink.attr("href");
        var productLinkArray = productLink.split("-");
        productLinkArray[1] = colorID;
        var newLink = productLinkArray.join("-");
        elementProductLink.attr("href", newLink);
        //Change color attr of product Modal
        $(this).parent().parent().siblings("div.item-thumb").find("div.fs-product-modal").attr("fs-product-modal-color", colorID);
        //Call Ajax
        $.ajax({
            url: "ajax/color.html",
            method: "POST",
            data: {
                colorID: colorID
            },
            dataType: 'json',
            success: function(response) {
                $("img[fs-product-for-img=" + productID + "]")
                    .hide()
                    .attr("src", "assets/images/products/subImg/" + response.productSubImgsList[0].urlImg)
                    .attr("alt", response.productSubImgsList[0].urlImg)
                    .fadeIn(600);
            }
        });
    });

    /* FUNCTION FOR OWL CAROUSEL */
    function fsCreateOwlCarousel() {
        var sync1 = $(".sync1");
        var sync2 = $(".sync2");
        sync1.owlCarousel({
            singleItem: true,
            slideSpeed: 1000,
            navigation: true,
            pagination: false,
            afterAction: syncPosition,
            responsiveRefreshRate: 200,
            navigationText: [
                "<i class='fa fa-chevron-left'></i>",
                "<i class='fa fa-chevron-right'></i>"
            ]
        });
        sync2.owlCarousel({
            items: 4,
            itemsDesktop: [1199, 4],
            itemsDesktopSmall: [979, 3],
            itemsTablet: [768, 3],
            itemsMobile: [479, 2],
            pagination: false,
            responsiveRefreshRate: 100,
            afterInit: function(el) {
                el.find(".owl-item").eq(0).addClass("synced");
            }
        });
    };

    function syncPosition(el) {
        var current = this.currentItem;
        $(".sync2")
            .find(".owl-item")
            .removeClass("synced")
            .eq(current)
            .addClass("synced");
        if ($(".sync2").data("owlCarousel") != undefined) {
            center(current);
        };
    };

    $(".sync2").on("click", ".owl-item", function(e) {
        e.preventDefault();
        var number = $(this).data("owlItem");
        sync1.trigger("owl.goTo", number);
    });

    function center(number) {
        var sync2visible = sync2.data("owlCarousel").owl.visibleItems;
        var num = number;
        var found = false;
        for (var i in sync2visible) {
            if (num == sync2visible[i]) {
                found = true;
            }
        };

        if (found == false) {
            if (num > sync2visible[sync2visible.length - 1]) {
                sync2.trigger("owl.goTo", num - sync2visible.length + 2)
            } else {
                if (num - 1 == -1) {
                    num = 0;
                }
                sync2.trigger("owl.goTo", num);
            }
        } else if (num == sync2visible[sync2visible.length - 1]) {
            sync2.trigger("owl.goTo", sync2visible[1]);
        } else if (num == sync2visible[0]) {
            sync2.trigger("owl.goTo", num - 1);
        };
    };

    // prettyPhoto
    // ---------------------------------------------------------------------------------------
    $("a[rel^='prettyPhoto']").prettyPhoto({
        theme: 'facebook',
        slideshow: 5000,
        autoplay_slideshow: true
    });


    /* AJAX CALL MODAL */
    $("body").on("click", ".fs-product-modal", function() {
        $(".fs-quantity-in-stock").text("---");
        $('#error-cart-product-modal').html("");
        var productID = $(this).attr("fs-product");
        var colorID = $(this).attr("fs-product-modal-color");
        var productModal = $("#productModal");
        $(".fs-modal-input-number").val(1);
        $(".fs-modal-btn-quantity-minus").attr("disabled", "disabled");
        //$(".fs-modal-btn-quantity-plus").removeAttr("disabled");

        $.ajax({
            url: "ajax/findProduct.html",
            data: {
                productID: productID
            },
            method: "POST",
            dataType: "json",
            success: function(response) {
                /* Init Name and Price */
                $("h3.fs-product-name").text(response.productName);
                $("h3.fs-product-name").attr("fs-product-modal-id", productID);
                $("div.fs-product-price").text("$ " + response.price + ".00");


                /* Init color img  */
                var colorImgStr = "<p>Color<span>*</span></p>";
                var sizeStr = "";
                var str_change_big_img = "";
                var str_change_small_img = "";
                $.each(response.productColorListWorking, function(i, item) {
                    colorImgStr += "<div class=\"fs-product-modal-color-border\">\n\
                                        <img fs-color=\"" + item.colorID + "\" src=\"assets/images/products/colors/" + item.urlColorImg + "\" class=\"img-responsive\" alt=\"" + item.urlColorImg + "\" title=\"" + item.color + "\"/>\n\
                                    </div>";
                    if (item.colorID == colorID) {
                        /* Init size By ColorID */

                        $.each(item.sizeListWorking, function(j, size) {
                            if (size.quantity == 0) {
                                sizeStr += "<div class=\"fs-particular-size fs-unselectable\" fs-size=\"" + size.sizeID + "\">" + size.productSize + "</div>";
                            } else {
                                sizeStr += "<div class=\"fs-particular-size\" fs-size=\"" + size.sizeID + "\">" + size.productSize + "</div>";
                            }

                        });
                        /* Init product Image By Color*/
                        $.each(item.productSubImgsList, function(k, subImg) {
                            str_change_big_img += "<div class=\"item\"><img src=\"assets/images/products/subImg/" + subImg.urlImg + "\" alt=\"" + subImg.urlImg + "\"></div>";
                            str_change_small_img += "<div class=\"item\"><img src=\"assets/images/products/subImg/" + subImg.urlImg + "\" alt=\"" + subImg.urlImg + "\"></div>";
                        });
                    }
                });
                var finalStrToChangeImg = "<div class=\"owl-carousel sync1 fs-main-product-img\">" + str_change_big_img + "</div><div class=\"owl-carousel sync2 fs-main-product-img\">" + str_change_small_img + "</div>";
                $("div.fs-product-modal-color").html(colorImgStr);
                $(".fs-product-modal-color-border").each(function() {
                    if ($(this).children("img").attr("fs-color") == colorID) {
                        $(this).addClass("fs-product-selected");
                    }
                });
                $("#fs-product-modal-size").html(sizeStr);
                $("#fs-product-modal-slide-img").html(finalStrToChangeImg);
                fsCreateOwlCarousel();
                $(".fs-product-modal-link-to-detail").attr("href", productID + "-" + colorID + "-" + response.productNameNA + ".html");
                //Call Modal
                productModal.modal("show");
            }
        });
    });
    // ADD WISHLIST MODAL

    //    $(".fs-wl-add-detail").click(function () {
    //        var userID = $(this).attr("fs-userID");
    //        var input = $("input[name='emailUser']");
    //        if (input.val() != "") {
    //            //Có session
    //            if (!$(this).hasClass("fs-heart-color")) {
    //                $(this).addClass("fs-heart-color");
    //                $.ajax({
    //                    url: "user/ajax/addWishList.html",
    //                    method: "POST",
    //                    data: {userID: userID, productID: productID},
    //                    success: function (response) {
    //                        if (response == "1") {
    //                            $("#fs-mess-wl-error").text("");
    //                            $("#fs-mess-wl-success").text("SUCCESS");
    //                            $("#fs-mess-body-wl").text("Add Wish List success.");
    //                            $("#fs-wl-ajax-error").modal("show");
    //                        } else if (response == "0") {
    //                            $("#fs-mess-wl-success").text("");
    //                            $("#fs-mess-wl-error").text("ERROR");
    //                            $("#fs-mess-body-wl").text("Error, Fail add wishlist.");
    //                            $("#fs-wl-ajax-error").modal("show");
    //                        }
    //                    }
    //                });
    //            } else {
    //                $(this).removeClass("fs-heart-color");
    //                $.ajax({
    //                    url: "user/ajax/deleteWishListt.html",
    //                    method: "POST",
    //                    data: {userID: userID, productID: productID},
    //                    success: function (response) {
    //                        if (response == "1") {
    //                            $("#fs-mess-wl-success").text("");
    //                            $("#fs-mess-wl-error").text("DELETE");
    //                            $("#fs-mess-body-wl").text("Delete Wish List success.");
    //                            $("#fs-wl-ajax-error").modal("show");
    //                        }
    //                    }
    //                });
    //            }
    //        } else {
    //            //Khong có session
    //            productModal.modal("hide");
    //            $("#fs-modal-mess").modal("show");
    //            $(".fs-modal-wl-close").click(function () {
    //                productModal.modal("show");
    //            });
    //            $(".fs-btn-login-wl").click(function () {
    //                $("#fs-modal-mess").modal("hide");
    //                productModal.modal("hide");
    //                window.location = window.location.href;
    ////                            $("#loginModal").modal("show");
    //            });
    //        }
    //    });
    /* MODAL - EVENT CLICK ON COLOR IMG */
    $("div.fs-product-modal-color").on("click", ".fs-product-modal-color-border", function() {
        $(".fs-quantity-in-stock").text("---");
        $('#error-cart-product-modal').html("");
        $(".fs-product-modal-color-border").removeClass("fs-product-selected");
        $(this).addClass("fs-product-selected");
        var colorID = $(this).find("img").attr("fs-color");
        var oldLink = $(".fs-product-modal-link-to-detail").attr("href");
        var linkArray = oldLink.split("-");
        linkArray[1] = colorID;
        var newLink = linkArray.join("-");
        $(".fs-product-modal-link-to-detail").attr("href", newLink);
        $.ajax({
            url: "ajax/color.html",
            method: "POST",
            data: {
                colorID: colorID
            },
            dataType: "json",
            success: function(response) {

                /* Change Image */
                var str_change_big_img = "";
                var str_change_small_img = "";
                $.each(response.productSubImgsList, function(i, item) {
                    if (item.status != 0) {
                        str_change_big_img += "<div class=\"item\"><img src=\"assets/images/products/subImg/" + item.urlImg + "\" alt=\"" + item.urlImg + "\"></div>";
                        str_change_small_img += "<div class=\"item\"><img src=\"assets/images/products/subImg/" + item.urlImg + "\" alt=\"" + item.urlImg + "\"></div>";
                    }
                });
                var finalStrToChangeImg = "<div class=\"owl-carousel sync1 fs-main-product-img\">" + str_change_big_img + "</div><div class=\"owl-carousel sync2 fs-main-product-img\">" + str_change_small_img + "</div>";
                $("#fs-product-modal-slide-img").hide().html(finalStrToChangeImg).fadeIn(1000);
                fsCreateOwlCarousel();
                /* Change Size */
                var str_change_size = "";

                $.each(response.sizeListWorking, function(i, item) {
                    if (item.quantity == 0) {
                        str_change_size += "<div class=\"fs-particular-size fs-unselectable\" fs-size=\"" + item.sizeID + "\">" + item.productSize + "</div>";
                    } else {
                        str_change_size += "<div class=\"fs-particular-size\" fs-size=\"" + item.sizeID + "\">" + item.productSize + "</div>";
                    }
                });
                $("#fs-product-modal-size").hide().html(str_change_size).fadeIn(1000);
            }
        });
    });
    /* EVENT INCREASE OR DECREASE QUANTITY */
    $(".fs-modal-btn-number").click(function() {
        $('#error-cart-product-modal').html("");
        var action = $(this).attr("data-type");
        var currentVal = parseInt($(".fs-modal-input-number").val());
        if (!isNaN(currentVal)) {
            if (action == "minus") {
                if (currentVal > $(".fs-modal-input-number").attr("min")) {
                    $(".fs-modal-input-number").val(currentVal - 1).change();
                }
                if (parseInt($(".fs-modal-input-number").val()) == $(".fs-modal-input-number").attr("min")) {
                    $(".fs-modal-btn-quantity-minus").attr("disabled", "disabled");
                }

            } else if (action == "plus") {
                if (currentVal < $(".fs-modal-input-number").attr("max")) {
                    $(".fs-modal-input-number").val(currentVal + 1).change();
                }
                if (parseInt($(".fs-modal-input-number").val()) == $(".fs-modal-input-number").attr("max")) {
                    $(".fs-modal-btn-quantity-plus").attr("disabled", "disabled");
                }
            }
        } else {
            $(".fs-modal-input-number").val(1);
        }
    });
    $(".fs-modal-input-number").focusin(function() {
        $('#error-cart-product-modal').html("");
        $(this).data("oldVal", $(this).val()); //Lấy value từ input, lưu vào key "oldValue"
    });

    $(".fs-modal-input-number").on("change", function() {
        var currentValue = parseInt($(".fs-modal-input-number").val());
        var minValue = parseInt($(".fs-modal-input-number").attr("min"));
        var maxValue = parseInt($(".fs-modal-input-number").attr("max"));
        if (currentValue >= minValue) {
            $(".fs-modal-btn-quantity-minus").removeAttr("disabled");
        } else {
            $('#error-cart-product-modal').html("<div class=\"alert alert-danger\"><strong>QUANTITY MUST BE AT LEAST 1</strong></div>");
            $(this).val($(this).data('oldVal'));
        }

        if (currentValue <= maxValue) {
            $(".fs-modal-btn-quantity-plus").removeAttr("disabled");
        } else {
            $('#error-cart-product-modal').html("<div class=\"alert alert-danger\"><strong>QUANTITY MUST BE LESS THAN " + maxValue + "</strong></div>");
            $(this).val($(this).data('oldVal'));
        }
    });
    $(".fs-modal-input-number").keydown(function(e) {
        $('#error-cart-product-modal').html("");
        var press = e.keyCode || e.which;
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(press, [46, 8, 9, 27, 190, 17]) != -1) {
            // let it happen, don't do anything
            return;
        }
        if (press == '13') {
            $(this).blur();
        }
        // Ensure that it is a number and stop the keypress
        if ((press < 48 || press > 57) && (press < 96 || press > 105) && (press < 112 || press > 123)) {
            e.preventDefault();
        }
    });
    /* ------------------ PRODUCT_DETAIL ------------------- */
    /* CHANGE DATA WHEN CHOOSE A COLOR */
    $(".fs-product-color-border").on("click", function() {
        $(".fs-quantity-in-stock").text("---");
        $(".fs-input-number, .fs-modal-input-number").attr("disabled", "disabled");
        $(".fs-btn-quantity-plus, .fs-modal-btn-quantity-plus").attr("disabled", "disabled");
        $("#error-product-detail").html("");
        $(".fs-product-color-border").removeClass("fs-product-selected");
        $(this).addClass("fs-product-selected");
        var colorID = $(this).find("img").attr("fs-color");
        $.ajax({
            url: "ajax/color.html",
            method: "POST",
            data: {
                colorID: colorID
            },
            dataType: "json",
            success: function(response) {

                /* Change Image */
                var str_change_big_img = "";
                var str_change_small_img = "";
                $.each(response.productSubImgsList, function(i, item) {
                    if (item.status != 0) {
                        str_change_big_img += "<div class=\"item\">\n\
                                                    <img src=\"assets/images/products/subImg/" + item.urlImg + "\" alt=\"" + item.urlImg + "\"/>\n\
                                                    <a href=\"assets/images/products/subImg/" + item.urlImg + "\" rel=\"prettyPhoto[gallery2]\" title=\"Product\" class=\"caption-link\">\n\
                                                        <i class=\"fa fa-arrows-alt\"></i>\n\
                                                    </a>\n\
                                                </div>";
                        str_change_small_img += "<div class=\"item\">\n\
                                                      <img src=\"assets/images/products/subImg/" + item.urlImg + "\" alt=\"" + item.urlImg + "\"/>\n\\n\
                                                   </div>";
                    }
                });
                var finalStr = "<div class=\"owl-carousel prod-slider sync1\">" + str_change_big_img + "</div><div class=\"owl-carousel sync2\">" + str_change_small_img + "</div>";
                $("#fs-product-detail-slide-img").hide().html(finalStr).fadeIn(1000);
                fsCreateOwlCarousel();
                /* Change Size */
                var str_change_size = "";
                $.each(response.sizeListWorking, function(i, item) {
                    if (item.quantity == 0) {
                        str_change_size += "<div class=\"fs-particular-size fs-unselectable\" fs-size=\"" + item.sizeID + "\">" + item.productSize + "</div>";
                    } else {
                        str_change_size += "<div class=\"fs-particular-size\" fs-size=\"" + item.sizeID + "\">" + item.productSize + "</div>";
                    }
                });
                $("#fs-product-size").hide().html(str_change_size).fadeIn(1000);
            }
        });
    });
    /* EVENT CLICK WHEN CHOOSE SIZE */
    $(document).on("click", ".fs-particular-size", function() {
        $('#error-cart-product-modal').html("");
        $("#error-product-detail").html("");
        var classList = $(this).attr("class").split(" ");
        var rs = $.inArray("fs-unselectable", classList);
        if (rs == -1) {
            $(".fs-particular-size").removeClass("fs-product-selected");
            $(this).addClass("fs-product-selected");
        }

        var sizeID = $(this).attr("fs-size");

        $.ajax({
            url: "ajax/checkquantity.html",
            method: "POST",
            data: {
                sizeID: sizeID
            },
            success: function(response) {
                if (response != 0) {
                    $(".fs-input-number, .fs-modal-input-number").removeAttr("disabled");
                    $(".fs-btn-quantity-plus, .fs-modal-btn-quantity-plus").removeAttr("disabled");
                    $(".fs-quantity-in-stock").text("In Stock (" + response + ")");
                    $(".fs-input-number, .fs-modal-input-number").attr("max", response);
                }
            }
        });
    });
    /* EVENT INCREASE OR DECREASE QUANTITY */
    $(".fs-btn-number").click(function() {
        $("#error-product-detail").html("");
        var action = $(this).attr("data-type");
        var currentVal = parseInt($(".fs-input-number").val());
        if (!isNaN(currentVal)) {
            if (action == "minus") {
                if (currentVal > $(".fs-input-number").attr("min")) {
                    $(".fs-input-number").val(currentVal - 1).change();
                }
                if (parseInt($(".fs-input-number").val()) == $(".fs-input-number").attr("min")) {
                    $(".fs-btn-quantity-minus").attr("disabled", "disabled");
                }

            } else if (action == "plus") {
                if (currentVal < $(".fs-input-number").attr("max")) {
                    $(".fs-input-number").val(currentVal + 1).change();
                }
                if (parseInt($(".fs-input-number").val()) == $(".fs-input-number").attr("max")) {
                    $(".fs-btn-quantity-plus").attr("disabled", "disabled");
                }
            }
        } else {
            $(".fs-input-number").val(1);
        }
    });

    $(".fs-input-number").focusin(function() {
        $("#error-product-detail").html("");
        $(this).data("oldValue", $(this).val()); //Lấy value từ input, lưu vào key "oldValue"
    });
    $(".fs-input-number").on("change", function() {
        var currentValue = parseInt($(".fs-input-number").val());
        var minValue = parseInt($(".fs-input-number").attr("min"));
        var maxValue = parseInt($(".fs-input-number").attr("max"));
        if (currentValue >= minValue) {
            $(".fs-btn-quantity-minus").removeAttr("disabled");
        } else {
            $("#error-product-detail").html("<div class=\"alert alert-danger\"><strong>QUANTITY MUST BE AT LEAST 1</strong></div>");
            $(this).val($(this).data('oldValue'));
        }
        if (currentValue <= maxValue) {
            $(".fs-btn-quantity-plus").removeAttr("disabled");
        } else {
            $("#error-product-detail").html("<div class=\"alert alert-danger\"><strong>QUANTITY MUST BE LESS THAN " + maxValue + "</strong></div>");
            $(this).val($(this).data('oldValue'));
        }
    });

    //    $(".fs-input-number").keydown(function (e) {
    //        $("#error-product-detail").html("");
    //        var press = e.keyCode || e.which;
    //        // Allow: backspace, delete, tab, escape, enter and .
    //        if ($.inArray(press, [46, 8, 9, 27, 13, 190, 17]) != -1) {
    //            // let it happen, don't do anything
    //            $(this).blur().focus();
    //            return;
    //        }
    //        // Ensure that it is a number and stop the keypress
    //        if ((press < 48 || press > 57) && (press < 96 || press > 105) && (press < 112 || press > 123)) {
    //            e.preventDefault();
    //        }
    //    });

    $('#fs-rating-star').barrating({
        theme: 'fontawesome-stars-o',
        showSelectedRating: false,
        //readonly: true,
        onSelect: function(value, text, event) {
            if (typeof(event) !== 'undefined') {
                // rating was selected by a user
                $("#fs-div-vote-value").html("<strong style=\"font-size: 20px; color: #d6644a\">" + value + " </strong>Star");
                $("#fs-rating-star").val(value);
            } else {
                // rating was selected programmatically
                // by calling `set` method
            }
        }
    });

    var currentRating = $('#fs-rating-star-result').data('current-rating');
    $('#fs-rating-star-result').barrating({
        theme: 'fontawesome-stars-o',
        initialRating: currentRating,
        showSelectedRating: false,
        readonly: true,
    });

    for (var i = 0; i < parseInt($("#fs-number-of-rating").attr("fs-nort")); i++) {
        var rating = $('#fs-rating-star-' + i).data('current-rating');
        $('#fs-rating-star-' + i).barrating({
            theme: 'fontawesome-stars-o',
            initialRating: rating,
            showSelectedRating: false,
            readonly: true
        });
    };

    $("#fs-product-detail-page").on("click", "#fs-btn-rating-review", function() {
        var ratingVal = $("#fs-rating-star").val();
        var review = $("#fs-review-product").val();
        var userID = $(this).attr("fs-user-id");
        var productID = $(this).attr("fs-product-id");
        $.ajax({
            url: "ajax/submitReviewRating.html",
            method: "POST",
            data: {
                productID: productID,
                userID: userID,
                ratingVal: ratingVal,
                review: review
            },
            beforeSend: function(xhr) {
                $("#fs-ajax-loading-2").css("display", "block");
            },
            success: function(response) {
                if (response == "ok") {
                    setTimeout(function() {
                        $("#fs-ajax-loading-2").css("display", "none");
                        $("#fs-form-rating-review").empty();
                        $("#fs-form-rating-review").html("<h3>Thank you for your review! </h3>");
                        $.notify({
                            icon: 'glyphicon glyphicon-ok-sign',
                            title: '<strong>Thank you!</strong>',
                            message: "You voted " + ratingVal + " Star for this Product!."
                        }, {
                            type: 'success',
                            placement: {
                                from: 'top',
                                align: 'right'
                            },
                            delay: 2500,
                            timer: 200,
                            mouse_over: 'pause',
                            animate: {
                                enter: 'animated fadeInRight',
                                exit: 'animated fadeOutRight'
                            },
                            template: '<div data-notify="container" class="col-xs-11 col-sm-6 col-md-5 col-lg-3 alert alert-{0}" role="alert">' +
                                '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                                '<span data-notify="icon"></span> ' +
                                '<span data-notify="title">{1}</span> ' +
                                '<span data-notify="message">{2}</span>' +
                                '<div class="progress" data-notify="progressbar">' +
                                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                                '</div>' +
                                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                                '</div>'
                        });
                    }, 600);
                } else {
                    $.notify({
                        icon: 'glyphicon glyphicon-warning-sign',
                        title: '<strong>Error!</strong>',
                        message: 'Something was wrong! Please try again later!.'
                    }, {
                        type: 'danger',
                        placement: {
                            from: 'top',
                            align: 'right'
                        },
                        delay: 3000,
                        timer: 200,
                        mouse_over: 'pause',
                        animate: {
                            enter: 'animated fadeInRight',
                            exit: 'animated fadeOutRight'
                        },
                        template: '<div data-notify="container" class="col-xs-11 col-sm-6 col-md-5 col-lg-3 alert alert-{0}" role="alert">' +
                            '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                            '<span data-notify="icon"></span> ' +
                            '<span data-notify="title">{1}</span> ' +
                            '<span data-notify="message">{2}</span>' +
                            '<div class="progress" data-notify="progressbar">' +
                            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                            '</div>' +
                            '<a href="{3}" target="{4}" data-notify="url"></a>' +
                            '</div>'
                    });
                }
            }
        });
    });

    $("#fs-product-detail-page").on("click", "#fs-btn-login-to-review", function() {
        $("#loginModal").modal("show");
    });
    /* PRODUCT CATEGORY-GRID */
    /* AJAX PAGINATION */
    var colorFilterArr = [];
    var sizeFilterArr = [];
    $("#fs-sort-product-by").selectBoxIt();
    $("#fs-number-of-item-on-page").selectBoxIt();
    /* AJAX ON CLICK PAGE */
    $("#fs-shop-content").on("click", ".fs-page-number", function() {
        if (!$(this).hasClass("fs-page-number-active")) {
            $(".fs-page-number").removeClass("fs-page-number-active");
            $(this).addClass("fs-page-number-active");
            var page = $(this).attr("fs-page-number"); //Số trang hiện tại
            var itemPerPage = $("#fs-number-of-item-on-page").val(); //Số item trên 1 trang
            var cateID = $(this).attr("fs-category"); //Category ID
            var numberOfProducts = parseInt($("#fs-number-of-products").text()); //Tổng số lượng Product
            var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
            $(".fs-page-number[fs-page-number=" + page + "]").addClass("fs-page-number-active");
            var from = (page - 1) * itemPerPage + 1; //STT của sp đầu tiên của trang
            var to = (page - 1) * itemPerPage + parseInt(itemPerPage); //STT của sp cuối cùng của trang
            if (to > numberOfProducts) {
                to = numberOfProducts;
            }
            var currentProductPageInfo = from + " - " + to;
            var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
            var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"

            $.ajax({
                url: "ajax/productPagination.html",
                method: "POST",
                data: {
                    cateID: cateID,
                    page: page,
                    itemPerPage: itemPerPage,
                    sortBy: sortBy,
                    fromPrice: fromPrice,
                    toPrice: toPrice,
                    colorFilterArr: colorFilterArr,
                    sizeFilterArr: sizeFilterArr
                },
                dataType: 'JSON',
                beforeSend: function() {
                    $("#fs-ajax-loading").css("display", "block");
                },
                success: function(response) {
                    setTimeout(function() {
                        $("#fs-ajax-loading").css("display", "none");
                        if (response != "") {
                            $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                            //Change product content
                            var result = "";
                            $.each(response, function(i, prod) {
                                var renderColor = "";
                                if (prod.productColorListWorking.length > 1) {
                                    $.each(prod.productColorListWorking, function(j, color) {
                                        renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                            "              class=\"img-responsive fs-index-color-img\" \n" +
                                            "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                            "              fs-product=\"" + prod.productID + "\" \n" +
                                            "              alt=\"" + color.urlColorImg + "\" \n" +
                                            "              title=\"" + color.color + "\"/>";
                                    });
                                }

                                if (prod.productDiscount == 0) {
                                    result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                        "     <div class=\"product-item\">\n" +
                                        "          <div class=\"item-thumb\">\n" +
                                        "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                        "                    class=\"img-responsive\" \n" +
                                        "                    alt=\"" + prod.urlImg + "\"\n" +
                                        "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                        "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                        "                     data-toggle=\"modal\" \n" +
                                        "                     fs-product=\"" + prod.productID + "\" \n" +
                                        "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                        "                </div>\n" +
                                        "                <div class=\"product-overlay\">\n" +
                                        "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                        "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                        "                </div>\n" +
                                        "          </div>\n" +
                                        "      <div class=\"product-info\">\n" +
                                        "          <h4 class=\"product-title\">\n" +
                                        "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                        "                 " + prod.productName + "\n" +
                                        "              </a>\n" +
                                        "          </h4>\n" +
                                        "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                        "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                        renderColor +
                                        "          </div>\n" +
                                        "      </div>\n" +
                                        "    </div>\n" +
                                        "</div>";
                                } else {
                                    result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                        "     <div class=\"product-item\">\n" +
                                        "          <div class=\"item-thumb\">\n" +
                                        "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                        "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                        "                    class=\"img-responsive\" \n" +
                                        "                    alt=\"" + prod.urlImg + "\"\n" +
                                        "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                        "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                        "                     data-toggle=\"modal\" \n" +
                                        "                     fs-product=\"" + prod.productID + "\" \n" +
                                        "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                        "                </div>\n" +
                                        "                <div class=\"product-overlay\">\n" +
                                        "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                        "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                        "                </div>\n" +
                                        "          </div>\n" +
                                        "      <div class=\"product-info\">\n" +
                                        "          <h4 class=\"product-title\">\n" +
                                        "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                        "                 " + prod.productName + "\n" +
                                        "              </a>\n" +
                                        "          </h4>\n" +
                                        "          <span class=\"product-price\">\n" +
                                        "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                        "          </span>\n" +
                                        "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                        renderColor +
                                        "          </div>\n" +
                                        "      </div>\n" +
                                        "    </div>\n" +
                                        "</div>";
                                }
                            });
                            $("#fs-change-data-here").html(result);
                        }
                    }, 400);
                }
            });
        }
    });

    /* AJAX ON CHANGE SORT PRODUCT BY  */
    $("#fs-shop-content").on("change", "#fs-sort-product-by", function() {
        var sortBy = $(this).val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var cateID = $(this).attr("fs-category");
        var page = 1;
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        $(".fs-page-number").removeClass("fs-page-number-active");
        $(".fs-page-number[fs-page-number='1']").addClass("fs-page-number-active");
        var numberOfProducts = parseInt($("#fs-number-of-products").text());
        var from = (page - 1) * itemPerPage + 1;
        var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
        if (to > numberOfProducts) {
            to = numberOfProducts;
        }
        var currentProductPageInfo = from + " - " + to;

        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến

        $.ajax({
            url: "ajax/productPagination.html",
            method: "POST",
            data: {
                cateID: cateID,
                page: page,
                itemPerPage: itemPerPage,
                sortBy: sortBy,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArr: colorFilterArr,
                sizeFilterArr: sizeFilterArr
            },
            dataType: 'JSON',
            beforeSend: function() {
                $("#fs-ajax-loading").css("display", "block");
            },
            success: function(response) {
                setTimeout(function() {
                    $("#fs-ajax-loading").css("display", "none");
                    if (response != 0) {
                        $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);

                        //Change product content
                        var result = "";
                        $.each(response, function(i, prod) {
                            var renderColor = "";
                            if (prod.productColorListWorking.length > 1) {
                                $.each(prod.productColorListWorking, function(j, color) {
                                    renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                        "              class=\"img-responsive fs-index-color-img\" \n" +
                                        "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                        "              fs-product=\"" + prod.productID + "\" \n" +
                                        "              alt=\"" + color.urlColorImg + "\" \n" +
                                        "              title=\"" + color.color + "\"/>";
                                });
                            }

                            if (prod.productDiscount == 0) {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            } else {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">\n" +
                                    "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                    "          </span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            }
                        });
                        $("#fs-change-data-here").html(result);
                    }
                }, 400);
            }
        });
    });

    /* AJAX ON CHANGE NUMBER OF PRODUCT PER PAGE */
    $("#fs-shop-content").on("change", "#fs-number-of-item-on-page", function() {
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        var page = 1;
        var cateID = $(this).attr("fs-category");
        var numberOfProducts = parseInt($("#fs-number-of-products").text());
        var numberOfPages = Math.ceil(numberOfProducts / itemPerPage);

        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"

        //change productPageInfo
        var from = (page - 1) * itemPerPage + 1;
        var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
        if (to > numberOfProducts) {
            to = numberOfProducts;
        }
        var currentProductPageInfo = from + " - " + to;

        //Change pagination
        var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-category=\"" + cateID + "\">1</span></li>";

        if (numberOfPages > 1) {
            for (var i = 2; i <= numberOfPages; i++) {
                pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-category=\"" + cateID + "\">" + i + "</span></li>";
            }
        }

        //Change Product in page
        $.ajax({
            url: "ajax/productPagination.html",
            method: "POST",
            data: {
                cateID: cateID,
                page: page,
                itemPerPage: itemPerPage,
                sortBy: sortBy,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArr: colorFilterArr,
                sizeFilterArr: sizeFilterArr
            },
            dataType: 'JSON',
            beforeSend: function() {
                $("#fs-ajax-loading").css("display", "block");
            },
            success: function(response) {
                setTimeout(function() {
                    $("#fs-ajax-loading").css("display", "none");
                    if (response != 0) {
                        $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);

                        //Change pagination
                        $(".fs-ul-page-nav").html(pagination);

                        //Change product content
                        var result = "";
                        $.each(response, function(i, prod) {
                            var renderColor = "";
                            if (prod.productColorListWorking.length > 1) {
                                $.each(prod.productColorListWorking, function(j, color) {
                                    renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                        "              class=\"img-responsive fs-index-color-img\" \n" +
                                        "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                        "              fs-product=\"" + prod.productID + "\" \n" +
                                        "              alt=\"" + color.urlColorImg + "\" \n" +
                                        "              title=\"" + color.color + "\"/>";
                                });
                            }

                            if (prod.productDiscount == 0) {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            } else {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">\n" +
                                    "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                    "          </span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            }
                        });
                        $("#fs-change-data-here").html(result);
                    }
                }, 400);
            }
        });
    });

    /* FILTER PRODUCT BY PRICE */
    $("#fs-shop-content").on("click", "#fs-btn-filter-price", function() {

        $(this).attr("fs-filter", "yes");
        var page = 1;
        var fromPrice = $("#fs-price-from").val();
        var toPrice = $("#fs-price-to").val();
        var cateID = $("#fs-price-from").attr("fs-category");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();

        if (fromPrice == "") {
            fromPrice = $("#fs-price-from-text").attr("fs-min-price");
        }

        if (toPrice == "") {
            toPrice = $("#fs-price-to-text").attr("fs-max-price");
        }

        if (fromPrice > toPrice) {
            $("#fs-filter-price-error").text("\"From\" Price must be less than \"To\" Price");
            $("#fs-price-from").val("");
            $("#fs-price-to").val("");
            $("#fs-price-from").focus();
        } else {
            if (fromPrice != "") {
                $("#fs-price-from-text").text(fromPrice);
            }

            if (toPrice != "") {
                $("#fs-price-to-text").text(toPrice);
            }
            $("#fs-filter-price-error").text("");
            $.ajax({
                url: "ajax/getNumberOfProductsByFilter_OfACategory.html",
                method: "POST",
                data: {
                    cateID: cateID,
                    fromPrice: fromPrice,
                    toPrice: toPrice,
                    colorFilterArr: colorFilterArr,
                    sizeFilterArr: sizeFilterArr
                },
                success: function(numberOfProducts) {
                    $.ajax({
                        url: "ajax/productPagination.html",
                        method: "POST",
                        data: {
                            cateID: cateID,
                            page: page,
                            itemPerPage: itemPerPage,
                            sortBy: sortBy,
                            fromPrice: fromPrice,
                            toPrice: toPrice,
                            colorFilterArr: colorFilterArr,
                            sizeFilterArr: sizeFilterArr
                        },
                        dataType: 'JSON',
                        beforeSend: function() {
                            $("#fs-ajax-loading").css("display", "block");
                        },
                        success: function(response) {
                            setTimeout(function() {
                                $("#fs-ajax-loading").css("display", "none");
                                if (response.length == 0) {
                                    $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");
                                    //change productPageInfo
                                    var from = 0;
                                    var to = 0;
                                    var currentProductPageInfo = from + " - " + to;
                                    $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                    $(".fs-number-of-products").text(numberOfProducts);
                                } else {
                                    //Tổng số sản phẩm
                                    var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);

                                    //Change pagination
                                    var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-category=\"" + cateID + "\">1</span></li>";
                                    if (numberOfPages > 1) {
                                        for (var i = 2; i <= numberOfPages; i++) {
                                            pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-category=\"" + cateID + "\">" + i + "</span></li>";
                                        }
                                    }

                                    $(".fs-ul-page-nav").html(pagination);

                                    //change productPageInfo
                                    var from = (page - 1) * itemPerPage + 1;
                                    var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                    if (to > parseInt(numberOfProducts)) {
                                        to = parseInt(numberOfProducts);
                                    }
                                    var currentProductPageInfo = from + " - " + to;
                                    $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                    $(".fs-number-of-products").text(numberOfProducts);

                                    //Change product content
                                    var result = "";
                                    $.each(response, function(i, prod) {
                                        var renderColor = "";
                                        if (prod.productColorListWorking.length > 1) {
                                            $.each(prod.productColorListWorking, function(j, color) {
                                                renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                    "              class=\"img-responsive fs-index-color-img\" \n" +
                                                    "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                    "              fs-product=\"" + prod.productID + "\" \n" +
                                                    "              alt=\"" + color.urlColorImg + "\" \n" +
                                                    "              title=\"" + color.color + "\"/>";
                                            });
                                        }

                                        if (prod.productDiscount == 0) {
                                            result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                                "     <div class=\"product-item\">\n" +
                                                "          <div class=\"item-thumb\">\n" +
                                                "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                                "                    class=\"img-responsive\" \n" +
                                                "                    alt=\"" + prod.urlImg + "\"\n" +
                                                "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                                "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                                "                     data-toggle=\"modal\" \n" +
                                                "                     fs-product=\"" + prod.productID + "\" \n" +
                                                "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                                "                </div>\n" +
                                                "                <div class=\"product-overlay\">\n" +
                                                "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                                "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                                "                </div>\n" +
                                                "          </div>\n" +
                                                "      <div class=\"product-info\">\n" +
                                                "          <h4 class=\"product-title\">\n" +
                                                "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                                "                 " + prod.productName + "\n" +
                                                "              </a>\n" +
                                                "          </h4>\n" +
                                                "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                                "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                                renderColor +
                                                "          </div>\n" +
                                                "      </div>\n" +
                                                "    </div>\n" +
                                                "</div>";
                                        } else {
                                            result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                                "     <div class=\"product-item\">\n" +
                                                "          <div class=\"item-thumb\">\n" +
                                                "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                                "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                                "                    class=\"img-responsive\" \n" +
                                                "                    alt=\"" + prod.urlImg + "\"\n" +
                                                "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                                "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                                "                     data-toggle=\"modal\" \n" +
                                                "                     fs-product=\"" + prod.productID + "\" \n" +
                                                "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                                "                </div>\n" +
                                                "                <div class=\"product-overlay\">\n" +
                                                "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                                "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                                "                </div>\n" +
                                                "          </div>\n" +
                                                "      <div class=\"product-info\">\n" +
                                                "          <h4 class=\"product-title\">\n" +
                                                "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                                "                 " + prod.productName + "\n" +
                                                "              </a>\n" +
                                                "          </h4>\n" +
                                                "          <span class=\"product-price\">\n" +
                                                "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                                "          </span>\n" +
                                                "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                                renderColor +
                                                "          </div>\n" +
                                                "      </div>\n" +
                                                "    </div>\n" +
                                                "</div>";
                                        }
                                    });
                                    $("#fs-change-data-here").html(result);

                                    $("#fs-price-from").val("");
                                    $("#fs-price-to").val("");
                                }
                            }, 400);
                        }
                    });
                }

            });

        }
    });

    /* FILTER PRODUCT BY COLOR */
    $("#fs-shop-content").on("change", ".fs-color-checkbox", function() {
        if (this.checked) { //Check
            colorFilterArr.push($(this).val());
        } else { //Bỏ Check
            var index = colorFilterArr.indexOf($(this).val());
            if (index > -1) {
                colorFilterArr.splice(index, 1);
            }
        }

        var page = 1;
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"
        var cateID = $("#fs-price-from").attr("fs-category");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();

        $.ajax({
            url: "ajax/getNumberOfProductsByFilter_OfACategory.html",
            method: "POST",
            data: {
                cateID: cateID,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArr: colorFilterArr,
                sizeFilterArr: sizeFilterArr
            },
            success: function(numberOfProducts) {
                $.ajax({
                    url: "ajax/productPagination.html",
                    method: "POST",
                    data: {
                        cateID: cateID,
                        page: page,
                        itemPerPage: itemPerPage,
                        sortBy: sortBy,
                        fromPrice: fromPrice,
                        toPrice: toPrice,
                        colorFilterArr: colorFilterArr,
                        sizeFilterArr: sizeFilterArr
                    },
                    dataType: 'JSON',
                    beforeSend: function() {
                        $("#fs-ajax-loading").css("display", "block");
                    },
                    success: function(response) {

                        setTimeout(function() {
                            $("#fs-ajax-loading").css("display", "none");
                            if (response.length == 0) {
                                $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");

                                //change productPageInfo
                                var from = 0;
                                var to = 0;
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                            } else {
                                //Tổng số sản phẩm
                                var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);

                                //Change pagination
                                var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-category=\"" + cateID + "\">1</span></li>";
                                if (numberOfPages > 1) {
                                    for (var i = 2; i <= numberOfPages; i++) {
                                        pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-category=\"" + cateID + "\">" + i + "</span></li>";
                                    }
                                }

                                $(".fs-ul-page-nav").html(pagination);

                                //change productPageInfo
                                var from = (page - 1) * itemPerPage + 1;
                                var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                if (to > parseInt(numberOfProducts)) {
                                    to = parseInt(numberOfProducts);
                                }
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);

                                //Change product content
                                var result = "";
                                $.each(response, function(i, prod) {
                                    var renderColor = "";
                                    if (prod.productColorListWorking.length > 1) {
                                        $.each(prod.productColorListWorking, function(j, color) {
                                            renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                "              class=\"img-responsive fs-index-color-img\" \n" +
                                                "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                "              fs-product=\"" + prod.productID + "\" \n" +
                                                "              alt=\"" + color.urlColorImg + "\" \n" +
                                                "              title=\"" + color.color + "\"/>";
                                        });
                                    }

                                    if (prod.productDiscount == 0) {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    } else {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">\n" +
                                            "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                            "          </span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    }
                                });
                                $("#fs-change-data-here").html(result);
                            }
                        }, 400);
                    }
                });
            }
        });
    });

    /* FILTER PRODUCT BY SIZE */
    $("#fs-shop-content").on("change", ".fs-size-checkbox", function() {
        if (this.checked) { //Check
            sizeFilterArr.push($(this).val());
        } else { //Bỏ Check
            var index = sizeFilterArr.indexOf($(this).val());
            if (index > -1) {
                sizeFilterArr.splice(index, 1);
            }
        }

        var page = 1;
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"
        var cateID = $("#fs-price-from").attr("fs-category");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();

        $.ajax({
            url: "ajax/getNumberOfProductsByFilter_OfACategory.html",
            method: "POST",
            data: {
                cateID: cateID,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArr: colorFilterArr,
                sizeFilterArr: sizeFilterArr
            },
            success: function(numberOfProducts) {
                $.ajax({
                    url: "ajax/productPagination.html",
                    method: "POST",
                    data: {
                        cateID: cateID,
                        page: page,
                        itemPerPage: itemPerPage,
                        sortBy: sortBy,
                        fromPrice: fromPrice,
                        toPrice: toPrice,
                        colorFilterArr: colorFilterArr,
                        sizeFilterArr: sizeFilterArr
                    },
                    dataType: 'JSON',
                    beforeSend: function() {
                        $("#fs-ajax-loading").css("display", "block");
                    },
                    success: function(response) {

                        setTimeout(function() {
                            $("#fs-ajax-loading").css("display", "none");
                            if (response.length == 0) {
                                $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");
                                //change productPageInfo
                                var from = 0;
                                var to = 0;
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                            } else {
                                //Tổng số sản phẩm
                                var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);

                                //Change pagination
                                var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-category=\"" + cateID + "\">1</span></li>";

                                if (numberOfPages > 1) {
                                    for (var i = 2; i <= numberOfPages; i++) {
                                        pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-category=\"" + cateID + "\">" + i + "</span></li>";
                                    }
                                }
                                $(".fs-ul-page-nav").html(pagination);

                                //change productPageInfo
                                var from = (page - 1) * itemPerPage + 1;
                                var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                if (to > parseInt(numberOfProducts)) {
                                    to = parseInt(numberOfProducts);
                                }
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);

                                //Change product content
                                var result = "";
                                $.each(response, function(i, prod) {
                                    var renderColor = "";
                                    if (prod.productColorListWorking.length > 1) {
                                        $.each(prod.productColorListWorking, function(j, color) {
                                            renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                "              class=\"img-responsive fs-index-color-img\" \n" +
                                                "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                "              fs-product=\"" + prod.productID + "\" \n" +
                                                "              alt=\"" + color.urlColorImg + "\" \n" +
                                                "              title=\"" + color.color + "\"/>";
                                        });
                                    }

                                    if (prod.productDiscount == 0) {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    } else {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">\n" +
                                            "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                            "          </span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    }
                                });
                                $("#fs-change-data-here").html(result);
                            }
                        }, 400);
                    }
                });
            }
        });
    });

    /* PRODUCT SUB-CATEGORY-GRID */
    /* AJAX PAGINATION */
    var colorFilterArrSubCate = [];
    var sizeFilterArrSubCate = [];

    /* AJAX ON CLICK PAGE */
    $("#fs-shop-content-sub-category").on("click", ".fs-page-number", function() {
        var input = $("input[name='emailUser']").val();
        var userID = $("input[name='findUsersID']").val();
        if (!$(this).hasClass("fs-page-number-active")) {
            $(".fs-page-number").removeClass("fs-page-number-active");
            $(this).addClass("fs-page-number-active");
            var page = $(this).attr("fs-page-number"); //Số trang hiện tại
            var itemPerPage = $("#fs-number-of-item-on-page").val(); //Số item trên 1 trang
            var subCateID = $(this).attr("fs-subCategory"); //Category ID
            var numberOfProducts = parseInt($("#fs-number-of-products").text()); //Tổng số lượng Product
            var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
            $(".fs-page-number[fs-page-number=" + page + "]").addClass("fs-page-number-active");
            var from = (page - 1) * itemPerPage + 1; //STT của sp đầu tiên của trang
            var to = (page - 1) * itemPerPage + parseInt(itemPerPage); //STT của sp cuối cùng của trang
            if (to > numberOfProducts) {
                to = numberOfProducts;
            }
            var currentProductPageInfo = from + " - " + to;
            var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
            var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"

            $.ajax({
                url: "ajax/productPaginationForSubCate.html",
                method: "POST",
                data: {
                    subCateID: subCateID,
                    page: page,
                    itemPerPage: itemPerPage,
                    sortBy: sortBy,
                    fromPrice: fromPrice,
                    toPrice: toPrice,
                    colorFilterArrSubCate: colorFilterArrSubCate,
                    sizeFilterArrSubCate: sizeFilterArrSubCate
                },
                dataType: 'JSON',
                beforeSend: function() {
                    $("#fs-ajax-loading").css("display", "block");
                },
                success: function(response) {
                    setTimeout(function() {
                        $("#fs-ajax-loading").css("display", "none");
                        if (response != "") {
                            $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                            //Change product content
                            var result = "";
                            $.each(response, function(i, prod) {
                                var renderColor = "";
                                if (prod.productColorListWorking.length > 1) {
                                    $.each(prod.productColorListWorking, function(j, color) {
                                        renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                            "              class=\"img-responsive fs-index-color-img\" \n" +
                                            "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                            "              fs-product=\"" + prod.productID + "\" \n" +
                                            "              alt=\"" + color.urlColorImg + "\" \n" +
                                            "              title=\"" + color.color + "\"/>";
                                    });
                                }

                                if (prod.productDiscount == 0) {
                                    result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                        "     <div class=\"product-item\">\n" +
                                        "          <div class=\"item-thumb\">\n" +
                                        "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                        "                    class=\"img-responsive\" \n" +
                                        "                    alt=\"" + prod.urlImg + "\"\n" +
                                        "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                        "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                        "                     data-toggle=\"modal\" \n" +
                                        "                     fs-product=\"" + prod.productID + "\" \n" +
                                        "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                        "                </div>\n" +
                                        "                <div class=\"product-overlay\">\n" +
                                        "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                        "                     <a class=\"likeitem fa fa-heart-o fs-wl-add-sub-a\" \n" +
                                        "                     fs-userID=\"" + userID + "\" \n" +
                                        "                     fs-productID=\"" + prod.productID + "\"></a>\n" +
                                        "                </div>\n" +
                                        "          </div>\n" +
                                        "      <div class=\"product-info\">\n" +
                                        "          <h4 class=\"product-title\">\n" +
                                        "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                        "                 " + prod.productName + "\n" +
                                        "              </a>\n" +
                                        "          </h4>\n" +
                                        "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                        "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                        renderColor +
                                        "          </div>\n" +
                                        "      </div>\n" +
                                        "    </div>\n" +
                                        "</div>";
                                } else {
                                    result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                        "     <div class=\"product-item\">\n" +
                                        "          <div class=\"item-thumb\">\n" +
                                        "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                        "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                        "                    class=\"img-responsive\" \n" +
                                        "                    alt=\"" + prod.urlImg + "\"\n" +
                                        "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                        "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                        "                     data-toggle=\"modal\" \n" +
                                        "                     fs-product=\"" + prod.productID + "\" \n" +
                                        "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                        "                </div>\n" +
                                        "                <div class=\"product-overlay\">\n" +
                                        "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                        "                     <a class=\"likeitem fa fa-heart-o fs-wl-add-sub-a\" \n" +
                                        "                     fs-userID=\"" + userID + "\" \n" +
                                        "                     fs-productID=\"" + prod.productID + "\"></a>\n" +
                                        "                </div>\n" +
                                        "          </div>\n" +
                                        "      <div class=\"product-info\">\n" +
                                        "          <h4 class=\"product-title\">\n" +
                                        "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                        "                 " + prod.productName + "\n" +
                                        "              </a>\n" +
                                        "          </h4>\n" +
                                        "          <span class=\"product-price\">\n" +
                                        "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                        "          </span>\n" +
                                        "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                        renderColor +
                                        "          </div>\n" +
                                        "      </div>\n" +
                                        "    </div>\n" +
                                        "</div>";
                                }
                            });
                            $("#fs-change-data-here").html(result);
                        }
                    }, 400);
                }
            });
        }
    });

    //    MODAL WISH-LIST

    $("#fs-shop-content-sub-category").on("click", ".fs-wl-add-sub-a", function() {
        var input = $("input[name='emailUser']");
        var userID = $(this).attr("fs-userID");
        var productID = $(this).attr("fs-productID");

        if (input.val() != "") {
            //Có session
            if (!$(this).hasClass("fs-heart-color")) {
                $(this).addClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/addWishList.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #31b131;'>Success</h1>",
                                text: "Add Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        } else if (response == "0") {
                            swal({
                                title: "<h1 style='color: #F65D20;' >Error!",
                                text: "Error, Fail add wishlist",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            } else {
                $(this).removeClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/deleteWishListt.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #ff0000;' >Delete</h1>",
                                text: "Delete Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            }

        } else {
            //Khong có session
            $("#fs-modal-mess").modal("show");
            $(".fs-btn-login-wl").click(function() {
                $("#fs-modal-mess").modal("hide");
                window.location = window.location.href;
                //                $("#loginModal").modal("show");
            });
        }


    });

    /* AJAX ON CHANGE SORT PRODUCT BY  */
    $("#fs-shop-content-sub-category").on("change", "#fs-sort-product-by", function() {
        var sortBy = $(this).val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var subCateID = $(this).attr("fs-subCategory");
        var page = 1;
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        $(".fs-page-number").removeClass("fs-page-number-active");
        $(".fs-page-number[fs-page-number='1']").addClass("fs-page-number-active");
        var numberOfProducts = parseInt($("#fs-number-of-products").text());
        var from = (page - 1) * itemPerPage + 1;
        var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
        if (to > numberOfProducts) {
            to = numberOfProducts;
        }
        var currentProductPageInfo = from + " - " + to;
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến

        $.ajax({
            url: "ajax/productPaginationForSubCate.html",
            method: "POST",
            data: {
                subCateID: subCateID,
                page: page,
                itemPerPage: itemPerPage,
                sortBy: sortBy,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArrSubCate: colorFilterArrSubCate,
                sizeFilterArrSubCate: sizeFilterArrSubCate
            },
            dataType: 'JSON',
            beforeSend: function() {
                $("#fs-ajax-loading").css("display", "block");
            },
            success: function(response) {
                setTimeout(function() {
                    $("#fs-ajax-loading").css("display", "none");
                    if (response != 0) {
                        $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                        //Change product content
                        var result = "";
                        $.each(response, function(i, prod) {
                            var renderColor = "";
                            if (prod.productColorListWorking.length > 1) {
                                $.each(prod.productColorListWorking, function(j, color) {
                                    renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                        "              class=\"img-responsive fs-index-color-img\" \n" +
                                        "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                        "              fs-product=\"" + prod.productID + "\" \n" +
                                        "              alt=\"" + color.urlColorImg + "\" \n" +
                                        "              title=\"" + color.color + "\"/>";
                                });
                            }

                            if (prod.productDiscount == 0) {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            } else {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">\n" +
                                    "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                    "          </span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            }
                        });
                        $("#fs-change-data-here").html(result);
                    }
                }, 400);
            }
        });
    });
    /* AJAX ON CHANGE NUMBER OF PRODUCT PER PAGE */
    $("#fs-shop-content-sub-category").on("change", "#fs-number-of-item-on-page", function() {
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        var page = 1;
        var subCateID = $(this).attr("fs-subCategory");
        var numberOfProducts = parseInt($("#fs-number-of-products").text());
        var numberOfPages = Math.ceil(numberOfProducts / itemPerPage);
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"

        //change productPageInfo
        var from = (page - 1) * itemPerPage + 1;
        var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
        if (to > numberOfProducts) {
            to = numberOfProducts;
        }
        var currentProductPageInfo = from + " - " + to;
        //Change pagination
        var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-subCategory=\"" + subCateID + "\">1</span></li>";
        if (numberOfPages > 1) {
            for (var i = 2; i <= numberOfPages; i++) {
                pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-subCategory=\"" + subCateID + "\">" + i + "</span></li>";
            }
        }

        //Change Product in page
        $.ajax({
            url: "ajax/productPaginationForSubCate.html",
            method: "POST",
            data: {
                subCateID: subCateID,
                page: page,
                itemPerPage: itemPerPage,
                sortBy: sortBy,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArrSubCate: colorFilterArrSubCate,
                sizeFilterArrSubCate: sizeFilterArrSubCate
            },
            dataType: 'JSON',
            beforeSend: function() {
                $("#fs-ajax-loading").css("display", "block");
            },
            success: function(response) {
                setTimeout(function() {
                    $("#fs-ajax-loading").css("display", "none");
                    if (response != 0) {
                        $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                        //Change pagination
                        $(".fs-ul-page-nav").html(pagination);
                        //Change product content
                        var result = "";
                        $.each(response, function(i, prod) {
                            var renderColor = "";
                            if (prod.productColorListWorking.length > 1) {
                                $.each(prod.productColorListWorking, function(j, color) {
                                    renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                        "              class=\"img-responsive fs-index-color-img\" \n" +
                                        "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                        "              fs-product=\"" + prod.productID + "\" \n" +
                                        "              alt=\"" + color.urlColorImg + "\" \n" +
                                        "              title=\"" + color.color + "\"/>";
                                });
                            }

                            if (prod.productDiscount == 0) {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            } else {
                                result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                    "     <div class=\"product-item\">\n" +
                                    "          <div class=\"item-thumb\">\n" +
                                    "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                    "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                    "                    class=\"img-responsive\" \n" +
                                    "                    alt=\"" + prod.urlImg + "\"\n" +
                                    "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                    "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                    "                     data-toggle=\"modal\" \n" +
                                    "                     fs-product=\"" + prod.productID + "\" \n" +
                                    "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                    "                </div>\n" +
                                    "                <div class=\"product-overlay\">\n" +
                                    "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                    "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                    "                </div>\n" +
                                    "          </div>\n" +
                                    "      <div class=\"product-info\">\n" +
                                    "          <h4 class=\"product-title\">\n" +
                                    "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                    "                 " + prod.productName + "\n" +
                                    "              </a>\n" +
                                    "          </h4>\n" +
                                    "          <span class=\"product-price\">\n" +
                                    "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                    "          </span>\n" +
                                    "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                    renderColor +
                                    "          </div>\n" +
                                    "      </div>\n" +
                                    "    </div>\n" +
                                    "</div>";
                            }
                        });
                        $("#fs-change-data-here").html(result);
                    }
                }, 400);
            }
        });
    });
    /* FILTER PRODUCT BY PRICE */
    $("#fs-shop-content-sub-category").on("click", "#fs-btn-filter-price", function() {

        $(this).attr("fs-filter", "yes");
        var page = 1;
        var fromPrice = $("#fs-price-from").val();
        var toPrice = $("#fs-price-to").val();
        var subCateID = $("#fs-price-from").attr("fs-subCategory");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        if (fromPrice == "") {
            fromPrice = $("#fs-price-from-text").attr("fs-min-price");
        }

        if (toPrice == "") {
            toPrice = $("#fs-price-to-text").attr("fs-max-price");
        }

        if (fromPrice > toPrice) {
            $("#fs-filter-price-error").text("\"From\" Price must be less than \"To\" Price");
            $("#fs-price-from").val("");
            $("#fs-price-to").val("");
            $("#fs-price-from").focus();
        } else {
            if (fromPrice != "") {
                $("#fs-price-from-text").text(fromPrice);
            }

            if (toPrice != "") {
                $("#fs-price-to-text").text(toPrice);
            }
            $("#fs-filter-price-error").text("");
            $.ajax({
                url: "ajax/getNumberOfProductsByFilter_OfASubCategory.html",
                method: "POST",
                data: {
                    subCateID: subCateID,
                    fromPrice: fromPrice,
                    toPrice: toPrice,
                    colorFilterArrSubCate: colorFilterArrSubCate,
                    sizeFilterArrSubCate: sizeFilterArrSubCate
                },
                success: function(numberOfProducts) {
                    $.ajax({
                        url: "ajax/productPaginationForSubCate.html",
                        method: "POST",
                        data: {
                            subCateID: subCateID,
                            page: page,
                            itemPerPage: itemPerPage,
                            sortBy: sortBy,
                            fromPrice: fromPrice,
                            toPrice: toPrice,
                            colorFilterArrSubCate: colorFilterArrSubCate,
                            sizeFilterArrSubCate: sizeFilterArrSubCate
                        },
                        dataType: 'JSON',
                        beforeSend: function() {
                            $("#fs-ajax-loading").css("display", "block");
                        },
                        success: function(response) {
                            setTimeout(function() {
                                $("#fs-ajax-loading").css("display", "none");
                                if (response.length == 0) {
                                    $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");
                                    //change productPageInfo
                                    var from = 0;
                                    var to = 0;

                                    var currentProductPageInfo = from + " - " + to;
                                    $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                    $(".fs-number-of-products").text(numberOfProducts);
                                } else {
                                    //Tổng số sản phẩm
                                    var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);
                                    //Change pagination
                                    var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-subCategory=\"" + subCateID + "\">1</span></li>";
                                    if (numberOfPages > 1) {
                                        for (var i = 2; i <= numberOfPages; i++) {
                                            pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-subCategory=\"" + subCateID + "\">" + i + "</span></li>";
                                        }
                                    }

                                    $(".fs-ul-page-nav").html(pagination);
                                    //change productPageInfo
                                    var from = (page - 1) * itemPerPage + 1;
                                    var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                    if (to > parseInt(numberOfProducts)) {
                                        to = parseInt(numberOfProducts);
                                    }
                                    var currentProductPageInfo = from + " - " + to;
                                    $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                    $(".fs-number-of-products").text(numberOfProducts);
                                    //Change product content
                                    var result = "";
                                    $.each(response, function(i, prod) {
                                        var renderColor = "";
                                        if (prod.productColorListWorking.length > 1) {
                                            $.each(prod.productColorListWorking, function(j, color) {
                                                renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                    "              class=\"img-responsive fs-index-color-img\" \n" +
                                                    "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                    "              fs-product=\"" + prod.productID + "\" \n" +
                                                    "              alt=\"" + color.urlColorImg + "\" \n" +
                                                    "              title=\"" + color.color + "\"/>";
                                            });
                                        }

                                        if (prod.productDiscount == 0) {
                                            result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                                "     <div class=\"product-item\">\n" +
                                                "          <div class=\"item-thumb\">\n" +
                                                "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                                "                    class=\"img-responsive\" \n" +
                                                "                    alt=\"" + prod.urlImg + "\"\n" +
                                                "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                                "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                                "                     data-toggle=\"modal\" \n" +
                                                "                     fs-product=\"" + prod.productID + "\" \n" +
                                                "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                                "                </div>\n" +
                                                "                <div class=\"product-overlay\">\n" +
                                                "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                                "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                                "                </div>\n" +
                                                "          </div>\n" +
                                                "      <div class=\"product-info\">\n" +
                                                "          <h4 class=\"product-title\">\n" +
                                                "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                                "                 " + prod.productName + "\n" +
                                                "              </a>\n" +
                                                "          </h4>\n" +
                                                "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                                "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                                renderColor +
                                                "          </div>\n" +
                                                "      </div>\n" +
                                                "    </div>\n" +
                                                "</div>";
                                        } else {
                                            result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                                "     <div class=\"product-item\">\n" +
                                                "          <div class=\"item-thumb\">\n" +
                                                "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                                "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                                "                    class=\"img-responsive\" \n" +
                                                "                    alt=\"" + prod.urlImg + "\"\n" +
                                                "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                                "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                                "                     data-toggle=\"modal\" \n" +
                                                "                     fs-product=\"" + prod.productID + "\" \n" +
                                                "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                                "                </div>\n" +
                                                "                <div class=\"product-overlay\">\n" +
                                                "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                                "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                                "                </div>\n" +
                                                "          </div>\n" +
                                                "      <div class=\"product-info\">\n" +
                                                "          <h4 class=\"product-title\">\n" +
                                                "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                                "                 " + prod.productName + "\n" +
                                                "              </a>\n" +
                                                "          </h4>\n" +
                                                "          <span class=\"product-price\">\n" +
                                                "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                                "          </span>\n" +
                                                "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                                renderColor +
                                                "          </div>\n" +
                                                "      </div>\n" +
                                                "    </div>\n" +
                                                "</div>";
                                        }
                                    });
                                    $("#fs-change-data-here").html(result);
                                    $("#fs-price-from").val("");
                                    $("#fs-price-to").val("");
                                }
                            }, 400);
                        }
                    });
                }

            });
        }
    });
    /* FILTER PRODUCT BY COLOR */
    $("#fs-shop-content-sub-category").on("change", ".fs-color-checkbox", function() {
        if (this.checked) { //Check
            colorFilterArrSubCate.push($(this).val());
        } else { //Bỏ Check
            var index = colorFilterArrSubCate.indexOf($(this).val());
            if (index > -1) {
                colorFilterArrSubCate.splice(index, 1);
            }
        }

        var page = 1;
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"
        var subCateID = $("#fs-price-from").attr("fs-subCategory");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        $.ajax({
            url: "ajax/getNumberOfProductsByFilter_OfASubCategory.html",
            method: "POST",
            data: {
                subCateID: subCateID,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArrSubCate: colorFilterArrSubCate,
                sizeFilterArrSubCate: sizeFilterArrSubCate
            },
            success: function(numberOfProducts) {
                $.ajax({
                    url: "ajax/productPaginationForSubCate.html",
                    method: "POST",
                    data: {
                        subCateID: subCateID,
                        page: page,
                        itemPerPage: itemPerPage,
                        sortBy: sortBy,
                        fromPrice: fromPrice,
                        toPrice: toPrice,
                        colorFilterArrSubCate: colorFilterArrSubCate,
                        sizeFilterArrSubCate: sizeFilterArrSubCate
                    },
                    dataType: 'JSON',
                    beforeSend: function() {
                        $("#fs-ajax-loading").css("display", "block");
                    },
                    success: function(response) {

                        setTimeout(function() {
                            $("#fs-ajax-loading").css("display", "none");
                            if (response.length == 0) {
                                $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");
                                //change productPageInfo
                                var from = 0;
                                var to = 0;
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                            } else {
                                //Tổng số sản phẩm
                                var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);
                                //Change pagination
                                var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-subCategory=\"" + subCateID + "\">1</span></li>";
                                if (numberOfPages > 1) {
                                    for (var i = 2; i <= numberOfPages; i++) {
                                        pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-subCategory=\"" + subCateID + "\">" + i + "</span></li>";
                                    }
                                }

                                $(".fs-ul-page-nav").html(pagination);
                                //change productPageInfo
                                var from = (page - 1) * itemPerPage + 1;
                                var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                if (to > parseInt(numberOfProducts)) {
                                    to = parseInt(numberOfProducts);
                                }
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                                //Change product content
                                var result = "";
                                $.each(response, function(i, prod) {
                                    var renderColor = "";
                                    if (prod.productColorListWorking.length > 1) {
                                        $.each(prod.productColorListWorking, function(j, color) {
                                            renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                "              class=\"img-responsive fs-index-color-img\" \n" +
                                                "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                "              fs-product=\"" + prod.productID + "\" \n" +
                                                "              alt=\"" + color.urlColorImg + "\" \n" +
                                                "              title=\"" + color.color + "\"/>";
                                        });
                                    }

                                    if (prod.productDiscount == 0) {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    } else {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">\n" +
                                            "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                            "          </span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    }
                                });
                                $("#fs-change-data-here").html(result);
                            }
                        }, 400);
                    }
                });
            }
        });
    });
    /* FILTER PRODUCT BY SIZE */
    $("#fs-shop-content-sub-category").on("change", ".fs-size-checkbox", function() {
        if (this.checked) { //Check
            sizeFilterArrSubCate.push($(this).val());
        } else { //Bỏ Check
            var index = sizeFilterArrSubCate.indexOf($(this).val());
            if (index > -1) {
                sizeFilterArrSubCate.splice(index, 1);
            }
        }

        var page = 1;
        var fromPrice = $("#fs-price-from-text").text(); //Lọc giá Product "Từ"
        var toPrice = $("#fs-price-to-text").text(); //Lọc giá Product "Đến"
        var subCateID = $("#fs-price-from").attr("fs-subCategory");
        var sortBy = $("#fs-sort-product-by").val(); //1: Newest; 2: Low to High Price; 3: High to Low Price
        var itemPerPage = $("#fs-number-of-item-on-page").val();
        $.ajax({
            url: "ajax/getNumberOfProductsByFilter_OfASubCategory.html",
            method: "POST",
            data: {
                subCateID: subCateID,
                fromPrice: fromPrice,
                toPrice: toPrice,
                colorFilterArrSubCate: colorFilterArrSubCate,
                sizeFilterArrSubCate: sizeFilterArrSubCate
            },
            success: function(numberOfProducts) {
                $.ajax({
                    url: "ajax/productPaginationForSubCate.html",
                    method: "POST",
                    data: {
                        subCateID: subCateID,
                        page: page,
                        itemPerPage: itemPerPage,
                        sortBy: sortBy,
                        fromPrice: fromPrice,
                        toPrice: toPrice,
                        colorFilterArrSubCate: colorFilterArrSubCate,
                        sizeFilterArrSubCate: sizeFilterArrSubCate
                    },
                    dataType: 'JSON',
                    beforeSend: function() {
                        $("#fs-ajax-loading").css("display", "block");
                    },
                    success: function(response) {

                        setTimeout(function() {
                            $("#fs-ajax-loading").css("display", "none");
                            if (response.length == 0) {
                                $("#fs-change-data-here").html("<div class='col-xs-12'><h1>Nothing To Show!</h1></div>");
                                //change productPageInfo
                                var from = 0;
                                var to = 0;
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                            } else {
                                //Tổng số sản phẩm
                                var numberOfPages = Math.ceil(parseInt(numberOfProducts) / itemPerPage);
                                //Change pagination
                                var pagination = "<li><span class=\"fs-page-number fs-page-number-active\" fs-page-number=\"1\" fs-subCategory=\"" + subCateID + "\">1</span></li>";
                                if (numberOfPages > 1) {
                                    for (var i = 2; i <= numberOfPages; i++) {
                                        pagination += "<li><span class=\"fs-page-number\" fs-page-number=\"" + i + "\" fs-subCategory=\"" + subCateID + "\">" + i + "</span></li>";
                                    }
                                }
                                $(".fs-ul-page-nav").html(pagination);
                                //change productPageInfo
                                var from = (page - 1) * itemPerPage + 1;
                                var to = (page - 1) * itemPerPage + parseInt(itemPerPage);
                                if (to > parseInt(numberOfProducts)) {
                                    to = parseInt(numberOfProducts);
                                }
                                var currentProductPageInfo = from + " - " + to;
                                $(".fs-change-currentProductPageInfo").text(currentProductPageInfo);
                                $(".fs-number-of-products").text(numberOfProducts);
                                //Change product content
                                var result = "";
                                $.each(response, function(i, prod) {
                                    var renderColor = "";
                                    if (prod.productColorListWorking.length > 1) {
                                        $.each(prod.productColorListWorking, function(j, color) {
                                            renderColor += "<img src=\"assets/images/products/colors/" + color.urlColorImg + "\" \n" +
                                                "              class=\"img-responsive fs-index-color-img\" \n" +
                                                "              fs-index-color-img=\"" + color.colorID + "\" \n" +
                                                "              fs-product=\"" + prod.productID + "\" \n" +
                                                "              alt=\"" + color.urlColorImg + "\" \n" +
                                                "              title=\"" + color.color + "\"/>";
                                        });
                                    }

                                    if (prod.productDiscount == 0) {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">$" + prod.price + ".00</span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    } else {
                                        result += "<div class=\"col-md-4 col-sm-6\">\n" +
                                            "     <div class=\"product-item\">\n" +
                                            "          <div class=\"item-thumb\">\n" +
                                            "               <span class=\"badge offer\">-" + prod.productDiscount + "%</span>\n" +
                                            "               <img src=\"assets/images/products/" + prod.urlImg + "\" \n" +
                                            "                    class=\"img-responsive\" \n" +
                                            "                    alt=\"" + prod.urlImg + "\"\n" +
                                            "                    fs-product-for-img=\"" + prod.productID + "\"/>\n" +
                                            "                <div class=\"overlay-rmore fa fa-search quickview fs-product-modal\" \n" +
                                            "                     data-toggle=\"modal\" \n" +
                                            "                     fs-product=\"" + prod.productID + "\" \n" +
                                            "                     fs-product-modal-color=\"" + prod.productColorListWorking[0].colorID + "\">\n" +
                                            "                </div>\n" +
                                            "                <div class=\"product-overlay\">\n" +
                                            "                     <a href=\"#\" class=\"addcart fa fa-shopping-cart\"></a>\n" +
                                            "                     <a href=\"#\" class=\"likeitem fa fa-heart-o\"></a>\n" +
                                            "                </div>\n" +
                                            "          </div>\n" +
                                            "      <div class=\"product-info\">\n" +
                                            "          <h4 class=\"product-title\">\n" +
                                            "              <a href=\"" + prod.productID + "-" + prod.productColorListWorking[0].colorID + "-" + prod.productNameNA + ".html\">\n" +
                                            "                 " + prod.productName + "\n" +
                                            "              </a>\n" +
                                            "          </h4>\n" +
                                            "          <span class=\"product-price\">\n" +
                                            "               <small class=\"cutprice\">$ " + prod.price + ".00 </small>  $ " + (prod.price - prod.price * prod.productDiscount / 100) +
                                            "          </span>\n" +
                                            "          <div class=\"item-colors\" style=\"height: 25px;\">\n" +
                                            renderColor +
                                            "          </div>\n" +
                                            "      </div>\n" +
                                            "    </div>\n" +
                                            "</div>";
                                    }
                                });
                                $("#fs-change-data-here").html(result);
                            }
                        }, 400);
                    }
                });
            }
        });
    });
    /*======================================END VINH - PRODUCT==================================================*/

    /*========================================NGAN - ORDER====================================================*/
    $("select#select-quantity-shoppingcart").selectBoxIt();
    //Load cart in header
    $.ajax({
        url: "orders/ajax/cart.html",
        method: "GET",
        dataType: 'html',
        success: function(response) {
            $("#cart").html(response).fadeIn(1000);
        }
    });

    //order-history.jsp
    //$("#loginModal").modal('hide'); show
    if (window.location.href.includes("order-history")) {
        //lazyload order-history.jsp
        var mincount = 5;
        var maxcount = 10;
        $("#table-order-history tbody tr").slice(5).hide();
        $(window).scroll(function() {
            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 450) {
                $("#table-order-history tbody tr").slice(mincount, maxcount).fadeIn(1000);
                mincount = mincount + 5;
                maxcount = maxcount + 5;
            }
        });
    };

    //checkout.jsp
    //Discount in checkout.jsp
    //Load form discount in checkout
    $("input[name=address-chose]").on("click", function() {
        var checked = $('input[name=address-chose]:checked').val();
        if (checked == "difference") {
            $('.shipping-address').css("display", "list-item");
        } else {
            $('.shipping-address').prop("style", false);
        }
    });
    $('#btnCheckoutPlaceOrder').on("click", function(e) {
        e.preventDefault();
        var errorHead = "<div class=\"alert alert-danger\"><strong>";
        var errorFoot = "</strong></div>";
        var addressSize = $("input[name=addressSize]").val();
        var addressChoose = $("input[name=address-chose]:checked").val();
        if (addressSize == "0") {
            var firstname = $('input[name=diffFirstname]').val().trim();
            var lastname = $('input[name=diffLastname]').val().trim();
            var phone = $('input[name=diffPhone]').val().trim();
            var address = $('input[name=diffAddress]').val().trim();
            var note = $('input[name=note]').val().trim();
            if (firstname == "") {
                $('#error-checkout-firstname').text("FIRSTNAME REQUIRED");
                $('body,html').animate({
                    scrollTop: $('#diff-address').offset().top
                }, 500);
            } else if (firstname.length < 4 || firstname.length > 30) {
                $('#error-checkout-firstname').text("FIRSTNAME LENGTH 4 - 30 CHARACTERS");
                $('body,html').animate({
                    scrollTop: $('#diff-address').offset().top
                }, 500);
            } else if (lastname == "") {
                $('#error-checkout-lastname').text("LASTNAME REQUIRED");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (lastname.length < 4 || lastname.length > 30) {
                $('#error-checkout-lastname').text("LASTNAME LENGTH 4 - 30 CHARACTERS");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (phone == "") {
                $('#error-checkout-phone').text("PHONE REQUIRED");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (!phone.match("^(01[2689]|09)[0-9]{8}$")) { // Regex phone /^(01[2689]|09)[0-9]{8}$/
                $('#error-checkout-phone').text("ENTER VALID PHONE");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (address == "") {
                $('#error-checkout-address').text("ADDRESS REQUIRED");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (address.length < 4 || address.length > 100) {
                $('#error-checkout-address').text("ADDRESS LENGTH 4 - 100 CHARACTERS");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else if (note.length > 500) {
                $('#error-checkout-note').text("NOTE LENGTH MAXIMUM 500 CHARACTERS");
                $('body,html').animate({
                    scrollTop: $('.shipping-address').offset().top
                }, 500);
            } else {
                $('#checkout-form').submit();
            }
        } else {
            if (addressChoose == null) {
                $('#error-checkout').html(errorHead + "ADDRESS METHOD must be CHOOSEN!" + errorFoot);
                $('body,html').animate({
                    scrollTop: 0
                }, 500);
            } else {
                var note = $('input[name=note]').val().trim();
                if (addressChoose == "difference") {
                    var firstname = $('input[name=diffFirstname]').val().trim();
                    var lastname = $('input[name=diffLastname]').val().trim();
                    var phone = $('input[name=diffPhone]').val().trim();
                    var address = $('input[name=diffAddress]').val().trim();
                    if (firstname == "") {
                        $('#error-checkout-firstname').text("FIRSTNAME REQUIRED");
                        $('body,html').animate({
                            scrollTop: $('#diff-address').offset().top
                        }, 500);
                    } else if (firstname.length < 4 || firstname.length > 30) {
                        $('#error-checkout-firstname').text("FIRSTNAME LENGTH 4 - 30 CHARACTERS");
                        $('body,html').animate({
                            scrollTop: $('#diff-address').offset().top
                        }, 500);
                    } else if (lastname == "") {
                        $('#error-checkout-lastname').text("LASTNAME REQUIRED");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (lastname.length < 4 || lastname.length > 30) {
                        $('#error-checkout-lastname').text("LASTNAME LENGTH 4 - 30 CHARACTERS");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (phone == "") {
                        $('#error-checkout-phone').text("PHONE REQUIRED");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (!phone.match("^(01[2689]|09)[0-9]{8}$")) { // Regex phone /^(01[2689]|09)[0-9]{8}$/
                        $('#error-checkout-phone').text("ENTER VALID PHONE");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (address == "") {
                        $('#error-checkout-address').text("ADDRESS REQUIRED");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (address.length < 4 || address.length > 100) {
                        $('#error-checkout-address').text("ADDRESS LENGTH 4 - 100 CHARACTERS");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else if (note.length > 500) {
                        $('#error-checkout-note').text("NOTE LENGTH MAXIMUM 500 CHARACTERS");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else {
                        $('#checkout-form').submit();
                    }
                } else {
                    if (note.length > 500) {
                        $('#error-checkout-note').text("NOTE LENGTH MAXIMUM 500 CHARACTERS");
                        $('body,html').animate({
                            scrollTop: $('.shipping-address').offset().top
                        }, 500);
                    } else {
                        $('#checkout-form').submit();
                    }
                }
            }
        }
    });
    $('#checkout-form').on('keyup keypress', function(e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });
    $("#coupon_code").keydown(function() {
        $("#fs-checkout-discountvou-error").text("");
    });
    $("input[name=address-chose]").on("click", function() {
        $('#error-checkout').html("");
    });
    $('input[name=diffFirstname]').keydown(function() {
        $('#error-checkout-firstname').text("");
    });
    $('input[name=diffLastname]').keydown(function() {
        $('#error-checkout-lastname').text("");
    });
    $('input[name=diffPhone]').keydown(function() {
        $('#error-checkout-phone').text("");
    });
    $('input[name=diffAddress]').keydown(function() {
        $('#error-checkout-address').text("");
    });
    $('input[name=note]').keydown(function() {
        $('#error-checkout-note').text("");
    });
    $('.discount-code #discount-order').on("click", function() {
        $("#fs-checkout-discountvou-error").text("");
        $(".cart-table").remove(".foot");
        $(".cart-table").add("<tfoot class=\"foot\"></tfoot>");
        var discountCode = $("input[name=coupon_code]").val().trim();
        var emailUser = $('input[name=emailUser]').val().trim();
        var hiddenDiscountCode = $('input[name=discount-code-input]').val();
        if (hiddenDiscountCode == null) {
            if (discountCode == "") {
                $("#fs-checkout-discountvou-error").text("ENTER YOUR DISCOUNT CODE!");
            } else {
                $.ajax({
                    url: "orders/ajax/discount.html",
                    method: "POST",
                    data: {
                        discountCode: discountCode,
                        emailUser: emailUser
                    },
                    dataType: 'JSON',
                    success: function(response) {
                        if (response != null) {
                            var xResponse = response.status;
                            if (xResponse != "1" && xResponse != "0" && xResponse != "2" && xResponse != "3" && xResponse != "4" && xResponse != "5" && xResponse != "6") {
                                $(".discount-show").html(response.showDiscountPercent);
                                $(".foot").html(response.showDiscount);
                            } else {
                                $.get("orders/ajax/nodiscount.html", function(responsenodiscount) {
                                    if (xResponse == "1" || xResponse == "6") {
                                        $("#fs-checkout-discountvou-error").text("Wrong Discount Code or Discount Code not existed");
                                        $(".foot").html(responsenodiscount);
                                    } else if (xResponse == "0") {
                                        $("#fs-checkout-discountvou-error").text("Your Discount Code is out of quantity");
                                        $(".foot").html(responsenodiscount);
                                    } else if (xResponse == "2") {
                                        $("#fs-checkout-discountvou-error").text("Your Discount Code Begin Date: " + response.showDiscount);
                                        $(".foot").html(responsenodiscount);
                                    } else if (xResponse == "3") {
                                        $("#fs-checkout-discountvou-error").text("Your Discount Code End Date: " + response.showDiscount);
                                        $(".foot").html(responsenodiscount);
                                    } else if (xResponse == "4") {
                                        $("#fs-checkout-discountvou-error").text("Your Discount Code Already Used");
                                        $(".foot").html(responsenodiscount);
                                    } else if (xResponse == "5") {
                                        $("#fs-checkout-discountvou-error").text("Error Happened!");
                                        $(".foot").html(responsenodiscount);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        } else {
            $("#fs-checkout-discountvou-error").text("You already have input discount code");
        }
    });
    $("input[name=coupon_code]").keypress(function(e) {
        if (e.which == 13) {
            $("#fs-checkout-discountvou-error").text("");
            $(".cart-table").remove(".foot");
            $(".cart-table").add("<tfoot class=\"foot\"></tfoot>");
            var discountCode = $("input[name=coupon_code]").val().trim();
            var emailUser = $('input[name=emailUser]').val().trim();
            var hiddenDiscountCode = $('input[name=discount-code-input]').val();
            if (hiddenDiscountCode == null) {
                if (discountCode == "") {
                    $("#fs-checkout-discountvou-error").text("ENTER YOUR DISCOUNT CODE!");
                } else {
                    $.ajax({
                        url: "orders/ajax/discount.html",
                        method: "POST",
                        data: {
                            discountCode: discountCode,
                            emailUser: emailUser
                        },
                        dataType: 'JSON',
                        success: function(response) {
                            if (response != null) {
                                var xResponse = response.status;
                                if (xResponse != "1" && xResponse != "0" && xResponse != "2" && xResponse != "3" && xResponse != "4" && xResponse != "5" && xResponse != "6") {
                                    $(".discount-show").html(response.showDiscountPercent);
                                    $(".foot").html(response.showDiscount);
                                } else {
                                    $.get("orders/ajax/nodiscount.html", function(responsenodiscount) {
                                        if (xResponse == "1" || xResponse == "6") {
                                            $("#fs-checkout-discountvou-error").text("Wrong Discount Code or Discount Code not existed");
                                            $(".foot").html(responsenodiscount);
                                        } else if (xResponse == "0") {
                                            $("#fs-checkout-discountvou-error").text("Your Discount Code is out of quantity");
                                            $(".foot").html(responsenodiscount);
                                        } else if (xResponse == "2") {
                                            $("#fs-checkout-discountvou-error").text("Your Discount Code Begin Date: " + response.showDiscount);
                                            $(".foot").html(responsenodiscount);
                                        } else if (xResponse == "3") {
                                            $("#fs-checkout-discountvou-error").text("Your Discount Code End Date: " + response.showDiscount);
                                            $(".foot").html(responsenodiscount);
                                        } else if (xResponse == "4") {
                                            $("#fs-checkout-discountvou-error").text("Your Discount Code Already Used");
                                            $(".foot").html(responsenodiscount);
                                        } else if (xResponse == "5") {
                                            $("#fs-checkout-discountvou-error").text("Error Happened!");
                                            $(".foot").html(responsenodiscount);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            } else {
                $("#fs-checkout-discountvou-error").text("You already have input discount code");
            }
        };
    });

    //Cancel Order side client in order-history-detail.jsp
    $('#confirm-cancel-order').on('show.bs.modal', function(e) {
        $(this).find('.btn-cancel-order-ok').attr('href', $(e.relatedTarget).data('href'));
    });

    //Add to cart in product-detail.jsp
    $("#fs-product-detail-add-to-cart").on("click", function() {
        var colorID = $(".fs-product-color .fs-product-selected").find("img").attr("fs-color");
        var sizeID = $("#fs-product-size .fs-product-selected").attr("fs-size");
        var productID = $(".fs-product-detail-name").attr("fs-product-id");
        var quantity = $(".fs-input-number").val();
        if (colorID == null && sizeID == null) {
            $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                "<strong>YOU MUST CHOOSE COLOR AND SIZE</strong>\n" +
                "</div>");
        } else {
            if (colorID == null) {
                $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                    "<strong>YOU MUST CHOOSE COLOR</strong>\n" +
                    "</div>");
            } else if (sizeID == null) {
                $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                    "<strong>YOU MUST CHOOSE SIZE</strong>\n" +
                    "</div>");
            } else {
                $.ajax({
                    url: "orders/ajax/addtocart.html",
                    method: "POST",
                    data: {
                        productID: productID,
                        sizeID: sizeID,
                        colorID: colorID,
                        quantity: quantity
                    },
                    dataType: 'html',
                    success: function(response) {
                        if (response == "3") {
                            $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                                "<strong>PRODUCT ERROR</strong>\n" +
                                "</div>");
                        } else if (response == "2") {
                            $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                                "<strong>COLOR AND SIZE ERROR</strong>\n" +
                                "</div>");
                        } else if (response == "1") {
                            $("#error-product-detail").html("<div class=\"alert alert-danger\">\n" +
                                "<strong>NOT ENOUGH STOCK! PLEASE ENTER DIFFERENT QUANTITY</strong>\n" +
                                "</div>");
                        } else {
                            $('body,html').animate({
                                scrollTop: 0
                            }, 500);
                            $("#error-product-detail").html("<div class=\"alert alert-success\">\n" +
                                "<strong>ADD PRODUCT TO CART SUCCESSFULLY</strong>\n" +
                                "</div>");
                            $.ajax({
                                url: "orders/ajax/cart.html",
                                method: "GET",
                                dataType: 'html',
                                success: function(response) {
                                    $("#cart").html(response).fadeIn(1000);
                                }
                            });
                        }
                    }
                });
            }
        }
    });

    //Add to cart in modal.jsp
    $(".fs-modal-btn-addtobag").on("click", function() {
        var errorHead = "<div class=\"alert alert-danger\"><strong>";
        var errorHeadSuccess = "<div class=\"alert alert-success\"><strong>";
        var errorFoot = "</strong></div>";
        var colorID = $(".fs-product-modal-color .fs-product-selected").find("img").attr("fs-color");
        var sizeID = $("#fs-product-modal-size .fs-product-selected").attr("fs-size");
        var productID = $(".fs-product-name").attr("fs-product-modal-id");
        var quantity = $(".fs-modal-input-number").val();
        if (colorID == null && sizeID == null) {
            $('#error-cart-product-modal').html(errorHead + "YOU MUST CHOOSE COLOR AND SIZE" + errorFoot);
        } else {
            if (colorID == null) {
                $('#error-cart-product-modal').html(errorHead + "YOU MUST CHOOSE COLOR" + errorFoot);
            } else if (sizeID == null) {
                $('#error-cart-product-modal').html(errorHead + "YOU MUST CHOOSE SIZE" + errorFoot);
            } else {
                $.ajax({
                    url: "orders/ajax/addtocart.html",
                    method: "POST",
                    data: {
                        productID: productID,
                        sizeID: sizeID,
                        colorID: colorID,
                        quantity: quantity
                    },
                    dataType: 'html',
                    success: function(response) {
                        if (response == "3") {
                            $('#error-cart-product-modal').html(errorHead + "PRODUCT ERROR!" + errorFoot);
                        } else if (response == "2") {
                            $('#error-cart-product-modal').html(errorHead + "COLOR AND SIZE ERROR!" + errorFoot);
                        } else if (response == "1") {
                            $('#error-cart-product-modal').html(errorHead + "NOT ENOUGH STOCK! PLEASE ENTER DIFFERENT QUANTITY" + errorFoot);
                        } else {
                            $('#error-cart-product-modal').html(errorHeadSuccess + "ADD PRODUCT TO CART SUCCESSFULLY!" + errorFoot);
                            $.ajax({
                                url: "orders/ajax/cart.html",
                                method: "GET",
                                dataType: 'html',
                                success: function(response) {
                                    $("#cart").html(response).fadeIn(1000);
                                }
                            });
                        }
                    }
                });
            }
        }
    });
    $(".fs-modal-close").on("click", function() {
        $('#error-cart-product-modal').html("");
    });

    //    var orderUrl = window.location.href;
    //    if (orderUrl.includes("order-history")) {
    //        window.onbeforeunload = function () {
    //            $.ajax({
    //                url: "orders/ajax/getSession.html",
    //                method: "GET",
    //                dataType: 'html',
    //                success: function (response) {
    //                    if (response == "0") {
    //                        $("#loginModal").modal('show');
    //                    }
    //                }
    //            });
    //        }
    //    }
    /*==========================END NGAN - ORDER==================================*/

    /*===========================DUONG - USER===================================*/
    //THÔNG BÁO KHI CLICK VÀO ADD ADDRESS KHI VƯỢT QUÁ MỨC CHO PHÉP
    //    $(".fs-add-address-user").on("click", function () {
    //        var userID = $(this).attr("fs-userID");
    //        var modal = $(this).attr("fs-message");
    //        $.ajax({
    //            url: "user/address-add/" + userID + ".html",
    //            method: "GET",
    //            data: {userID: userID},
    //            success: function (response) {
    //                alert('không thể thêm address');
    //            }
    //        });
    //    });

    //CUỘN LẠI, HIỆN RA TABLE ADDRESS-LIST

    $(".fs-panel").on("click", ".panel-heading span.clickable", function() {
        var abc = $(this);
        if (!abc.hasClass("panel-collapsed")) {
            abc.closest(".panel").find(".panel-body").slideUp();
            abc.addClass("panel-collapsed");
            abc.find("i").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
        } else {
            abc.closest(".panel").find(".panel-body").slideDown();
            abc.removeClass("panel-collapsed");
            abc.find("i").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
        }
    });
    $(".fs-panel-add").on("click", ".panel-heading span.clickable", function() {
        var abc = $(this);
        if (!abc.hasClass("panel-collapsed")) {
            abc.closest(".panel").find(".panel-body").slideUp();
            abc.addClass("panel-collapsed");
            abc.find("i").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
        } else {
            abc.closest(".panel").find(".panel-body").slideDown();
            abc.removeClass("panel-collapsed");
            abc.find("i").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
        }
    });
    //    $("div#fs-table-add-address span.clickable").click();


    // CẢNH CÁO KHI BẤM XÓA
    $(".fs-delete-button-AD").click(function() {
        var addressID = $(this).attr("fs-addressID");
        swal({
            title: "Are you sure?",
            text: "You will sure delete record this",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function(isConfirm) {
            if (!isConfirm)
                return;
            $("#fs-list-id-" + addressID).remove();
            $.ajax({
                url: "user/deleteAddress/" + addressID + ".html",
                type: "POST",
                data: {
                    addressID: addressID
                },
                success: function(response) {
                    if (response == "1")
                        swal("Done!", "It was succesfully deleted!", "success");
                    //                    window.location = window.location.href;
                },
                error: function(xhr, ajaxOptions, thrownError) {
                    swal("Error deleting!", "Please try again", "error");
                }
            });
        });
    });


    // CHINH TRANG THAI CHO MODAL(BAM RA NGOAI KHONG TAT MODAL)

    $("#loginModal").on("hidden.bs.modal", function(e) {
        $(this).find("input").val("").end().find("input[type=checkbox], input[type=radio]").prop("checked", "").end();
        $(this).find("p").html("");
        $(this).find("span#glypcn-fs-login-user").remove("span");
        $(this).find("span#glypcn-fs-create-user").remove("span");
        $(this).find("#fs-error-show").text("");
        $(this).find("#fs-error-show-register").text("");
    });

    // BẮT LỖI FORM LOGIN USER MODAL

    function checkEmail(email) {
        email = $("#fs-email-login-user").val().trim();
        var pattern = new RegExp(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/);
        if (email == "") {
            $("#fs-email-login-user-error").text("Email cannot be empty!");
            $("#fs-email-login-user").focus();
            var div = $("#fs-email-login-user").closest("div.fs-email-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (!pattern.test(email)) {
            $("#fs-email-login-user-error").text("Please enter valid Email!");
            $("#fs-email-login-user").focus();
            var div = $("#fs-email-login-user").closest("div.fs-email-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-email-login-user-error").text("");
            var div = $("#fs-email-login-user").closest("div.fs-email-user");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-login-user").remove();
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    // VALIDATION CLICK BUTTON LOGIN

    $(".fs-button-login-user").click(function(e) {
        e.preventDefault();
        var email = $("#fs-email-login-user").val().trim();
        var pass = $("#fs-pass-login-user").val().trim();
        //        var checkremember = $("#fs-check-remember").val();
        var checkremember = $('input[name="checkremember"]:checked').val();
        //        alert(checkremember);
        if (!checkEmail(email)) {
            return false;
        } else if (pass == "") { // => đúng là pass != ==
            $("#fs-pass-login-user-error").text("Password cannot be empty!");
            $("#fs-pass-login-user").focus();
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (pass.length < 6 || pass.length > 100) { //=> đúng là  <=6 pass.length < 100 
            $("#fs-pass-login-user-error").text("Password 6 to 100 characters!");
            $("#fs-pass-login-user").focus();
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-pass-login-user-error").text("");
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-login-user").remove();
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            $.ajax({
                url: "login.html",
                method: "POST",
                data: {
                    email: email,
                    password: pass,
                    checkremember: checkremember
                },
                //dataType: 'html',
                success: function(response) {
                    if (response == "2") {
                        EmailWrong(email);
                    } else if (response == "3") {
                        PassWrong(pass);
                    } else if (response == "4") {
                        $("#fs-error-show").text("Fail account wrong!");
                    } else {
                        var currentUrl = window.location.href;
                        window.location = currentUrl;
                        $("#loginModal").modal('hide');
                    }
                }
            });
            return true;
        }
    });

    function EmailWrong(email) {
        email = $("#fs-email-login-user").val().trim();
        if ($("#loginModal").modal('show')) {
            $("#fs-error-show").text("Email is wrong!");
            $("#fs-email-login-user").focus();
            var div = $("#fs-email-login-user").closest("div.fs-email-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        }
    };

    function PassWrong(password) {
        password = $("#fs-pass-login-user").val().trim();
        if ($("#loginModal").modal('show')) {
            $("#fs-error-show").text("Password is wrong!");
            $("#fs-pass-login-user").focus();
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        }
    };

    // VALIDATION KEYUP 

    $("#fs-email-login-user").keyup(function() {
        var email = $("#fs-email-login-user").val().trim();
        //        $("#fs-error-show").text("");
        if (!checkEmail(email)) {
            return false;
        }
    });
    $("#fs-pass-login-user").keyup(function() {
        var pass = $("#fs-pass-login-user").val().trim();
        //        $("#fs-error-show").text("");
        if (pass == "") {
            $("#fs-pass-login-user-error").text("Password cannot be empty!");
            $("#fs-pass-login-user").focus();
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (pass.length < 6 || pass.length > 100) {
            $("#fs-pass-login-user-error").text("Password 6 to 100 characters!");
            $("#fs-pass-login-user").focus();
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-success");
            $("#glypcn-fs-login-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-pass-login-user-error").text("");
            var div = $("#fs-pass-login-user").closest("div.fs-pass-user");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-login-user").remove();
            div.append('<span id="glypcn-fs-login-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    });

    // BẮT VALIDATION TRÊN FORM CREATE

    function checkemail(email) {
        email = $("#fs-create-email").val().trim();
        var pattern = new RegExp(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/);
        if (email == "") {
            $("#fs-email-create-user-error").text("Email cannot be empty!");
            $("#fs-create-email").focus();
            var div = $("#fs-create-email").closest("div.fs-email-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (!pattern.test(email)) {
            $("#fs-email-create-user-error").text("Please enter valid Email!");
            $("#fs-create-email").focus();
            var div = $("#fs-create-email").closest("div.fs-email-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-email-create-user-error").text("");
            var div = $("#fs-create-email").closest("div.fs-email-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkPass(password) {
        password = $("#password").val().trim();
        if (password == "") {
            $("#fs-pass-create-user-error").text("Password cannot be empty!");
            $("#password").focus();
            var div = $("#password").closest("div.fs-password-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (password.length < 6 || password.length > 100) {
            $("#fs-pass-create-user-error").text("Password 6 to 100 characters!");
            //            $("#password").focus();
            var div = $("#password").closest("div.fs-password-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-pass-create-user-error").text("");
            var div = $("#password").closest("div.fs-password-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkRePass(repassword, password) {
        repassword = $("#Repassword").val().trim();
        password = $("#password").val().trim();
        if (repassword == "") {
            $("#fs-repass-create-user-error").text("Repassword cannot be empty!");
            $("#Repassword").focus();
            var div = $("#Repassword").closest("div.fs-repassword-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (repassword.length < 6 || repassword.length > 100) {
            $("#fs-repass-create-user-error").text("Password 6 to 100 characters!");
            //            $("#Repassword").focus();
            var div = $("#Repassword").closest("div.fs-repassword-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (repassword != password) {
            $("#fs-repass-create-user-error").text("Repassword is not the same as password!");
            //            $("#Repassword").focus();
            var div = $("#Repassword").closest("div.fs-repassword-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-repass-create-user-error").text("");
            var div = $("#Repassword").closest("div.fs-repassword-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkFirstName(firstname) {
        firstname = $("#fs-create-firstname").val().trim();
        if (firstname == "") {
            $("#fs-fname-create-user-error").text("First Name cannot be empty!");
            $("#fs-create-firstname").focus();
            var div = $("#fs-create-firstname").closest("div.fs-firstname-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (firstname.length < 2 || firstname.length > 50) {
            $("#fs-fname-create-user-error").text("First Name 2 to 50 characters!");
            //            $("#fs-create-firstname").focus();
            var div = $("#fs-create-firstname").closest("div.fs-firstname-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-fname-create-user-error").text("");
            var div = $("#fs-create-firstname").closest("div.fs-firstname-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkLastName(lastname) {
        lastname = $("#fs-create-lastname").val().trim();
        if (lastname == "") {
            $("#fs-lname-create-user-error").text("Last Name cannot be empty!");
            $("#fs-create-lastname").focus();
            var div = $("#fs-create-lastname").closest("div.fs-lastname-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (lastname.length < 2 || lastname.length > 50) {
            $("#fs-lname-create-user-error").text("Last Name 2 to 50 characters!");
            //            $("#fs-create-lastname").focus();
            var div = $("#fs-create-lastname").closest("div.fs-lastname-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-lname-create-user-error").text("");
            var div = $("#fs-create-lastname").closest("div.fs-lastname-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkBirthDay(birthday) {
        birthday = $("#txtBirthday").val().trim();
        if (birthday == "") {
            $("#fs-bday-create-user-error").text("BirthDay cannot be empty!");
            $("#txtBirthday").focus();
            var div = $("#txtBirthday").closest("div.fs-birthday-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-bday-create-user-error").text("");
            var div = $("#txtBirthday").closest("div.fs-birthday-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkPhone(phone) {
        //        var regex = new RegExp(/^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/);
        var regex = new RegExp(/^(01[2689]|09)[0-9]{8}$/);
        phone = $("#fs-create-phone").val().trim();
        if (phone == "") {
            $("#fs-phone-create-user-error").text("Phone cannot be empty!");
            $("#fs-create-phone").focus();
            var div = $("#fs-create-phone").closest("div.fs-phone-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (!regex.test(phone)) {
            $("#fs-phone-create-user-error").text("Please enter valid phone - Phone begin 01 or 09 and 10 to 11 number!");
            $("#fs-create-phone").focus();
            var div = $("#fs-create-phone").closest("div.fs-phone-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-phone-create-user-error").text("");
            var div = $("#fs-create-phone").closest("div.fs-phone-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }

    };

    function checkAddress(address) {
        address = $("#fs-create-address").val().trim();
        if (address == "") {
            $("#fs-address-create-user-error").text("Address cannot be empty!");
            $("#fs-create-address").focus();
            var div = $("#fs-create-address").closest("div.fs-address-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (address.length < 10 || address.length > 255) {
            $("#fs-address-create-user-error").text("Address has 10 - 255 characters!");
            $("#fs-create-address").focus();
            var div = $("#fs-create-address").closest("div.fs-address-create");
            div.removeClass("has-success");
            $("#glypcn-fs-create-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-address-create-user-error").text("");
            var div = $("#fs-create-address").closest("div.fs-address-create");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-create-user").remove();
            div.append('<span id="glypcn-fs-create-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    $("body").on("click", "#fs-button-create-user", function(e) {
        e.preventDefault();
        console.log("123");
        var email = $("#fs-create-email").val().trim();
        var password = $("#password").val().trim();
        var repassword = $("#Repassword").val().trim();
        var firstname = $("#fs-create-firstname").val().trim();
        var lastname = $("#fs-create-lastname").val().trim();
        var birthday = $("#txtBirthday").val();
        var gender = $('input[name="gender"]:checked').val();
        var phone = $("#fs-create-phone").val().trim();
        var address = $("#fs-create-address").val().trim();
        var formData = new FormData();
        var mfile = $("#fs-upImage")[0].files[0];
        formData.append("email", email);
        formData.append("password", password);
        formData.append("firstName", firstname);
        formData.append("lastName", lastname);
        formData.append("gender", gender);
        formData.append("birthday", birthday);
        formData.append("upImage", mfile);
        formData.append("phoneNumber", phone);
        formData.append("address", address);

        if (!checkemail(email)) {
            return false;
        }

        if (!checkPass(password)) {
            return false;
        }
        if (!checkRePass(repassword, password)) {
            return false;
        }
        if (!checkFirstName(firstname)) {
            return true;
        }
        if (!checkLastName(lastname)) {
            return false;
        }
        if (!checkBirthDay(birthday)) {
            return false;
        }

        if (phone != "") {
            if (!checkPhone(phone)) {
                return false;
            }
            if (!checkAddress(address)) {
                return false;
            }
        } else {
            $("#fs-phone-create-user-error").text("");
            $("#fs-address-create-user-error").text("");
        }

        //        else {
        $.ajax({
            url: "user/register.html",
            method: "POST",
            //data: $("#fs-form-create-user").serialize(),
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            //dataType: 'html',
            success: function(response) {
                console.log(response);
                if (response == "2") {
                    $("#fs-error-show-register").html("<div class=\"alert alert-danger\">FAILED!. Account is exists!</div>");
                } else if (response == "0") {
                    $("#fs-error-show-register").html("<div class=\"alert alert-danger\">FAILED!. Error was happened!!</div>");
                } else {
                    var currentUrl = window.location.href;
                    window.location = currentUrl;
                    $("#loginModal").modal('hide');
                }

            }
        });
        //        }
    });
    //        VALIDATION REGISTER KEYUP

    $("#fs-create-email").keyup(function() {
        var email = $("#fs-create-email").val().trim();
        if (!checkemail(email)) {
            return false;
        }
    });
    $("#password").keyup(function() {
        var password = $("#password").val().trim();
        if (!checkPass(password)) {
            return false;
        }
    });
    $("#Repassword").keyup(function() {
        var repassword = $("#Repassword").val().trim();
        var password = $("#password").val().trim();
        if (!checkRePass(repassword, password)) {
            return false;
        }
    });
    $("#fs-create-firstname").keyup(function() {
        var firstname = $("#fs-create-firstname").val().trim();
        if (!checkFirstName(firstname)) {
            return false;
        }
    });
    $("#fs-create-lastname").keyup(function() {
        var lastname = $("#fs-create-lastname").val().trim();
        if (!checkLastName(lastname)) {
            return false;
        }
    });
    $("#txtBirthday").keyup(function() {
        var birthday = $("#txtBirthday").val().trim();
        if (!checkBirthDay(birthday)) {
            return false;
        }
    });
    $("#fs-create-phone").keyup(function() {
        var phone = $("#fs-create-phone").val().trim();
        if (!checkPhone(phone)) {
            return false;
        }
    });
    $("#fs-create-address").keyup(function() {
        var address = $("#fs-create-address").val().trim();
        if (!checkAddress(address)) {
            return false;
        }
    });

    //    BẮT VALIDATION CẬP NHẬT THÔNG TIN CÁ NHÂN     

    $("#txtbirthday").click(function() {
        $("#txtbirthday").datepicker({
            dateFormat: "dd/mm/yy",
            showAnim: "drop",
            changeMonth: true,
            changeYear: true,
            defaultDate: '01/01/1960',
            yearRange: "1960:1999"
        });
    });
    // BẮT VỚI UPDATE ACCOUNT CLICK



    function emailcheck(email) {
        email = $("#fs-update-email").val().trim();
        var pattern = new RegExp(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/);
        if (email == "") {
            $("#fs-email-update-user-error").text("Email cannot be empty!");
            $("#fs-update-email").focus();
            var div = $("#fs-update-email").closest("div.fs-email-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (!pattern.test(email)) {
            $("#fs-email-update-user-error").text("Please enter valid Email!");
            //            $("#fs-create-email").focus();
            var div = $("#fs-update-email").closest("div.fs-email-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-email-update-user-error").text("");
            var div = $("#fs-update-email").closest("div.fs-email-update");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-update-user").remove();
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checkfirstName(firstname) {
        firstname = $("#fs-update-firstname").val().trim();
        if (firstname == "") {
            $("#fs-firstname-update-user-error").text("First Name cannot be empty!");
            $("#fs-update-firstname").focus();
            var div = $("#fs-update-firstname").closest("div.fs-firstname-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (firstname.length < 2 || firstname.length > 50) {
            $("#fs-firstname-update-user-error").text("First Name 2 to 50 characters!");
            //            $("#fs-create-firstname").focus();
            var div = $("#fs-update-firstname").closest("div.fs-firstname-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-firstname-update-user-error").text("");
            var div = $("#fs-update-firstname").closest("div.fs-firstname-update");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-update-user").remove();
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    function checklastName(lastname) {
        lastname = $("#fs-update-lastname").val().trim();
        if (lastname == "") {
            $("#fs-lastname-update-user-error").text("Last Name cannot be empty!");
            $("#fs-update-lastname").focus();
            var div = $("#fs-update-lastname").closest("div.fs-lastname-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else if (lastname.length < 2 || lastname.length > 50) {
            $("#fs-lastname-update-user-error").text("Last Name 2 to 50 characters!");
            //            $("#fs-create-lastname").focus();
            var div = $("#fs-update-lastname").closest("div.fs-lastname-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-lastname-update-user-error").text("");
            var div = $("#fs-update-lastname").closest("div.fs-lastname-update");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-update-user").remove();
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };


    function checkbirthDay(birthday) {
        birthday = $("#txtbirthday").val().trim();
        if (birthday == "") {
            $("#fs-birthday-update-user-error").text("BirthDay cannot be empty!");
            $("#txtbirthday").focus();
            var div = $("#txtbirthday").closest("div.fs-birthday-update");
            div.removeClass("has-success");
            $("#glypcn-fs-update-user").remove();
            div.addClass("has-error has-feedback");
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
            return false;
        } else {
            $("#fs-birthday-update-user-error").text("");
            var div = $("#txtbirthday").closest("div.fs-birthday-update");
            div.removeClass("has-error");
            div.addClass("has-success has-feedback");
            $("#glypcn-fs-update-user").remove();
            div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
            return true;
        }
    };

    $(".fs-button-update-user").click(function(e) {
        var email = $("#fs-update-email").val().trim();
        var firstname = $("#fs-update-firstname").val().trim();
        var lastname = $("#fs-update-lastname").val().trim();
        var birthday = $("#txtbirthday").val().trim();
        e.preventDefault();
        if (!emailcheck(email)) {
            return false;
        } else if (!checkfirstName(firstname)) {
            return false;
        } else if (!checklastName(lastname)) {
            return false;
        } else if (!checkbirthDay(birthday)) {
            return false;
        } else {
            $("#fs-form-update-account").submit();
        }
    });



    // VALIDATION KEYUP UPDATE-ACCOUNT

    $("#fs-update-email").keyup(function() {
        var email = $("#fs-update-email").val().trim();
        if (!emailcheck(email)) {
            return false;
        }
    });
    // BẮT EMAIL TRÙNG:
    //    $("#fs-update-email").keyup(function () {
    //        var email = $("#fs-update-email").val();
    //        $.ajax({
    //            url: "ajax/emailExist.html",
    //            method: "POST",
    ////            data: {email: email},
    //            dataType: "JSON",
    //            success: function (response) {
    //                for (var i = 0; i < response.length; i++) {
    //                    var item = response[i];
    //                    if (email == item) {
    //                        $("#fs-email-update-user-error").text("Email is Exist!");
    //                        $("#fs-update-email").focus();
    //                        var div = $("#fs-update-email").closest("div.fs-email-update");
    //                        div.removeClass("has-success");
    //                        $("#glypcn-fs-update-user").remove();
    //                        div.addClass("has-error has-feedback");
    //                        div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-remove form-control-feedback"></span>');
    //                        return false;
    //                    }
    ////                    else {
    ////                        $("#fs-email-update-user-error").text("");
    ////                        var div = $("#fs-update-email").closest("div.fs-email-update");
    ////                        div.removeClass("has-error");
    ////                        div.addClass("has-success has-feedback");
    ////                        $("#glypcn-fs-update-user").remove();
    ////                        div.append('<span id="glypcn-fs-update-user" class="glyphicon glyphicon-ok form-control-feedback"></span>');
    ////                        return true;
    ////                    }
    //
    //                }
    //            }
    //        });
    //    });
    $("#fs-update-firstname").keyup(function() {
        var firstname = $("#fs-update-firstname").val().trim();
        if (!checkfirstName(firstname)) {
            return false;
        }
    });
    $("#fs-update-lastname").keyup(function() {
        var lastname = $("#fs-update-lastname").val().trim();
        if (!checklastName(lastname)) {
            return false;
        }
    });

    $("#txtbirthday").keyup(function() {
        var birthday = $("#txtbirthday").val().trim();
        if (!checkbirthDay(birthday)) {
            return false;
        }
    });

    // VALIDATION CHANGE PASS CLICK

    $(".fs-button-change-pass").click(function(e) {
        e.preventDefault();
        var oldpass = $(".fs-old-pass").val().trim();
        var pass = $(".fs-password").val().trim();
        var repass = $(".fs-repass").val().trim();

        if (oldpass == "") {
            $("#fs-oldpass-user-error").text("Current Password cannot be empty!");
        } else if (oldpass.length < 6 || oldpass.length > 100) {
            $("#fs-oldpass-user-error").text("Current Password has 6 to 100 characters!");
        } else {
            $("#fs-oldpass-user-error").text("");
        }

        if (pass == "") {
            $("#fs-pass-user-error").text("Password cannot be empty!");
        } else if (pass.length < 6 || pass.length > 100) {
            $("#fs-pass-user-error").text("Password has 6 to 100 characters!");
        } else {
            $("#fs-pass-user-error").text("");
        }

        if (repass == "") {
            $("#fs-repass-user-error").text("Password Confirm cannot be empty!");
        } else if (repass != pass) {
            $("#fs-repass-user-error").text("Password Confirm is different with Password");
        } else if (repass.length < 6 || repass.length > 100) {
            $("#fs-repass-user-error").text("Password Confirm has 6 to 100 characters!");
        } else {
            $("#fs-repass-user-error").text("");
            $(".fs-form-change-pass").submit();
        }



    });

    // VALIDATION CHANGE PASS KEYUP

    $(".fs-old-pass").keyup(function() {
        var oldpass = $(".fs-old-pass").val().trim();

        if (oldpass == "") {
            $("#fs-oldpass-user-error").text("Current Password cannot be empty!");
        } else if (oldpass.length < 6 || oldpass.length > 100) {
            $("#fs-oldpass-user-error").text("Current Password has 6 to 100 characters!");
        } else {
            $("#fs-oldpass-user-error").text("");
        }
    });

    $(".fs-password").keyup(function() {
        var pass = $(".fs-password").val().trim();

        if (pass == "") {
            $("#fs-pass-user-error").text("Password cannot be empty!");
        } else if (pass.length < 6 || pass.length > 100) {
            $("#fs-pass-user-error").text("Password has 6 to 100 characters!");
        } else {
            $("#fs-pass-user-error").text("");
        }
    });

    $(".fs-repass").keyup(function() {
        var repass = $(".fs-repass").val().trim();
        var pass = $(".fs-password").val().trim();

        if (repass == "") {
            $("#fs-repass-user-error").text("Password Confirm cannot be empty!");
        } else if (repass != pass) {
            $("#fs-repass-user-error").text("Password Confirm is different with Password");
        } else if (repass.length < 6 || repass.length > 100) {
            $("#fs-repass-user-error").text("Password Confirm has 6 to 100 characters!");
        } else {
            $("#fs-repass-user-error").text("");
        }
    });

    // VALIDATION CREATE ADDRESS CLICK

    $(".fs-button-add-address").click(function(e) {
        e.preventDefault();
        var address = $(".fs-address-add").val().trim();
        var phone = $(".fs-phone-add").val().trim();
        //        phone = phone.replace('(+84)', '0');
        //        phone = phone.replace('+84', '0');
        //        phone = phone.replace('0084', '0');
        var regex = new RegExp(/^(01[2689]|09)[0-9]{8}$/);

        if (address == "") {
            $("#fs-address-add-user-error").text("Address cannot be empty!");
        } else if (address.length < 10 || address.length > 255) {
            $("#fs-address-add-user-error").text("Address has 10 to 255 characters!");
        } else {
            $("#fs-address-add-user-error").text("");
        }

        if (phone == "") {
            $("#fs-phone-add-user-error").text("Phone Number cannot be empty!");
        } else if (!regex.test(phone)) {
            $("#fs-phone-add-user-error").text("Phone begin 01 or 09 and 10 to 11 number");
        } else {
            $("#fs-phone-add-user-error").text("");
            $(".fs-form-add-address").submit();
        }

        //        if((address.length > 10 && address.length < 255) && regex.test(phone)){
        //            var userID = $(this).attr("fs-userID");
        //            $.ajax({
        //                url: "user/address-add/"+ userID +".html",
        //                method: "POST",
        //                data:{userID: userID},
        //                success: function (response) {
        //                    if(response == "1"){
        //                        alert("trùng");
        //                    }else if(response == "2") {
        //                        alert("ok");
        //                    }else{
        //                        alert("hihi");
        //                    }
        //                }
        //            });
        //        }

    });

    $(".fs-button-reset-address").click(function() {
        $("#fs-phone-add-user-error").text("");
        $("#fs-address-add-user-error").text("");
    });

    // VALIDATION CREATE-ADDRESS KEYUP

    $(".fs-address-add").keyup(function() {
        var address = $(".fs-address-add").val().trim();

        if (address == "") {
            $("#fs-address-add-user-error").text("Address cannot be empty!");
        } else if (address.length < 10 || address.length > 255) {
            $("#fs-address-add-user-error").text("Address has 10 to 255 characters!");
        } else {
            $("#fs-address-add-user-error").text("");
        }
    });

    $(".fs-phone-add").keyup(function() {
        var phone = $(".fs-phone-add").val().trim();
        var regex = new RegExp(/^(01[2689]|09)[0-9]{8}$/);

        if (phone == "") {
            $("#fs-phone-add-user-error").text("Phone Number cannot be empty!");
        } else if (!regex.test(phone)) {
            $("#fs-phone-add-user-error").text("Phone begin 01 or 09 and 10 to 11 number");
        } else {
            $("#fs-phone-add-user-error").text("");
        }
    });


    // VALIDATION UPDATE-ADDRESS CLICK

    $(".fs-button-update-address").click(function(e) {
        e.preventDefault();
        var address = $(".fs-update-address").val().trim();
        var phone = $(".fs-update-phone").val().trim();
        var regex = new RegExp(/^(01[2689]|09)[0-9]{8}$/);

        if (address == "") {
            $("#fs-address-update-user-error").text("Address cannot be empty!");
        } else if (address.length < 10 || address.length > 255) {
            $("#fs-address-update-user-error").text("Address has 10 to 255 characters!");
        } else {
            $("#fs-address-update-user-error").text("");
        }

        if (phone == "") {
            $("#fs-phone-update-user-error").text("Phone Number cannot be empty!");
        } else if (!regex.test(phone)) {
            $("#fs-phone-update-user-error").text("Phone begin 01 or 09 and 10 to 11 number");
        } else {
            $("#fs-phone-update-user-error").text("");
            //            $(".fs-form-update-address").submit();
        }

        if ((address.length > 10 && address.length < 255) && regex.test(phone)) {
            $(".fs-form-update-address").submit();
        }
    });

    // VALIDATION UPDATE-ADDRESS KEYUP

    $(".fs-update-address").keyup(function() {
        var address = $(".fs-update-address").val().trim();

        if (address == "") {
            $("#fs-address-update-user-error").text("Address cannot be empty!");
        } else if (address.length < 10 || address.length > 255) {
            $("#fs-address-update-user-error").text("Address has 10 to 255 characters!");
        } else {
            $("#fs-address-update-user-error").text("");
        }
    });

    $(".fs-update-phone").keyup(function() {
        var phone = $(".fs-update-phone").val().trim();
        var regex = new RegExp(/^(01[2689]|09)[0-9]{8}$/);

        if (phone == "") {
            $("#fs-phone-update-user-error").text("Phone Number cannot be empty!");
        } else if (!regex.test(phone)) {
            $("#fs-phone-update-user-error").text("Phone begin 01 or 09 and 10 to 11 number");
        } else {
            $("#fs-phone-update-user-error").text("");
        }
    });


    // ADD WISH-LIST: PAGE HOME FEATURED PRODUCTS

    $(".fs-wishlish-add").click(function() {
        var userID = $(this).attr("fs-userID");
        var productID = $(this).attr("fs-productID");
        var input = $("input[name='emailUser']");
        if (input.val() != "") {
            //Có session
            if (!$(this).hasClass("fs-heart-color")) {
                $(this).addClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/addWishList.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #31b131;'>Success</h1>",
                                text: "Add Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        } else if (response == "0") {
                            swal({
                                title: "<h1 style='color: #F65D20;' >Error!",
                                text: "Error, Fail add wishlist",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            } else {
                $(this).removeClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/deleteWishListt.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #ff0000;' >Delete</h1>",
                                text: "Delete Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            }

        } else {
            //Khong có session
            $("#fs-modal-mess").modal("show");
            $(".fs-btn-login-wl").click(function() {
                $("#fs-modal-mess").modal("hide");
                window.location = window.location.href;
                //                $("#loginModal").modal("show");
            });
        }
    });


    // ADD WISH-LIST: PAGE HOME LATEST PRODUCTS

    $(".fs-wl-add-lsp").click(function() {
        var userID = $(this).attr("fs-userID");
        var productID = $(this).attr("fs-productID");
        var input = $("input[name='emailUser']");
        if (input.val() != "") {
            //Có session
            //            $(this).addClass("fs-heart-color");
            if (!$(this).hasClass("fs-heart-color")) {
                $(this).addClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/addWishList.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #31b131;'>Success</h1>",
                                text: "Add Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        } else if (response == "0") {
                            swal({
                                title: "<h1 style='color: #F65D20;' >Error!",
                                text: "Error, Fail add wishlist",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            } else {
                $(this).removeClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/deleteWishListt.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #ff0000;' >Delete</h1>",
                                text: "Delete Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            }

        } else {
            //Khong có session
            $("#fs-modal-mess").modal("show");
            $(".fs-btn-login-wl").click(function() {
                $("#fs-modal-mess").modal("hide");
                window.location = window.location.href;
                //                $("#loginModal").modal("show");
            });
        }
    });


    // ADD WISH-LIST: SUB_CATEGORY

    $(".fs-wl-add-sub").click(function() {
        var userID = $(this).attr("fs-userID");
        var productID = $(this).attr("fs-productID");
        var input = $("input[name='emailUser']");
        if (input.val() != "") {
            //Có session
            if (!$(this).hasClass("fs-heart-color")) {
                $(this).addClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/addWishList.html",
                    method: "POST",
                    data: {
                        userID: userID,
                        productID: productID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #31b131;'>Success</h1>",
                                text: "Add Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        } else if (response == "0") {
                            swal({
                                title: "<h1 style='color: #F65D20;' >Error!",
                                text: "Error, Fail add wishlist",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            } else {
                $(this).removeClass("fs-heart-color");
                $.ajax({
                    url: "user/ajax/deleteWishListt.html",
                    method: "POST",
                    data: {
                        productID: productID,
                        userID: userID
                    },
                    success: function(response) {
                        if (response == "1") {
                            swal({
                                title: "<h1 style='color: #ff0000;' >Delete</h1>",
                                text: "Delete Wish List success.",
                                timer: 2000,
                                showConfirmButton: false,
                                html: true
                            });
                        }
                    }
                });
            }

        } else {
            //Khong có session
            $("#fs-modal-mess").modal("show");
            $(".fs-btn-login-wl").click(function() {
                $("#fs-modal-mess").modal("hide");
                window.location = window.location.href;
                //                $("#loginModal").modal("show");
            });
        }
    });

    // DELETE WISHLIST 

    $(".fs-btn-delete-wl").click(function() {
        var wishID = $(this).attr("fs-wl-wlID");
        //        alert(wishID);
        $("#fs-list-id-" + wishID).remove();
        $.ajax({
            url: "user/ajax/deleteWishList/" + wishID + ".html",
            method: "POST",
            data: {
                wishID: wishID
            },
            success: function(response) {
                if (response == "1") {
                    swal({
                        title: "<h1 style='color: #ff0000;'>Delete</h1>",
                        text: "Delete Wish List success.",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });

                }
            }
        });
    });




    /*===========================END DUONG - USER===================================*/

    /*========================================END DUONG - USER====================================================*/

    /*========================================THANH - BLOG====================================================*/
    var mincount = 2;
    var maxcount = 4;
    $(".image-gallery-ul li").slice(2).hide();
    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 350) {
            $(".image-gallery-ul li").slice(mincount, maxcount).fadeIn(2000);
            mincount = mincount + 2;
            maxcount = maxcount + 2;
        }
    });
    /*========================================END THANH - BLOG====================================================*/

});