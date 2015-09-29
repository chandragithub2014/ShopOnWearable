package com.andr.wearable_shop_on_mobile.application;

import android.app.Activity;
import android.app.Application;

/**
 * Created by 245742 on 9/8/2015.
 */
public class MobileApplication extends Application {
    private static MobileApplication singleton;
    private Activity activity;
    private String json;
    private String imageURL;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public static MobileApplication getInstance() {
        return singleton;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
