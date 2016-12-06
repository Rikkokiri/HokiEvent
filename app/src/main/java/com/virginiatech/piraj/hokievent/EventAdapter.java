package com.virginiatech.piraj.hokievent;

import android.content.Intent;
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

    private List<HokiEvent> eventList;

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView eventName;
        protected TextView eventDate;
        protected TextView eventTags;
        private HokiEvent event;

        private static final String EVENT_KEY = "event";

        public EventViewHolder(View v) {
            super(v);
            eventName = (TextView) v.findViewById(R.id.cardEventName);
            eventDate = (TextView) v.findViewById(R.id.cardEventDate);
            eventTags = (TextView) v.findViewById(R.id.cardEventTags);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent startEventDetailsActivity = new Intent(view.getContext(), EventDetailsActivity.class);
        }
    }

    public EventAdapter(List<HokiEvent> events)
    {
        eventList = events;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        HokiEvent event = eventList.get(i);
        eventViewHolder.eventName.setText(event.getEventName());
        eventViewHolder.eventDate.setText(event.getEventStartDate());
        eventViewHolder.eventTags.setText(event.getInterests());
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new EventViewHolder(itemView);
    }


}
