<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <base href="${pageContext.servletContext.contextPath}/" />
        <title>Order Invoice</title>
        <script src="assets/admin/vendor/jquery/jquery.min.js"></script>
        <script src="assets/admin/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="assets/admin/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
            @page 
            {
                size:  auto;
                margin-bottom: 5mm;
                margin-top: 5mm;
                margin-left: 10mm;
                margin-right: 10mm;
            }
        </style>
    </head>
    <body>
        <div align="center" id="toPrint">
            <table border="0" style="border-collapse: collapse; width: 800px;">
                <tr>
                    <td align="left" style="padding-left: 10px"><img src="assets/images/basic/logo.png" class="img-responsive" alt=""></img></td>
                    <td align="left"><b style="font-size: 50px;">INVOICE</b></td>
                </tr>
                <tr>
                    <td><br></br><br/></td>
                </tr>
                <tr>
                    <td align="right"><b>Order No:</b></td>
                    <td># ${orders.ordersID}</td>
                </tr>
                <tr>
                    <td align="right"><b>Order Date:</b></td>
                    <td><fmt:formatDate value="${orders.ordersDate}" pattern="dd-MM-yyyy hh:mm:ss"/></td>
                </tr>
            </table>
            <br/>
            <table border="1" style="border-collapse: collapse; width: 800px;">
                <thead>
                    <tr>
                        <th style="padding-left: 20px;">Client Order</th>
                        <th style="padding-left: 20px;">Ship to Address</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="padding-left: 20px;">
                            <b>${orders.user.firstName} &nbsp ${orders.user.lastName}</b><br/>
                            ${orders.user.email}
                        </td>
                        <td style="padding-left: 20px;">
                            <b>${orders.receiverFirstName} &nbsp ${orders.receiverLastName}</b><br/>
                            ${orders.deliveryAddress}<br/>
                            Phone: ${orders.phoneNumber}<br/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br/>
            <table border="1" style="border-collapse: collapse; width: 800px;">
                <thead>
                    <tr>
                        <th>Product name</th>
                        <th>Quantity</th>
                        <th>Price for one</th>
                        <th>Product discount</th>
                        <th>SubTotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders.orderDetailList}" var="orderDetail">
                        <tr>
                            <td align="center">${orderDetail.product.productName}</td>
                            <td align="center">${orderDetail.quantity}</td>
                            <td align="center">$${orderDetail.product.price}</td>
                            <td align="center">-$${orderDetail.product.getProductDiscountPrice()}</td>
                            <td align="center">${orderDetail.getSubTotal()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" align="right" style="padding-right: 5px;">Order Discount</td>
                        <td align="center">-$${orders.getOrderDiscountPrice()}</td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right" style="padding-right: 5px;">Payment Total</td>
                        <td align="center">$${orders.getPaymentTotal()}</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div class="document"></div>
        <br/>
        <div align="center">
            <button class="btn btn-primary" 
                    onclick="$(this).css('visibility', 'hidden'); $('#btnDocument').css('visibility', 'hidden'); $('#btnBack').css('visibility', 'hidden');
                            window.print();
                            $(this).css('visibility', 'visible'); $('#btnDocument').css('visibility', 'visible'); $('#btnBack').css('visibility', 'visible');" type="button">PRINT</button>
            <!--<button class="btn btn-primary" id="btnDocument" onclick="getDocument();">SAVE PDF</button>-->
            <button onclick="window.location = 'admin/orders/orderlistdetail/${orders.ordersID}.html'" class="btn btn-primary" id="btnBack">BACK TO ORDER DETAILS</button>
        </div>
    </body>
    <script type="text/javascript">
        function getDocument() {
            var htmlcontent = document.getElementById("toPrint").innerHTML;
            $.ajax({
                url: "admin/orders/createPDF.html",
                method: "POST",
                data: {htmlContent: htmlcontent},
                dataType: 'html',
                success: function (response) {
                    alert(response);
                }
            });
        }
    </script>
</html>
