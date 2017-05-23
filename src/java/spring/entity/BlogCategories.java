/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BlogCategories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogCateID;
    private String blogCateName;
    private String blogCateNameNA;
    
    @OneToMany(mappedBy = "blogCategory")
    @JsonManagedReference
    private List<Blogs> blogList;

    public Integer getBlogCateID() {
        return blogCateID;
    }

    public void setBlogCateID(Integer blogCateID) {
        this.blogCateID = blogCateID;
    }

    public String getBlogCateName() {
        return blogCateName;
    }

    public void setBlogCateName(String blogCateName) {
        this.blogCateName = blogCateName;
    }

    public String getBlogCateNameNA() {
        return blogCateNameNA;
    }

    public void setBlogCateNameNA(String blogCateNameNA) {
        this.blogCateNameNA = blogCateNameNA;
    }

    public List<Blogs> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blogs> blogList) {
        this.blogList = blogList;
    }

}
