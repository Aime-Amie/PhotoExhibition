package ru.uj.photoexhibition.DataClasses;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Required;


public class Photo extends RealmObject implements Serializable {

    @Required
    private String mId;
    private String mTimesStamp;

    public Photo() {
        mId = UUID.randomUUID().toString();
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String id) {
        mId = id;
    }

    public  String getPhotoFilename() {
        return "IMG_" + getmId() + ".jpg";
    }


    public String getmTimesStamp() {
        return mTimesStamp;
    }

    public void setmTimesStamp(String timesStamp) {
        mTimesStamp = timesStamp;
    }

}
