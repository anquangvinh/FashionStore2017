/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class SubCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCateID;
    private String subCateName;
    private String subCateNameNA;
    
    @OneToMany(mappedBy = "subCate")
    @JsonManagedReference
    private List<Products> productList;
    
    @ManyToOne
    @JoinColumn(name = "cateID")
    @JsonBackReference
    private Categories category;

    public Integer getSubCateID() {
        return subCateID;
    }

    public void setSubCateID(Integer subCateID) {
        this.subCateID = subCateID;
    }

    public String getSubCateName() {
        return subCateName;
    }

    public void setSubCateName(String subCateName) {
        this.subCateName = subCateName;
    }

    public String getSubCateNameNA() {
        return subCateNameNA;
    }

    public void setSubCateNameNA(String subCateNameNA) {
        this.subCateNameNA = subCateNameNA;
    }
    
    public List<Products> getProductList() {
        return productList;
    }
    
    public List<Products> getProductListWorking(){
        List<Products> productListWorking = new ArrayList<>();
        for (Products p : productList) {
            if(p.getStatus() == 1){
                productListWorking.add(p);
            }
        }
        return productListWorking;
    }
    
    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
    
    
}
