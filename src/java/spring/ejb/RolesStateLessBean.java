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
import spring.entity.Roles;
import spring.entity.Users;

/**
 *
 * @author hoang
 */
@Stateless
public class RolesStateLessBean implements RolesStateLessBeanLocal {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    @Override
    public Roles findRoles(int roleID) {
        return getEm().find(Roles.class, roleID);
    }

    @Override
    public int addRoles(Roles roles) {
        int error;
        Roles findRN = findRoleName(roles.getRoleName());
        if (findRN != null) {
            error = 2; // RoleName trùng
        } else {
            try {
                getEm().persist(roles);
                error = 1; // insert role thành công
            } catch (Exception e) {
                error = 0; // xảy ra lỗi 
            }
        }
        return error;
    }

    @Override
    public Roles findRoleName(String roleName) {
        Query q = getEm().createQuery("SELECT r FROM Roles r WHERE r.roleName = :roleName",Roles.class);
        q.setParameter("roleName", roleName);
        try {
            return (Roles) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean editRolesForUsers(int userID, int roleID) {
        Users user = getEm().find(Users.class, userID); //Lấy ra user cũ, mọi thứ đều cũ
        user.getRole().getUserList().remove(user); //Lấy ra cái role cũ. từ role cũ có dc cái list chứa user, lấy cái list đó, remove user này đi
        Roles role = findRoles(roleID); //role mới
        
        user.setRole(role); //set Role mới cho user
        role.getUserList().add(user); //vào cái role mới, có userList mới của role này, add thằng user này vào.
        
        getEm().merge(user); //lưu vào database
        return true;
    }

    @Override
    public List<Roles> getRole() {
        Query q = getEm().createQuery("SELECT r FROM Roles r WHERE r.roleID != :roleID", Roles.class);
        q.setParameter("roleID", 1);
        return q.getResultList();
    }

    @Override
    public int editRoles(Roles role) {
        int error;
        Roles roleold = findRoles(role.getRoleID());
        Roles rolenew = findRoleName(role.getRoleName());
        if(roleold.getRoleName().equals(role.getRoleName())){ // không thay đổi roleName
            try {
                getEm().merge(role);
                error = 1;
            } catch (Exception e) {
                error = 0;
            }
        }
        else{
        
        if(rolenew != null){
            error = 2; // trùng
        }else {
            try {
                getEm().merge(role);
                error = 1;
            } catch (Exception e) {
                error = 2;
            }
        }
        }
        return error;
    }

    @Override
    public List<Users> listRoleUserID(int roleID) {
       Query q = getEm().createQuery("SELECT u.userID FROM Users u WHERE u.role.roleID = :roleID", Users.class);
       q.setParameter("roleID", roleID);
       return q.getResultList();
    }

    @Override
    public int deleteRole(int roleID) {
        int error;
        List<Users> listRoleUser = listRoleUserID(roleID);
        if(listRoleUser.isEmpty()){
            try {
                Roles role = findRoles(roleID);
                if(role != null){
                    getEm().remove(role);   
                }
                error = 1; //xóa thành công
            } catch (Exception e) {
                error = 0;
            }
        }else{
            error = 2; //không được xóa
        }
        return error;
    }

    @Override
    public List<Roles> findRName() {
        Query q = getEm().createQuery("SELECT r.roleName FROM Roles r", Roles.class);
        return q.getResultList();
    }
    
    
}
