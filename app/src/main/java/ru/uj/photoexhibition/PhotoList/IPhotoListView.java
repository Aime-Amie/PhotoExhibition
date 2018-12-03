package ru.uj.photoexhibition.PhotoList;

public interface IPhotoListView {
    void showProgressDialog(String messageToDisplay);
    void hideProgressDialog();
    void showError(String messageToDisplay);
//    void onGetPhotos(RealmResults<Photo> photos);
    void showEmptyView();
    void hideEmptyView();
    void updateUI();
}
