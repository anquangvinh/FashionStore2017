<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style type="text/css">
    #area-chart{
        width: 840px;
        height: 300px;
    }
</style>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"> 
                    <strong>Orders</strong> 
                    <i class="fa fa-caret-right fa-style" aria-hidden="true" style="color: #337ab7"></i> 
                    <span style="font-size: 0.9em">Report</span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <table width="100%" class="table table-striped table-bordered table-hover">
            <tr>
                <th class="text-center fs-valign-middle" style="background: lightblue;" colspan="4">Order Status Statistics</th>
            </tr>
            <tr>
                <th colspan="2" width="50%" class="text-center fs-valign-middle">Total Order</th>
                <td colspan="2" class="text-center fs-valign-middle">${totalOrders}</td>
            </tr>
            <tr>
                <th class="text-center fs-valign-middle">Orders Pending</th>
                <th class="text-center fs-valign-middle">Orders Confirmed</th>
                <th class="text-center fs-valign-middle">Orders Completed</th>
                <th class="text-center fs-valign-middle">Orders Canceled</th>
            </tr>
            <tr>
                <td class="text-center fs-valign-middle">${pendingOrders}</td>
                <td class="text-center fs-valign-middle">${confirmedOrders}</td>
                <td class="text-center fs-valign-middle">${completedOrders}</td>
                <td class="text-center fs-valign-middle">${cancelOrders}</td>
            </tr>
            <tr>
                <th class="text-center fs-valign-middle" style="background: lightblue;" colspan="4">Average Statistics</th>
            </tr>
            <tr>
                <th colspan="2" class="text-center fs-valign-middle">Average orders per user daily in month (${thisMonth})</th>
                <td colspan="2" class="text-center fs-valign-middle">${avgOrderPerUserByMonth}</td>
            </tr>
            <tr>
                <th colspan="2" class="text-center fs-valign-middle">Average revenue orders in year (${thisYear})</th>
                <td colspan="2" class="text-center fs-valign-middle">$${avgOrdersRevenueInYear}</td>
            </tr>
        </table>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div class="col-sm-6 text-center">
            <label><span style="font-weight: 900;">TOTAL QUANTITY ORDERED BY CATEGORY</span></label>
            <div id="donut-chart-category" ></div>
        </div>
        <div class="col-sm-6 text-center">
            <label><span style="font-weight: 900;">TOTAL QUANTITY ORDERED BY SUB-CATEGORY</span></label>
            <div id="donut-chart-subcategory" ></div>
        </div>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div class="col-sm-6 text-center">
            <label><span style="font-weight: 900;">TOTAL REVENUE ORDERED BY CATEGORY</span></label>
            <div id="donut-chart-category-money" ></div>
        </div>
        <div class="col-sm-6 text-center">
            <label><span style="font-weight: 900;">TOTAL REVENUE ORDERED BY SUB-CATEGORY</span></label>
            <div id="donut-chart-subcategory-money" ></div>
        </div>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div class="col-sm-6" style="height: 50px;"></div>
        <div>
            <table width="100%" class="table table-striped table-bordered fs-order-line-chart">
                <tr>
                    <th class="text-center fs-valign-middle" colspan="2">TOTAL REVENUE DAILY IN MONTH</th>
                </tr>
                <tr>
                    <th class="text-center">YEAR</th>
                    <th class="text-center">MONTH</th>
                </tr>
                <tr>
                    <td class="text-center fs-valign-middle">
                        <select class="input-sm" name="order-chart-year" id="order-chart-year">
                            <c:forEach items="${listYear}" var="year">
                                <option value="${year}">${year}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="text-center fs-valign-middle">
                        <button class="btn btn-primary" id="btn-order-month-1" style="width: 100px;" value="1" onclick="showOrderLineChart(this.value)">JANUARY</button>
                        <button class="btn btn-primary" id="btn-order-month-2" style="width: 100px;" value="2" onclick="showOrderLineChart(this.value)">FEBRUARY</button>
                        <button class="btn btn-primary" id="btn-order-month-3" style="width: 100px;" value="3" onclick="showOrderLineChart(this.value)">MARCH</button>
                        <button class="btn btn-primary" id="btn-order-month-4" style="width: 100px;" value="4" onclick="showOrderLineChart(this.value)">APRIL</button>
                        <button class="btn btn-primary" id="btn-order-month-5" style="width: 100px;" value="5" onclick="showOrderLineChart(this.value)">MAY</button>
                        <button class="btn btn-primary" id="btn-order-month-6" style="width: 100px;" value="6" onclick="showOrderLineChart(this.value)">JUNE</button>
                        <br/><div style="padding-bottom: 10px;"></div>
                        <button class="btn btn-primary" id="btn-order-month-7" style="width: 100px;" value="7" onclick="showOrderLineChart(this.value)">JULY</button>
                        <button class="btn btn-primary" id="btn-order-month-8" style="width: 100px;" value="8" onclick="showOrderLineChart(this.value)">AUGUST</button>
                        <button class="btn btn-primary" id="btn-order-month-9" style="width: 100px;" value="9" onclick="showOrderLineChart(this.value)">SEPTEMBER</button>
                        <button class="btn btn-primary" id="btn-order-month-10" style="width: 100px;" value="10" onclick="showOrderLineChart(this.value)">OCTOBER</button>
                        <button class="btn btn-primary" id="btn-order-month-11" style="width: 100px;" value="11" onclick="showOrderLineChart(this.value)">NOVEMBER</button>
                        <button class="btn btn-primary" id="btn-order-month-12" style="width: 100px;" value="12" onclick="showOrderLineChart(this.value)">DECEMBER</button>
                    </td>
                </tr>
                <tr>
                    <td class="text-center fs-valign-middle" colspan="2" id="day-money-order-chart-div">
                        <div id="day-money-order-chart" ></div>
                    </td>
                </tr>
            </table>
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->
<script type="text/javascript">
    function showOrderLineChart(month) {
        var year = $('#order-chart-year').val();
        $('#day-money-order-chart').remove();
        $('#day-money-order-chart-div').html("<div id=\"day-money-order-chart\" ></div>");
        var arrayMonth = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];
        $.ajax({
            url: "admin/orders/ajax/orderLineMoneyChart.html",
            method: "GET",
            data: {month: month, year: year},
            dataType: 'JSON',
            success: function (response) {
                if (response.length == 0) {
                    $('#day-money-order-chart').html("<span style=\"font-weight: 700; color: red;\">NO DATA " + arrayMonth[month - 1] + "</span>")
                }
                config = {
                    data: response,
                    xkey: "label",
                    ykeys: ['value'],
                    labels: ["Total Revenue"],
                    fillOpacity: 1,
                    hideHover: "auto",
                    behaveLikeLine: true,
                    resize: true,
                    pointFillColors: ["#ffffff"],
                    pointStrokeColors: ["black"],
                    lineColors: ["red"],
                    preUnits: "$"
                };
                config.element = "day-money-order-chart";
                Morris.Line(config);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    }
    ;
</script>