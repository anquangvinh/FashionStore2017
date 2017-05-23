package spring.client.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ejb.BlogsSBLocal;
import spring.ejb.ProductStateLessBeanLocal;
import spring.ejb.UsersStateLessBeanLocal;
import spring.entity.Blogs;
import spring.entity.Categories;
import spring.entity.Products;
import spring.entity.Users;
import spring.functions.SharedFunctions;

@Controller
public class GeneralController {

    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();
    BlogsSBLocal blogsSB = lookupBlogsSBLocal();
    UsersStateLessBeanLocal usersStateLessBean = lookupUsersStateLessBeanLocal();
    @Autowired
    SharedFunctions sharedFunc;

    @RequestMapping(value = "/index")
    public String index(ModelMap model) {
        List<Categories> cateList = productStateLessBean.categoryList();
        List<Blogs> blogListIndex = blogsSB.getAllBlogsIndex();
        for (Categories cate : cateList) {
            List<Products> productListByCate = cate.getProductList();
            Collections.shuffle(productListByCate);
        }
        List<Object> bestSellerList = productStateLessBean.getTop3ProductBestSeller();
        List<Products> mostViewList = productStateLessBean.getTop3ProductMostViewed();
        
        List<Object[]> productTopRateList = productStateLessBean.getProductTop3Rated();
        List<Object[]> newTopRateList = new ArrayList<>();
        
        for (Object[] rate : productTopRateList) {
            int productID = (int) rate[0];
            Products product = productStateLessBean.findProductByID(productID);
            double avgRating = (double) rate[1];
            Object[] newObj = new Object[]{product, avgRating};
            newTopRateList.add(newObj);
        }
        
        model.addAttribute("cateList", cateList);
        model.addAttribute("latestProducts", productStateLessBean.productList("client")); //lấy sản phẩm mới nhất
        model.addAttribute("bestSellerList", bestSellerList); //lấy sản phẩm bán chạy nhất
        model.addAttribute("mostViewList", mostViewList); //lấy sản phẩm xem nhiều nhất
        model.addAttribute("productTopRateList", newTopRateList); //lấy sản phẩm được rate nhiều nhất
        model.addAttribute("blogListIndex", blogListIndex);
        return "client/pages/index";
    }

    @RequestMapping(value = "/admin/index")
    public String admin(ModelMap model) {
        return "admin/pages/index";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap model,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "checkremember", required = false) Short checkremember,
            HttpSession session, RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ) {
        String err = "";
        int error = usersStateLessBean.checkLoginUser(email, sharedFunc.encodePassword(password));
        if (error == 1) {
            session.setAttribute("emailUser", email);
            err = (String) session.getAttribute("emailUser");
            Users userfindUserID = usersStateLessBean.findUserByEmail(email);
            session.setAttribute("findUsersID", userfindUserID.getUserID());
            session.setAttribute("USfirstname", userfindUserID.getFirstName() + " " + userfindUserID.getLastName());

            if (checkremember != null && checkremember == 1) {
                Cookie ckEmail = new Cookie("emailU", email);
                ckEmail.setMaxAge(24 * 60 * 60);
                response.addCookie(ckEmail);
                Cookie ckPassword = new Cookie("passwordU", sharedFunc.encodePassword(password));
                ckPassword.setMaxAge(24 * 60 * 60);
                response.addCookie(ckPassword);
            }
            return err;
        } else if (error == 2) {
            return "2";
        } else if (error == 3) {
            return "4";
        } else {
            return "3";
        }
    }
    
    @RequestMapping(value = "/logout")
    public String logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //Remove Session
        session.removeAttribute("emailUser");
        session.removeAttribute("findUsersID");
        session.removeAttribute("USfirstname");

        //Remove cookie
        for (Cookie ck : request.getCookies()) {
            if (ck.getName().equalsIgnoreCase("emailU")) {
                ck.setMaxAge(0);
                response.addCookie(ck);
            }
            if (ck.getName().equalsIgnoreCase("passwordU")) {
                ck.setMaxAge(0);
                response.addCookie(ck);
            }
        }
        return "redirect:/index.html";
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

    private BlogsSBLocal lookupBlogsSBLocal() {
        try {
            Context c = new InitialContext();
            return (BlogsSBLocal) c.lookup("java:global/fashionshop/BlogsSB!spring.ejb.BlogsSBLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
