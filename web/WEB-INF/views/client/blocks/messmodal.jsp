<%-- 
    Document   : messmodal
    Created on : May 20, 2017, 6:30:16 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="fs-modal-mess" class="modal fade fs-modal-wl-mess" tabindex="-1" role="dialog" aria-labelledby="messlodalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-content fs-modal-wl-content">
        <p class="text-center fs-wl-text"><b>If you want add WishList - You should Login</b></p>
        <div class="modal-header">
            <a class="close fs-modal-wl-close" data-dismiss="modal" aria-label="Close">x</a>
        </div>
        <div class="modal-body fs-modal-wl-body">
            <a class="btn fs-btn-wl fs-btn-login-wl text-center">Login</a>
        </div>
    </div>
</div>

<!--MODAL THONG BAO CHO KHI CO MODAL-->
<div id="fs-wl-ajax-error" class="modal fade fs-modal-mess-wl" tabindex="-1" role="dialog">
    <div class="modal-content fs-modal-wl-content">
        <!--<h1 id="fs-mess-wl" style="color: #31b131; text-align: center">SUCCESS</h1>-->
        <h1 id="fs-mess-wl-success" style="color: #31b131; text-align: center"></h1>
        <h1 id="fs-mess-wl-error" style="color: #F65D20; text-align: center"></h1>

        <div class="modal-header">
            <button class="close" data-dismiss="modal">&times;</button>
            <h1 id="fs-mess-wl" style="color: #31b131; text-align: center"></h1>
        </div>
        <div class="modal-body">
            <!--<p id="fs-mess-body-wl">Add Wish List success.</p>-->
            <p id="fs-mess-body-wl"></p>
        </div>
    </div>
</div>
<div class="ajax-progress"></div>


<!--TEST-->

<!--    <div id="fs-full-width" class="modal container fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h1 class="modal-title" id="fs-mess-wl-success" style="color: #31b131; text-align: center" ></h1>
  </div>
  <div class="modal-body">
    <p id="fs-mess-body-wl"></p>
  </div>
  <div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
    <button type="button" class="btn btn-primary">Save changes</button>
  </div>
</div>-->
