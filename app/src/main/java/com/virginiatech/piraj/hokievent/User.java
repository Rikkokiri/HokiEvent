package com.virginiatech.piraj.hokievent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kaboodles on 12/4/2016.
 */

public class User implements Parcelable{

    private int userID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userEmail;
    private int phoneNumber;
    private String password;
    private String interests;
    private String ownEvents;
    private String savedEvents;

    public static final String USER = "user";




    public User(String first, String middle, String last, String email, int phone, String newInterests, String pass) {
        this(first, middle, last, email, phone, newInterests);
        password = pass;
    }


    public User(String first, String middle, String last, String email, int phone, String newInterests) {
        firstName = first;
        lastName = last;
        userEmail = email;
        phoneNumber = phone;
        interests = newInterests;

    }

    protected User(Parcel in) {
        userID = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        userEmail = in.readString();
        password = in.readString();
        phoneNumber = in.readInt();
        interests = in.readString();
        ownEvents = in.readString();
        savedEvents = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userEmail);
        dest.writeString(password);
        dest.writeInt(phoneNumber);
        dest.writeString(interests);
        dest.writeString(ownEvents);
        dest.writeString(savedEvents);


    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {

            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() { return middleName; }

    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getOwnEvents() {
        return ownEvents;
    }

    public void setOwnEvents(String ownEvents) {
        this.ownEvents = ownEvents;
    }

    public String getSavedEvents() {
        return savedEvents;
    }

    public void setSavedEvents(String savedEvents) { this.savedEvents = savedEvents; }


}