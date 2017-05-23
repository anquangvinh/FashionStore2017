/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ejb.OrderStateLessBeanLocal;
import spring.ejb.ProductStateLessBeanLocal;
import spring.entity.CartLineInfo;
import spring.entity.Categories;
import spring.entity.DiscountVoucher;
import spring.entity.QuantityOrderChart;
import spring.entity.Orders;
import spring.entity.OrdersDetail;
import spring.entity.ProductColors;
import spring.entity.Products;
import spring.entity.RevenueOrderChart;
import spring.entity.SizesByColor;
import spring.entity.SubCategories;

@Controller
@RequestMapping(value = "/admin/orders/")
public class Orders_Controller {

    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();
    OrderStateLessBeanLocal orderStateLessBean = lookupOrderStateLessBeanLocal();

    @RequestMapping(value = "list")
    public String ordersList(ModelMap model) {
        model.addAttribute("orderStatus", 4);
        model.addAttribute("orderList", orderStateLessBean.getAllOrder());
        return "admin/pages/orders-list";
    }
    
    @RequestMapping(value = "list/{status}")
    public String ordersListByStatus(ModelMap model, @PathVariable("status") Integer status) {
        model.addAttribute("orderStatus", status);
        model.addAttribute("orderList", orderStateLessBean.getAllOrderByStatus(status));
        return "admin/pages/orders-list";
    }

    @RequestMapping(value = "orderchart")
    public String ordersChart(ModelMap model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM-yyyy");
        String thisMonth = sdfMonth.format(new Date());
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        String thisYear = sdfYear.format(new Date());
        List<Orders> orderList = orderStateLessBean.getAllOrder();
        float totalRevenue = 0;
        for (Orders order : orderList) {
            if (order.getOrdersDate().getYear() == (new Date().getYear())) {
                totalRevenue += order.getPaymentTotal();
            }
        }
        model.addAttribute("thisYear", thisYear);
        model.addAttribute("avgOrdersRevenueInYear", (totalRevenue/12));
        model.addAttribute("thisMonth", thisMonth);
        model.addAttribute("avgOrderPerUserByMonth", orderStateLessBean.averageOrdersPerUserByMonth(date));
        model.addAttribute("totalOrders", orderStateLessBean.countOrders());
        model.addAttribute("pendingOrders", orderStateLessBean.countOrderByStatus(2));
        model.addAttribute("confirmedOrders", orderStateLessBean.countOrderByStatus(3));
        model.addAttribute("completedOrders", orderStateLessBean.countOrderByStatus(1));
        model.addAttribute("cancelOrders", orderStateLessBean.countOrderByStatus(0));
        model.addAttribute("listYear", orderStateLessBean.getAllYearOrdered());
        return "admin/pages/orders-chart";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/orderDonutQuantitySubcategory", method = RequestMethod.GET)
    public String getDonutSubcategoryQuantityForChart() {
        List<SubCategories> subCategoriesList = productStateLessBean.subCategoryList();
        List<OrdersDetail> ordersDetailList = orderStateLessBean.getAllOrderDetail();
        List<QuantityOrderChart> subcateQuantityChartList = new ArrayList<>();
        for (SubCategories subCategory : subCategoriesList) {
            String label = subCategory.getCategory().getCateName() + "-" + subCategory.getSubCateName();
            int value = 0;
            for (OrdersDetail ordersDetail : ordersDetailList) {
                if (ordersDetail.getOrder().getStatus() == 1) {
                    if (ordersDetail.getProduct().getSubCate().getCategory().getCateID().equals(subCategory.getCategory().getCateID())
                              && ordersDetail.getProduct().getSubCate().getSubCateID().equals(subCategory.getSubCateID())) {
                        value += ordersDetail.getQuantity();
                    }
                }
            }
            QuantityOrderChart totalQuantityOrderChart = new QuantityOrderChart();
            totalQuantityOrderChart.setLabel(label);
            totalQuantityOrderChart.setValue(value);
            subcateQuantityChartList.add(totalQuantityOrderChart);
        }
        Collections.sort(subcateQuantityChartList, new QuantityOrderChart.QuantityOrderChartComparator());
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(subcateQuantityChartList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }

    @ResponseBody
    @RequestMapping(value = "ajax/orderDonutQuantityCategory", method = RequestMethod.GET)
    public String getDonutCategoryQuantityForChart() {
        List<Categories> categoriesList = productStateLessBean.categoryList();
        List<OrdersDetail> ordersDetailList = orderStateLessBean.getAllOrderDetail();
        List<QuantityOrderChart> categoryQuantityChartList = new ArrayList<>();
        for (Categories categories : categoriesList) {
            String label = categories.getCateName();
            int value = 0;
            for (OrdersDetail ordersDetail : ordersDetailList) {
                if (ordersDetail.getOrder().getStatus() == 1) {
                    if (ordersDetail.getProduct().getCategory().getCateID().equals(categories.getCateID())) {
                        value += ordersDetail.getQuantity();
                    }
                }
            }
            QuantityOrderChart totalQuantityOrderChart = new QuantityOrderChart();
            totalQuantityOrderChart.setLabel(label);
            totalQuantityOrderChart.setValue(value);
            categoryQuantityChartList.add(totalQuantityOrderChart);
        }
        Collections.sort(categoryQuantityChartList, new QuantityOrderChart.QuantityOrderChartComparator());
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(categoryQuantityChartList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }

    @ResponseBody
    @RequestMapping(value = "ajax/orderDonutMoneyCategory", method = RequestMethod.GET)
    public String getDonutCategoryRevenueForChart() {
        List<Categories> categoriesList = productStateLessBean.categoryList();
        List<OrdersDetail> ordersDetailList = orderStateLessBean.getAllOrderDetail();
        List<RevenueOrderChart> totalRevenueSubcateChartList = new ArrayList<>();
        for (Categories categories : categoriesList) {
            String label = categories.getCateName();
            float value = 0;
            for (OrdersDetail ordersDetail : ordersDetailList) {
                if (ordersDetail.getOrder().getStatus() == 1) {
                    if (ordersDetail.getProduct().getCategory().getCateID().equals(categories.getCateID())) {
                        value += ordersDetail.getSubTotal();
                    }
                }
            }
            RevenueOrderChart revenueOrderChart = new RevenueOrderChart();
            revenueOrderChart.setLabel(label);
            revenueOrderChart.setValue(value);
            totalRevenueSubcateChartList.add(revenueOrderChart);
        }
        Collections.sort(totalRevenueSubcateChartList, new RevenueOrderChart.RevenueOrderChartComparator());
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(totalRevenueSubcateChartList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }

    @ResponseBody
    @RequestMapping(value = "ajax/orderDonutMoneySubcategory", method = RequestMethod.GET)
    public String getDonutSubcategoryRevenueForChart() {
        List<SubCategories> subCategoriesList = productStateLessBean.subCategoryList();
        List<OrdersDetail> ordersDetailList = orderStateLessBean.getAllOrderDetail();
        List<RevenueOrderChart> totalRevenueSubcateChartList = new ArrayList<>();
        for (SubCategories subCategory : subCategoriesList) {
            String label = subCategory.getCategory().getCateName() + "-" + subCategory.getSubCateName();
            float value = 0;
            for (OrdersDetail ordersDetail : ordersDetailList) {
                if (ordersDetail.getOrder().getStatus() == 1) {
                    if (ordersDetail.getProduct().getSubCate().getCategory().getCateID().equals(subCategory.getCategory().getCateID())
                              && ordersDetail.getProduct().getSubCate().getSubCateID().equals(subCategory.getSubCateID())) {
                        value += ordersDetail.getSubTotal();
                    }
                }
            }
            RevenueOrderChart revenueOrderChart = new RevenueOrderChart();
            revenueOrderChart.setLabel(label);
            revenueOrderChart.setValue(value);
            totalRevenueSubcateChartList.add(revenueOrderChart);
        }
        Collections.sort(totalRevenueSubcateChartList, new RevenueOrderChart.RevenueOrderChartComparator());
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(totalRevenueSubcateChartList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }

    @ResponseBody
    @RequestMapping(value = "ajax/orderLineMoneyChart", method = RequestMethod.GET)
    public String getLineRevenueForChart(@RequestParam("month") Integer month,
              @RequestParam("year") Integer year) {
        List<Orders> orderList = orderStateLessBean.getAllOrderByMonth(month,year);
        List<Integer> dayOrderedList = orderStateLessBean.getAllDayOrderedByMonth(month,year);
        List<RevenueOrderChart> totalRevenueSubcateChartList = new ArrayList<>();
        for (Integer day : dayOrderedList) {
            String label = String.valueOf(year) + "-" + String.valueOf(month) + "-" + day;
            float value = 0;
            for (Orders order : orderList) {
                if (order.getOrdersDate().getDate() == day && order.getStatus() == 1) {
                    value += order.getPaymentTotal();
                }
            }
            RevenueOrderChart revenueOrderChart = new RevenueOrderChart();
            revenueOrderChart.setLabel(label);
            revenueOrderChart.setValue(value);
            totalRevenueSubcateChartList.add(revenueOrderChart);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(totalRevenueSubcateChartList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "ajax/getMonthOrderedByYear", method = RequestMethod.GET)
    public String getMonthOrderedByYear(@RequestParam("year") Integer year) {
        List<Integer> monthList = orderStateLessBean.getAllMonthOrderedByYear(year);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(monthList);
            return result;
        } catch (Exception e) {
            return "Error!" + e.getMessage();
        }
    }

    @RequestMapping(value = "discountlist")
    public String discountlist(ModelMap model) {
        model.addAttribute("discountList", orderStateLessBean.getAllDiscountVoucher());
        return "admin/pages/discount-list";
    }

    @RequestMapping(value = "confirmstatusOrder/{orderid}/{status}", method = RequestMethod.GET)
    public String confirmstatusOrder(@PathVariable("orderid") int orderid, @PathVariable("status") short status) {
        Orders order = orderStateLessBean.getOrderByID(orderid);
        if (order != null) {
            if (orderStateLessBean.confirmStatusOrder(order, status)) {
                return "redirect:/admin/orders/list.html";
            }
        }
        return "redirect:/admin/orders/list.html";
    }

    @RequestMapping(value = "orderlistdetail/{orderid}", method = RequestMethod.GET)
    public String orderlistdetail(@PathVariable("orderid") int orderid, ModelMap model) {
        Orders order = orderStateLessBean.getOrderByID(orderid);
        if (order != null) {
            model.addAttribute("orderdetailList", orderStateLessBean.getAllOrderDetailByOrderID(orderid));
            model.addAttribute("order", order);
            return "admin/pages/orders-list-detail";
        }
        return "redirect:/admin/orders/list.html";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/searchproduct", method = RequestMethod.POST)
    public String searchProduct(@RequestParam("searchType") Integer searchType,
              @RequestParam("searchText") String searchText) {
        String html = "";
        if (searchType == 1) {
            String temp = Normalizer.normalize(searchText, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            List<Products> proList = orderStateLessBean.getListProductsByName(pattern.matcher(temp).replaceAll("").replaceAll(" ", "%"));
            if (proList != null) {
                for (int i = 0; i < proList.size(); i++) {
                    html += "<tr style=\"height: 30px;\">\n"
                              + "<td class=\"text-center fs-valign-middle proID\">" + proList.get(i).getProductID() + "</td>\n"
                              + "<td class=\"text-center fs-valign-middle\">" + proList.get(i).getProductName() + "</td>\n"
                              + "<td class=\"text-center fs-valign-middle\">" + proList.get(i).getCategory().getCateName() + "/" + proList.get(i).getSubCate().getSubCateName() + "</td>\n"
                              + "<td class=\"text-center fs-valign-middle\"> $" + proList.get(i).getProductDiscount() + "</td>\n"
                              + "<td class=\"text-center fs-valign-middle\"> $" + proList.get(i).getPrice() + "</td>\n"
                              + "</tr>";
                }
            } else {
                html = "0";
            }
        } else {
            Products pro = orderStateLessBean.getProductByID(Integer.parseInt(searchText));
            if (pro != null) {
                html = "<tr style=\"height: 30px;\">\n"
                          + "<td class=\"proID\">" + pro.getProductID() + "</td>\n"
                          + "<td class=\"text-center fs-valign-middle\">" + pro.getProductName() + "</td>\n"
                          + "<td class=\"text-center fs-valign-middle\">" + pro.getCategory().getCateName() + "/" + pro.getSubCate().getSubCateName() + "</td>\n"
                          + "<td class=\"text-center fs-valign-middle\"> $" + pro.getProductDiscount() + "</td>\n"
                          + "<td class=\"text-center fs-valign-middle\"> $" + pro.getPrice() + "</td>\n"
                          + "</tr>";
            } else {
                html = "0";
            }
        }
        return html;
    }

    @ResponseBody
    @RequestMapping(value = "ajax/searchcolor", method = RequestMethod.POST)
    public String searchColor(@RequestParam("productID") Integer productID) {
        String html = "<option value=\"0\"> -- Choose Color -- </option>";
        List<ProductColors> proColorList = orderStateLessBean.getListProductColorsByProductID(productID);
        for (ProductColors proColor : proColorList) {
            html += "<option value=\"" + proColor.getColorID() + "\">" + proColor.getColor() + "</option>";
        }
        return html;
    }

    @ResponseBody
    @RequestMapping(value = "ajax/searchsize", method = RequestMethod.POST)
    public String searchSizeByColor(@RequestParam("colorID") Integer colorID) {
        String html = "<option value=\"0\"> -- Choose Size -- </option>";
        List<SizesByColor> sizeByColorList = orderStateLessBean.getListSizesByColorByColorID(colorID);
        for (SizesByColor sizeByColor : sizeByColorList) {
            if (sizeByColor.getQuantity() > 0) {
                html += "<option value=\"" + sizeByColor.getSizeID() + "\">" + sizeByColor.getProductSize() + "</option>";
            }
        }
        return html;
    }

    @RequestMapping(value = "confirmstatusOrderDetail/{orderdetailid}/{status}", method = RequestMethod.GET)
    public String confirmstatusOrderDetail(@PathVariable("orderdetailid") int orderdetailid, @PathVariable("status") short status) {
        OrdersDetail orderDetail = orderStateLessBean.getOrderDetailByID(orderdetailid);
        if (orderDetail != null) {
            if (orderStateLessBean.confirmStatusOrderDetail(orderDetail, status)) {
                return "redirect:/admin/orders/orderlistdetail/" + orderDetail.getOrder().getOrdersID() + ".html";
            }
        }
        return "redirect:/admin/orders/orderlistdetail/" + orderDetail.getOrder().getOrdersID() + ".html";
    }

    @RequestMapping(value = "ordersdetailadd/{orderID}")
    public String ordersdetailadd(ModelMap model, @PathVariable("orderID") Integer orderID) {
        Orders orders = orderStateLessBean.getOrderByID(orderID);
        if (orders != null && orders.getStatus() != 2) {
            return "redirect:/admin/orders/list.html";
        }
        OrdersDetail ordersDetail = new OrdersDetail();
        model.addAttribute("orderDetail", ordersDetail);
        model.addAttribute("orderID", orderID);
        return "admin/pages/orders-list-detail-add";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/addOrderDetail", method = RequestMethod.POST)
    public String addOrderDetail(@RequestParam("orderID") Integer orderID,
              @RequestParam("productID") Integer productID,
              @RequestParam("colorID") Integer colorID,
              @RequestParam("sizeID") Integer sizeID,
              @RequestParam("quantity") Integer quantity) {
        SizesByColor sizesByColor = orderStateLessBean.getSizesByColorBySizeIDandColorID(sizeID, colorID);
        if (sizesByColor != null) {
            if (sizesByColor.getQuantity() < quantity) {
                return "0";
            } else {
                Products pro = orderStateLessBean.getProductByID(productID);
                Orders orders = orderStateLessBean.getOrderByID(orderID);
                if (pro == null || orders == null) {
                    return "2";
                } else {
                    CartLineInfo cartLineInfo = new CartLineInfo();
                    cartLineInfo.setProduct(pro);
                    cartLineInfo.setQuantity(quantity);
                    cartLineInfo.setSizesByColor(sizesByColor);
                    if (orderStateLessBean.createOrderDetail(cartLineInfo, orders) == 0) {
                        return "2";
                    } else {
                        return "1";
                    }
                }
            }
        }
        return "2";
    }

    @RequestMapping(value = "discountadd", method = RequestMethod.GET)
    public String discountadd(ModelMap model) {
        DiscountVoucher discountVoucher = new DiscountVoucher();
        model.addAttribute("discountVoucher", discountVoucher);
        return "admin/pages/discount-add";
    }

    @RequestMapping(value = "discountadd", method = RequestMethod.POST)
    public String discountadd(ModelMap model, @ModelAttribute("discountVoucher") DiscountVoucher newDiscountVoucher,
              RedirectAttributes flashAttr) {
        int checkSta = orderStateLessBean.createDiscountVoucher(newDiscountVoucher);
        if (checkSta == 2) {
            model.addAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>DISCOUNT CODE HAS BEEN EXISTED</strong>\n"
                      + "</div>");
            model.addAttribute("discountVoucher", newDiscountVoucher);
            return "admin/pages/discount-add";
        } else if (checkSta == 0) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>ERROR HAPPENED</strong>\n"
                      + "</div>");
            return "redirect:/admin/orders/discountadd.html";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                      + "<strong>CREATE DISCOUNT CODE SUCCESSFULLY</strong>\n"
                      + "</div>");
            return "redirect:/admin/orders/discountadd.html";
        }
    }

    @RequestMapping(value = "discountupdate/{voucherID}", method = RequestMethod.GET)
    public String discountupdate(@PathVariable("voucherID") String voucherID, ModelMap model, RedirectAttributes flashAttr) {
        DiscountVoucher targetDiscountVoucher = orderStateLessBean.getDiscountVoucherByID(voucherID);
        if (targetDiscountVoucher != null) {
            model.addAttribute("targetDiscountVoucher", targetDiscountVoucher);
            return "admin/pages/discount-update";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>VOUCHER NOT FOUND</strong>\n"
                      + "</div>");
            return "redirect:/admin/orders/discountlist.html";
        }
    }

    @RequestMapping(value = "discountupdate/{voucherID}", method = RequestMethod.POST)
    public String discountupdate(@PathVariable("voucherID") String voucherID, ModelMap model,
              @ModelAttribute("targetDiscountVoucher") DiscountVoucher targetDiscountVoucher, RedirectAttributes flashAttr) {
        int checkSta = orderStateLessBean.updateDiscountVoucher(targetDiscountVoucher);
        if (checkSta == 2) {
            model.addAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>VOUCHER NOT FOUND</strong>\n"
                      + "</div>");
            model.addAttribute("targetDiscountVoucher", targetDiscountVoucher);
            return "admin/pages/discount-update";
        } else if (checkSta == 0) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                      + "<strong>ERROR WAS HAPPENED</strong>\n"
                      + "</div>");
            return "redirect:/admin/orders/discountupdate/" + voucherID + ".html";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                      + "<strong>DISCOUNT VOUCHER " + voucherID + " UPDATE SUCCESSFULLY</strong>\n"
                      + "</div>");
            return "redirect:/admin/orders/discountupdate/" + voucherID + ".html";
        }
    }

    @RequestMapping(value = "discountdelete/{voucherID}", method = RequestMethod.GET)
    public String discountupdate(@PathVariable("voucherID") String voucherID, RedirectAttributes flashAttr) {
        DiscountVoucher discountVoucher = orderStateLessBean.getDiscountVoucherByID(voucherID);
        if (discountVoucher != null) {
            int checkSta = orderStateLessBean.deleteDiscountVoucher(discountVoucher);
            if (checkSta == 2) {
                flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                          + "<strong>VOUCHER CANNOT DELETE! VOUCHER CONNECT TO ORDERS.</strong>\n"
                          + "</div>");
                return "redirect:/admin/orders/discountlist.html";
            } else if (checkSta == 0) {
                flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                          + "<strong>DISCOUNT DELETE PROCESS ERROR!!!</strong>\n"
                          + "</div>");
                return "redirect:/admin/orders/discountlist.html";
            } else {
                flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                          + "<strong>DISCOUNT VOUCHER " + voucherID + " DELETE SUCCESSFULLY</strong>\n"
                          + "</div>");
                return "redirect:/admin/orders/discountlist.html";
            }
        }
        flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                  + "<strong>VOUCHER NOT FOUND</strong>\n"
                  + "</div>");
        return "redirect:/admin/orders/discountlist.html";
    }

    @RequestMapping(value = "invoice/{orderID}")
    public String invoice(ModelMap model, @PathVariable("orderID") Integer orderID) {
        Orders orders = orderStateLessBean.getOrderByID(orderID);
        model.addAttribute("orders", orders);
        return "admin/orders-invoice";
    }

    private OrderStateLessBeanLocal lookupOrderStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (OrderStateLessBeanLocal) c.lookup("java:global/fashionshop/OrderStateLessBean!spring.ejb.OrderStateLessBeanLocal");
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
}
