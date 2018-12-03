package ru.uj.photoexhibition.PhotoList;

import android.support.v4.app.Fragment;

import ru.uj.photoexhibition.SingleFragmentActivity;

public class PhotoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoListFragment.newInstance();
    }
}
