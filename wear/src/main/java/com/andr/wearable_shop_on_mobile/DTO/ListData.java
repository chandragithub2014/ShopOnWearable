package com.andr.wearable_shop_on_mobile.DTO;

/**
 * Created by CHANDRASAIMOHAN on 9/18/2015.
 */
public class ListData {
    private String item1; //image
    private String item2; //name
    private String item3; // cost
    private String imageurl; // url
    private String productDescription;
    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

   public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}

