/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ejb.ProductStateLessBeanLocal;
import spring.entity.Categories;
import spring.entity.ProductColors;
import spring.entity.ProductSubImgs;
import spring.entity.Products;
import spring.entity.SizesByColor;
import spring.entity.SubCategories;
import spring.functions.SharedFunctions;

@Controller
@RequestMapping(value = "/admin/")
public class Product_Controller {

    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();

    @Autowired
    SharedFunctions shareFunc;

    @Autowired
    ServletContext app;

    /*========================================================================
     *                                                                       *
     *                          CATEGORY TREATMENT                           *
     *                                                                       *
     ========================================================================*/
    @RequestMapping(value = "product-category")
    public String productCateList(ModelMap model) {
        model.addAttribute("cateList", productStateLessBean.categoryList());
        return "admin/pages/product-category-list";
    }

    @RequestMapping(value = "product-category/create", method = RequestMethod.GET)
    public String productCateAdd(ModelMap model) {
        Categories newCate = new Categories();
        model.addAttribute("newCate", newCate);
        return "admin/pages/product-category-add";
    }

    @RequestMapping(value = "product-category/create", method = RequestMethod.POST)
    public String productCateAdd(ModelMap model, @ModelAttribute("newCate") Categories newCate, RedirectAttributes flashAttr) {
        newCate.setCateNameNA(shareFunc.changeText(newCate.getCateName()));
        int error_code = productStateLessBean.createNewCategory(newCate);
        if (error_code == 2) {
            model.addAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Category has been existed!.\n"
                    + "</div>");
            model.addAttribute("newCate", newCate);
            return "admin/pages/product-category-add";
        } else if (error_code == 1) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                    + "<strong>Success!</strong> Create New Category Successfully!.\n"
                    + "</div>");
            return "redirect:/admin/product-category/create.html";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Error was happened!.\n"
                    + "</div>");
            return "redirect:/admin/product-category/create.html";
        }
    }

    @RequestMapping(value = "product-category/{cateNameNA}-{cateID}", method = RequestMethod.GET)
    public String productCateUpdate(ModelMap model, @PathVariable("cateID") Integer cateID) {
        Categories targetCate = productStateLessBean.findCategoryByID(cateID);
        model.addAttribute("targetCate", targetCate);
        return "admin/pages/product-category-update";
    }

    @RequestMapping(value = "product-category/{cateNameNA}-{cateID}", method = RequestMethod.POST)
    public String productCateUpdate(ModelMap model,
            RedirectAttributes flashAttr,
            @ModelAttribute("targetCate") Categories targetCate,
            @PathVariable("cateID") Integer cateID) {
        Categories oldCate = productStateLessBean.findCategoryByID(cateID);
        targetCate.setCateNameNA(shareFunc.changeText(targetCate.getCateName()));

        int errorCode = productStateLessBean.updateCategory(targetCate);
        if (errorCode == 1) { //Update thành công
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                    + "<strong>Success!</strong> Update Category Successfully!.\n"
                    + "</div>");
            model.addAttribute("targetCate", targetCate);
            return "redirect:/admin/product-category/" + targetCate.getCateNameNA() + "-" + cateID + ".html";
        } else if (errorCode == 0) { //Update bị lỗi
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Error was happened!.\n"
                    + "</div>");
        } else if (errorCode == 2) { //Update lỗi trùng tên đã tồn tại trước đó
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-warning\">\n"
                    + "<strong>Danger!</strong> Category name \"" + targetCate.getCateName() + "\" existed!.\n"
                    + "</div>");
        }
        return "redirect:/admin/product-category/" + oldCate.getCateNameNA() + "-" + cateID + ".html";
    }

    @RequestMapping(value = "deletecategory-{cateID}", method = RequestMethod.GET)
    public String deletecategory(
            @PathVariable("cateID") Integer cateID,
            RedirectAttributes flashAttr
    ) {
        if (productStateLessBean.deleteCategory(cateID)) {
            flashAttr.addFlashAttribute("error", "0");
        } else {
            flashAttr.addFlashAttribute("error", "1");
        }

        return "redirect:/admin/product-category.html";
    }

    @RequestMapping(value = "ajax/checkDupCategory", method = RequestMethod.POST)
    @ResponseBody
    public String checkDupCategory(
            @RequestParam(value = "cateID", required = false) Integer cateID,
            @RequestParam(value = "cateName") String cateName
    ) {
        if (cateID != null) { //kiểm tra khi update
            Categories cate = productStateLessBean.findCategoryByID(cateID);
            List<Categories> cateList = productStateLessBean.categoryList();
            for (Categories c : cateList) {
                if (!cate.getCateName().equalsIgnoreCase(cateName) && c.getCateName().equalsIgnoreCase(cateName)) {
                    return "1"; //trùng
                }
            }
            return "0";
        } else { //kiểm tra khi create
            if (productStateLessBean.findCategoryLikeName(cateName).size() > 0) {
                return "1"; //trùng
            } else {
                return "0"; //chưa tồn tại
            }
        }
    }
    /*========================================================================
     *                                                                       *
     *                       SUB-CATEGORY TREATMENT                          *
     *                                                                       *
     ========================================================================*/

    @RequestMapping(value = "product-subcategory")
    public String productSubCateList(ModelMap model) {
        model.addAttribute("subCateList", productStateLessBean.subCategoryList());
        return "admin/pages/product-subcategory-list";
    }

    @RequestMapping(value = "product-subcategory/create", method = RequestMethod.GET)
    public String productSubCateAdd(ModelMap model) {
        SubCategories subCategory = new SubCategories();
        model.addAttribute("subCategory", subCategory);
        return "admin/pages/product-subcategory-add";
    }

    @RequestMapping(value = "product-subcategory/create", method = RequestMethod.POST)
    public String productSubCateAdd(ModelMap model,
            @RequestParam("category.cateID") Integer cateID,
            @ModelAttribute("subCategory") SubCategories newSubCategory,
            RedirectAttributes flashAttr) {
        newSubCategory.setSubCateNameNA(shareFunc.changeText(newSubCategory.getSubCateName()));
        Categories cate = productStateLessBean.findCategoryByID(cateID);
        int errorCode = productStateLessBean.createNewSubCategory(newSubCategory);
        if (errorCode == 1) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                    + "<strong>Success!</strong> Create New SubCategory Successfully!.\n"
                    + "</div>");
            return "redirect:/admin/product-subcategory/create.html";
        } else if (errorCode == 2) {
            model.addAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Already have SubCategory<b>\"" + newSubCategory.getSubCateName() + "\"</b> in <b>\"" + cate.getCateName() + "\"</b>!.\n"
                    + "</div>");
            model.addAttribute("subCategory", newSubCategory);
            return "admin/pages/product-subcategory-add";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Error! </strong> Error was happened!.\n"
                    + "</div>");
            return "redirect:/admin/product-subcategory/create.html";
        }
    }

    @RequestMapping(value = "product-subcategory/{subCateNameNA}-{subCateID}", method = RequestMethod.GET)
    public String productSubCateUpdate(ModelMap model,
            @PathVariable("subCateID") Integer subCateID) {
        SubCategories targetSubCategory = productStateLessBean.findSubCategoryByID(subCateID);
        model.addAttribute("targetSubCategory", targetSubCategory);
        return "admin/pages/product-subcategory-update";
    }

    @RequestMapping(value = "product-subcategory/{subCateNameNA}-{subCateID}", method = RequestMethod.POST)
    public String productSubCateUpdate(ModelMap model,
            @PathVariable("subCateID") Integer subCateID,
            @RequestParam("category.cateID") Integer cateID,
            RedirectAttributes flashAttr,
            @ModelAttribute("targetSubCategory") SubCategories targetSubCategory) {
        SubCategories oldCategory = productStateLessBean.findSubCategoryByID(subCateID);
        targetSubCategory.setSubCateNameNA(shareFunc.changeText(targetSubCategory.getSubCateName()));
        Categories cate = productStateLessBean.findCategoryByID(cateID);

        int errorCode = productStateLessBean.updateSubCategory(targetSubCategory);
        if (errorCode == 1) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                    + "<strong>Success!</strong> Update SubCategory Successfully!.\n"
                    + "</div>");
            return "redirect:/admin/product-subcategory/" + targetSubCategory.getSubCateNameNA() + "-" + subCateID + ".html";
        } else if (errorCode == 2) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Already have SubCategory<b>\"" + targetSubCategory.getSubCateName() + "\"</b> in <b>\"" + cate.getCateName() + "\"</b>!.\n"
                    + "</div>");
            return "redirect:/admin/product-subcategory/" + oldCategory.getSubCateNameNA() + "-" + subCateID + ".html";
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Error! </strong> Error was happened!.\n"
                    + "</div>");
            return "redirect:/admin/product-subcategory/" + oldCategory.getSubCateNameNA() + "-" + subCateID + ".html";
        }
    }

    @RequestMapping(value = "deletesubcategory-{subCateID}", method = RequestMethod.GET)
    public String deleteSubCategory(
            @PathVariable("subCateID") Integer subCateID,
            RedirectAttributes flashAttr
    ) {
        if (productStateLessBean.deleteSubCate(subCateID)) {
            flashAttr.addFlashAttribute("error", "0");
        } else {
            flashAttr.addFlashAttribute("error", "1");
        }

        return "redirect:/admin/product-subcategory.html";
    }

    @RequestMapping(value = "ajax/checkDupSubCategory", method = RequestMethod.POST)
    @ResponseBody
    public String checkDupSubCategory(
            @RequestParam("cateID") Integer cateID,
            @RequestParam(value = "subCateID", required = false) Integer subCateID,
            @RequestParam("subCateName") String subCateName
    ) {
        if (subCateID != null) { //check update
            SubCategories originalSubCate = productStateLessBean.findSubCategoryByID(subCateID);
            if (Objects.equals(originalSubCate.getCategory().getCateID(), cateID) && originalSubCate.getSubCateName().equalsIgnoreCase(subCateName)) {
                return "0";
            } else {
                Categories cate = productStateLessBean.findCategoryByID(cateID);
                List<SubCategories> subCateList = cate.getSubCateList();
                int count = 0;
                for (SubCategories sc : subCateList) {
                    if (subCateName.equalsIgnoreCase(sc.getSubCateName())) {
                        count++;
                    }
                }

                if (count != 0) {
                    return "1";//trùng
                } else {
                    return "0";//chưa tồn tại
                }
            }
        } else { //check create
            Categories cate = productStateLessBean.findCategoryByID(cateID);
            List<SubCategories> subCateList = cate.getSubCateList();

            int count = 0;
            for (SubCategories sc : subCateList) {
                if (subCateName.equalsIgnoreCase(sc.getSubCateName())) {
                    count++;
                }
            }

            if (count != 0) {
                return "1";//trùng
            } else {
                return "0";//chưa tồn tại
            }
        }
    }
    /*========================================================================
     *                                                                       *
     *                          PRODUCT TREATMENT                            *
     *                                                                       *
     ========================================================================*/

    @RequestMapping(value = "product")
    public String productList(ModelMap model) {
        model.addAttribute("productList", productStateLessBean.productList("admin"));
        return "admin/pages/product-list";
    }

    @RequestMapping(value = "product/create", method = RequestMethod.GET)
    public String productAdd() {
        return "admin/pages/product-add";
    }

    @RequestMapping(value = "product/create", method = RequestMethod.POST)
    public String productAdd(HttpServletRequest request,
            ModelMap model,
            @RequestParam MultiValueMap<String, String> allRequestParams,
            RedirectAttributes flashAttr) {
        String productName = allRequestParams.get("productName").get(0);
        if (productStateLessBean.checkDuplicateProductName(productName)) {
            model.addAttribute("error", "<div class=\"col-xs-12 col-sm-6 col-sm-offset-3 alert alert-danger\">\n"
                    + "<strong>Error!</strong> Duplicate Product Name !.\n"
                    + "</div>");
            return "admin/pages/product-add";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        int cateID = Integer.parseInt(allRequestParams.get("category").get(0));
        int subCateID = Integer.parseInt(allRequestParams.get("subCategory").get(0));

        Float price = Float.parseFloat(allRequestParams.get("price").get(0));
        Short discount = Short.parseShort(allRequestParams.get("discount").get(0));
        String description = allRequestParams.get("description").get(0);
        List<String> colorList = allRequestParams.get("color");
        List<MultipartFile> colorImgs = ((DefaultMultipartHttpServletRequest) request).getFiles("colorImg[]");

        Products product = new Products();

        //Load thông tin category theo cateID
        Categories cate = productStateLessBean.findCategoryByID(cateID);

        //Load thông tin subCategory theo subCateID;
        SubCategories subCate = productStateLessBean.findSubCategoryByID(subCateID);

        //Load List<productColors>
        List<ProductColors> productColorsList = new ArrayList<>();

        for (int i = 0; i < colorList.size(); i++) {
            ProductColors productColor = new ProductColors();
            productColor.setColor(colorList.get(i));
            productColor.setColorNA(shareFunc.changeText(colorList.get(i)));
            productColor.setStatus((short) 1);
            productColor.setProduct(product);
            productColor.setColorOrder(i);

            //setUrlColorImg
            MultipartFile colorImg = colorImgs.get(i);
            if (!colorImg.isEmpty()) {
                //set productColor UrlColorImg
                productColor.setUrlColorImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(colorImg.getOriginalFilename())); //Tên hình

                //Luu file duong dan
                String path = app.getRealPath("/assets/images/products/colors/") + "/" + productColor.getUrlColorImg();
                try {
                    colorImg.transferTo(new File(path));
                } catch (IOException | IllegalStateException ex) {
                    Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //set List<SizeByColor>
            List<SizesByColor> sizeList = new ArrayList<>();
            if (i == 0) {
                List<String> sizeListFromClient = allRequestParams.get("size");
                for (int j = 0; j < sizeListFromClient.size(); j++) {
                    SizesByColor size = new SizesByColor();
                    size.setProductSize(sizeListFromClient.get(j).toUpperCase());
                    size.setQuantity(Integer.parseInt(allRequestParams.get("quantity").get(j)));
                    size.setSizeOrder(j);
                    size.setStatus((short) 1);
                    size.setColor(productColor);
                    sizeList.add(size);
                }
            } else {
                List<String> sizeListFromClient = allRequestParams.get("size_" + i);
                for (int j = 0; j < sizeListFromClient.size(); j++) {
                    SizesByColor size = new SizesByColor();
                    size.setProductSize(sizeListFromClient.get(j).toUpperCase());
                    size.setQuantity(Integer.parseInt(allRequestParams.get("quantity_" + i).get(j)));
                    size.setSizeOrder(j);
                    size.setStatus((short) 1);
                    size.setColor(productColor);
                    sizeList.add(size);
                }
            }
            productColor.setSizeList(sizeList);

            //set List<ProductSubImg>
            List<ProductSubImgs> productSubImgsList = new ArrayList<>();
            List<MultipartFile> subImgsList;
            if (i == 0) {
                subImgsList = ((DefaultMultipartHttpServletRequest) request).getFiles("productSubImg[]");
            } else {
                subImgsList = ((DefaultMultipartHttpServletRequest) request).getFiles("productSubImg_" + i + "[]");
            }
            int k = 0;
            for (MultipartFile file : subImgsList) {

                ProductSubImgs psi = new ProductSubImgs();
                //set urlimg
                psi.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(file.getOriginalFilename()));
                psi.setSubImgOrder(k);
                //Luu file vao duong dan
                String subImgPath = app.getRealPath("/assets/images/products/subImg/") + "/" + psi.getUrlImg();
                try {
                    file.transferTo(new File(subImgPath));
                } catch (IOException | IllegalStateException ex) {
                    Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                psi.setProductColor(productColor);
                productSubImgsList.add(psi);
                k++;
            }
            productColor.setProductSubImgsList(productSubImgsList);

            productColorsList.add(productColor);
        }

        //Load Main IMG
        MultipartFile mainImgFile = ((DefaultMultipartHttpServletRequest) request).getFile("urlImg");
        if (mainImgFile.isEmpty()) {
            model.addAttribute("mainImgFileError", "Product Image cannot be empty!");
        } else {
            //set product urlImg
            product.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(mainImgFile.getOriginalFilename()));

            //luu file vào duong dan
            String path = app.getRealPath("/assets/images/products/") + "/" + product.getUrlImg();
            try {
                mainImgFile.transferTo(new File(path));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        product.setCategory(cate);
        product.setSubCate(subCate);
        product.setProductName(productName);
        product.setProductNameNA(shareFunc.changeText(productName));
        product.setPrice(price);
        product.setProductDescription(description);
        product.setProductDiscount(discount);
        product.setPostedDate(new Date());
        product.setProductViews(0);
        product.setStatus((short) 1);
        product.setProductColorList(productColorsList);

        if (productStateLessBean.createNewProduct(product)) {
            flashAttr.addFlashAttribute("error", "<div class=\"col-xs-12 col-sm-6 col-sm-offset-3 alert alert-success\">\n"
                    + "<strong>Success!</strong> Create New Product Successfully!.\n"
                    + "</div>");
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"col-xs-12 col-sm-6 col-sm-offset-3 alert alert-danger\">\n"
                    + "<strong>Error!</strong> Sorry error was happened, please try again!.\n"
                    + "</div>");
        }
        return "redirect:/admin/product/create.html";
    }

    @RequestMapping(value = "ajax/checkProductName", method = RequestMethod.POST)
    @ResponseBody
    public String checkProductName(
            @RequestParam(value = "productID", required = false) Integer productID,
            @RequestParam("productName") String productName) {
        if (productID != null) { //kiem tra update
            Products p = productStateLessBean.findProductByID(productID);
            if (productStateLessBean.checkDuplicateProductName(productName) && !productName.equalsIgnoreCase(p.getProductName())) {
                return "1"; //trung
            } else {
                return "0"; //OK
            }
        } else { //kiem tra create
            if (productStateLessBean.checkDuplicateProductName(productName)) {
                return "1"; //trung
            } else {
                return "0"; //chua ton tai
            }
        }
    }

    @RequestMapping(value = "ajax/changeProductStatus", method = RequestMethod.POST)
    @ResponseBody
    public void changeProductStatus(
            @RequestParam("newProductStatus") short newProductStatus,
            @RequestParam("productID") int productID) {
        productStateLessBean.updateProductStatus(productID, newProductStatus);
    }

    @RequestMapping(value = "ajax/getSubCategory", method = RequestMethod.POST)
    @ResponseBody
    public String getSubCategory(@RequestParam("cateID") Integer cateID) {
        Categories cate = productStateLessBean.findCategoryByID(cateID);
        List<SubCategories> subCateList = cate.getSubCateList();
        List<Properties> newList = new ArrayList<>();
        for (SubCategories sc : subCateList) {
            Properties prop = new Properties();
            prop.setProperty("subCateID", sc.getSubCateID().toString());
            prop.setProperty("subCateName", sc.getSubCateName());
            newList.add(prop);
        }
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(newList);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @RequestMapping(value = "product/edit-{productID}")
    public String productUpdate(
            @PathVariable("productID") Integer productID,
            ModelMap model) {
        Products targetProduct = productStateLessBean.findProductByID(productID);
        model.addAttribute("targetProduct", targetProduct);
        Categories cate = productStateLessBean.findCategoryByID(targetProduct.getCategory().getCateID());
        List<SubCategories> subCateListByCate = cate.getSubCateList();
        model.addAttribute("productID", productID);
        model.addAttribute("firstColorID", targetProduct.getProductColorListWorking().get(0).getColorID());
        model.addAttribute("productNameNA", targetProduct.getProductNameNA());
        model.addAttribute("subCateList", subCateListByCate);
        return "admin/pages/product-update";
    }

    @RequestMapping(value = "product/edit-general-info-{productID}", method = RequestMethod.POST)
    public String productEditGeneralInfo(
            @PathVariable("productID") Integer productID,
            @RequestParam("category") Integer categoryID,
            @RequestParam("subCategory") Integer subCategoryID,
            @RequestParam("productName") String productName,
            @RequestParam("price") Float price,
            @RequestParam("discount") Short discount,
            @RequestParam("urlImg") MultipartFile urlImg,
            @RequestParam("description") String description,
            RedirectAttributes flashAttr
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Products targetProduct = productStateLessBean.findProductByID(productID);
        String oldImg = targetProduct.getUrlImg();
        if (targetProduct != null) {
            targetProduct.setCategory(productStateLessBean.findCategoryByID(categoryID));
            targetProduct.setSubCate(productStateLessBean.findSubCategoryByID(subCategoryID));
            targetProduct.setProductName(productName);
            targetProduct.setProductNameNA(shareFunc.changeText(productName));
            targetProduct.setPrice(price);
            targetProduct.setProductDiscount(discount);
            targetProduct.setProductDescription(description);

            if (!urlImg.isEmpty()) {
                //Xoa old img trong folder
                String oldPath = app.getRealPath("/assets/images/products/") + "/" + oldImg;
                File oldFile = new File(oldPath);
                oldFile.delete();

                //Them file moi
                targetProduct.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(urlImg.getOriginalFilename()));

                //luu file vào duong dan
                String path = app.getRealPath("/assets/images/products/") + "/" + targetProduct.getUrlImg();
                try {
                    urlImg.transferTo(new File(path));
                } catch (IOException | IllegalStateException ex) {
                    Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (productStateLessBean.updateProductGeneralInfo(targetProduct)) {
                flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                        + "                                                                               <strong>Success!</strong> Update Products Info Completed!.\n"
                        + "                                                                          </div>");
            } else {
                flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                        + "                                                                               <strong>Error!</strong> Update Products Info FAIL!, Please try again!.\n"
                        + "                                                                          </div>");
            }
            return "redirect:/admin/product/edit-" + productID + ".html";
        } else {
            return "Ve Trang Loi";
        }

    }

    @RequestMapping(value = "ajax/updateProductColorStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateProductColorStatus(
            @RequestParam("colorID") Integer colorID,
            @RequestParam("newStt") Short newStt
    ) {
        if (productStateLessBean.updateProductColorStatus(colorID, newStt)) {
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "ajax/getProductColorByID", method = RequestMethod.POST)
    @ResponseBody
    public String getProductColorByID(
            @RequestParam("colorID") Integer colorID
    ) {
        ProductColors color = productStateLessBean.getProductColorByID(colorID);

        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(color);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @RequestMapping(value = "ajax/updateProductColor", method = RequestMethod.POST)
    @ResponseBody
    public String updateProductColor(
            @RequestParam("productID") Integer productID,
            @RequestParam("colorID") Integer colorID,
            @RequestParam("color") String color,
            @RequestParam(value = "colorImg", required = false) MultipartFile colorImg
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        ProductColors targetColor = productStateLessBean.getProductColorByID(colorID);
        targetColor.setColor(color);
        targetColor.setColorNA(shareFunc.changeText(color));

        if (colorImg != null) {
            if (!colorImg.isEmpty()) {
                //Xoa old img trong folder
                String oldPath = app.getRealPath("/assets/images/products/colors") + "/" + colorImg;
                File oldFile = new File(oldPath);
                oldFile.delete();

                //Them file moi
                targetColor.setUrlColorImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(colorImg.getOriginalFilename()));

                //luu file vào duong dan
                String path = app.getRealPath("/assets/images/products/colors") + "/" + targetColor.getUrlColorImg();
                try {
                    colorImg.transferTo(new File(path));
                } catch (IOException | IllegalStateException ex) {
                    Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (productStateLessBean.updateProductColor(targetColor)) {
            List<ProductColors> productColorsList = productStateLessBean.findProductByID(productID).getProductColorList();
            String result = "";

            for (ProductColors c : productColorsList) {
                String chooseOpt = "";

                if (c.getStatus() == 0) {
                    chooseOpt += "<div class=\"fs-stopworking-icon-product-color-update\">\n"
                            + "       <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + "   </div>\n"
                            + "   <select class=\"form-control fs-product-update-color-status\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                            + "        <option value=\"1\">\n"
                            + "            Working\n"
                            + "        </option>\n"
                            + "        <option value=\"0\" selected>\n"
                            + "            Stopped\n"
                            + "        </option>\n"
                            + "   </select>";
                } else {
                    chooseOpt += "<div class=\"fs-stopworking-icon-product-color-update fs-display-none\">\n"
                            + "       <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + "   </div>\n"
                            + "   <select class=\"form-control fs-product-update-color-status\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                            + "        <option value=\"1\" selected>\n"
                            + "            Working\n"
                            + "        </option>\n"
                            + "        <option value=\"0\">\n"
                            + "            Stopped\n"
                            + "        </option>\n"
                            + "   </select>";
                }
                result += "<tr class=\"text-center\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                        + "   <td class=\"fs-valign-middle\">" + c.getColorOrder() + "</td>\n"
                        + "   <td class=\"fs-valign-middle\">" + c.getColor() + "</td>\n"
                        + "   <td class=\"fs-valign-middle\">\n"
                        + "       <img style=\"width: 30px\" src=\"assets/images/products/colors/" + c.getUrlColorImg() + "\"/>\n"
                        + "   </td>\n"
                        + "   <td class=\"fs-valign-middle\" style=\"position: relative\">\n"
                        + chooseOpt
                        + "   </td>\n"
                        + "   <td class=\"fs-valign-middle\">\n"
                        + "       <button type=\"button\" class=\"btn btn-warning btn-edit-product-color\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                        + "           <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i> Edit\n"
                        + "       </button>\n"
                        + "   </td>\n"
                        + "</tr>";
            }
            return result;
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "ajax/updateColorOrder", method = RequestMethod.POST)
    @ResponseBody
    public void updateColorOrder(
            @RequestParam("colorID") Integer colorID,
            @RequestParam("position") Integer position
    ) {
        ProductColors targetColor = productStateLessBean.getProductColorByID(colorID);
        targetColor.setColorOrder(position);

        productStateLessBean.updateProductColor(targetColor);
    }

    @RequestMapping(value = "ajax/getColorList", method = RequestMethod.POST)
    @ResponseBody
    public String getColorList(
            @RequestParam("productID") Integer productID
    ) {
        List<ProductColors> productColorsList = productStateLessBean.findProductByID(productID).getProductColorList();
        String result = "";

        for (ProductColors c : productColorsList) {
            String chooseOpt = "";

            if (c.getStatus() == 0) {
                chooseOpt += "<div class=\"fs-stopworking-icon-product-color-update\">\n"
                        + "       <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                        + "   </div>\n"
                        + "   <select class=\"form-control fs-product-update-color-status\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                        + "        <option value=\"1\">\n"
                        + "            Working\n"
                        + "        </option>\n"
                        + "        <option value=\"0\" selected>\n"
                        + "            Stopped\n"
                        + "        </option>\n"
                        + "   </select>";
            } else {
                chooseOpt += "<div class=\"fs-stopworking-icon-product-color-update fs-display-none\">\n"
                        + "       <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                        + "   </div>\n"
                        + "   <select class=\"form-control fs-product-update-color-status\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                        + "        <option value=\"1\" selected>\n"
                        + "            Working\n"
                        + "        </option>\n"
                        + "        <option value=\"0\">\n"
                        + "            Stopped\n"
                        + "        </option>\n"
                        + "   </select>";
            }
            result += "<tr class=\"text-center\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                    + "   <td class=\"fs-valign-middle\">" + c.getColorOrder() + "</td>\n"
                    + "   <td class=\"fs-valign-middle\">" + c.getColor() + "</td>\n"
                    + "   <td class=\"fs-valign-middle\">\n"
                    + "       <img style=\"width: 30px\" src=\"assets/images/products/colors/" + c.getUrlColorImg() + "\"/>\n"
                    + "   </td>\n"
                    + "   <td class=\"fs-valign-middle\" style=\"position: relative\">\n"
                    + chooseOpt
                    + "   </td>\n"
                    + "   <td class=\"fs-valign-middle\">\n"
                    + "       <button type=\"button\" class=\"btn btn-warning btn-edit-product-color\" fs-product-colorID=\"" + c.getColorID() + "\">\n"
                    + "           <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i> Edit\n"
                    + "       </button>\n"
                    + "   </td>\n"
                    + "</tr>";
        }
        return result;

    }

    @RequestMapping(value = "ajax/checkDuplicateColor.html", method = RequestMethod.POST)
    @ResponseBody
    public String checkDuplicateColor(
            @RequestParam("productID") Integer productID,
            @RequestParam("color") String color,
            @RequestParam("colorID") Integer colorID
    ) {
        ProductColors currentColor = productStateLessBean.getProductColorByID(colorID);
        String currentColorName = currentColor.getColor();
        List<ProductColors> productColorsList = productStateLessBean.findProductByID(productID).getProductColorList();

        for (ProductColors c : productColorsList) {
            if (c.getColor().equalsIgnoreCase(color) && !color.equalsIgnoreCase(currentColorName)) {
                return "1";
            }
        }

        return "0";
    }

    @RequestMapping(value = "ajax/checkDuplicateColorInProduct.html", method = RequestMethod.POST)
    @ResponseBody
    public String checkDuplicateColorInProduct(
            @RequestParam("productID") Integer productID,
            @RequestParam("color") String color
    ) {
        List<ProductColors> alist = productStateLessBean.getProductColorsListOfAProductByName(productID, color);

        if (alist.size() > 0) { // trùng
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "ajax/updateSubImgOrder", method = RequestMethod.POST)
    @ResponseBody
    public void updateSubImgOrder(
            @RequestParam("productSubImgID") Integer productSubImgID,
            @RequestParam("position") Integer position
    ) {
        ProductSubImgs targetSubImg = productStateLessBean.getProductSubImgByID(productSubImgID);
        targetSubImg.setSubImgOrder(position);

        productStateLessBean.updateProductSubImg(targetSubImg);
    }

    @RequestMapping(value = "ajax/getSubImgListByColor", method = RequestMethod.POST)
    @ResponseBody
    public String getSubImgListByColor(
            @RequestParam("colorID") Integer colorID
    ) {
        List<ProductSubImgs> subImgsList = productStateLessBean.findProductColorByColorID(colorID).getProductSubImgsList();
        String result = "";
        for (ProductSubImgs si : subImgsList) {
            result += "<tr class=\"text-center\" fs-productSubImgID=\"" + si.getSubImgID() + "\">\n"
                    + "    <td class=\"fs-valign-middle\">" + si.getSubImgOrder() + "</td>\n"
                    + "    <td class=\"fs-valign-middle fs-update-sub-img-change-image-here\">\n"
                    + "        <img src=\"assets/images/products/subImg/" + si.getUrlImg() + "\" style=\"width: 80px\"/>\n"
                    + "    </td>\n"
                    + "    <td class=\"fs-valign-middle\">\n"
                    + "        <input type=\"file\" name=\"fs-update-product-sub-img\" class=\"fs-update-product-sub-img\" disabled/>\n"
                    + "        <p class=\"help-block fs-update-product-sub-img-error-mes\"></p>\n"
                    + "    </td>\n"
                    + "    <td class=\"fs-valign-middle\">\n"
                    + "        <button type=\"button\" class=\"btn btn-warning fs-btn-edit-product-sub-img-form\">\n"
                    + "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i> Edit\n"
                    + "        </button>\n"
                    + "        <button type=\"button\" class=\"btn btn-danger fs-btn-delete-product-sub-img\">\n"
                    + "            <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                    + "        </button>\n"
                    + "    </td>\n"
                    + "</tr>";
        }

        return result;
    }

    @RequestMapping(value = "ajax/changeSubImg", method = RequestMethod.POST)
    @ResponseBody
    public String changeSubImg(
            @RequestParam("subImgID") Integer subImgID,
            @RequestParam("newImg") MultipartFile newImg
    ) {
        ProductSubImgs targetSubImg = productStateLessBean.getProductSubImgByID(subImgID);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        if (!newImg.isEmpty()) {
            //Xoa old img trong folder
            String oldPath = app.getRealPath("/assets/images/products/subImg") + "/" + targetSubImg.getUrlImg();
            File oldFile = new File(oldPath);
            oldFile.delete();

            //Them file moi
            targetSubImg.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(newImg.getOriginalFilename()));

            //luu file vào duong dan
            String path = app.getRealPath("/assets/images/products/subImg") + "/" + targetSubImg.getUrlImg();
            try {
                newImg.transferTo(new File(path));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (productStateLessBean.updateProductSubImg(targetSubImg)) {
            return targetSubImg.getUrlImg();
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "ajax/deleteProductSubImg", method = RequestMethod.POST)
    @ResponseBody
    public String deleteProductSubImg(
            @RequestParam("subImgID") Integer subImgID,
            @RequestParam("colorID") Integer colorID
    ) {
        int err = productStateLessBean.deleteProductSubImg(subImgID);
        if (err == 0) {
            List<ProductSubImgs> subImgsList = productStateLessBean.findProductColorByColorID(colorID).getProductSubImgsList();
            String result = "Nothing to Show!";
            for (ProductSubImgs si : subImgsList) {
                result += "<tr class=\"text-center\" fs-productSubImgID=\"" + si.getSubImgID() + "\">\n"
                        + "    <td class=\"fs-valign-middle\">" + si.getSubImgOrder() + "</td>\n"
                        + "    <td class=\"fs-valign-middle fs-update-sub-img-change-image-here\">\n"
                        + "        <img src=\"assets/images/products/subImg/" + si.getUrlImg() + "\" style=\"width: 80px\"/>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <input type=\"file\" name=\"fs-update-product-sub-img\" class=\"fs-update-product-sub-img\" disabled/>\n"
                        + "        <p class=\"help-block fs-update-product-sub-img-error-mes\"></p>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <button type=\"button\" class=\"btn btn-warning fs-btn-edit-product-sub-img-form\">\n"
                        + "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i> Edit\n"
                        + "        </button>\n"
                        + "        <button type=\"button\" class=\"btn btn-danger fs-btn-delete-product-sub-img\">\n"
                        + "            <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                        + "        </button>\n"
                        + "    </td>\n"
                        + "</tr>";
            }

            return result;
        } else if (err == 1) {
            return "1"; //Không tìm ra img
        } else {
            return "2"; //Lỗi trong quá trình xóa
        }
    }

    @RequestMapping(value = "ajax/changeProductSize", method = RequestMethod.POST)
    @ResponseBody
    public String changeProductSize(
            @RequestParam("name") String prodSize,
            @RequestParam("pk") Integer sizeID,
            @RequestParam("value") String newSize
    ) {
        SizesByColor targetSize = productStateLessBean.getSizeByID(sizeID);
        ProductColors colorOfSize = targetSize.getColor();
        List<SizesByColor> sizeList = colorOfSize.getSizeList();
        for (SizesByColor s : sizeList) {
            if (s.getProductSize().equalsIgnoreCase(newSize) && !newSize.equalsIgnoreCase(targetSize.getProductSize())) {
                return "2";
            }
        }

        targetSize.setProductSize(newSize.toUpperCase());

        if (productStateLessBean.updateSize(targetSize)) {
            return "0";
        } else {
            return "1";
        }
    }

    @RequestMapping(value = "ajax/changeProductQuantity", method = RequestMethod.POST)
    @ResponseBody
    public String changeProductQuantity(
            @RequestParam("pk") Integer sizeID,
            @RequestParam("value") Integer newQuantity
    ) {
        SizesByColor targetSize = productStateLessBean.getSizeByID(sizeID);

        targetSize.setQuantity(newQuantity);

        if (productStateLessBean.updateSize(targetSize)) {
            return "0";
        } else {
            return "1";
        }
    }

    @RequestMapping(value = "ajax/deleteProductSize", method = RequestMethod.POST)
    @ResponseBody
    public String deleteProductSize(
            @RequestParam("sizeID") Integer sizeID,
            @RequestParam("colorID") Integer colorID
    ) {
        int err = productStateLessBean.deleteProductSize(sizeID);
        if (err == 0) {
            List<SizesByColor> SizesList = productStateLessBean.findProductColorByColorID(colorID).getSizeList();
            String result = "Nothing to Show!";
            for (SizesByColor si : SizesList) {
                String sltb = "";
                if (si.getStatus() == 0) {
                    sltb += " <div class=\"fs-stopworking-icon-product-color-update\">\n"
                            + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + " </div>\n"
                            + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                            + "       <option value=\"0\" selected>\n"
                            + "               Stopped\n"
                            + "       </option>\n"
                            + "       <option value=\"1\">\n"
                            + "               Working\n"
                            + "       </option>\n"
                            + " </select>\n";
                } else {
                    sltb += " <div class=\"fs-stopworking-icon-product-color-update fs-display-none\">\n"
                            + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + " </div>\n"
                            + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                            + "       <option value=\"0\">\n"
                            + "               Stopped\n"
                            + "       </option>\n"
                            + "       <option value=\"1\" selected>\n"
                            + "               Working\n"
                            + "       </option>\n"
                            + " </select>\n";
                }

                String btn = "";
                if (si.getOrdersDetailList().size() > 0) {
                    btn += "<button type=\"button\" \n"
                            + "    fs-size-id=\"" + si.getSizeID() + "\"\n"
                            + "    fs-size=\"" + si.getProductSize() + "\"\n"
                            + "    class=\"btn btn-danger fs-update-product-button-delete-size\" disabled>\n"
                            + "    <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                            + "    </button>";
                } else {
                    btn += "<button type=\"button\" \n"
                            + "     fs-size-id=\"" + si.getSizeID() + "\"\n"
                            + "     fs-size=\"" + si.getProductSize() + "\"\n"
                            + "     class=\"btn btn-danger fs-update-product-button-delete-size\">\n"
                            + "     <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                            + "  </button>\n";
                }

                result += "<tr class=\"text-center\">\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "         <span class=\"fs-edit-product-size-val\" data-type=\"text\" \n"
                        + "               data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductSize.html\" \n"
                        + "               data-title=\"Enter New Size\" data-name=\"productSize\">\n"
                        + "               " + si.getProductSize() + "\n"
                        + "         </span>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <span class=\"fs-edit-product-quantity-val\" data-type=\"text\" \n"
                        + "              data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductQuantity.html\" \n"
                        + "              data-title=\"Enter New Quantity\" data-name=\"quantity\">\n"
                        + "             " + si.getQuantity() + "\n"
                        + "         </span>\n"
                        + "     </td>\n"
                        + "     <td class=\"fs-valign-middle\" style=\"position: relative\">\n"
                        + sltb
                        + "     </td>\n"
                        + "     <td class=\"fs-valign-middle\">\n"
                        + btn
                        + "     </td>\n"
                        + " </tr>";
            }

            return result;
        } else if (err == 1) {
            return "1";//Không tìm thấy SIZE
        } else {
            return "2"; //Quá trình xóa lỗi
        }
    }

    @RequestMapping(value = "ajax/changeSizeStatus", method = RequestMethod.POST)
    @ResponseBody
    public String changeSizeStatus(
            @RequestParam("sizeID") Integer sizeID,
            @RequestParam("newSTT") Short newSTT
    ) {
        SizesByColor targetSize = productStateLessBean.getSizeByID(sizeID);
        targetSize.setStatus(newSTT);

        if (productStateLessBean.updateSize(targetSize)) {
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "createNewProductColor", method = RequestMethod.POST)
    public String createNewProductColor(
            @RequestParam("productID") Integer productID,
            @RequestParam("fs-update-input-add-more-color") String color,
            @RequestParam("fs-update-input-add-color-img") MultipartFile colorImg,
            @RequestParam("size") List<String> size,
            @RequestParam("quantity") List<Integer> quantity,
            @RequestParam("fs-update-input-add-sub-img-in-add-more-color[]") List<MultipartFile> prodSubImg,
            RedirectAttributes flashAttr
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Products targetProduct = productStateLessBean.findProductByID(productID);
        targetProduct.getProductColorList().size();
        ProductColors newColor = new ProductColors();
        newColor.setColor(color);
        newColor.setColorNA(shareFunc.changeText(color));
        newColor.setProduct(targetProduct);
        newColor.setStatus((short) 1);
        newColor.setColorOrder(targetProduct.getProductColorList().size());

        //set productColor UrlColorImg
        newColor.setUrlColorImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(colorImg.getOriginalFilename())); //Tên hình

        //Luu file duong dan
        String path = app.getRealPath("/assets/images/products/colors/") + "/" + newColor.getUrlColorImg();
        try {
            colorImg.transferTo(new File(path));
        } catch (IOException | IllegalStateException ex) {
            Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<SizesByColor> sizeList = new ArrayList<>();
        int sizeOrder = 0;
        for (String s : size) {
            SizesByColor newSize = new SizesByColor();
            newSize.setProductSize(s.toUpperCase());
            newSize.setQuantity(quantity.get(sizeOrder));
            newSize.setSizeOrder(sizeOrder);
            newSize.setStatus((short) 1);
            newSize.setColor(newColor);
            sizeList.add(newSize);
            sizeOrder++;
        }
        newColor.setSizeList(sizeList);
        List<ProductSubImgs> productSubImgsList = new ArrayList<>();
        int k = 0;
        for (MultipartFile file : prodSubImg) {
            ProductSubImgs psi = new ProductSubImgs();
            //set urlimg
            psi.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(file.getOriginalFilename()));
            psi.setSubImgOrder(k);
            //Luu file vao duong dan
            String subImgPath = app.getRealPath("/assets/images/products/subImg/") + "/" + psi.getUrlImg();
            try {
                file.transferTo(new File(subImgPath));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            psi.setProductColor(newColor);
            productSubImgsList.add(psi);
            k++;
        }
        newColor.setProductSubImgsList(productSubImgsList);

        if (productStateLessBean.createNewProductColor(newColor)) {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-success\">\n"
                    + "<strong>Success!</strong> Create New Color completed!.\n"
                    + "</div>");
        } else {
            flashAttr.addFlashAttribute("error", "<div class=\"alert alert-danger\">\n"
                    + "<strong>Danger!</strong> Error was happened!.\n"
                    + "</div>");
        }

        return "redirect:/admin/product/edit-" + productID + ".html";
    }

    @RequestMapping(value = "ajax/addNewProductSubImage", method = RequestMethod.POST)
    @ResponseBody
    public String addNewProductSubImage(
            @RequestParam("colorID") Integer colorID,
            @RequestParam("newImg") MultipartFile newImg
    ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        ProductSubImgs newSubImg = new ProductSubImgs();
        ProductColors color = productStateLessBean.findProductColorByColorID(colorID);
        newSubImg.setProductColor(color);
        newSubImg.setSubImgOrder(color.getProductSubImgsList().size());

        //set productColor UrlColorImg
        newSubImg.setUrlImg(simpleDateFormat.format(new Date()) + shareFunc.changeText(newImg.getOriginalFilename())); //Tên hình

        //Luu file duong dan
        String path = app.getRealPath("/assets/images/products/subImg/") + "/" + newSubImg.getUrlImg();
        try {
            newImg.transferTo(new File(path));
        } catch (IOException | IllegalStateException ex) {
            Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (productStateLessBean.addProductSubImage(newSubImg)) {
            List<ProductSubImgs> subImgsList = productStateLessBean.findProductColorByColorID(colorID).getProductSubImgsList();
            String result = "";
            for (ProductSubImgs si : subImgsList) {
                result += "<tr class=\"text-center\" fs-productSubImgID=\"" + si.getSubImgID() + "\">\n"
                        + "    <td class=\"fs-valign-middle\">" + si.getSubImgOrder() + "</td>\n"
                        + "    <td class=\"fs-valign-middle fs-update-sub-img-change-image-here\">\n"
                        + "        <img src=\"assets/images/products/subImg/" + si.getUrlImg() + "\" style=\"width: 80px\"/>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <input type=\"file\" name=\"fs-update-product-sub-img\" class=\"fs-update-product-sub-img\" disabled/>\n"
                        + "        <p class=\"help-block fs-update-product-sub-img-error-mes\"></p>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <button type=\"button\" class=\"btn btn-warning fs-btn-edit-product-sub-img-form\">\n"
                        + "            <i class=\"fa fa-wrench\" aria-hidden=\"true\"></i> Edit\n"
                        + "        </button>\n"
                        + "        <button type=\"button\" class=\"btn btn-danger fs-btn-delete-product-sub-img\">\n"
                        + "            <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                        + "        </button>\n"
                        + "    </td>\n"
                        + "</tr>";
            }
            return result; //thành công
        } else {
            return "1"; // lỗi
        }

    }

    @RequestMapping(value = "ajax/addProductSize", method = RequestMethod.POST)
    @ResponseBody
    public String addProductSize(
            @RequestParam("colorID") Integer colorID,
            @RequestParam("size") String size,
            @RequestParam("quantity") Integer quantity
    ) {
        ProductColors color = productStateLessBean.findProductColorByColorID(colorID);
        SizesByColor newSize = new SizesByColor();
        newSize.setProductSize(size.toUpperCase());
        newSize.setQuantity(quantity);
        newSize.setSizeOrder(color.getSizeList().size());
        newSize.setColor(color);
        newSize.setStatus((short) 1);

        if (productStateLessBean.addSize(newSize)) {
            String result = "";
            List<SizesByColor> sizeList = productStateLessBean.getProductColorByID(colorID).getSizeList();
            for (SizesByColor si : sizeList) {
                String sltb = "";
                if (si.getStatus() == 0) {
                    sltb += " <div class=\"fs-stopworking-icon-product-color-update\">\n"
                            + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + " </div>\n"
                            + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                            + "       <option value=\"0\" selected>\n"
                            + "               Stopped\n"
                            + "       </option>\n"
                            + "       <option value=\"1\">\n"
                            + "               Working\n"
                            + "       </option>\n"
                            + " </select>\n";
                } else {
                    sltb += " <div class=\"fs-stopworking-icon-product-color-update fs-display-none\">\n"
                            + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                            + " </div>\n"
                            + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                            + "       <option value=\"0\">\n"
                            + "               Stopped\n"
                            + "       </option>\n"
                            + "       <option value=\"1\" selected>\n"
                            + "               Working\n"
                            + "       </option>\n"
                            + " </select>\n";
                }

                String btn = "";
                if (si.getOrdersDetailList().size() > 0) {
                    btn += "<button type=\"button\" \n"
                            + "    fs-size-id=\"" + si.getSizeID() + "\"\n"
                            + "    fs-size=\"" + si.getProductSize() + "\"\n"
                            + "    class=\"btn btn-danger fs-update-product-button-delete-size\" disabled>\n"
                            + "    <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                            + "    </button>";
                } else {
                    btn += "<button type=\"button\" \n"
                            + "     fs-size-id=\"" + si.getSizeID() + "\"\n"
                            + "     fs-size=\"" + si.getProductSize() + "\"\n"
                            + "     class=\"btn btn-danger fs-update-product-button-delete-size\">\n"
                            + "     <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                            + "  </button>\n";
                }

                result += "<tr class=\"text-center\">\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "         <span class=\"fs-edit-product-size-val\" data-type=\"text\" \n"
                        + "               data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductSize.html\" \n"
                        + "               data-title=\"Enter New Size\" data-name=\"productSize\">\n"
                        + "               " + si.getProductSize() + "\n"
                        + "         </span>\n"
                        + "    </td>\n"
                        + "    <td class=\"fs-valign-middle\">\n"
                        + "        <span class=\"fs-edit-product-quantity-val\" data-type=\"text\" \n"
                        + "              data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductQuantity.html\" \n"
                        + "              data-title=\"Enter New Quantity\" data-name=\"quantity\">\n"
                        + "             " + si.getQuantity() + "\n"
                        + "         </span>\n"
                        + "     </td>\n"
                        + "     <td class=\"fs-valign-middle\" style=\"position: relative\">\n"
                        + sltb
                        + "     </td>\n"
                        + "     <td class=\"fs-valign-middle\">\n"
                        + btn
                        + "     </td>\n"
                        + " </tr>";
            }

            return result;
        } else {
            return "1"; //error
        }
    }

    @RequestMapping(value = "ajax/updateSizeOrder", method = RequestMethod.POST)
    @ResponseBody
    public void updateSizeOrder(
            @RequestParam("sizeID") Integer sizeID,
            @RequestParam("position") Integer position
    ) {
        SizesByColor targetSize = productStateLessBean.getSizeByID(sizeID);
        targetSize.setSizeOrder(position);

        productStateLessBean.updateSize(targetSize);
    }

    @RequestMapping(value = "ajax/getSizeListByColor", method = RequestMethod.POST)
    @ResponseBody
    public String getSizeListByColor(
            @RequestParam("colorID") Integer colorID
    ) {
        String result = "";
        List<SizesByColor> sizeList = productStateLessBean.getProductColorByID(colorID).getSizeList();
        for (SizesByColor si : sizeList) {
            String sltb = "";
            if (si.getStatus() == 0) {
                sltb += " <div class=\"fs-stopworking-icon-product-color-update\">\n"
                        + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                        + " </div>\n"
                        + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                        + "       <option value=\"0\" selected>\n"
                        + "               Stopped\n"
                        + "       </option>\n"
                        + "       <option value=\"1\">\n"
                        + "               Working\n"
                        + "       </option>\n"
                        + " </select>\n";
            } else {
                sltb += " <div class=\"fs-stopworking-icon-product-color-update fs-display-none\">\n"
                        + "    <i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i>\n"
                        + " </div>\n"
                        + " <select class=\"form-control fs-product-update-size-status\" fs-size-id=\"" + si.getSizeID() + "\">\n"
                        + "       <option value=\"0\">\n"
                        + "               Stopped\n"
                        + "       </option>\n"
                        + "       <option value=\"1\" selected>\n"
                        + "               Working\n"
                        + "       </option>\n"
                        + " </select>\n";
            }

            String btn = "";
            if (si.getOrdersDetailList().size() > 0) {
                btn += "<button type=\"button\" \n"
                        + "    fs-size-id=\"" + si.getSizeID() + "\"\n"
                        + "    fs-size=\"" + si.getProductSize() + "\"\n"
                        + "    class=\"btn btn-danger fs-update-product-button-delete-size\" disabled>\n"
                        + "    <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                        + "    </button>";
            } else {
                btn += "<button type=\"button\" \n"
                        + "     fs-size-id=\"" + si.getSizeID() + "\"\n"
                        + "     fs-size=\"" + si.getProductSize() + "\"\n"
                        + "     class=\"btn btn-danger fs-update-product-button-delete-size\">\n"
                        + "     <i class=\"fa fa-close\" aria-hidden=\"true\"></i> Delete\n"
                        + "  </button>\n";
            }

            result += "<tr class=\"text-center\">\n"
                    + "    <td class=\"fs-valign-middle\">\n"
                    + "         <span class=\"fs-edit-product-size-val\" data-type=\"text\" \n"
                    + "               data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductSize.html\" \n"
                    + "               data-title=\"Enter New Size\" data-name=\"productSize\">\n"
                    + "               " + si.getProductSize() + "\n"
                    + "         </span>\n"
                    + "    </td>\n"
                    + "    <td class=\"fs-valign-middle\">\n"
                    + "        <span class=\"fs-edit-product-quantity-val\" data-type=\"text\" \n"
                    + "              data-pk=\"" + si.getSizeID() + "\" data-url=\"admin/ajax/changeProductQuantity.html\" \n"
                    + "              data-title=\"Enter New Quantity\" data-name=\"quantity\">\n"
                    + "             " + si.getQuantity() + "\n"
                    + "         </span>\n"
                    + "     </td>\n"
                    + "     <td class=\"fs-valign-middle\" style=\"position: relative\">\n"
                    + sltb
                    + "     </td>\n"
                    + "     <td class=\"fs-valign-middle\">\n"
                    + btn
                    + "     </td>\n"
                    + " </tr>";
        }

        return result;
    }
    /*========================================================================
     *                                                                       *
     *                              MISCELLANEOUS                            *
     *                                                                       *
     ========================================================================*/

//    @ResponseBody
//    @RequestMapping(value = "ajax/getReturningVisitorData", method = RequestMethod.POST)
//    public String getReturningVisitorData() {
//        List<ReturningVisitor> returningVisitorList = productStateLessBean.getReturningVisitorList();
//        int countNewVisitor = 0;
//        int countReturningVisitor = 0;
//        for (ReturningVisitor visitor : returningVisitorList) {
//            if (visitor.getVisitTimes() == 1) {
//                countNewVisitor++;
//            } else {
//                countReturningVisitor++;
//            }
//        }
//
//        String result = countNewVisitor + "-" + countReturningVisitor;
//        return result;
//    }
//
//    @RequestMapping(value = "ajax/getVisitTimes", method = RequestMethod.POST)
//    @ResponseBody
//    @SuppressWarnings("empty-statement")
//    public String getVisitTimes(
//            @RequestParam(value = "month", required = false) Integer month,
//            @RequestParam(value = "week", required = false) Integer week
//    ) {
//        if (month == null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
//            month = Integer.parseInt(dateFormat.format(new Date()));
//        }
//
//        String weekCondition = "";
//
//        if (week != null) {
//            weekCondition = "AND ((DAY(r.onDate)-1) / 7) + 1 = " + week;
//        }
//
//        List<Object[]> resultList = productStateLessBean.getVisitTimesByMonthAndWeek(month, weekCondition);
//        List<long[]> testList = new ArrayList<>();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        for (Object[] rs : resultList) {
//            String date = (String) rs[0];
//            try {
//                Date newDate = formatter.parse(date);
//                long timeStamp = newDate.getTime();
//                long[] newArray = new long[]{timeStamp, (int) rs[1]};
//                testList.add(newArray);
//            } catch (ParseException ex) {
//                Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        String result = "";
//
//        try {
//            result = mapper.writeValueAsString(testList);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(Product_Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }

    //Chuẩn bị dữ liệu cho select box Category
    @ModelAttribute("categories")
    public List<Categories> getAllCategory() {
        return productStateLessBean.categoryList();
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
