package com.andr.wearable_shop_on_mobile.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.Adapters.OrderDetailAdapter;
import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.DTO.OrderDetailData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String orderId;
    private String mParam2;
    private  WearableListView listView;
    int mContainerId = -1;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(String param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.wearable_inset_list, container, false);
        mContainerId = container.getId();
        listView =
                (WearableListView) view.findViewById(R.id.wearable_list);
        listView.setGreedyTouchMode(true);

        Toolbar mToolBar = (Toolbar)getActivity().findViewById(R.id.toolbar);
      TextView  toolBarTitle = (TextView)mToolBar.findViewById(R.id.title);
        toolBarTitle.setText("Order Details");
      TextView  checkOut = (TextView)mToolBar.findViewById(R.id.checkout);
        checkOut.setVisibility(View.GONE);
        ImageView back_img = (ImageView)mToolBar.findViewById(R.id.back);
        back_img.setVisibility(View.VISIBLE);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(mContainerId, new OrderListFragment()).addToBackStack(null).commit();
            }
        });

        HashMap<String,List<OrderDetailData>> orderDetailHashh =  WearableApplication.getInstance().getOrderDetailHashh();
        List<OrderDetailData> orderDetailList = orderDetailHashh.get(orderId);

        if(orderDetailList!=null && orderDetailList.size()>0){

                listView.setAdapter(new OrderDetailAdapter(getActivity(), orderDetailList)); // Set the adapter

        }


      /*  HashMap<String, HashMap<String, HashMap<String, Integer>>> orderDetailHash = WearableApplication.getInstance().getOrderDetailHash();
        HashMap<String, HashMap<String, Integer>> orderDetails = orderDetailHash.get(orderId);
        if(orderDetails!=null && orderDetails.size()>0) {
           List<OrderDetailData> orderDetailList = constructOrderDetail(orderDetails);
            if(orderDetailList!=null && orderDetailList.size()>0){
                if(orderDetailList.size()>0){
                    listView.setAdapter(new OrderDetailAdapter(getActivity(), orderDetailList)); // Set the adapter
                }
            }
        }
*/
         return view;
    }


    private List<OrderDetailData> constructOrderDetail( HashMap<String, HashMap<String, Integer>> orderDetails ){

            List<OrderDetailData> orderDetailList = new ArrayList<OrderDetailData>();
            Set<String> keySet = orderDetails.keySet();
            Iterator<String> keySetIterator = keySet.iterator();
            while (keySetIterator.hasNext()) {


                System.out.println("------------------------------------------------");
                System.out.println("Iterating Map in Java using KeySet Iterator");
                String key = keySetIterator.next();
                HashMap<String, Integer> innerMap = orderDetails.get(key);
                Set<String> innerkeySet = innerMap.keySet();
                Iterator<String> innerkeySetIterator = innerkeySet.iterator();
                String innerKey = "";
                int qty = -1;
                while (innerkeySetIterator.hasNext()) {
                    innerKey = innerkeySetIterator.next();
                    qty = innerMap.get(innerKey);
                }
                System.out.println("key: " + key + " value: " + innerKey + " " + qty);
                OrderDetailData temp = new OrderDetailData();
                temp.setProductName(key);
                temp.setProductPrice(innerKey);
                temp.setProductQty("" + qty);
                orderDetailList.add(temp);
            }
       return orderDetailList;
    }
}
