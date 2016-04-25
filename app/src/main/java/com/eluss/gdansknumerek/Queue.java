package com.eluss.gdansknumerek;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eluss on 31/03/16.
 */
public class Queue implements Parcelable {
    String id;
    String letter;
    String name;
    String currentNumber;
    String peopleInQueue;
    String activeHandlers;
    String handlingTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getPeopleInQueue() {
        return peopleInQueue;
    }

    public void setPeopleInQueue(String peopleInQueue) {
        this.peopleInQueue = peopleInQueue;
    }

    public String getActiveHandlers() {
        return activeHandlers;
    }

    public void setActiveHandlers(String activeHandlers) {
        this.activeHandlers = activeHandlers;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public Queue() {

    }

    public Queue(Parcel in) {
        String[] data = new String[7];

        in.readStringArray(data);
        this.id = data[0];
        this.letter = data[1];
        this.name = data[2];
        this.currentNumber = data[3];
        this.peopleInQueue = data[4];
        this.activeHandlers = data[5];
        this.handlingTime = data[6];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.id,
                this.letter,
                this.name,
                this.currentNumber,
                this.peopleInQueue,
                this.activeHandlers,
                this.handlingTime});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Queue createFromParcel(Parcel in) {
            return new Queue(in);
        }

        public Queue[] newArray(int size) {
            return new Queue[size];
        }
    };
}
