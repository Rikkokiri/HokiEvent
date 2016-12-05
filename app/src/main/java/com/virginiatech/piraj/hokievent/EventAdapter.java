package com.virginiatech.piraj.hokievent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kaboodles on 12/5/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<HokiEvent> ownedEventList;
    private List<HokiEvent> savedEventList;

    @Override
    public int getItemCount() {
        return ownedEventList.size();
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        HokiEvent event = ownedEventList.get(i);
        eventViewHolder.eventName.setText(event.getEventName());
        eventViewHolder.eventDate.setText(event.getEventStart());
        eventViewHolder.eventTags.setText(event.getInterests());
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new EventViewHolder(itemView);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        protected TextView eventName;
        protected TextView eventDate;
        protected TextView eventTags;

        public EventViewHolder(View v) {
            super(v);
            eventName = (TextView) v.findViewById(R.id.cardEventName);
            eventDate = (TextView) v.findViewById(R.id.cardEventDate);
            eventTags = (TextView) v.findViewById(R.id.cardEventTags);

        }
    }
}
