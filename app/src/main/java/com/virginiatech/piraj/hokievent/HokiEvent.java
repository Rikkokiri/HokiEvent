package com.virginiatech.piraj.hokievent;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.IDN;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kaboodles on 12/4/2016.
 *
 * Basic event item
 */

public class HokiEvent implements Parcelable {
    private int eventID;
    private String eventName;
    private String eventDesc;
    private String eventLoc;
    private String eventStart;
    private String eventEnd;
    private String interests;

    public static final String EVENT = "event";

    public HokiEvent(int id, String name, String desc, String loc, String start, String end, String tags)
    {
        eventID = id;
        eventName = name;
        eventDesc = desc;
        eventLoc = loc;
        eventStart = start;
        eventEnd = end;
        interests = tags;
    }

    protected HokiEvent(Parcel in) {
        eventID = in.readInt();
        eventName = in.readString();
        eventDesc = in.readString();
        eventLoc = in.readString();
        eventStart = in.readString();
        eventEnd = in.readString();
        interests = in.readString();
    }

    public static final Creator<HokiEvent> CREATOR = new Creator<HokiEvent>() {
        @Override
        public HokiEvent createFromParcel(Parcel in) {
            return new HokiEvent(in);
        }

        @Override
        public HokiEvent[] newArray(int size) {
            return new HokiEvent[size];
        }
    };

    public int getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEventLoc() {
        return eventLoc;
    }

    public String getEventStart() {
        return eventStart;
    }

    public String getEventEnd() { return eventEnd; }

    public String getInterests() {
        return interests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventID);
        dest.writeString(eventName);
        dest.writeString(eventDesc);
        dest.writeString(eventLoc);
        dest.writeString(eventStart);
        dest.writeString(eventEnd);
        dest.writeString(interests);
    }
}
