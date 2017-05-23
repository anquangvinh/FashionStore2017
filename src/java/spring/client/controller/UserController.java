package spring.client.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ejb.ProductStateLessBeanLocal;
import spring.ejb.RolesStateLessBeanLocal;
import spring.ejb.UserAddressesStateLessBeanLocal;
import spring.ejb.UsersStateLessBeanLocal;
import spring.entity.Categories;
import spring.entity.Products;
import spring.entity.UserAddresses;
import spring.entity.Users;
import spring.entity.WishList;
import spring.functions.SharedFunctions;

@Controller
@RequestMapping(value = "/user/")
public class UserController {

    UserAddressesStateLessBeanLocal userAddressesStateLessBean = lookupUserAddressesStateLessBeanLocal();
    RolesStateLessBeanLocal rolesStateLessBean = lookupRolesStateLessBeanLocal();
    UsersStateLessBeanLocal usersStateLessBean = lookupUsersStateLessBeanLocal();
    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();

    @Autowired
    SharedFunctions sharedFunc;
    @Autowired
    ServletContext app;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(ModelMap model) {
//        Users users = new Users();
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
//        model.addAttribute("users", users);
        return "client/pages/index";
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String createUser(
            @RequestParam("email") String email, @RequestParam("password") String password,
            @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
            @RequestParam("gender") short gender, @RequestParam("birthday") String birthday,
            @RequestParam(value = "upImage", required = false) MultipartFile image,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "address", required = false) String address,
            RedirectAttributes redirectAttributes,
            ModelMap model, HttpSession session) {
          
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
        Date newBirthday = new Date();
        try {
            newBirthday = df.parse(birthday);
        } catch (ParseException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setPassword(sharedFunc.encodePassword(password));
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setGender(gender);
        newUser.setBirthday(newBirthday);
        newUser.setStatus(Short.parseShort("1"));
        newUser.setRole(rolesStateLessBean.findRoles(3));
        try {
            newUser.setRegistrationDate(new Date());
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (image != null) {
            if (image.isEmpty()) {
                newUser.setAvatar("default_user.jpg");
            } else {
                try {
                    newUser.setAvatar(simpleDateFormat.format(new Date()) + sharedFunc.changeText(image.getOriginalFilename()));
                    String path = app.getRealPath("/assets/images/avatar/") + "/" + newUser.getAvatar();
                    image.transferTo(new File(path));

                } catch (IOException | IllegalStateException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            newUser.setAvatar("default_user.jpg");
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        if (address == null) {
            address = "";
        }

        int error = usersStateLessBean.addUsers(newUser, phoneNumber, address);
        String err = "";
        if (error == 1) {
            session.setAttribute("emailUser", newUser.getEmail());
            err = (String) session.getAttribute("emailUser");
            Users userfindUserID = usersStateLessBean.findUserByEmail(newUser.getEmail());
            session.setAttribute("findUsersID", userfindUserID.getUserID());
            session.setAttribute("USfirstname", userfindUserID.getFirstName() + " " + userfindUserID.getLastName());
            return err;
        } else if (error == 2) {
            return "2";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "change-password/{userID}", method = RequestMethod.GET)
    public String changePass(ModelMap model, @PathVariable("userID") int userID) {
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/changepassword";
    }

    @RequestMapping(value = "change-password/{userID}", method = RequestMethod.POST)
    public String changePass(@PathVariable("userID") int userID, @RequestParam("password") String password,
            @RequestParam("repassword") String repassword, @RequestParam("oldpassword") String oldpassword,
            RedirectAttributes redirectAttributes) {
        Users findOldUserID = usersStateLessBean.getUserByID(userID);

        if (!findOldUserID.getPassword().equals(sharedFunc.encodePassword(oldpassword))) {
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-5  alert alert-danger\">FAILED!. Error Current Password wrong!</div>");
        } else if (password.equals(repassword)) {
            usersStateLessBean.changePass(userID, sharedFunc.encodePassword(password));
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-success\">Update Password Successfully!</div>");
        } else if (!password.equals(repassword)) {
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-5  alert alert-danger\">FAILED!. Error was happened, Password and Repassword is wrong!</div>");
        }

        return "redirect:/user/change-password/" + userID + ".html";
    }

    @RequestMapping(value = "address-add/{userID}", method = RequestMethod.POST)
    public String addressAdd(@PathVariable("userID") int userID, @ModelAttribute("userAddress") UserAddresses userAddress,
            RedirectAttributes redirectAttributes) {

        List<UserAddresses> listAddress = usersStateLessBean.getUserByID(userID).getUserAddressList();
        for (UserAddresses list : listAddress) {
            if (list.getAddress().equals(userAddress.getAddress()) && list.getPhoneNumber().equals(userAddress.getPhoneNumber())) {
                redirectAttributes.addFlashAttribute("error", "bị trùng");
                return "redirect:/user/address-add/" + userID + ".html";
            }
            break;
        }
        userAddressesStateLessBean.addAddressUser(userAddress, userID);
        redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-success\">Create Address Successfully!</div>");
        return "redirect:/user/address-list/" + userID + ".html";
    }

    @RequestMapping(value = "address-list/{userID}")
    public String addresslist(ModelMap model, @PathVariable("userID") int userID) {
        List<UserAddresses> listAddress = usersStateLessBean.getUserByID(userID).getUserAddressList();
        model.addAttribute("listua", listAddress);
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        List<UserAddresses> ualist = userAddressesStateLessBean.AddressListUser(userID);
        model.addAttribute("ualist", ualist);
        model.addAttribute("userAddress", new UserAddresses());
        return "client/pages/address-list";
    }

    @RequestMapping(value = "address-book/{userID}-{addressID}", method = RequestMethod.GET)
    public String addressbook(ModelMap model, @PathVariable("userID") int userID,
            @PathVariable("addressID") int addressID) {
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);

        UserAddresses userAddresses = userAddressesStateLessBean.findID(userID);
        UserAddresses userAddresses1 = userAddressesStateLessBean.findAddressID(addressID);

        model.addAttribute("userAddresses", userAddresses1);
        return "client/pages/address-book";

    }

    @RequestMapping(value = "address-book/{userID}-{addressID}", method = RequestMethod.POST)
    public String addressbook(ModelMap model, @ModelAttribute("userAddresses") UserAddresses userAddresses,
            RedirectAttributes redirectAttributes, @PathVariable("userID") int userID,
            @PathVariable("addressID") int addressID) {
        int error;
        model.addAttribute("addressID", userAddresses.getAddressID());
        error = userAddressesStateLessBean.editAddressUser(userAddresses, userID);
        if (error == 1) {
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-success\">Update Address Successfully!</div>");
        } else if (error == 0) {
            redirectAttributes.addFlashAttribute("error", "lỗi");
        }
        return "redirect:/user/address-book/" + userID + "-" + addressID + ".html";
    }

    @ResponseBody
    @RequestMapping(value = "deleteAddress/{addressID}", method = RequestMethod.POST)
    public String deleteaddress(@PathVariable("addressID") int addressID, ModelMap model) {
        UserAddresses usa = userAddressesStateLessBean.findAddressID(addressID);
//        Integer userID = userAddressesStateLessBean.findAddressID(addressID).getUser().getUserID();
        if (usa != null) {
            userAddressesStateLessBean.deleteAddress(addressID);
        }
        return "1";
    }

    @RequestMapping(value = "account-information/{userID}", method = RequestMethod.GET)
    public String accountinfo(@PathVariable("userID") int userID, ModelMap model) {
        Users updateUser = usersStateLessBean.getUserByID(userID);

        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);

        //Change normal date to string
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = updateUser.getBirthday();
        String formattedDate = dateFormat.format(date);
        model.addAttribute("formattedDate", formattedDate);

        model.addAttribute("updateUser", updateUser);
        return "client/pages/account-information";
    }

    @RequestMapping(value = "account-information/{userID}", method = RequestMethod.POST)
    public String accountinfo(@PathVariable("userID") int userID,
            @ModelAttribute("updateUser") Users updateUser,
            RedirectAttributes redirectAttributes, @RequestParam("upImage") MultipartFile image) {
        Users oldUser = usersStateLessBean.getUserByID(userID); // thong tin user chua chinh sua
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        try {
            if (!image.isEmpty()) {
                updateUser.setAvatar(simpleDateFormat.format(new Date()) + sharedFunc.changeText(image.getOriginalFilename()));
                String path = app.getRealPath("/assets/images/avatar/") + "/" + updateUser.getAvatar();
                image.transferTo(new File(path));
            } else {
                updateUser.setAvatar(oldUser.getAvatar());
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            updateUser.setRegistrationDate(oldUser.getRegistrationDate());
            updateUser.setStatus(oldUser.getStatus());
            updateUser.setRole(oldUser.getRole());
            updateUser.setPassword(oldUser.getPassword());
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int error = usersStateLessBean.updateUser(updateUser);

        if (error == 1) {
            Users afterUpdateUser = usersStateLessBean.getUserByID(userID);
            //Change normal date to string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = afterUpdateUser.getBirthday();
            String formattedDate = dateFormat.format(date);
            redirectAttributes.addFlashAttribute("formattedDate", formattedDate);
            redirectAttributes.addFlashAttribute("updateUser", afterUpdateUser);
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-success\">Update Account Successfully!</div>");
        } else if (error == 2) {
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-danger\">FAILED!. Email Exitsted! </div>");
        } else if (error == 0) {
            redirectAttributes.addFlashAttribute("error", "<div class=\"col-md-12  alert alert-danger\">FAILED!. Error was happened!</div>");
        }
        return "redirect:/user/account-information/" + userID + ".html";
    }

    @RequestMapping(value = "myaccount")
    public String checkOut(ModelMap model) {
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/my-account";
    }

    @RequestMapping(value = "wishlist/{userID}")
    public String getWishList(ModelMap model, @PathVariable("userID") int userID) {
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        List<WishList> getWishList = usersStateLessBean.getAllWishList(userID);
        model.addAttribute("wishList", getWishList);
        return "client/pages/wishlist";
    }

    @ResponseBody
    @RequestMapping(value = "ajax/addWishList", method = RequestMethod.POST)
    public String addWL(@RequestParam("userID") int userID, @RequestParam("productID") int productID) {
        WishList wishList = new WishList();
        Users findUserID = usersStateLessBean.getUserByID(userID);
        Products findProductID = productStateLessBean.findProductByID(productID);
        wishList.setUser(findUserID);
        wishList.setProduct(findProductID);
        try {
            wishList.setCreateDate(new Date());
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int error = usersStateLessBean.addWishlist(wishList, userID, productID);
        if (error == 1) {
            return "1"; // thanh cong
        } 
        else {
            return "0"; // loi
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "ajax/deleteWishList/{wishID}", method = RequestMethod.POST)
    public String deleteWL(@PathVariable("wishID") int wishID, ModelMap model){
        WishList findwishID = usersStateLessBean.findWishID(wishID);
        if(findwishID != null){
            usersStateLessBean.deleteWishLish(wishID);
        }
        return "1";
    }
    
    @ResponseBody
    @RequestMapping(value = "ajax/deleteWishListt", method = RequestMethod.POST)
    public String deleteWLL(@RequestParam("productID") int productID,@RequestParam("userID") int userID, ModelMap model){
            usersStateLessBean.deleteWL(productID, userID);
        return "1";
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

    private RolesStateLessBeanLocal lookupRolesStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (RolesStateLessBeanLocal) c.lookup("java:global/fashionshop/RolesStateLessBean!spring.ejb.RolesStateLessBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UserAddressesStateLessBeanLocal lookupUserAddressesStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (UserAddressesStateLessBeanLocal) c.lookup("java:global/fashionshop/UserAddressesStateLessBean!spring.ejb.UserAddressesStateLessBeanLocal");
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
