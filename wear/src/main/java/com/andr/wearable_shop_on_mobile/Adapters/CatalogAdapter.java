package com.andr.wearable_shop_on_mobile.Adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andr.wearable_shop_on_mobile.DTO.CatalogData;
import com.andr.wearable_shop_on_mobile.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by CHANDRASAIMOHAN on 9/23/2015.
 */
public class CatalogAdapter extends WearableListView.Adapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<CatalogData> dataset;
    private HashMap<String,String> orderHash;

    public CatalogAdapter(Context context, List<CatalogData> dataset) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        this.dataset = dataset;
    }


    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder
            /*implements WearableListView.OnCenterProximityListener*/
    {
        private TextView textView,item3;
        //     private ImageView mCircle;
        private ImageView mCircle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            textView = (TextView) itemView.findViewById(R.id.catalog_name);
            mCircle = (ImageView) itemView.findViewById(R.id.catalog_image);
            //  mCircle.setVisibility(View.GONE);
        }


       /* @Override
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
*/
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView name = itemHolder.textView;
        name.setText(dataset.get(position).getCatalogName());
        String imageName = dataset.get(position).getCatalogImage();
        ImageView iamge = itemHolder.mCircle;
   //     iamge.setImageResource(R.drawable.ic_launcher);

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        iamge.setImageResource(drawableResourceId);
/*mDataset[position]*/

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.catalog_item, null));
    }


}
