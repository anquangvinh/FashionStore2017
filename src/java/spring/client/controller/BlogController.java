/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.client.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.ejb.BlogCategoriesSBLocal;
import spring.ejb.BlogsSBLocal;
import spring.ejb.ProductStateLessBeanLocal;
import spring.ejb.UsersStateLessBeanLocal;
import spring.entity.BlogCategories;
import spring.entity.Blogs;
import spring.entity.Categories;
import spring.functions.SharedFunctions;

@Controller
public class BlogController {

    BlogCategoriesSBLocal categoriesSB = lookupBlogCategoriesSBLocal();
    BlogsSBLocal blogsSB = lookupBlogsSBLocal();
    ProductStateLessBeanLocal productStateLessBean = lookupProductStateLessBeanLocal();
    UsersStateLessBeanLocal usersStateLessBean = lookupUsersStateLessBeanLocal();
    BlogCategoriesSBLocal blogCategoriesSB = lookupBlogCategoriesSBLocal();

    @Autowired
    SharedFunctions shareFunc;

    @Autowired
    ServletContext app;

    @RequestMapping(value = "/blog")
    public String blog(ModelMap model) {
        List<Blogs> getShowAllBlogs = blogsSB.getAllBlogs();
        List<Blogs> blogListIndex = blogsSB.getAllBlogsIndex();
        model.addAttribute("blogListIndex", blogListIndex);
        model.addAttribute("blogListClient", getShowAllBlogs);
        model.addAttribute("PopularPosts", blogsSB.getAllBlogs());
        //2 dòng này thêm để render ra menu chính
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
        model.addAttribute("blogCateListClient", getBlogCateList);
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/blog";
    }

    @RequestMapping(value = "/blog/{month}")
    public String blog(ModelMap model, @PathVariable("month") Integer month) {
        List<Blogs> blogListIndex = blogsSB.getAllBlogsByMonth(month);
        List<Blogs> getBlogsListByCate = blogsSB.getAllBlogsByMonth(month);
          model.addAttribute("getBlogsListByCate", getBlogsListByCate);
        model.addAttribute("blogListIndex", blogListIndex);
        model.addAttribute("PopularPosts", blogsSB.getAllBlogs());
        //2 dòng này thêm để render ra menu chính
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
        model.addAttribute("blogCateListClient", getBlogCateList);
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        return "client/pages/blog";
    }

    //WHERE blogTitle LIKE '%g%' AND (MONTH(postedDate) = MONTH('2017-01-10') OR MONTH(postedDate) = MONTH('2017-02-10') OR MONTH(postedDate) = MONTH('2017-03-10'))
    @RequestMapping(value = "/blog-categories", method = RequestMethod.POST)
    public String blog_categories_search(HttpServletRequest request, ModelMap model) {
        String searchBlog = request.getParameter("searchBlog");
        if (searchBlog == null || searchBlog.equals("")) {
            return "redirect:/blog.html";
        } else {
            List<Blogs> getBlogsListBySearch = blogsSB.findBlogsByTitle(searchBlog, null);
            model.addAttribute("getBlogsListByCate", getBlogsListBySearch);
        }
        model.addAttribute("PopularPosts", blogsSB.getAllBlogs());
        model.addAttribute("searchBlog", request.getParameter("searchBlog"));
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
        model.addAttribute("getBlogCateList", getBlogCateList);
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        List<Blogs> getShowAllBlogs = blogsSB.getAllBlogs();
        model.addAttribute("blogListClient", getShowAllBlogs);
        model.addAttribute("cateList", cateList);
        return "client/pages/blog-categories";
    }

    @RequestMapping(value = "/blog-categories/{blogCateID}")
    public String blog_categories(@PathVariable("blogCateID") Integer blogCateID, ModelMap model) {
//          //2 dòng này thêm để render ra menu chính
        List<Blogs> getShowAllBlogs = blogsSB.getAllBlogs();
        List<Categories> cateList = productStateLessBean.categoryList();
        List<Blogs> getBlogsListByCate = blogsSB.getListBlogsByCategory(blogCateID);
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
//        List<Blogs> getAllBlogsByMonth = blogsSB.getAllBlogsByMonth(); // List theo month
//        model.addAttribute("blogListByMonth", getAllBlogsByMonth);
        model.addAttribute("blogCateListClient", getBlogCateList);
        model.addAttribute("blogListClient", getShowAllBlogs);
        model.addAttribute("getBlogCateList", getBlogCateList);
        model.addAttribute("getBlogsListByCate", getBlogsListByCate);
        model.addAttribute("cateList", cateList);
        model.addAttribute("PopularPosts", blogsSB.getAllBlogs());
        return "client/pages/blog-categories";
    }

    @RequestMapping(value = "/blog-detail/{blogID}")
    public String blogdetail(@PathVariable("blogID") Integer blogID, ModelMap model) {
        Blogs getShowAllBlogsDetail = blogsSB.findBlogsByID(blogID);
        List<BlogCategories> getBlogCateListDetail = blogCategoriesSB.getBlogCategoriesList();
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        model.addAttribute("getShowAllBlogsDetail", getShowAllBlogsDetail);
        model.addAttribute("getBlogCateListDetail", getBlogCateListDetail);
        model.addAttribute("PopularPosts", blogsSB.getAllBlogs());
        //2 dòng này thêm để render ra menu chính
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
        model.addAttribute("blogCateListClient", getBlogCateList);
        if (getShowAllBlogsDetail != null) {
            Blogs blogUpdateView = getShowAllBlogsDetail;
            if (blogUpdateView.getBlogViews() == null) {
                blogUpdateView.setBlogViews(1);
            }
            blogUpdateView.setBlogViews(getShowAllBlogsDetail.getBlogViews() + 1);
            blogsSB.editBlogs(blogUpdateView);
        }
        return "client/pages/blog-details";
    }

    @RequestMapping(value = "/blog-detail")
    public String blogdetail(ModelMap model) {
        List<BlogCategories> getBlogCateList = blogCategoriesSB.getBlogCategoriesList();
        model.addAttribute("getBlogCateListDetail", blogCategoriesSB.getBlogCategoriesList());
        model.addAttribute("getShowAllBlogsDetail", blogsSB.getAllBlogs());
        //2 dòng này thêm để render ra menu chính
        List<Categories> cateList = productStateLessBean.categoryList();
        model.addAttribute("cateList", cateList);
        model.addAttribute("blogCateListClient", getBlogCateList);
        return "client/pages/blog-details";
    }

    private BlogCategoriesSBLocal lookupBlogCategoriesSBLocal() {
        try {
            Context c = new InitialContext();
            return (BlogCategoriesSBLocal) c.lookup("java:global/fashionshop/BlogCategoriesSB!spring.ejb.BlogCategoriesSBLocal");
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

    private UsersStateLessBeanLocal lookupUsersStateLessBeanLocal() {
        try {
            Context c = new InitialContext();
            return (UsersStateLessBeanLocal) c.lookup("java:global/fashionshop/UsersStateLessBean!spring.ejb.UsersStateLessBeanLocal");
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
