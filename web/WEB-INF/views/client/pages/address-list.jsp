<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<%@include file="../pages/modaldetailAD.jsp" %>--%>
<!-- BREADCRUMBS -->
<jsp:include page="../blocks/breadcrumbs.jsp" flush="true" />

<!-- MY ACCOUNT -->
<div class="account-wrap">
    <div class="container">
        <div class="row">
            <div class="col-sm-8 col-md-9">
                <!-- HTML -->
                <div id="account-id">
                    <h4 class="account-title"><span class="fa fa-chevron-right"></span>List Address</h4>                                                                  
                    <div class="account-form">
                        <div class="fs-lv-bg" id="fs-header" style="margin-top: -25px;">
                            <h1 class="fs-site-title">address by list</h1>
                            <!--<p>this is my address</p>-->  
                        </div>
                        <br/><br/>
                        <!-- table address-list -->
                        <div class="panel panel-success fs-panel" style="margin-top: -20px;">
                            <div class="panel-heading" style="padding: 10px;">
                                <h3 class="panel-title text-center">Table Address</h3>
                                <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
                            </div>
                            <div class="panel-body" style="margin-top: -40px;">
                                <div class="container-fluid fs-inner">
                                    <table class="table-hover fs-tableizer-table table">
                                        <tr class="fs-tableizer-firstrow">
                                            <th class="text-center" style="background: lavenderblush;padding: 10px;">NO</th>
                                            <th class="text-center" style="background: lavenderblush;padding: 10px;">ADDRESS</th>
                                            <th class="text-center" style="background: lavenderblush;padding: 10px;">PHONE</th>
                                            <th class="text-center" style="background: lavenderblush;padding: 10px;">ACTION</th>
                                        </tr>
                                        <c:forEach var="uad" items="${ualist}" varStatus="no">
                                            <tr id="fs-list-id-${uad.addressID}" >
                                                <td align="center" style="border: 4px #B8E834 #fff;
                                                    width: auto; padding: 10px;background: #f8f8f8" >${no.index + 1}</td>
                                                <td align="center" style="border: 4px #B8E834 #fff;
                                                    width: auto; padding: 10px;background: #f8f8f8" >${uad.address}</td>
                                                <td align="center" style="border: 4px #B8E834 #fff;
                                                    width: auto; padding: 10px;background: #f8f8f8">${uad.phoneNumber}</td>
                                                <td align="center" style="border: 4px #B8E834 #fff;
                                                    width: auto; padding: 10px;background: #f8f8f8">
                                                    <a class="btn btn-warning btn-sm" href="user/address-book/${findUsersID}-${uad.addressID}.html"><i class="fa fa-edit"></i> Update</a>
                                                    <a class="btn btn-danger btn-sm fs-delete-button-AD" fs-addressID="${uad.addressID}"><i class="fa fa-remove"></i> Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>   
                        <div class="panel panel-warning fs-panel-add ">
                            <div class="panel-heading " id="fs-table-add-address" style="padding: 10px;">
                                <h3 class="panel-title text-center">Add New Address</h3>
                                <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
                            </div>
                            <div class="panel-body">
                                <form:form id="shipping-zip-form" cssClass="fs-form-add-address" action="user/address-add/${sessionScope.findUsersID}.html" method="post" modelAttribute="userAddress">                                       
                                    ${error}
                                    <c:set var="aaa" value="${listua}"/>
                                    <c:choose>
                                        <c:when test="${fn:length(aaa) > 17}">
                                        <p style="color: red; text-align: center; font-size: 16px">You cannot insert Address</p>
                                        <ul class="form-list row">
                                        <li class="col-md-6 col-sm-6">
                                            <label >Address <em>*</em></label>
                                            <form:input path="address" id="txtaddress"  cssClass="input-text fs-address-add" disabled="true"/>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-address-add-user-error"></p>
                                            <span></span>
                                        </li>
                                        <li class="col-md-6 col-sm-6">
                                            <label ><i class="fa fa-phone"></i> Phone Number <em>*</em></label>
                                            <form:input path="phoneNumber" id="txtphone" cssClass="input-text fs-phone-add" disabled="true"/>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-phone-add-user-error"></p>
                                        </li>
                                    </ul>
<!--                                    <div class="buttons-set" >
                                        <button class="btn-black fs-button-add-address" type="submit" fs-userID="${sessionScope.findUsersID}"><span><span>Create</span></span></button>
                                        <button class="btn-black fs-button-reset-address" type="reset"><span><span>Cancel</span></span></button>
                                    </div>-->
                                        </c:when>
                                        <c:otherwise>
                                        <p style="color: #00A8FF; text-align: center; font-size: 16px">You can insert 20 Address</p>
                                    <ul class="form-list row">
                                        <li class="col-md-6 col-sm-6">
                                            <label >Address <em>*</em></label>
                                            <form:input path="address" id="txtaddress"  cssClass="input-text fs-address-add"/>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-address-add-user-error"></p>
                                            <span></span>
                                        </li>
                                        <li class="col-md-6 col-sm-6">
                                            <label ><i class="fa fa-phone"></i> Phone Number <em>*</em></label>
                                            <form:input path="phoneNumber" id="txtphone" cssClass="input-text fs-phone-add"/>
                                            <p class="help-block" style="color: red; text-align: center; margin-top: -10px;" id="fs-phone-add-user-error"></p>
                                        </li>
                                    </ul>
                                    <div class="buttons-set" >
                                        <button class="btn-black fs-button-add-address" type="submit" fs-userID="${sessionScope.findUsersID}"><span><span>Create</span></span></button>
                                        <button class="btn-black fs-button-reset-address" type="reset"><span><span>Cancel</span></span></button>
                                    </div>
                                        </c:otherwise>
                                    </c:choose>
                                </form:form>
                            </div >
                        </div>
                    </div>                                    
                </div>
            </div>

            <div class="col-sm-4 col-md-3  checkout-steps">
                <!-- USER-RIGHT-MENU -->
                <jsp:include page="../blocks/user-right-menu.jsp" flush="true" />
            </div>
        </div>
    </div>
</div>
<div class="clearfix space20"></div>
