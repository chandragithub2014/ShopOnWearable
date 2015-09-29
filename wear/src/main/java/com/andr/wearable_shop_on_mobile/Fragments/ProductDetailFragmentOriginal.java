package com.andr.wearable_shop_on_mobile.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.DTO.ListData;
import com.andr.wearable_shop_on_mobile.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailFragmentOriginal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailFragmentOriginal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragmentOriginal extends Fragment implements MessageApi.MessageListener,View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,DataApi.DataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String productName;
    private String mParam2;
    private String productDescription,prodPrice;

    TextView somProductName;
    EditText somProdDescrition;
    TextView somProductPrice;
    private OnFragmentInteractionListener mListener;
    int mContainerId = -1;

    private static final long CONNECTION_TIME_OUT_MS = 100;
    private static final String MOBILE_PATH = "/mobile";
    private static final String URL_PATH = "/url_path";

    private GoogleApiClient googleApiClient;
    private String nodeId;
    String  imageURL="";
     ImageView productImage;

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.prodImg){
            if (nodeId != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                            googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);

                        MessageApi.SendMessageResult result =  Wearable.MessageApi.sendMessage(googleApiClient, nodeId, URL_PATH, imageURL.getBytes()).await();
                        Log.d("ProductDetails","Result:::"+result.getStatus().isSuccess());
                        if (result.getStatus().isSuccess()) {
                            Log.d("onClick", "isSuccess is true");
                        } else {
                            Log.d("onClick", "isSuccess is false");
                        }

                          googleApiClient.disconnect();

                        //     Log.d("Product Detail Fragment","nodeID::::"+nodeId);
                    }
                }).start();
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragmentOriginal newInstance(String param1) {
        ProductDetailFragmentOriginal fragment = new ProductDetailFragmentOriginal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
    //    args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragmentOriginal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productName = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initGoogleApiClient();

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, messageFilter);
    }

    private void initGoogleApiClient(){
        googleApiClient = getGoogleApiClient(getActivity());
        retrieveDeviceNode();
    }

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }


    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
                    googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);

                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();

                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    nodeId = nodes.get(0).getId();

                Log.v("ProductDetailFragment", "Node ID of phone: " + nodeId);

                googleApiClient.disconnect();
            }
        }).start();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      /*  TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);*/

        View view = inflater.inflate(R.layout.product_detail_layout, container, false);
        mContainerId = container.getId();
        Toolbar mToolBar = (Toolbar)getActivity().findViewById(R.id.toolbar);
      TextView  toolBarTitle = (TextView)mToolBar.findViewById(R.id.title);
        TextView checkOut = (TextView)mToolBar.findViewById(R.id.checkout);
        checkOut.setVisibility(View.VISIBLE);
        checkOut.setText("BUY");
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeToBasket();
            }
        });
        productImage = (ImageView)view.findViewById(R.id.prodImg);

        productImage.setOnClickListener(this);
        toolBarTitle.setText(productName);
        HashMap<String,ListData> multiValProductHash = WearableApplication.getInstance().getProductMultiValHash();
        ListData productData = multiValProductHash.get(productName);
        somProductName = (TextView)view.findViewById(R.id.productName);
        somProdDescrition = (EditText)view.findViewById(R.id.productDesc);
        somProductName.setText(productName);
        somProdDescrition.setText(productData.getProductDescription());
        somProductPrice = (TextView)view.findViewById(R.id.prod_price);
        somProductPrice.setText("Rs."+productData.getItem3());
        prodPrice = productData.getItem3(); // price
          imageURL = productData.getImageurl();
        ImageView back_img = (ImageView)mToolBar.findViewById(R.id.back);
        back_img.setVisibility(View.VISIBLE);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(mContainerId, new ListScreenFragment()).addToBackStack(null).commit();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
Log.d("Product DetailsFragment","OnResume()");

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.d("TAG","OnDataChanged()......");
    }

    private void storeToBasket(){
        if (WearableApplication.getInstance().getBasketMap().get(productName) != null) {
            WearableApplication.getInstance().setListType("Basket");
            HashMap<String, Integer> innerHashMap = WearableApplication.getInstance().getBasketMap().get(productName);
            int qty = innerHashMap.get(prodPrice);
            qty = qty + 1;
            innerHashMap.put(prodPrice, qty);
            HashMap<String, HashMap<String, Integer>> basket = WearableApplication.getInstance().getBasketMap();
            basket.remove(productName);
            basket.put(productName, innerHashMap);
            WearableApplication.getInstance().setBasketMap(basket);
            Toast.makeText(getActivity(),
                    "Added to Basket",
                    Toast.LENGTH_SHORT).show();
        } else {
            WearableApplication.getInstance().setListType("Basket");
            HashMap<String, HashMap<String, Integer>> basketMap = WearableApplication.getInstance().getBasketMap();//new HashMap<String, HashMap<String, Integer>>();
            HashMap<String, Integer> innerHashMap = new HashMap<String, Integer>();
            innerHashMap.put(prodPrice, 1);
            basketMap.put(productName, innerHashMap);
            WearableApplication.getInstance().setBasketMap(basketMap);
            Toast.makeText(getActivity(),
                    "Added to Basket",
                    Toast.LENGTH_SHORT).show();
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

/*    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        Log.d("TAG","OnDataChanged Wear ProductDetailFragment");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");

                Bitmap bitmap = loadBitmapFromAsset(profileAsset);

              setImage(bitmap);
                // Do something with the bitmap
            }
        }
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("ProductDetailFragment", "Connected to Google Api Service");

  //  Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onStop() {
        if (null != googleApiClient && googleApiClient.isConnected()) {
          //  Wearable.DataApi.removeListener(googleApiClient, this);
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                googleApiClient.blockingConnect(30, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                googleApiClient, asset).await().getInputStream();
        googleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w("ListenerService", "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }
    public  void  setImage(Bitmap name){
        Log.d("TAG","From Listener"+name);
        productImage.setImageBitmap(name);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
          //  String message = intent.getStringExtra("message");
            //Log.v("myTag", "Main activity received message: " + message);


            //
            if(intent.hasExtra("byteArray")) {

                Bitmap b = BitmapFactory.decodeByteArray(
                        intent.getByteArrayExtra("byteArray"),0,intent.getByteArrayExtra("byteArray").length);
                productImage.setImageBitmap(b);
            }
            //
            // Display message in UI
          //  mTextView.setText(message);
        }
    }
}
