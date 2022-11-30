package com.example.foodfinder;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username, firstName, lastName, phoneNumber, emailAddress, password;

    public User() {

    }

    public User(String username, String firstName, String lastName, String phoneNumber,
                String emailAddress, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
//    // write your object's data to the passed-in Parcel
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
//        out.writeString(username);
//        out.writeString(firstName);
//        out.writeString(lastName);
//        out.writeString(phoneNumber);
//        out.writeString(emailAddress);
//        out.writeString(password);
//    }
//
//    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
//    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
//
//    // example constructor that takes a Parcel and gives you an object populated with it's values
//    private User(Parcel in) {
//        username = in.readString();
//    }
}
