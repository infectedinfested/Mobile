package com.example.kevin.mobile.Adaptors;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.mobile.Collectors.DatabaseHelper;
import com.example.kevin.mobile.Models.Item;
import com.example.kevin.mobile.R;

import java.util.ArrayList;

/**
 * Created by kevin on 12/03/2018.
 */

public class ItemAdaptor extends RecyclerView.Adapter<ItemAdaptor.ItemViewHolder>{
    private ArrayList<Item> mItems;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdaptor(Context context, ArrayList<Item> items) {
        this.mContext = context;
        this.mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (mItems.isEmpty())
            return;
        Item currentItem = mItems.get(position);

        if (currentItem.getWatch()>0){
            holder.watchImageView.setImageResource(R.drawable.fav);
        }else{
            holder.watchImageView.setImageResource(R.drawable.nofav);
        }

        //holder.nameTextView.setText("ID: "+ array.getJSONArray("id").get(position));
        holder.nameTextView.setText(currentItem.getName());
        holder.itemView.setTag(currentItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mItems == null){
            return 0;
        }
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Toast mToast;


        ImageView watchImageView;


        TextView nameTextView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            watchImageView = itemView.findViewById(R.id.iv_watch);
            nameTextView = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Item item = mItems.get(clickedPosition);
            item.setWatch(item.getWatch()+1);
            if (item.getWatch()>5)
                item.setWatch(0);


            switch (item.getWatch()) {
                case 0: showToast("off");
                    break;
                case 1:  showToast("10%");
                    break;
                case 2:  showToast("20%");
                    break;
                case 3:  showToast("30%");
                    break;
                case 4:  showToast("40%");
                    break;
                case 5:  showToast("50%");
                    break;
                default: ;
                    break;
            }
            adjustItem(item);
            if (item.getWatch()>0){
                watchImageView.setImageResource(R.drawable.fav);
            }else{
                watchImageView.setImageResource(R.drawable.nofav);
            }
        }

        private void adjustItem(Item item){
            DatabaseHelper db = new DatabaseHelper(mContext);
            item.setOriginSell(item.getSell());
            item.setOriginBuy(item.getBuy());
            db.updateItem(item);
        }

        private void showToast(String text){
            if(mToast != null){
                mToast.cancel();
            }

            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
            mToast.show();
        }

    }
}
