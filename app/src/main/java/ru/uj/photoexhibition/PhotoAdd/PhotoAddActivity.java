package ru.uj.photoexhibition.PhotoAdd;

import android.support.v4.app.Fragment;

import ru.uj.photoexhibition.SingleFragmentActivity;

public class PhotoAddActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoAddFragment.newInstance();
    }

}
