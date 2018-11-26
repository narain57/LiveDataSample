package com.android.livedatasample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.android.livedatasample.db.FavoriteDbHelper;
import com.android.livedatasample.model.Favorite;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteDbHelper mFavHelper;
    private MutableLiveData<List<Favorite>> mFavs;

    public FavoriteViewModel(Application application) {
        super(application);
        mFavHelper = new FavoriteDbHelper(application);
    }

    public MutableLiveData<List<Favorite>> getFavs() {
        if (mFavs == null) {
            mFavs = new MutableLiveData<>();
            loadFavs();
        }

        return mFavs;
    }


    public void addFav(String name, String color) {

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("color",color);
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        db.insert("fav",null,values);
        List<Favorite> favourites = mFavs.getValue();

        ArrayList<Favorite> clonedFavs;
        if (favourites == null) {
            clonedFavs = new ArrayList<>();
        } else {
            clonedFavs = new ArrayList<>(favourites.size());
            for (int i = 0; i < favourites.size(); i++) {
                clonedFavs.add(new Favorite(favourites.get(i)));
            }
        }

        Favorite fav = new Favorite();
        fav.setColor(color);
        fav.setName(name);
        clonedFavs.add(fav);
        mFavs.setValue(clonedFavs);

    }

    public void loadFavs() {
        List<Favorite> newFavs = new ArrayList<>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor = db.query("fav",
                new String[]{
                        "name",
                        "color"
                },
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int name = cursor.getColumnIndex("name");
            int color = cursor.getColumnIndex("color");
            Favorite fav = new Favorite();
            fav.setName(cursor.getString(name));
            fav.setColor(cursor.getString(color));
            newFavs.add(fav);
        }

        cursor.close();
        db.close();
        mFavs.setValue(newFavs);
    }

    public void removeFav(String name) {

        ContentValues values = new ContentValues();
        values.put("name",name);
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        db.delete("fav","name = ?",new String[]{name});
        List<Favorite> favs = mFavs.getValue();
        ArrayList<Favorite> clonedFavs = new ArrayList<>(favs.size());
        for (int i = 0; i < favs.size(); i++) {
            clonedFavs.add(new Favorite(favs.get(i)));
        }

        int index = -1;
        for (int i = 0; i < clonedFavs.size(); i++) {
            Favorite favourites = clonedFavs.get(i);
            if (favourites.getName() == name) {
                index = i;
            }
        }
        if (index != -1) {
            clonedFavs.remove(index);
        }
        mFavs.setValue(clonedFavs);
    }
}
