/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Local;
import spring.entity.Blogs;

/**
 *
 * @author Phan
 */
@Local
public interface BlogsSBLocal {

    public List<Blogs> getAllBlogs();

    public List<Blogs> getAllBlogsIndex();

    public List<Blogs> getAllBlogsIndexMonth();

    public List<Blogs> getAllBlogsAdmin();

    List<Blogs> getListBlogsByCategory(int blogCateID);

    public List<Blogs> getAllBlogsByMonth(int month);

    boolean blogAdd(Blogs newBlogs);

    Blogs findBlogsByID(int id);

    boolean editBlogs(Blogs targetBlogs);

    List<Blogs> findBlogsByTitle(String blogTitle, List<Integer> monthList);

//    BlogCategories findBlogCategoryByBlogCateName(String blogCateName);
    int deleteBlog(Blogs blog);
    
      public Integer getAllNumberBlogsInCate(int blogCateID);
    

}
