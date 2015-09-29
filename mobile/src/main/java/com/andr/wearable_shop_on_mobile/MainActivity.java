package com.andr.wearable_shop_on_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andr.wearable_shop_on_mobile.application.MobileApplication;
import com.andr.wearable_shop_on_mobile.googleservice.GoogleMessageServiceActivity;
import com.andr.wearable_shop_on_mobile.interfaces.ReceiveListener;
import com.andr.wearable_shop_on_mobile.webservicesparser.TransDBParser;

public class MainActivity extends AppCompatActivity implements ReceiveListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TransDBParser(MainActivity.this,MainActivity.this,"",-1,"Loading",false).execute("http://gtomobilecoe.cognizant.com/AiM/151510/productdeals");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void receiveResult(String result) {
        if(!TextUtils.isEmpty(result) && !result.equalsIgnoreCase("failure") ){
            Log.d("MainActivity", "Result:::" + result);
            MobileApplication.getInstance().setJson(result);
            Intent messageIntent = new Intent(MainActivity.this,GoogleMessageServiceActivity.class);
            startActivity(messageIntent);
        }
    }
}
