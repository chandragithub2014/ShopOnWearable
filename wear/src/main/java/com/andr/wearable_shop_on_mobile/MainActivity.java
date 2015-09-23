package com.andr.wearable_shop_on_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.Fragments.CatalogListFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity_layout);
      /*  final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/
        android.support.v7.widget.Toolbar toolbar = ( android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        WearableApplication.getInstance().setActivity(MainActivity.this);
     /*   mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);*/
        getFragmentManager().beginTransaction()
                .replace(R.id.framelayout, new CatalogListFragment())
                .commit();
    }
}
