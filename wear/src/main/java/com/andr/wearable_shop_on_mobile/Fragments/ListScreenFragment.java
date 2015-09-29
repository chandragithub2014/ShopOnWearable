package com.andr.wearable_shop_on_mobile.Fragments;

/*import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;*/

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andr.wearable_shop_on_mobile.Adapters.Adapter;
import com.andr.wearable_shop_on_mobile.Application.WearableApplication;
import com.andr.wearable_shop_on_mobile.DTO.ListData;
import com.andr.wearable_shop_on_mobile.Parsers.WearJsonParser;
import com.andr.wearable_shop_on_mobile.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;*/


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
//import android.support.wearable.R;
public class ListScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListAdapter mAdapter;
    List<ListData> jsonParsedData = null;
    int mContainerId = -1;
    static WearableListView listView ;
    //private OnFragmentInteractionListener mListener;

    static TextView  toolBarTitle;
    static TextView checkOut;
/**
 * The fragment's ListView/GridView.
 *//*

    private AbsListView mListView;

    */
/**
 * The Adapter which will be used to populate the ListView/GridView with
 * Views.
 *//*



    // TODO: Rename and change types of parameters
    public static ListScreenFragment newInstance(String param1, String param2) {
        ListScreenFragment fragment = new ListScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ListScreenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content

/*   mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);*/

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ListFragment", "onResume()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wearable_inset_list, container, false);
        Log.d("ListFragment","onCreateView()");
        mContainerId = container.getId();
         listView =
                (WearableListView) view.findViewById(R.id.wearable_list);
        listView.setGreedyTouchMode(true);
        listView.setClickListener(mClickListener);
        listView.smoothScrollToPosition(1);
        Toolbar mToolBar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolBarTitle = (TextView)mToolBar.findViewById(R.id.title);
        checkOut = (TextView)mToolBar.findViewById(R.id.checkout);
        checkOut.setVisibility(View.GONE);
        toolBarTitle.setText("Products");
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
        populateList(getActivity());

      /*  mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);*/


        // Set OnItemClickListener so we can be notified on item clicks
    //    mListView.setOnItemClickListener(getActivity());

      /*  listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CardFragment cardFragment = CardFragment.create("Basket",
                        "Added"+v.getTransitionName(),
                        R.drawable.ic_launcher);
                fragmentTransaction.add(R.id.frame_layout, cardFragment);
                fragmentTransaction.commit();
            }
        });*/

        return view;
    }

    private void populateList(Context context){
        Log.d("ListScreenFragment", "populateList()");
    //    if(!WearableApplication.getInstance().getListType().equalsIgnoreCase("Basket")) {
            String productJSON = "";
            if (!TextUtils.isEmpty(WearableApplication.getInstance().getJson())) {
                productJSON = WearableApplication.getInstance().getJson();
            }else {
                productJSON = WearJsonParser.getInstance().getJSON("listdata", context);
            }
            if (!TextUtils.isEmpty(productJSON)) {
                JSONObject json = WearJsonParser.getInstance().initializeJSON(productJSON, context);
                if (json != null) {
                    JSONArray listjsonArray = WearJsonParser.getInstance().getSelectedCategoryJSONArray("productdeals", json);
                    if (listjsonArray != null) {
                        jsonParsedData = WearJsonParser.getInstance().getParsedListInfo(listjsonArray);
                    }
                }
            }
            // Assign an adapter to the list
            if (jsonParsedData != null && jsonParsedData.size() > 0) {
                //  Adapter listAdapter = new Adapter(getActivity(), jsonParsedData);
                listView.setAdapter(new Adapter(context, jsonParsedData)); // Set the adapter
            }
        //}
/*        else{  //basket
            if(WearableApplication.getInstance().getBasketMap()!=null) {
                List<ListData> jsonParseData = new ArrayList<ListData>();
                listView.setAdapter(new Adapter(context, jsonParseData));
                traverseBasketMap();

            }
        }*/
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
                           "Product Details",
                            Toast.LENGTH_SHORT).show();


                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    View v = viewHolder.itemView;
                    TextView name = (TextView)v.findViewById(R.id.name);
                    TextView item2 = (TextView)v.findViewById(R.id.item3);

                    String itemName = name.getText().toString();
                    String item3 =  item2.getText().toString();


                    fragmentTransaction.replace(mContainerId, ProductDetailFragment.newInstance(itemName)).addToBackStack(null).commit();


                }

                @Override
                public void onTopEmptyRegionClick() {
                    Toast.makeText(getActivity(),
                            "Top empty area tapped", Toast.LENGTH_SHORT).show();
                }
            };

  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardFragment cardFragment = CardFragment.create("Basket",
                "Added"+view.getTransitionName(),
                R.drawable.ic_launcher);
        fragmentTransaction.add(R.id.frame_layout, cardFragment);
        fragmentTransaction.commit();
    }*/

    /*   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*//*

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

  */
/*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }
*//*

    */
/**
 * The default content for this Fragment has a TextView that is shown when
 * the list is empty. If you would like to change the text, call this method
 * to supply the text it should use.
 *//*

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    */
/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 *//*

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
*/

    private void traverseBasketMap(){
        HashMap<String,HashMap<String,Integer>> basketMap = WearableApplication.getInstance().getBasketMap();

        Set<String> keySet = basketMap.keySet();
        Iterator<String> keySetIterator = keySet.iterator();
        while (keySetIterator.hasNext()) {
            System.out.println("------------------------------------------------");
            System.out.println("Iterating Map in Java using KeySet Iterator");
            String key = keySetIterator.next();
            HashMap<String,Integer> innerMap = basketMap.get(key);
            Set<String> innerkeySet = innerMap.keySet();
            Iterator<String> innerkeySetIterator = innerkeySet.iterator();
            String innerKey="";
            int qty=-1;
            while(innerkeySetIterator.hasNext()){
                innerKey = innerkeySetIterator.next();
                qty = innerMap.get(innerKey);
            }
            System.out.println("key: " + key + " value: " + innerKey +" " +qty);
        }


        //  Read more: http://javarevisited.blogspot.com/2011/12/how-to-traverse-or-loop-hashmap-in-java.html#ixzz3lH0RpeWg
    }

    public  void  refreshFragment(Context ctx){
        Log.d("ListFragment", "FragmentCalledonSwipe");
        toolBarTitle.setText("Products");
        checkOut.setVisibility(View.GONE);
        populateList(ctx);
    }



    }
