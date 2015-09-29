package com.andr.wearable_shop_on_mobile.DTO;

/**
 * Created by 245742 on 9/24/2015.
 */
public class OrderDetailData {
    private String productPrice;
    private String productName;
    private String productQty;

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }
}
