package spring.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ejb.OrderStateFulBeanLocal;
import spring.ejb.OrderStateLessBeanLocal;
import spring.ejb.ProductStateLessBeanLocal;
import spring.ejb.UserAddressesStateLessBeanLocal;
import spring.ejb.UsersStateLessBeanLocal;
import spring.entity.CartLineInfo;
import spring.entity.Categories;
import spring.entity.CheckoutResponse;
import spring.entity.DiscountVoucher;
import spring.entity.Orders;
import spring.entity.Products;
import spring.entity.SizesByColor;
import spring.entity.UserAddresses;
import spring.entity.Users;

@Controller
@RequestMapping(value = "/orders/")
public class OrdersController {

    UserAddressesStateLessBeanLocal userAddressesStateLessBean = lookupUserAddressesStateLessBeanLocal();
    UsersStateLessBeanLocal usersStateLessBean = lookupUsersStateLessBeanLocal();
    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();
    OrderStateFulBeanLocal orderStateFulBean = lookupOrderStateFulBeanLocal();
    OrderStateLessBeanLocal orderStateLessBean = lookupOrderStateLessBeanLocal();

    @ResponseBody
    @RequestMapping(value = "ajax/getSession", method = RequestMethod.GET)
    public String ajaxGetSession(HttpServletRequest request) {
        String emailUser = (String) request.getSession().getAttribute("emailUser");
        if (emailUser == null || emailUser.equals("")) {
            return "0";
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/addtocart", method = RequestMethod.POST)
    public String ajaxAddtocart(@RequestParam("productID") Integer productID,
              @RequestParam("sizeID") Integer sizeID,
              @RequestParam("colorID") Integer colorID,
              @RequestParam("quantity") Integer quantity) {
        Products pro = orderStateLessBean.getProductByID(productID);
        SizesByColor sizesByColor = orderStateLessBean.getSizesByColorBySizeIDandColorID(sizeID, colorID);
        if (pro != null) {
            if (sizesByColor != null) {
                if (sizesByColor.getQuantity() < quantity) {
                    return "1"; //Not enough stock! Please enter different quantity.
                }
                CartLineInfo cartLineInfo = new CartLineInfo();
                cartLineInfo.setProduct(pro);
                cartLineInfo.setSizesByColor(sizesByColor);
                cartLineInfo.setQuantity(quantity);
                orderStateFulBean.addProduct(cartLineInfo);
                return "0"; //Add Product to Cart Successfully!
            }
            return "2"; //Color and Size error!
        }
        return "3";
    }

    @RequestMapping(value = "checkout", method = RequestMethod.GET)
    public String checkout(ModelMap model, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("emailUser");
        if (email == null || orderStateFulBean.showCart().size() == 0) {
            return "redirect:/index.html";
        } else {
            Users users = usersStateLessBean.findUserByEmail(email);
            if (users == null) {
                return "redirect:/index.html";
            } else {
                //2 dòng này thêm để render ra menu chính
                List<Categories> cateList = productStateLessBean.categoryList();
                model.addAttribute("emailU", users);
                model.addAttribute("cateList", cateList);
                model.addAttribute("userAddressList", userAddressesStateLessBean.AddressListUser(users.getUserID()));
                model.addAttribute("cartList", orderStateFulBean.showCart());
                model.addAttribute("grandTotal", orderStateFulBean.subTotal());
                return "client/pages/checkout";
            }
        }
    }

    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public String checkoutPost(ModelMap model, HttpServletRequest request, RedirectAttributes flashAttr) {
        String addressChoice = request.getParameter("address-chose");
        String success_orderID = "";
        String email = (String) request.getSession().getAttribute("emailUser");
        Users users = usersStateLessBean.findUserByEmail(email);
        String discountCode = request.getParameter("discount-code-input");
        String addressSize = request.getParameter("addressSize");
        DiscountVoucher discountVoucher = null;
        if (discountCode != null) {
            discountVoucher = orderStateLessBean.getDiscountVoucherByID(discountCode);
        }
        String note = request.getParameter("note");
        if (addressSize.equals("0")) {
            String firstname = request.getParameter("diffFirstname");
            String lastname = request.getParameter("diffLastname");
            String address = request.getParameter("diffAddress");
            String phone = request.getParameter("diffPhone");
            Orders orders = new Orders();
            orders.setUser(users);
            orders.setOrdersDate(new Date());
            orders.setReceiverFirstName(firstname);
            orders.setReceiverLastName(lastname);
            orders.setPhoneNumber(phone);
            orders.setDeliveryAddress(address);
            if (discountVoucher != null) {
                orders.setVoucher(discountVoucher);
            } else {
                orders.setVoucher(null);
            }
            orders.setNote(note);
            orders.setStatus(Short.parseShort("2"));
            success_orderID = orderStateFulBean.completePurchase(orders);
        } else {
            if (addressChoice.equals("difference")) {
                String firstname = request.getParameter("diffFirstname");
                String lastname = request.getParameter("diffLastname");
                String address = request.getParameter("diffAddress");
                String phone = request.getParameter("diffPhone");
                Orders orders = new Orders();
                orders.setUser(users);
                orders.setOrdersDate(new Date());
                orders.setReceiverFirstName(firstname);
                orders.setReceiverLastName(lastname);
                orders.setPhoneNumber(phone);
                orders.setDeliveryAddress(address);
                if (discountVoucher != null) {
                    orders.setVoucher(discountVoucher);
                } else {
                    orders.setVoucher(null);
                }
                orders.setNote(note);
                orders.setStatus(Short.parseShort("2"));
                success_orderID = orderStateFulBean.completePurchase(orders);
            } else {
                UserAddresses userAddresses = userAddressesStateLessBean.findAddressID(Integer.parseInt(addressChoice));
                if (userAddresses != null) {
                    String firstname = users.getFirstName();
                    String lastname = users.getLastName();
                    String address = userAddresses.getAddress();
                    String phone = userAddresses.getPhoneNumber();
                    Orders orders = new Orders();
                    orders.setUser(users);
                    orders.setOrdersDate(new Date());
                    orders.setReceiverFirstName(firstname);
                    orders.setReceiverLastName(lastname);
                    orders.setPhoneNumber(phone);
                    orders.setDeliveryAddress(address);
                    if (discountVoucher != null) {
                        orders.setVoucher(discountVoucher);
                    } else {
                        orders.setVoucher(null);
                    }
                    orders.setNote(note);
                    orders.setStatus(Short.parseShort("2"));
                    success_orderID = orderStateFulBean.completePurchase(orders);
                } else {
                    return "redirect:/orders/checkout.html";
                }
            }
        }
        if (success_orderID.equals("000")) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>ERROR</strong>\n"
                      + "</div>");
            return "redirect:/orders/checkout.html";
        } else {
            return "redirect:/orders/checkout-success/" + success_orderID + ".html";
        }
    }

    @RequestMapping(value = "checkout-success/{success_orderID}")
    public String checkout_success(ModelMap model, @PathVariable("success_orderID") Integer success_orderID) {
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        model.addAttribute("success_orderID", success_orderID);
        return "client/pages/checkout-success";
    }

    @RequestMapping(value = "shoppingcart")
    public String shoppingcart(ModelMap model, HttpServletRequest request) {
        if (orderStateFulBean.showCart().size() == 0 || orderStateFulBean.showCart() == null) {
            return "redirect:/index.html";
        }
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        model.addAttribute("cartList", orderStateFulBean.showCart());
        model.addAttribute("grandTotal", orderStateFulBean.subTotal());
        return "client/pages/shoppingcart";
    }

    @RequestMapping(value = "updatecart", method = RequestMethod.POST)
    public String updatecart(ModelMap model, HttpServletRequest request, RedirectAttributes flashAttr) {
        String error = "";
        for (CartLineInfo cartLineInfo : orderStateFulBean.showCart()) {
            String codeIdentify = cartLineInfo.getProduct().getProductID() + "-"
                      + cartLineInfo.getSizesByColor().getSizeID() + "-"
                      + cartLineInfo.getSizesByColor().getColor().getColorID();
            if (request.getParameter(codeIdentify) != null) {
                CartLineInfo oldCartLineInfo = cartLineInfo;
                cartLineInfo.setQuantity(Integer.parseInt(request.getParameter(codeIdentify)));
                SizesByColor sizesByColor = orderStateLessBean.getSizesByColorBySizeIDandColorID(cartLineInfo.getSizesByColor().getSizeID(), cartLineInfo.getSizesByColor().getColor().getColorID());
                if (sizesByColor != null) {
                    if (sizesByColor.getQuantity() < Integer.parseInt(request.getParameter(codeIdentify))) {
                        error += " " + sizesByColor.getColor().getProduct().getProductName() + "  ";
                    } else {
                        orderStateFulBean.updateProduct(oldCartLineInfo, cartLineInfo);
                    }
                }
            }
        }
        if (error.equals("")) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                      + "<strong>UPDATE CART SUCCESSFULLY</strong>\n"
                      + "</div>");
            return "redirect:/orders/shoppingcart.html";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>PRODUCT" + error + " OUT OF STOCK</strong>\n"
                      + "</div>");
            return "redirect:/orders/shoppingcart.html";
        }
    }

    @RequestMapping(value = "deleteitemCart/{productid}/{sizeID}/{colorID}", method = RequestMethod.GET)
    public String deleteitemCart(@PathVariable("productid") int productid,
              @PathVariable("sizeID") int sizeid,
              @PathVariable("colorID") int colorid,
              RedirectAttributes flashAttr) {
        CartLineInfo cartLineInfo = orderStateFulBean.getProductInListByID(productid, sizeid, colorid);
        if (cartLineInfo != null) {
            orderStateFulBean.deleteProduct(cartLineInfo);
            if (orderStateFulBean.showCart().size() == 0 || orderStateFulBean.showCart() == null) {
                return "redirect:/index.html";
            }
        }
        flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                  + "<strong>DELETE ITEM IN CART SUCCESSFULLY</strong>\n"
                  + "</div>");
        return "redirect:/orders/shoppingcart.html";
    }

    @ResponseBody
    @RequestMapping(value = "deleteitemCartInHeader", method = RequestMethod.POST)
    public String deleteitemCartInHeader(@RequestParam("productID") Integer productid,
              @RequestParam("sizeID") Integer sizeid,
              @RequestParam("colorID") Integer colorid) {
        CartLineInfo cartLineInfo = orderStateFulBean.getProductInListByID(productid, sizeid, colorid);
        if (cartLineInfo != null) {
            orderStateFulBean.deleteProduct(cartLineInfo);
        }
        return getCart();
    }

    @RequestMapping(value = "order-history")
    public String orderhistory(ModelMap model, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("emailUser");
        if (email == null) {
            return "redirect:/index.html";
        }
        model.addAttribute("orderStatus", 4);
        model.addAttribute("orderList", orderStateLessBean.getAllOrderByUserID(usersStateLessBean.findUserByEmail(email).getUserID()));
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/order-history";
    }

    @RequestMapping(value = "order-history/{status}")
    public String orderhistoryByStatus(ModelMap model, HttpServletRequest request, @PathVariable("status") Integer status) {
        String email = (String) request.getSession().getAttribute("emailUser");
        if (email == null) {
            return "redirect:/index.html";
        }
        model.addAttribute("orderStatus", status);
        model.addAttribute("orderList", orderStateLessBean.getAllOrderByUserIDAndStatus(usersStateLessBean.findUserByEmail(email).getUserID(), status));
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/order-history";
    }

    @RequestMapping(value = "order-history-detail/{orderID}")
    public String orderhistorydetail(ModelMap model, @PathVariable("orderID") Integer orderID, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("emailUser");
        if (email == null) {
            return "redirect:/index.html";
        }
        model.addAttribute("orderdetailList", orderStateLessBean.getAllOrderDetailByOrderID(orderID));
        model.addAttribute("order", orderStateLessBean.getOrderByID(orderID));
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/order-history-detail";
    }

    @RequestMapping(value = "cancelorder/{orderID}", method = RequestMethod.GET)
    public String cancelorder(@PathVariable("orderID") Integer orderID) {
        Orders order = orderStateLessBean.getOrderByID(orderID);
        if (order != null) {
            if (orderStateLessBean.confirmStatusOrder(order, Short.parseShort("0"))) {
                return "redirect:/orders/order-history.html";
            }
        }
        return "redirect:/orders/order-history.html";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/cart", method = RequestMethod.GET)
    public String getCart() {
        String str_cart_detail = "";
        String str_cart_big = "";
        String str_cart_button = "";
        String str_subtotal = "";
        float subTotal = 0;
        int cartSize = 0;
        if (orderStateFulBean.showCart().size() == 0) {
            subTotal = 0;
            cartSize = 0;
        } else {
            subTotal = orderStateFulBean.subTotal();
            cartSize = orderStateFulBean.showCart().size();
            str_cart_button = "<div class=\"cart-btn\">\n"
                      + "                                <a href=\"orders/shoppingcart.html\">VIEW BAG</a>\n"
                      + "                                <button onclick=\"checkoutClick();\" style=\"background: #d6644a;\n"
                      + "                                font-size: 11px;\n"
                      + "                                color: #fff;\n"
                      + "                                text-transform: none;\n"
                      + "                                height: 33px;\n"
                      + "                                padding: 0 17px;\n"
                      + "                                line-height: 33px;\n"
                      + "                                font-weight: 700;\" class=\"btn\">CHECKOUT</button> \n"
                      + "                            </div>";
            str_subtotal = "<div class=\"ci-total\">Subtotal: $" + subTotal + "</div>";
            for (CartLineInfo cartLineInfo : orderStateFulBean.showCart()) {
                str_cart_detail += "<div class=\"ci-item\">\n"
                          + "        <img src=\"assets/images/products/" + cartLineInfo.getProduct().getUrlImg() + "\" width=\"90\" alt=\"\"/>\n"
                          + "        <div class=\"ci-item-info\">\n"
                          + "            <h5>\n"
                          + "                <a style=\"font-weight: 700;\" href=\"" + cartLineInfo.getProduct().getProductID() + "-" + cartLineInfo.getProduct().getProductColorList().get(0).getColorID() + "-" + cartLineInfo.getProduct().getProductNameNA() + ".html\">\n"
                          + "                    " + cartLineInfo.getProduct().getProductName() + "\n"
                          + "                </a>\n"
                          + "            </h5>\n"
                          + "<p>&nbsp Color: " + cartLineInfo.getSizesByColor().getColor().getColor() + "\n"
                          + "                                            <img fs-color=\"" + cartLineInfo.getSizesByColor().getColor().getColorID() + "\" \n"
                          + "                                                 src=\"assets/images/products/colors/" + cartLineInfo.getSizesByColor().getColor().getUrlColorImg() + "\" \n"
                          + "                                                 class=\"img-responsive\" \n"
                          + "                                                 alt=\"" + cartLineInfo.getSizesByColor().getColor().getUrlColorImg() + "\" \n"
                          + "                                                 title=\"" + cartLineInfo.getSizesByColor().getColor().getColor() + "\"\n"
                          + "                                                 style=\"width: 18px; height: 18px;\"/>\n"
                          + "                                        </p>"
                          + "<p>Size: " + cartLineInfo.getSizesByColor().getProductSize() + "</p>\n"
                          + " <p>Quantity: &nbsp " + cartLineInfo.getQuantity() + "</p>\n"
                          + "            <p>Price: &nbsp $" + cartLineInfo.getProduct().getPrice() + "</p>\n"
                          + "        </div>\n"
                          + "    </div>";
            }
        }
        //<div class=\"ci-edit\"><button onclick=\"deleteItem(" + cartLineInfo.getProduct().getProductID() + "," + cartLineInfo.getSizesByColor().getSizeID() + "," + cartLineInfo.getSizesByColor().getColor().getColorID() + ");\" class=\"edit fa fa-trash\"></button></div>\n"
        str_cart_big = "<span><i class=\"fa fa-shopping-cart\"></i></span>\n"
                  + "<div class=\"cart-info\">\n"
                  + "<small>You have <em class=\"highlight\">" + cartSize + " item(s)</em> in your shopping bag</small>\n"
                  + str_cart_detail
                  + str_subtotal
                  + str_cart_button
                  + "                        </div>";

        return str_cart_big;
    }

    @ResponseBody
    @RequestMapping(value = "ajax/discount", method = RequestMethod.POST)
    public String getDiscount(@RequestParam("discountCode") String discountCode, @RequestParam("emailUser") String emailUser) {
        DiscountVoucher discountVoucher = orderStateLessBean.getDiscountVoucherByID(discountCode);
        Users users = usersStateLessBean.findUserByEmail(emailUser);
        ObjectMapper mapper = new ObjectMapper();
        if (discountVoucher != null) {
            if (discountCode.equals(discountVoucher.getVoucherID())) {
                if (users != null) {
                    List<Users> userList = orderStateLessBean.getAllUserUseDiscountVoucherByVouID(discountVoucher.getVoucherID());
                    if (userList != null) {
                        if (userList.size() != 0) {
                            for (Users user : userList) {
                                if (user.getUserID() == users.getUserID()) { // da su dung voucher nay
                                    CheckoutResponse checkoutResponse = new CheckoutResponse();
                                    checkoutResponse.setStatus("4");
                                    try {
                                        String result = mapper.writeValueAsString(checkoutResponse);
                                        return result;
                                    } catch (JsonProcessingException ex) {
                                        Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                    if (discountVoucher.getQuantity() == 0) { // out of quantity
                        CheckoutResponse checkoutResponse = new CheckoutResponse();
                        checkoutResponse.setStatus("0");
                        try {
                            String result = mapper.writeValueAsString(checkoutResponse);
                            return result;
                        } catch (JsonProcessingException ex) {
                            Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        Date todayDate = new Date();
                        Date beginDate = discountVoucher.getBeginDate();
                        Date endDate = discountVoucher.getEndDate();
                        if ((beginDate == null && endDate == null)
                                  || (todayDate.after(beginDate) && todayDate.before(endDate))
                                  || (todayDate.after(beginDate) && endDate == null)
                                  || (beginDate == null && todayDate.before(endDate))) {
                            CheckoutResponse checkoutResponse = new CheckoutResponse();
                            DecimalFormat df = new DecimalFormat("#.#");
                            df.setRoundingMode(RoundingMode.FLOOR);
                            float discountTotal = orderStateFulBean.subTotal() * discountVoucher.getFloatDiscount();
                            float orderTotal = orderStateFulBean.subTotal() - discountTotal;
                            String str_show_discount = "<tr>\n"
                                      + "                                    <th>Discount</th>\n"
                                      + "                                    <td>\n"
                                      + "                                        <div class=\"\">-$" + df.format(discountTotal) + "</div>\n"
                                      + "                                    </td> \n"
                                      + "                                </tr>\n"
                                      + "                                <tr>\n"
                                      + "                                    <th>Order Total</th>\n"
                                      + "                                    <td>\n"
                                      + "                                        <div class=\"grandTotal\">$" + orderTotal + "</div>\n"
                                      + "                                    </td> \n"
                                      + "                                </tr>";
                            String str_show_percent_discount = "<div id=\"discountShow\" style=\"padding-bottom: 15px;\">\n"
                                      + "<input type=\"hidden\" id=\"discount-code-input\" name=\"discount-code-input\" value=\"" + discountCode + "\"/>\n"
                                      + "<b>Your Discount Code: " + discountCode + "; Discount " + discountVoucher.getDiscount() + "%</b>&nbsp<button type=\"button\" class=\"fa fa-times\" id=\"cancel-discount\"  onclick=\"enterDiscountAgain();\"></button>\n"
                                      + "</div>";
                            checkoutResponse.setShowDiscount(str_show_discount);
                            checkoutResponse.setShowDiscountPercent(str_show_percent_discount);
                            try {
                                String result = mapper.writeValueAsString(checkoutResponse);
                                return result;
                            } catch (JsonProcessingException ex) {
                                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if ((todayDate.before(beginDate) && endDate != null) || (todayDate.before(beginDate) && endDate == null)) { // chua toi han
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String date = sdf.format(beginDate);
                            CheckoutResponse checkoutResponse = new CheckoutResponse();
                            checkoutResponse.setStatus("2");
                            checkoutResponse.setShowDiscount(date);
                            try {
                                String result = mapper.writeValueAsString(checkoutResponse);
                                return result;
                            } catch (JsonProcessingException ex) {
                                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if ((todayDate.after(endDate) && beginDate != null) || (todayDate.after(endDate) && beginDate == null)) { // qua han
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String date = sdf.format(endDate);
                            CheckoutResponse checkoutResponse = new CheckoutResponse();
                            checkoutResponse.setStatus("3");
                            checkoutResponse.setShowDiscount(date);
                            try {
                                String result = mapper.writeValueAsString(checkoutResponse);
                                return result;
                            } catch (JsonProcessingException ex) {
                                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } else { // error
                    CheckoutResponse checkoutResponse = new CheckoutResponse();
                    checkoutResponse.setStatus("5");
                    try {
                        String result = mapper.writeValueAsString(checkoutResponse);
                        return result;
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else { // wrong discount code
                CheckoutResponse checkoutResponse = new CheckoutResponse();
                checkoutResponse.setStatus("6");
                try {
                    String result = mapper.writeValueAsString(checkoutResponse);
                    return result;
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }
        } else {
            CheckoutResponse checkoutResponse = new CheckoutResponse(); // khong tim thay discount vou
            checkoutResponse.setStatus("1");
            try {
                String result = mapper.writeValueAsString(checkoutResponse);
                return result;
            } catch (JsonProcessingException ex) {
                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "ajax/nodiscount", method = RequestMethod.GET)
    public String getNoDiscount() {
        String str_show = "<tr>\n"
                  + "                                    <th>Discount</th>\n"
                  + "                                    <td>\n"
                  + "                                        <div class=\"\">$0.0</div>\n"
                  + "                                    </td> \n"
                  + "                                </tr>\n"
                  + "                                <tr>\n"
                  + "                                    <th>Order Total</th>\n"
                  + "                                    <td>\n"
                  + "                                        <div class=\"grandTotal\">$" + orderStateFulBean.subTotal() + "</div>\n"
                  + "                                    </td> \n"
                  + "                                </tr>";
        return str_show;
    }

    // <editor-fold defaultstate="collapsed" desc="Look Up Beans Local">
    private OrderStateLessBeanLocal lookupOrderStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (OrderStateLessBeanLocal) c.lookup("java:global/fashionshop/OrderStateLessBean!spring.ejb.OrderStateLessBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OrderStateFulBeanLocal lookupOrderStateFulBeanLocal() {
        try {
            Context c = new InitialContext();
            return (OrderStateFulBeanLocal) c.lookup("java:global/fashionshop/OrderStateFulBean!spring.ejb.OrderStateFulBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ProductStateLessBeanLocal lookupProductStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (ProductStateLessBeanLocal) c.lookup("java:global/fashionshop/ProductStateLessBean!spring.ejb.ProductStateLessBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UsersStateLessBeanLocal lookupUsersStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (UsersStateLessBeanLocal) c.lookup("java:global/fashionshop/UsersStateLessBean!spring.ejb.UsersStateLessBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UserAddressesStateLessBeanLocal lookupUserAddressesStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (UserAddressesStateLessBeanLocal) c.lookup("java:global/fashionshop/UserAddressesStateLessBean!spring.ejb.UserAddressesStateLessBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }// </editor-fold>

}
