/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;
import spring.entity.CartLineInfo;
import spring.entity.Orders;
import spring.entity.Products;

/**
 *
 * @author NganNgo
 */
@Local
public interface OrderStateFulBeanLocal {
    void addProduct(CartLineInfo cartLineInfo);
    boolean deleteProduct(CartLineInfo cartLineInfo);
    boolean updateProduct(CartLineInfo oldCartLineInfo, CartLineInfo cartLineInfo);
    List<CartLineInfo> showCart();
    CartLineInfo getProductInListByID(int productid, int sizeid, int colorid);
    String completePurchase(Orders orders);
    float subTotal();
}
