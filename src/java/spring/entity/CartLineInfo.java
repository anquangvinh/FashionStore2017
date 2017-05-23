/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

/**
 *
 * @author NganNgo
 */
public class CartLineInfo {
    private Products product;
    private SizesByColor sizesByColor;
    private int quantity;

    public SizesByColor getSizesByColor() {
        return sizesByColor;
    }

    public void setSizesByColor(SizesByColor sizesByColor) {
        this.sizesByColor = sizesByColor;
    }

    public CartLineInfo() {
        this.quantity = 0;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public float getSubTotal(){
        return ((product.getPrice() * quantity)-((product.getPrice() * (product.getProductDiscount()/100))*quantity));
    }
    
    public float getProductDiscount(){
        return this.product.getProductDiscountPrice() * this.quantity;
    }
}
