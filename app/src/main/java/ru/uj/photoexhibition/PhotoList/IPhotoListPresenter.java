package ru.uj.photoexhibition.PhotoList;

public interface IPhotoListPresenter {
    void openPhotoAddImage();
//    void getPhotoList();
    void bindView(IPhotoListView view);
    void unbindView();
}
