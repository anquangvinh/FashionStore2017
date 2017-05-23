/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Local;
import spring.entity.BlogCategories;

/**
 *
 * @author Phan.
 */
@Local
public interface BlogCategoriesSBLocal {

    List<BlogCategories> getBlogCategoriesList();

    int addNewBlogCategories(BlogCategories newBlogCate);

    boolean updateCategories(BlogCategories cate);

    int findCategoriesByName(String blogCateName);

    BlogCategories findCategoryByID(int blogCateID);

    int deleteCategory(BlogCategories blogCategories);

}
