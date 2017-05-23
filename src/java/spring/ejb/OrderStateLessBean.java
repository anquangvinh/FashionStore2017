/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import spring.entity.CartLineInfo;
import spring.entity.Categories;
import spring.entity.DiscountVoucher;
import spring.entity.Orders;
import spring.entity.OrdersDetail;
import spring.entity.Products;
import spring.entity.SizesByColor;
import spring.entity.ProductColors;
import spring.entity.Users;

/**
 *
 * @author NganNgo
 */
@Stateless
public class OrderStateLessBean implements OrderStateLessBeanLocal {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Orders> getAllOrder() {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o ORDER BY o.ordersDate DESC", Orders.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OrdersDetail> getAllOrderDetail() {
        Query q = getEntityManager().createQuery("SELECT od FROM OrdersDetail od", OrdersDetail.class);
        return q.getResultList();
    }

    @Override
    public List<Orders> getAllOrderASC() {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o ORDER BY o.ordersDate ASC", Orders.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Orders> getAllOrderByUserID(int userID) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o WHERE o.user.userID = :userID ORDER BY o.ordersDate DESC", Orders.class);
            q.setParameter("userID", userID);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Orders> getAllOrderByUserIDAndStatus(int userID, int status) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o WHERE o.user.userID = :userID AND o.status = :status ORDER BY o.ordersDate DESC", Orders.class);
            q.setParameter("userID", userID);
            q.setParameter("status", status);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OrdersDetail> getAllOrderDetailByOrderID(int orderID) {
        try {
            Query q = getEntityManager().createQuery("SELECT od FROM OrdersDetail od WHERE od.order.ordersID = :orderID", OrdersDetail.class);
            q.setParameter("orderID", orderID);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Orders getOrderByID(int orderID) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o WHERE o.ordersID = :orderID", Orders.class);
            q.setParameter("orderID", orderID);
            return (Orders) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OrdersDetail getOrderDetailByID(int orderDetailID) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM OrdersDetail o WHERE o.ordersDetailID = :orderDetailID", OrdersDetail.class);
            q.setParameter("orderDetailID", orderDetailID);
            return (OrdersDetail) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Products getProductByID(int productID) {
        try {
            Query q = getEntityManager().createQuery("SELECT p FROM Products p WHERE p.productID = :productID", Products.class);
            q.setParameter("productID", productID);
            return (Products) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Products> getListProductsByName(String productName) {
        try {
            Query q = getEntityManager().createQuery("SELECT p FROM Products p WHERE p.productName LIKE :productName", Products.class);
            q.setParameter("productName", "%" + productName + "%"); //"%" + productName + "%"
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductColors> getListProductColorsByProductID(int productID) {
        try {
            Query q = getEntityManager().createQuery("SELECT pc FROM ProductColors pc WHERE pc.product.productID = :productID", ProductColors.class);
            q.setParameter("productID", productID);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SizesByColor> getListSizesByColorByColorID(int colorID) {
        try {
            Query q = getEntityManager().createQuery("SELECT sbc FROM SizesByColor sbc WHERE sbc.color.colorID = :colorID", SizesByColor.class);
            q.setParameter("colorID", colorID);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean confirmStatusOrder(Orders orders, short status) {
        try {
            orders.setStatus(status);
            getEntityManager().merge(orders);
            getEntityManager().flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean confirmStatusOrderDetail(OrdersDetail ordersDetail, short status) {
        try {
            ordersDetail.setStatus(status);
            getEntityManager().merge(ordersDetail);
            getEntityManager().flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<Categories> getAllCategory() {
        try {
            Query q = getEntityManager().createQuery("SELECT c FROM Categories c", Categories.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DiscountVoucher> getAllDiscountVoucher() {
        try {
            Query q = getEntityManager().createQuery("SELECT d FROM DiscountVoucher d", DiscountVoucher.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DiscountVoucher getDiscountVoucherByID(String discountVoucherID) {
        try {
            Query q = getEntityManager().createQuery("SELECT d FROM DiscountVoucher d WHERE d.voucherID = :discountVoucherID", DiscountVoucher.class);
            q.setParameter("discountVoucherID", discountVoucherID);
            return (DiscountVoucher) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int createDiscountVoucher(DiscountVoucher newDiscountVoucher) {
        int checkError = 0;
        DiscountVoucher discountVoucher = getDiscountVoucherByID(newDiscountVoucher.getVoucherID());
        if (discountVoucher != null) {
            checkError = 2;
        } else {
            try {
                getEntityManager().persist(newDiscountVoucher);
                getEntityManager().flush();
                checkError = 1;
            } catch (Exception e) {
                checkError = 0;
            }
        }
        return checkError;
    }

    @Override
    public int updateDiscountVoucher(DiscountVoucher targetDiscountVoucher) {
        int checkError;
        DiscountVoucher oldDiscountVoucher = getDiscountVoucherByID(targetDiscountVoucher.getVoucherID());
        if (oldDiscountVoucher != null) {
            targetDiscountVoucher.setOrdersList(oldDiscountVoucher.getOrdersList());
            try {
                getEntityManager().merge(targetDiscountVoucher);
                getEntityManager().flush();
                checkError = 1;
            } catch (Exception e) {
                checkError = 0;
            }
        } else {
            checkError = 2;
        }
        return checkError;
    }

    @Override
    public SizesByColor getSizesByColorBySizeIDandColorID(int sizeId, int colorId) {
        try {
            Query q = getEntityManager().createQuery("SELECT s FROM SizesByColor s WHERE s.sizeID = :sizeid AND s.color.colorID = :colorid", Categories.class);
            q.setParameter("sizeid", sizeId);
            q.setParameter("colorid", colorId);
            return (SizesByColor) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int createOrderDetail(CartLineInfo cartLineInfo, Orders orders) {
        int checkError = 0;
        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setOrder(orders);
        ordersDetail.setPrice(cartLineInfo.getProduct().getPrice());
        ordersDetail.setProduct(cartLineInfo.getProduct());
        ordersDetail.setProductDiscount(cartLineInfo.getProduct().getProductDiscount());
        ordersDetail.setQuantity(cartLineInfo.getQuantity());
        ordersDetail.setSize(cartLineInfo.getSizesByColor());
        ordersDetail.setStatus(Short.valueOf("2"));
        orders.getOrderDetailList().add(ordersDetail);
        SizesByColor sizesByColor = cartLineInfo.getSizesByColor();
        sizesByColor.setQuantity(sizesByColor.getQuantity() - cartLineInfo.getQuantity());
        try {
            getEntityManager().merge(orders);
            getEntityManager().merge(sizesByColor);
            getEntityManager().flush();
            checkError = 1;
        } catch (Exception e) {
            checkError = 0;
        }
        return checkError;
    }

    @Override
    public int deleteDiscountVoucher(DiscountVoucher discountVoucher) {
        if (discountVoucher.getOrdersList().size() != 0) {
            return 2;
        }
        try {
            if (!getEntityManager().contains(discountVoucher)) {
                discountVoucher = getEntityManager().merge(discountVoucher);
            }
            getEntityManager().remove(discountVoucher);
            getEntityManager().flush();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Integer> getAllYearOrdered() {
        try {
            Query q = getEntityManager().createQuery("SELECT FUNCTION('YEAR',o.ordersDate) FROM Orders o GROUP BY FUNCTION('YEAR',o.ordersDate) ORDER BY FUNCTION('YEAR',o.ordersDate) DESC", Orders.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Orders> getAllOrderByMonth(int month, int year) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o WHERE FUNCTION('MONTH',o.ordersDate) = FUNCTION('MONTH',:month) AND FUNCTION('YEAR',o.ordersDate) = FUNCTION('YEAR',:year)", Orders.class);
            q.setParameter("month", "2015-" + String.valueOf(month) + "-15");
            q.setParameter("year", String.valueOf(year) + "-" + String.valueOf(month) + "-15");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> getAllDayOrderedByMonth(int month, int year) {
        try {
            Query q = getEntityManager().createQuery("SELECT FUNCTION('DAY',o.ordersDate) FROM Orders o WHERE FUNCTION('MONTH',o.ordersDate) = FUNCTION('MONTH',:month) AND FUNCTION('YEAR',o.ordersDate) = FUNCTION('YEAR',:year) GROUP BY FUNCTION('DAY',o.ordersDate)", Orders.class);
            q.setParameter("month", "2015-" + String.valueOf(month) + "-15");
            q.setParameter("year", String.valueOf(year) + "-" + String.valueOf(month) + "-15");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> getAllMonthOrderedByYear(int year) {
        try {
            Query q = getEntityManager().createQuery("SELECT FUNCTION('MONTH',o.ordersDate) FROM Orders o WHERE FUNCTION('YEAR',o.ordersDate) = FUNCTION('YEAR',:year) GROUP BY FUNCTION('MONTH',o.ordersDate)", Orders.class);
            q.setParameter("year", String.valueOf(year) + "-05-15");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer countOrderByStatus(int status) {
        try {
            Query q = getEntityManager().createQuery("SELECT COUNT(o.status) FROM Orders o WHERE o.status = :status", Orders.class);
            q.setParameter("status", status);
            return Integer.parseInt(q.getSingleResult().toString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer countOrders() {
        try {
            Query q = getEntityManager().createQuery("SELECT COUNT(o.ordersID) FROM Orders o", Orders.class);
            return Integer.parseInt(q.getSingleResult().toString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer averageOrdersPerUserByMonth(String date) {
        int orders = countAllOrdersByMonthYear(date);
        int users = countAllUsersByRole();
        return Math.round(orders / users);
    }

    @Override
    public Integer countAllOrdersByMonthYear(String date) {
        try {
            Query q = getEntityManager().createQuery("SELECT COUNT(o.ordersID) FROM Orders o WHERE FUNCTION('MONTH',o.ordersDate) = FUNCTION('MONTH',:date) AND FUNCTION('YEAR',o.ordersDate) = FUNCTION('YEAR',:date)", Orders.class);
            q.setParameter("date", date);
            return Integer.parseInt(q.getSingleResult().toString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer countAllUsersByRole() {
        try {
            Query u = getEntityManager().createQuery("SELECT COUNT(u.userID) FROM Users u WHERE u.role.roleID = :roleID AND u.status = :status", Users.class);
            u.setParameter("roleID", 3);
            u.setParameter("status", 1);
            return Integer.parseInt(u.getSingleResult().toString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Orders> getAllOrderByStatus(int status) {
        try {
            Query q = getEntityManager().createQuery("SELECT o FROM Orders o WHERE o.status = :status ORDER BY o.ordersDate DESC", Orders.class);
            q.setParameter("status", status);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Users> getAllUserUseDiscountVoucherByVouID(String vouID) {
        try {
            Query q = getEntityManager().createQuery("SELECT u FROM Users u, Orders o, DiscountVoucher dv WHERE u.userID = o.user.userID AND o.voucher.voucherID = dv.voucherID AND dv.voucherID = :vouID", Orders.class);
            q.setParameter("vouID", vouID);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
