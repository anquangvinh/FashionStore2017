/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class DiscountVoucher implements Serializable {
    @Id
    private String voucherID;
    private Short discount;
    private Integer quantity;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-YYYY")
    private Date beginDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-YYYY")
    private Date endDate;
    private String description;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.REFRESH)
    @JsonManagedReference
    private List<Orders> ordersList;
    
    public float getFloatDiscount(){
        return (Float.parseFloat(discount.toString())/100);
    }
    
    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
    
    
}
