package com.andr.wearable_shop_on_mobile.Application;

import android.app.Activity;
import android.app.Application;

import com.andr.wearable_shop_on_mobile.DTO.ListData;
import com.andr.wearable_shop_on_mobile.DTO.OrderDetailData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 245742 on 9/7/2015.
 */
public class WearableApplication extends Application {
    private static WearableApplication singleton;
    private Activity activity;
    private String json;
    private HashMap<String,HashMap<String,Integer>> basketMap = new HashMap<String,HashMap<String,Integer>>();
    private String listType="";
    private HashMap<String,String> orderMap = new HashMap<String,String>();
    HashMap<String,ListData>  productMultiValHash = new HashMap<String,ListData>();
    private HashMap<String,HashMap<String,HashMap<String,Integer>>> orderDetailHash= new HashMap<String,HashMap<String,HashMap<String,Integer>>>();
    private HashMap<String,List<OrderDetailData>> orderDetailHashh = new HashMap<String,List<OrderDetailData>>();
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
    public static WearableApplication getInstance() {
        return singleton;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public HashMap<String, HashMap<String, Integer>> getBasketMap() {
        return basketMap;
    }

    public void setBasketMap(HashMap<String, HashMap<String, Integer>> basketMap) {
        this.basketMap = basketMap;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public HashMap<String, String> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(HashMap<String, String> orderMap) {
        this.orderMap = orderMap;
    }

    public HashMap<String, ListData> getProductMultiValHash() {
        return productMultiValHash;
    }

    public void setProductMultiValHash(HashMap<String, ListData> productMultiValHash) {
        this.productMultiValHash = productMultiValHash;
    }

    public HashMap<String, HashMap<String, HashMap<String, Integer>>> getOrderDetailHash() {
        return orderDetailHash;
    }

    public void setOrderDetailHash(HashMap<String, HashMap<String, HashMap<String, Integer>>> orderDetailHash) {
        this.orderDetailHash = orderDetailHash;
    }

    public HashMap<String, List<OrderDetailData>> getOrderDetailHashh() {
        return orderDetailHashh;
    }

    public void setOrderDetailHashh(HashMap<String, List<OrderDetailData>> orderDetailHashh) {
        this.orderDetailHashh = orderDetailHashh;
    }
}
