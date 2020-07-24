package com.example.wallpaper.model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

public class RealmController {
    private final Realm realm;
    public RealmController(){
        realm=Realm.getDefaultInstance();
    }
    public void savePhoto(photo p){
         realm.beginTransaction();
         realm.copyToRealm(p);
         realm.commitTransaction();
    }
    public boolean isPhotoSaved(String id){
        photo result=realm.where(photo.class).equalTo("id",id).findFirst();
        if(result==null)
          return false;
        else
          return true;}
    public void deletePhoto(final photo p){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                photo result=realm.where(photo.class).equalTo("id",p.getId()).findFirst();
                result.deleteFromRealm();
            }
        });
     }
    public List<photo> allPhotos(){
        return realm.where(photo.class).findAll();
    }
}
