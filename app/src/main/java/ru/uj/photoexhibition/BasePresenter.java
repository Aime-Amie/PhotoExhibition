package ru.uj.photoexhibition;

import android.support.v7.app.AppCompatActivity;

public abstract class BasePresenter<V> extends AppCompatActivity {
    protected V view;

    public void bindView(V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }
}
