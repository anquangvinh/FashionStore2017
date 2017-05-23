/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Local;
import spring.entity.UserAddresses;
import spring.entity.Users;

/**
 *
 * @author hoang
 */
@Local
public interface UserAddressesStateLessBeanLocal {
    
    List<UserAddresses> getAddressUser();
    
    void addAddressUser(UserAddresses userAddresses, int userID);
    
    UserAddresses findAddress(int userID);
    
    UserAddresses findPhone(int userID);
    
    int editAddressUser(UserAddresses userAddresses, int userID);
    
    Users findUserID(int userID);
    
    UserAddresses findID(int userID);
    
    void addUser(Users user);
    
    List<UserAddresses> AddressListUser(int userID);
    
    UserAddresses findAddressID(int addressID);
    
    int editAddress(int userID, int addressID);
    
    void deleteAddress(int addressID);
    
}
