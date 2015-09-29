/*
package com.andr.wearable_shop_on_mobile.Adapters;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.R;

*/
/**
 * Created by 245742 on 9/28/2015.
 *//*

public class ProductAdapter extends WearableListView.Adapter  {
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    private final class MyItemView extends FrameLayout implements WearableListView.Item {

       // final CircledImageView imgView;
        final TextView txtView,item3;
        private float mScale;
        private final int mFadedCircleColor;
        private final int mChosenCircleColor;

        public MyItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.product_item, this);
          */
/*  imgView = (CircledImageView) findViewById(R.id.image);
            txtView = (TextView) findViewById(R.id.text);*//*


            textView = (TextView) findViewById(R.id.name);
            item3  =  (TextView) findViewById(R.id.item3);
            mFadedCircleColor = getResources().getColor(android.R.color.darker_gray);
            mChosenCircleColor = getResources().getColor(android.R.color.holo_blue_dark);
        }

        @Override
        public float getProximityMinValue() {
            return mDefaultCircleRadius;
        }

        @Override
        public float getProximityMaxValue() {
            return mSelectedCircleRadius;
        }

        @Override
        public float getCurrentProximityValue() {
            return mScale;
        }

        @Override
        public void setScalingAnimatorValue(float value) {
            mScale = value;
            imgView.setCircleRadius(mScale);
            imgView.setCircleRadiusPressed(mScale);
        }

        @Override
        public void onScaleUpStart() {
            imgView.setAlpha(1f);
            txtView.setAlpha(1f);
            imgView.setCircleColor(mChosenCircleColor);
        }

        @Override
        public void onScaleDownStart() {
            imgView.setAlpha(0.5f);
            txtView.setAlpha(0.5f);
            imgView.setCircleColor(mFadedCircleColor);
        }
    }

}
*/
//https://github.com/PareshMayani/WearableListViewDemo/blob/master/wear/src/main/java/com/technotalkative/wearablelistviewdemo/AdvancedListActivity.java