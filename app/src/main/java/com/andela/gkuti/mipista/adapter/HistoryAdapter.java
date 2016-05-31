package com.andela.gkuti.mipista.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.model.History;

import java.util.List;

/**
 * HistoryAdapter class
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<History> historyList;

    /**
     * Constructor for HistoryAdapter class
     *
     * @param historyList arraylist of history
     */
    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_data_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.startTime.setText(history.getStartTime());
        holder.endTime.setText(history.getEndTime());
        holder.duration.setText(history.getDuration());
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView startTime;
        private TextView endTime;
        private TextView duration;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(View itemView) {
            super(itemView);
            startTime = (TextView) itemView.findViewById(R.id.time_from);
            endTime = (TextView) itemView.findViewById(R.id.time_to);
            duration = (TextView) itemView.findViewById(R.id.duration);
        }
    }
}
