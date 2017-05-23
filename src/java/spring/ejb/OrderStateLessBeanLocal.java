/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Local;
import spring.entity.CartLineInfo;
import spring.entity.Categories;
import spring.entity.DiscountVoucher;
import spring.entity.Orders;
import spring.entity.OrdersDetail;
import spring.entity.ProductColors;
import spring.entity.Products;
import spring.entity.SizesByColor;
import spring.entity.Users;

/**
 *
 * @author NganNgo
 */
@Local
public interface OrderStateLessBeanLocal {
    public List<Orders> getAllOrder();
    public List<OrdersDetail> getAllOrderDetail();
    public List<Orders> getAllOrderASC();
    public List<Categories> getAllCategory();
    public List<DiscountVoucher> getAllDiscountVoucher();
    public List<OrdersDetail> getAllOrderDetailByOrderID(int orderID);
    public List<Orders> getAllOrderByUserID(int userID);
    public List<Orders> getAllOrderByUserIDAndStatus(int userID, int status);
    public List<Products> getListProductsByName(String productName);
    public List<ProductColors> getListProductColorsByProductID(int productID);
    public List<SizesByColor> getListSizesByColorByColorID(int colorID);
    public List<Orders> getAllOrderByStatus(int status);
    public List<Users> getAllUserUseDiscountVoucherByVouID(String vouID);
    
    public Orders getOrderByID(int orderID);
    public Products getProductByID(int productID);
    public OrdersDetail getOrderDetailByID(int orderDetailID);
    public DiscountVoucher getDiscountVoucherByID(String discountVoucherID);
    public SizesByColor getSizesByColorBySizeIDandColorID(int sizeId, int colorId);
    
    public int createDiscountVoucher(DiscountVoucher newDiscountVoucher);
    public int createOrderDetail(CartLineInfo cartLineInfo, Orders orders);
    public int updateDiscountVoucher(DiscountVoucher targetDiscountVoucher);
    public int deleteDiscountVoucher(DiscountVoucher discountVoucher);
    
    public boolean confirmStatusOrder(Orders orders, short status);
    public boolean confirmStatusOrderDetail(OrdersDetail ordersDetail, short status);
    
    public List<Integer> getAllYearOrdered();
    public List<Integer> getAllMonthOrderedByYear(int year);
    public List<Integer> getAllDayOrderedByMonth(int month, int year);
    public List<Orders> getAllOrderByMonth(int month, int year);
    
    public Integer countOrderByStatus(int status);
    public Integer countOrders();
    public Integer averageOrdersPerUserByMonth(String date);
    public Integer countAllOrdersByMonthYear(String date);
    public Integer countAllUsersByRole();
}
