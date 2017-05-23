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
public class OrdersDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ordersDetailID;
    private Short productDiscount;
    private Integer quantity;
    private Float price;
    private Short status;
    
    @ManyToOne
    @JoinColumn(name = "ordersID")
    @JsonBackReference
    private Orders order;
    
    @ManyToOne
    @JoinColumn(name = "productID")
    @JsonBackReference
    private Products product;
    
    @ManyToOne
    @JoinColumn(name = "sizeID")
    @JsonBackReference
    private SizesByColor size;
    
    public float getTotalPrice() {
        return price*quantity;
    }
    
    public float getSubTotal(){
        return (getTotalPrice()-product.getProductDiscountPrice());
    }

    public Integer getOrdersDetailID() {
        return ordersDetailID;
    }

    public void setOrdersDetailID(Integer ordersDetailID) {
        this.ordersDetailID = ordersDetailID;
    }

    public Short getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Short productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public SizesByColor getSize() {
        return size;
    }

    public void setSize(SizesByColor size) {
        this.size = size;
    }
    
    
}
