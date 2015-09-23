package com.andr.wearable_shop_on_mobile.Application;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;

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
}
