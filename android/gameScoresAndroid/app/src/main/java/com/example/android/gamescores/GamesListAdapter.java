package com.example.android.gamescores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.GamesListViewHolder>{
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with waitlist data to display
     */
    public GamesListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public GamesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.games_list_item, parent, false);
        return new GamesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GamesListViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String gameType = null;
        gameType = mCursor.getString(mCursor.getColumnIndex("gameType"));


        String rankingAsString = mCursor.getString(mCursor.getColumnIndex("ranking"));
        JSONObject rankings;

        ArrayList<String> players = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();

       try {
           rankings = new JSONObject(rankingAsString);

           Iterator<String> iter = rankings.keys();
           while (iter.hasNext()) {
               String key = iter.next();
               try {
                   Integer value = rankings.getInt(key);
                   players.add(key);
                   scores.add(value);

               } catch (JSONException e) {
                   // Something went wrong!
               }
           }
       } catch (JSONException e){
           // Something went wrong!
       }

       if(players.size() >= 1){
           holder.pos1TextView.setText(players.get(0) + "\n" + scores.get(0));
           holder.pos1TextView.setVisibility(View.VISIBLE);
       }
        if(players.size() >= 2){
            holder.pos2TextView.setText(players.get(1) + "\n" + scores.get(1));
            holder.pos2TextView.setVisibility(View.VISIBLE);
        }
        if(players.size() >= 3){
            holder.pos3TextView.setText(players.get(2) + "\n" + scores.get(2));
            holder.pos3TextView.setVisibility(View.VISIBLE);
        }
        if(players.size() >= 4){
            holder.pos4TextView.setText(players.get(3) + "\n" + scores.get(3));
            holder.pos4TextView.setVisibility(View.VISIBLE);
        }
        if(players.size() >= 5){
            holder.pos5TextView.setText(players.get(4) + "\n" + scores.get(4));
            holder.pos5TextView.setVisibility(View.VISIBLE);
        }

        // Display the gamesList name
        holder.nameTextView.setText(gameType);
        // Display the party count
        // COMPLETED (7) Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(mCursor.getString(mCursor.getColumnIndex("id")));

    }


    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GamesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Toast mToast;

        // Will display the gamesList name
        TextView pos1TextView;
        TextView pos2TextView;
        TextView pos3TextView;
        TextView pos4TextView;
        TextView pos5TextView;

        // Will display the party size number
        TextView nameTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GamesListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GamesListViewHolder(View itemView) {
            super(itemView);
            pos1TextView = (TextView) itemView.findViewById(R.id.tv_pos1);
            pos2TextView = (TextView) itemView.findViewById(R.id.tv_pos2);
            pos3TextView = (TextView) itemView.findViewById(R.id.tv_pos3);
            pos4TextView = (TextView) itemView.findViewById(R.id.tv_pos4);
            pos5TextView = (TextView) itemView.findViewById(R.id.tv_pos5);

            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();

            if(mToast != null){
                mToast.cancel();
            }

            String toastMessage = "Item  #" + clickedPosition + " clicked.";
            mToast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG);
            mToast.show();
        }

    }


}