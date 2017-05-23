/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import spring.entity.Products;
import spring.entity.UserAddresses;
import spring.entity.Users;
import spring.entity.WishList;

/**
 *
 * @author hoang
 */
@Stateless
public class UsersStateLessBean implements UsersStateLessBeanLocal {
    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Users> getAllUsers() {
        return getEm().createQuery("SELECT u FROM Users u", Users.class).getResultList();
    }

    @Override
    public Users findUserByEmail(String email) {
        Query q = getEm().createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class);
        q.setParameter("email", email);
        try {
            return (Users) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addUserAddress(UserAddresses newUserAddress) {
        getEm().persist(newUserAddress);
    }

    @Override
    public int addUsers(Users users, String phone, String address) {
        int error;
        Users findE = findUserByEmail(users.getEmail());
        if (findE != null) {
            error = 2; //email tồn tại
        } else {
            try {
                getEm().persist(users);

                if (!"".equals(phone) && !"".equals(address)) {
                    getEm().flush();

                    UserAddresses newUserAddress = new UserAddresses();
                    newUserAddress.setUser(users);
                    newUserAddress.setAddress(address);
                    newUserAddress.setPhoneNumber(phone);

                    addUserAddress(newUserAddress);
                }
                error = 1;  //add mới thành công
            } catch (Exception e) {
                error = 0;  //Lỗi đã xảy ra
            }
        }
        return error;
    }

    @Override
    public Users getUserByID(int userID) {
        return getEm().find(Users.class, userID);
    }

    @Override
    public boolean updateStatusUser(int userID, short status) {
        Users targetUser = getUserByID(userID);

        if (targetUser == null) {
            return false;
        }

        targetUser.setStatus(status);
        getEm().merge(targetUser);

        return true;
    }

    @Override
    public void changePass(int userID,String newpass){
        Users findID = getUserByID(userID);
        findID.setPassword(newpass);
        getEm().merge(findID);
    }
    
    @Override
    public int login(String email, String pass) {
        int error;
        Users userfindemail = findUserByEmail(email);
        if (userfindemail == null) {
            error = 2; // sai email
        } else {
            if (userfindemail.getPassword().equals(pass)) { //trường hợp này được login => kiểm tra role ở đây
                if (userfindemail.getRole().getRoleID() == 1 || userfindemail.getRole().getRoleID() == 2) {
                    if(userfindemail.getStatus() == 1){
                    error = 1; // => admin or moderator
                    }else {
                        error = 4; // moderator bị block
                    }
                } else {
                    error = 3; // => user
                }
            } else {
                error = 0;// sai pass
            }
        }
        return error;
    }

    @Override
    public int checkLoginUser(String email, String pass) {
        int error;
        Users userfindEmail = findUserByEmail(email);
        if (userfindEmail == null) {
            error = 2; // sai email
        } else {
            if (userfindEmail.getPassword().equals(pass)) {
                if(userfindEmail.getStatus() == 1){
                error = 1; // login thành công
                }else {
                    error = 3; // users bị block
                }
            } else {
                error = 0; // sai password
            }
        }
        return error;
    }

    @Override
    public int updateUser(Users user) {
        int error;
        Users findID = getUserByID(user.getUserID());
//        if(findEmail == null){
        if(findID.getEmail().equals(user.getEmail())){ // không thay đổi email
            try {
                getEm().merge(user);
                error = 1;
            } catch (Exception e) {
                error = 0;
            }
        }
        else{
        if (findID.getEmail() != null) {
            error = 2;// email đã có
        } else {
            try {
                getEm().merge(user);
                error = 1; // update thành công
            } catch (Exception e) {
                error = 0; // lỗi
            }
        }
        }
        return error;
    }

    @Override
    public List<Users> getAllUserID(int userID) {
        Query q = getEm().createQuery("SELECT u FROM Users u WHERE u.userID = :userID", Users.class);
        q.setParameter("userID", userID);
        return q.getResultList();
    }

    @Override
    public List<Users> getAllEmail() {
        Query q = getEm().createQuery("SELECT u.email FROM Users u", Users.class);
        return q.getResultList();
    }

    @Override
    public int addWishlist(WishList wishList,int userID, int productID) {
        int error;
        Users findUserID = getUserByID(userID);
        Products findProduct = productStateLessBean.findProductByID(productID);
//        if(findProduct != null){
//            error = 2; // san pham da co
//        }else{
            try {
                wishList.setUser(findUserID);
                wishList.setProduct(findProduct);
                getEm().persist(wishList);
                error = 1; //them thanh cong
            } catch (Exception e) {
                error = 0; // loi
            }
//        }
        return error;
        
    }

    @Override
    public List<WishList> getAllWishList(int userID) {
        Query q = getEm().createQuery("SELECT w FROM WishList w WHERE w.user.userID = :userID", WishList.class);
        q.setParameter("userID", userID);
        return q.getResultList();
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

    @Override
    public void deleteWishLish(int wishID) {
        WishList findwishID = findWishID(wishID);
        findwishID.getWishID();
        getEm().remove(findwishID);
    }

    @Override
    public WishList findWishID(int wishID) {
        return getEm().find(WishList.class, wishID);
    }

    @Override
    public WishList findWishProductID(int productID, int userID) {
        Query q = getEm().createQuery("SELECT wf FROM WishList wf WHERE wf.product.productID = :productID AND wf.user.userID = :userID", WishList.class);
        q.setParameter("productID", productID);
        q.setParameter("userID", userID);
        try {
            return (WishList) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteWL(int productID, int userID) {
        WishList wll = findWishProductID(productID, userID);
        getEm().remove(wll);
    }
}
 
