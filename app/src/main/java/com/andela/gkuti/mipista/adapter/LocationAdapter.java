package com.andela.gkuti.mipista.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.activity.HistoryActivity;
import com.andela.gkuti.mipista.model.Location;

import java.util.List;

/**
 * LocationAdapter class
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<Location> locationList;
    private Context context;

    /**
     * Constructor for LocationAdapter
     *
     * @param locationList arraylist of location
     * @param context      the activity context
     */
    public LocationAdapter(List<Location> locationList, Context context) {
        this.locationList = locationList;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view.findViewById(R.id.location);
                Intent intent = new Intent(context, HistoryActivity.class);
                intent.putExtra("location", textView.getText());
                context.startActivity(intent);
            }
        });
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.location.setText(location.getLocation());
        holder.hits.setText(location.getHits());
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return locationList.size();
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView location;
        private TextView hits;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(View itemView) {
            super(itemView);
            location = (TextView) itemView.findViewById(R.id.location);
            hits = (TextView) itemView.findViewById(R.id.hits);
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "Secrets.ttf");
            location.setTypeface(face);
        }
    }
}
