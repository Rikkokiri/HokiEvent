package com.virginiatech.piraj.hokievent;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.IDN;
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
    private String eventAdd;
    private int eventStart;
    private int eventEnd;
    private String[] interests;

    public HokiEvent(int id, String name, String desc, String add, int start, int end, String[] tags)
    {
        eventID = id;
        eventName = name;
        eventDesc = desc;
        eventAdd = add;
        eventStart = start;
        eventEnd = end;
        interests = tags;
    }

    protected HokiEvent(Parcel in) {
        eventID = in.readInt();
        eventName = in.readString();
        eventDesc = in.readString();
        eventAdd = in.readString();
        eventStart = in.readInt();
        eventEnd = in.readInt();
        interests = in.createStringArray();
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

    public String getEventAdd() {
        return eventAdd;
    }

    public int getEventStart() {
        return eventStart;
    }

    public int getEventEnd() {
        return eventEnd;
    }

    public String[] getInterests() {
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
        dest.writeString(eventAdd);
        dest.writeInt(eventStart);
        dest.writeInt(eventEnd);
        dest.writeStringArray(interests);
    }
}
