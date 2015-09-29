package com.andr.wearable_shop_on_mobile.Adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.DTO.OrderDetailData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.HashMap;
import java.util.List;


/**
 * Created by 245742 on 9/2/2015.
 */

public class OrderDetailAdapter extends WearableListView.Adapter {

    private String[] mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<OrderDetailData> dataset;
    private HashMap<String,String> orderHash;


/*public Adapter(Context context, String[] dataset) {
     mContext=context;
    mInflater=LayoutInflater.from(context);
    mDataset=dataset;
   }*/

    public OrderDetailAdapter(Context context, List<OrderDetailData> dataset) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        this.dataset = dataset;
    }
    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder
implements WearableListView.OnCenterProximityListener
{
        private TextView name,cost,qty;
   //     private ImageView mCircle;
        private ImageView  mCircle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            name = (TextView) itemView.findViewById(R.id.order_prodname);
            cost  =  (TextView) itemView.findViewById(R.id.order_prodcost);
            qty  =  (TextView) itemView.findViewById(R.id.order_prodQty);
        }


   @Override
        public void onNonCenterPosition(boolean b) {
        //    mCircle.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
       name.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
       cost.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
       qty.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        }

        @Override
        public void onCenterPosition(boolean b) {
         //   mCircle.animate().scaleX(1f).scaleY(1f).alpha(1);
            name.animate().scaleX(1f).scaleY(1f).alpha(1);
            cost.animate().scaleX(1f).scaleY(1f).alpha(1);
            qty.animate().scaleX(1f).scaleY(1f).alpha(1);
        }

    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        // retrieve the text view
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView name = itemHolder.name;
        TextView cost = itemHolder.cost;
        TextView qty = itemHolder.qty;

        name.setText(dataset.get(position).getProductName()
/*mDataset[position]*/
);

        cost.setText("Cost(in Rs.)"+"\t"+"\t"+dataset.get(position).getProductPrice());
                qty.setText("Qty"+"\t"+"\t"+dataset.get(position).getProductQty());
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
        return new ItemViewHolder(mInflater.inflate(R.layout.order_detail_row, null));
    }


}

