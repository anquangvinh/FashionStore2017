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
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>Subscribe/Unsubscribe</h4>                                                                  
                    <div class="account-form">
                        <form id="shipping-zip-form">                                       
                            <ul class="form-list row">
                                <li class="col-md-6 col-sm-12 cbox">
                                    <p> Subscribe: </p> &nbsp;
                                    <p class="radio-inline"><input type="radio" name="optradio"> <label>Yes</label> </p>
                                    <p class="radio-inline"> <input type="radio" name="optradio"> <label> No </label> </p>
                                </li>                                                                                          
                            </ul>
                            <div class="buttons-set">
                                <button class="btn-black" type="submit"><span><span>Update</span></span></button>
                            </div>
                        </form>
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