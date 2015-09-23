package com.andr.wearable_shop_on_mobile.Adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.DTO.OrderData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.HashMap;
import java.util.List;


/**
 * Created by 245742 on 9/2/2015.
 */

public class OrderAdapter extends WearableListView.Adapter {

    private String[] mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<OrderData> dataset;
    private HashMap<String,String> orderHash;


/*public Adapter(Context context, String[] dataset) {
     mContext=context;
    mInflater=LayoutInflater.from(context);
    mDataset=dataset;
   }*/

    public OrderAdapter(Context context, List<OrderData> dataset) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        this.dataset = dataset;
    }
    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder
implements WearableListView.OnCenterProximityListener
{
        private TextView textView,item3;
   //     private ImageView mCircle;
        private ImageView  mCircle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            textView = (TextView) itemView.findViewById(R.id.name);
            item3  =  (TextView) itemView.findViewById(R.id.item3);
            mCircle = (ImageView) itemView.findViewById(R.id.circle);
            mCircle.setVisibility(View.GONE);
        }


   @Override
        public void onNonCenterPosition(boolean b) {
            mCircle.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
            textView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
            item3.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        }

        @Override
        public void onCenterPosition(boolean b) {
            mCircle.animate().scaleX(1f).scaleY(1f).alpha(1);
            textView.animate().scaleX(1f).scaleY(1f).alpha(1);
            item3.animate().scaleX(1f).scaleY(1f).alpha(1);
        }

    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        // retrieve the text view
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView viewItem2 = itemHolder.textView;
        viewItem2.setText("Order ID "+"\t"+"\t"+dataset.get(position).getOrderId()
/*mDataset[position]*/
);
        TextView viewItem3 = itemHolder.item3;
        viewItem3.setText("Cost(in Rs.)"+"\t"+"\t"+dataset.get(position).getOrderCost()
/*mDataset[position]*/
);
  /*    ImageView imageView = itemHolder.mCircle;
        // replace text contents
         String imageURL = dataset.get(position).getItem1();
        imageView.setImageResource(R.drawable.ic_launcher);*/
 /*       if(!TextUtils.isEmpty(imageURL)){
            if(imageURL.contains("http://")
                    || imageURL.contains("https://")) {
                imageView.setImageResource(R.drawable.ic_launcher);
                imageView.setImageUrl(imageURL);
//								imageView.setScaleType(ScaleType.CENTER);
            }else{
                if(imageURL.contains("."))
                    imageURL = imageURL.substring(0,imageURL.indexOf("."));
                int id = mContext.getResources().getIdentifier(imageURL,
                        "drawable", mContext.getPackageName());
                if (id > 0) {
                    imageView.setImageResource(mContext.getResources()
                            .getIdentifier(imageURL, "drawable",
                                    mContext.getPackageName()));
                }
            }
        }*/

        // replace list item's metadata
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate our custom layout for list items
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item_anim, null));
    }


}

