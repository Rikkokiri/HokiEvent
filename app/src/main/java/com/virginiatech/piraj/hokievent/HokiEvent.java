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
    private String eventStartDate;
    private String eventStartTime;
    private String eventEndDate;
    private String eventEndTime;
    private String interests;
    private String ownerEmail;


    public static final String EVENT = "event";

    public HokiEvent(String name, String desc, String loc, String startDate, String startTime, String endTime, String endDate, String tags)
    {
        this(name, desc, loc, startTime, startDate, tags );

        eventEndTime = endTime;
        eventEndDate = endDate;
    }

    public HokiEvent(String name, String desc, String loc, String date, String time, String tags)
    {
        eventName = name;
        eventDesc = desc;
        eventLoc = loc;
        eventStartTime = time;
        eventStartDate = date;
        interests = tags;
    }

    protected HokiEvent(Parcel in) {
        eventID = in.readInt();
        eventName = in.readString();
        eventDesc = in.readString();
        eventLoc = in.readString();
        eventStartDate = in.readString();
        eventStartTime = in.readString();
        eventEndDate = in.readString();
        eventEndTime = in.readString();
        interests = in.readString();
        ownerEmail = in.readString();
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

    public void setEventID(int newID) {  eventID = newID; }

    public String getEventName() {
        return eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEventLoc() {
        return eventLoc;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public String getEventEndDate() { return eventEndDate; }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public String getEventEndTime() { return eventEndTime; }

    public String getInterests() {
        return interests;
    }

    public void setEventEndDate(String endDate){
        this.eventEndDate = endDate;
    }

    public void setEventEndTime(String endTime){
        this.eventEndTime = endTime;
    }

    public void setOwnerEmail(String ownerEmail){
        this.ownerEmail = ownerEmail;
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
        dest.writeString(eventStartDate);
        dest.writeString(eventStartTime);
        dest.writeString(eventEndDate);
        dest.writeString(eventEndTime);
        dest.writeString(interests);
        dest.writeString(ownerEmail);
    }
}
