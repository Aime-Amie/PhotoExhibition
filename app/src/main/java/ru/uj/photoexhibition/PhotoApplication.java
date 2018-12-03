package ru.uj.photoexhibition;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Blokhin Evgeny on 29.11.2018.
 */
public class PhotoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
