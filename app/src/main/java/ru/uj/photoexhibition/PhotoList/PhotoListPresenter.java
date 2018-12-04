package ru.uj.photoexhibition.PhotoList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.uj.photoexhibition.BasePresenter;
import ru.uj.photoexhibition.PhotoAdd.PhotoAddActivity;

/**
 * Created by Blokhin Evgeny on 17.11.2018.
 */
public class PhotoListPresenter extends BasePresenter<IPhotoListView> implements IPhotoListPresenter {

    @Override
    public void openPhotoAddImage() {
        Context ctx = ((Fragment)view).getActivity();
        Intent intent = new Intent(ctx, PhotoAddActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void bindView(IPhotoListView view) {
        super.bindView(view);
    }
}
