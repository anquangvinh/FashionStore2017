/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Local;
import spring.entity.Roles;
import spring.entity.Users;

/**
 *
 * @author hoang
 */
@Local
public interface RolesStateLessBeanLocal {

    Roles findRoles(int roleID);

    int addRoles(Roles roles);

    Roles findRoleName(String roleName);

    boolean editRolesForUsers(int userID, int roleID);
    
    List<Roles> getRole();
    
    int editRoles(Roles role);
    
    List<Users> listRoleUserID(int roleID);
    
    int deleteRole(int roleID);
    
    List<Roles> findRName();
}
