package ru.uj.photoexhibition.PhotoList;

import java.io.Serializable;

public interface IPhotoListPresenter extends Serializable {
    void openPhotoAddImage();
    void bindView(IPhotoListView view);
    void unbindView();
}
