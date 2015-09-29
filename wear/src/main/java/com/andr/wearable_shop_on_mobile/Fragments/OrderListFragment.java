package com.andr.wearable_shop_on_mobile.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andr.wearable_shop_on_mobile.Adapters.OrderAdapter;
import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.DTO.OrderData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderListFragment#
 * create an instance of this fragment.
 */
public class OrderListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static WearableListView listView;
    int mContainerId = -1;

    static TextView toolBarTitle;
    static TextView checkOut;


    // TODO: Rename and change types and number of parameters
  /*  public static OrderListFragment newInstance(String param1, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.wearable_inset_list, container, false);

        mContainerId = container.getId();
        listView =
                (WearableListView) view.findViewById(R.id.wearable_list);
        listView.setGreedyTouchMode(true);
        listView.setClickListener(mClickListener);
        Toolbar mToolBar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolBarTitle = (TextView)mToolBar.findViewById(R.id.title);
        checkOut = (TextView)mToolBar.findViewById(R.id.checkout);
        ImageView back_img = (ImageView)mToolBar.findViewById(R.id.back);
        back_img.setVisibility(View.VISIBLE);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(mContainerId, new CatalogListFragment()).addToBackStack(null).commit();
            }
        });
        populateOrderList(getActivity());
        return  view;
    }


    public void populateOrderList(Context ctx) {
        toolBarTitle.setText("Orders");
        checkOut.setVisibility(View.GONE);
        List<OrderData> orderDataList = new ArrayList<OrderData>();
        if(WearableApplication.getInstance().getOrderMap().size()>0) {
            HashMap<String, String> orderHash = WearableApplication.getInstance().getOrderMap();
            Set<String> keySet = orderHash.keySet();
            Iterator<String> keySetIterator = keySet.iterator();
            while (keySetIterator.hasNext()) {
                String key   = keySetIterator.next();
                String value  = orderHash.get(key);
                OrderData orderData = new OrderData();
                orderData.setOrderId(key);
                orderData.setOrderCost(value);
                orderDataList.add(orderData);
            }

            if(orderDataList.size()>0){
                listView.setAdapter(new OrderAdapter(ctx, orderDataList)); // Set the adapter
            }
        }

    }


    // Handle our Wearable List's click events
    private WearableListView.ClickListener mClickListener =
            new WearableListView.ClickListener() {
                @Override
                public void onClick(WearableListView.ViewHolder viewHolder) {
                 /*  Toast.makeText(getActivity(),
                            String.format("You selected item #%s",
                                    viewHolder.getLayoutPosition() + 1),
                            Toast.LENGTH_SHORT).show();*/

                    Toast.makeText(getActivity(),
                            "Order Details",
                            Toast.LENGTH_SHORT).show();


                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    View v = viewHolder.itemView;
                    TextView name = (TextView)v.findViewById(R.id.name);
           //         TextView item2 = (TextView)v.findViewById(R.id.item3);

                    String itemName = name.getText().toString();
                   // String item3 =  item2.getText().toString();


                    fragmentTransaction.replace(mContainerId, OrderDetailFragment.newInstance(itemName,"")).addToBackStack(null).commit();


                }

                @Override
                public void onTopEmptyRegionClick() {
                    Toast.makeText(getActivity(),
                            "Top empty area tapped", Toast.LENGTH_SHORT).show();
                }
            };

}
