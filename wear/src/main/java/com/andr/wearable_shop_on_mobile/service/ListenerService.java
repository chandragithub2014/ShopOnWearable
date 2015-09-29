package com.andr.wearable_shop_on_mobile.service;

import android.util.Log;

import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.Fragments.ProductDetailFragment;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by 245742 on 9/28/2015.
 */
public class ListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("test", "onMessageReceived()");
        if(messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            Log.d("TAG","Received JSON Message:::"+message);
            WearableApplication.getInstance().setJson(message);
        }else {
            super.onMessageReceived(messageEvent);
        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");
                ProductDetailFragment.setImage("Received from Mobile");
           //     Bitmap bitmap = loadBitmapFromAsset(profileAsset);
                // Do something with the bitmap
            }
        }
    }

/*
    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w(TAG, "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }*/
}
