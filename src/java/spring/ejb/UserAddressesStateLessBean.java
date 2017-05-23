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
import spring.entity.UserAddresses;
import spring.entity.Users;

/**
 *
 * @author hoang
 */
@Stateless
public class UserAddressesStateLessBean implements UserAddressesStateLessBeanLocal {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<UserAddresses> getAddressUser() {
        return getEm().createQuery("SELECT a FROM UserAddresses a", UserAddresses.class).getResultList();
    }

    @Override
    public void addAddressUser(UserAddresses userAddresses, int userID) {
//        int error;

//        Users user = findUserID(userID);
//        UserAddresses findAD = findAddress(user.getUserID());
//        UserAddresses findP = findPhone(user.getUserID());
//        if (findAD != null && findP != null) {
//            error = 2; // phone hoặc address bị trùng
//        } else {
        try {
//                for(){
            Users u = findUserID(userID);
//                Users u = findUserID(userID);
//                    if (u != null) {
            userAddresses.setUser(u);
//                    }
            getEm().persist(userAddresses);
//                error = 1;
//                }
        } catch (Exception e) {
            e.printStackTrace();
//                error = 0;
        }
//        }
//        return error;
    }

    @Override
    public UserAddresses findAddress(int userID) {
        Query q = getEm().createQuery("SELECT ua.address FROM UserAddresses ua WHERE ua.user.userID = :userID", UserAddresses.class);
//        q.setParameter("userID", userID);
        q.setParameter("userID", userID);
        try {
            return (UserAddresses) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public UserAddresses findPhone(int userID) {
        Query q = getEm().createQuery("SELECT ua.phoneNumber FROM UserAddresses ua WHERE ua.user.userID = :userID", UserAddresses.class);
//        q.setParameter("userID", userID);
        q.setParameter("userID", userID);
        try {
            return (UserAddresses) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public int editAddressUser(UserAddresses userAddresses, int userID) {
        int error;
        
            try {
                    Users user = findUserID(userID);
                    userAddresses.setUser(user);
                    userAddresses.getAddressID();
                    getEm().merge(userAddresses);
                    error = 1;
                
            } catch (Exception e) {
                error = 0;
            }
            return error;
        }

    @Override
    public Users findUserID(int userID) {
        return getEm().find(Users.class, userID);
    }

    @Override
    public void addUser(Users user) {
        getEm().persist(user);
    }

    @Override
    public UserAddresses findID(int userID) {
        Query q = getEm().createQuery("SELECT ua FROM UserAddresses ua WHERE ua.user.userID = :userID", UserAddresses.class);
        q.setParameter("userID", userID);
        try {
            return (UserAddresses) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserAddresses> AddressListUser(int userID) {
        Query q = getEm().createQuery("SELECT ua FROM UserAddresses ua WHERE ua.user.userID = :userID", UserAddresses.class);
        q.setParameter("userID", userID);
        return q.getResultList();
    }

    @Override
    public UserAddresses findAddressID(int addressID) {
        return getEm().find(UserAddresses.class, addressID);
    }


    @Override
    public int editAddress(int userID, int addressID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAddress(int addressID) {
        UserAddresses findUseraddress = findAddressID(addressID);
        findUseraddress.getAddressID();
        getEm().remove(findUseraddress);
        
    }

}
