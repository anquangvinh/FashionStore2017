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
public class ProductColors implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer colorID;
    private String color;
    private String colorNA;
    private String urlColorImg;
    private Integer colorOrder;
    private Short status;

    @OneToMany(mappedBy = "color", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<SizesByColor> sizeList;

    @OneToMany(mappedBy = "productColor", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<ProductSubImgs> ProductSubImgsList;

    @ManyToOne
    @JoinColumn(name = "productID")
    @JsonBackReference
    private Products product;

    public Integer getColorID() {
        return colorID;
    }

    public void setColorID(Integer colorID) {
        this.colorID = colorID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorNA() {
        return colorNA;
    }

    public void setColorNA(String colorNA) {
        this.colorNA = colorNA;
    }

    public String getUrlColorImg() {
        return urlColorImg;
    }

    public void setUrlColorImg(String urlColorImg) {
        this.urlColorImg = urlColorImg;
    }

    public Integer getColorOrder() {
        return colorOrder;
    }

    public void setColorOrder(Integer colorOrder) {
        this.colorOrder = colorOrder;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public List<SizesByColor> getSizeList() {
        Collections.sort(sizeList, new Comparator<SizesByColor>(){
            @Override
            public int compare(SizesByColor s1, SizesByColor s2) {
                return s1.getSizeOrder() - s2.getSizeOrder();
            }            
        });
        return sizeList;
    }
    
    public List<SizesByColor> getSizeListWorking() {
        List<SizesByColor> sizeListWorking = new ArrayList<>();
        
        for (SizesByColor p : sizeList) {
            if(p.getStatus() == 1){
                sizeListWorking.add(p);
            }
        }
        Collections.sort(sizeListWorking, new Comparator<SizesByColor>(){
            @Override
            public int compare(SizesByColor s1, SizesByColor s2) {
                return s1.getSizeOrder() - s2.getSizeOrder();
            }            
        });
        return sizeListWorking;
    }

    public void setSizeList(List<SizesByColor> sizeList) {
        this.sizeList = sizeList;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public List<ProductSubImgs> getProductSubImgsList() {
        Collections.sort(ProductSubImgsList, new Comparator<ProductSubImgs>(){
            @Override
            public int compare(ProductSubImgs p1, ProductSubImgs p2) {
                return p1.getSubImgOrder() - p2.getSubImgOrder();
            }            
        });
        return ProductSubImgsList;
    }

    public void setProductSubImgsList(List<ProductSubImgs> ProductSubImgsList) {
        this.ProductSubImgsList = ProductSubImgsList;
    }

}
