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
public class CheckoutResponse {

    private String status;
    private String showDiscount;
    private String showDiscountPercent;

    public CheckoutResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowDiscount() {
        return showDiscount;
    }

    public void setShowDiscount(String showDiscount) {
        this.showDiscount = showDiscount;
    }

    public String getShowDiscountPercent() {
        return showDiscountPercent;
    }

    public void setShowDiscountPercent(String showDiscountPercent) {
        this.showDiscountPercent = showDiscountPercent;
    }
}
