package ru.uj.photoexhibition.DataClasses;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Blokhin Evgeny on 12.11.2018.
 */
public class PhotoLab {
    private static PhotoLab sPhotoLab;
    private Realm mRealm;
    private Context mContext;

    public static PhotoLab get(Context context) {
        if (sPhotoLab == null) {
            sPhotoLab = new PhotoLab(context);
        }
        return sPhotoLab;
    }

    private PhotoLab(Context context) {
        mContext = context.getApplicationContext();
        mRealm = Realm.getDefaultInstance();
    }

    public RealmResults<Photo> getPhotos() {
        return mRealm.where(Photo.class).findAll();
    }

    public Photo addPhoto() {
        mRealm.beginTransaction();
        Photo photo = mRealm.createObject(Photo.class);
        photo.setmId(UUID.randomUUID().toString());
        photo.setmTimesStamp(new SimpleDateFormat("E, MMM dd, yyyy, kk:mm:ss").format(new Date()));
        mRealm.commitTransaction();
        return photo;
    }

    public void deletePhoto(Photo photo) {
        mRealm.beginTransaction();
        RealmResults<Photo> photos = mRealm.where(Photo.class).equalTo("mId", photo.getmId()).findAll();

        if (!photos.isEmpty()) {

            for (int i = photos.size() - 1; i >= 0; i--) {

                photos.get(i).deleteFromRealm();
            }
        }
        mRealm.commitTransaction();
    }

    public Photo getPhoto(UUID id) {
        RealmResults<Photo> photos = mRealm.where(Photo.class).equalTo("mId", id.toString()).findAll();
        Photo photo = null;
        if (!photos.isEmpty()) {

            for (int i = photos.size() - 1; i >= 0; i--) {

                photo = photos.get(i);
            }
        }
        return photo;
    }

    public File getPhotoFile(Photo photo) {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, photo.getPhotoFilename());
    }
}
