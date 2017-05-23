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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Products implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productID;
    private String productName;
    private String productNameNA;
    private Float price;
    private String urlImg;
    private String productDescription;
    private Short productDiscount;
    @Temporal(TemporalType.DATE)
    private Date postedDate;
    private Integer productViews;
    private Short status;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<OrdersDetail> ordersDetailList;
    
    @ManyToOne
    @JoinColumn(name = "cateID")
    @JsonBackReference
    private Categories category;
    
    @ManyToOne
    @JoinColumn(name = "subCateID")
    @JsonBackReference
    private SubCategories subCate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<ProductColors> productColorList;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<WishList> wishList;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<ProductRating> productRatingList;
    
    public float getProductDiscountPrice(){
        return (price * (Float.parseFloat(productDiscount.toString())/100));
    }
    
    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameNA() {
        return productNameNA;
    }

    public void setProductNameNA(String productNameNA) {
        this.productNameNA = productNameNA;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Short getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Short productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getProductViews() {
        return productViews;
    }

    public void setProductViews(Integer productViews) {
        this.productViews = productViews;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public List<OrdersDetail> getOrdersDetailList() {
        return ordersDetailList;
    }

    public void setOrdersDetailList(List<OrdersDetail> ordersDetailList) {
        this.ordersDetailList = ordersDetailList;
    }

    public SubCategories getSubCate() {
        return subCate;
    }

    public void setSubCate(SubCategories subCate) {
        this.subCate = subCate;
    }

    public List<ProductColors> getProductColorList() {
        Collections.sort(productColorList, new Comparator<ProductColors>(){
            @Override
            public int compare(ProductColors p1, ProductColors p2) {
                return p1.getColorOrder() - p2.getColorOrder();
            }            
        });
        return productColorList;
    }
    
    public List<ProductColors> getProductColorListWorking(){
        List<ProductColors> productRatingWorking = new ArrayList<>();
        for (ProductColors p : productColorList) {
            if(p.getStatus() == 1){
                productRatingWorking.add(p);
            }
        }
        Collections.sort(productRatingWorking, new Comparator<ProductColors>(){
            @Override
            public int compare(ProductColors p1, ProductColors p2) {
                return p1.getColorOrder() - p2.getColorOrder();
            }            
        });
        return productRatingWorking;
    }
    
    public void setProductColorList(List<ProductColors> productColorList) {
        this.productColorList = productColorList;
    }

    public List<WishList> getWishList() {
        return wishList;
    }

    public void setWishList(List<WishList> wishList) {
        this.wishList = wishList;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<ProductRating> getProductRatingList() {
        return productRatingList;
    }
    
    public List<ProductRating> getProductRatingListVisible() {
        List<ProductRating> productRatingVisible = new ArrayList<>();
        for (ProductRating p : productRatingList) {
            if(p.getStatus() == 1){
                productRatingVisible.add(p);
            }
        }
        
        return productRatingVisible;
    }
    
    public void setProductRatingList(List<ProductRating> productRatingList) {
        this.productRatingList = productRatingList;
    }
    
    
}
