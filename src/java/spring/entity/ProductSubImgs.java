/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductSubImgs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subImgID;
    private String urlImg;
    private Integer subImgOrder;
    
    @ManyToOne
    @JoinColumn(name = "colorID")
    @JsonBackReference
    private ProductColors productColor;

    public Integer getSubImgID() {
        return subImgID;
    }

    public void setSubImgID(Integer subImgID) {
        this.subImgID = subImgID;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Integer getSubImgOrder() {
        return subImgOrder;
    }

    public void setSubImgOrder(Integer subImgOrder) {
        this.subImgOrder = subImgOrder;
    }
    
    public ProductColors getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColors productColor) {
        this.productColor = productColor;
    }
    
    
}
