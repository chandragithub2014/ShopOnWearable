package com.andr.wearable_shop_on_mobile.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by 245742 on 9/28/2015.
 */
public class ListenerService extends WearableListenerService {

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

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
    public void onPeerConnected(Node peer) {
        Log.d("ListenerService", "onPeerConnected: " + peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        Log.d("ListenerService", "onPeerDisconnected: " + peer);
    }

  @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.d("TAG","OnDataChanged Wear");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");

                  Bitmap bitmap = loadBitmapFromAsset(profileAsset);

                Intent messageIntent = new Intent();
                messageIntent.setAction(Intent.ACTION_SEND);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                messageIntent.putExtra("byteArray", bs.toByteArray());

               // messageIntent.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);

       //         ProductDetailFragment.setImage(bitmap);
                // Do something with the bitmap
            }
        }
    }


    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(30, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w("ListenerService", "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }




}
