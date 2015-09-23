package com.andr.wearable_shop_on_mobile.Adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.DTO.ListData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.List;

/**
 * Created by CHANDRASAIMOHAN on 9/18/2015.
 */
public class Adapter extends WearableListView.Adapter {

    private String[] mDataset;
    private final Context mContext;// chandra
    private final LayoutInflater mInflater;
    private List<ListData> dataset;


/*public Adapter(Context context, String[] dataset) {
     mContext=context;
    mInflater=LayoutInflater.from(context);
    mDataset=dataset;
   }*/

    public Adapter(Context context, List<ListData> dataset) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        this.dataset =dataset;
    }
    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder
            implements WearableListView.OnCenterProximityListener
    {
        private TextView textView,item3;
        //     private ImageView mCircle;
        private ImageView mCircle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            textView = (TextView) itemView.findViewById(R.id.name);
            item3  =  (TextView) itemView.findViewById(R.id.item3);
            mCircle = (ImageView) itemView.findViewById(R.id.circle);
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
        viewItem2.setText(dataset.get(position).getItem2()
/*mDataset[position]*/
        );
        TextView viewItem3 = itemHolder.item3;
        viewItem3.setText(dataset.get(position).getItem3()
/*mDataset[position]*/
        );
        ImageView imageView = itemHolder.mCircle;
        // replace text contents
        String imageURL = dataset.get(position).getItem1();
        imageView.setImageResource(R.drawable.ic_launcher);
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

