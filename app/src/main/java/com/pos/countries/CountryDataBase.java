package com.pos.countries;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pos.countries.Dao.CountryDAO;
import com.pos.countries.Model.Country;

@Database(entities = {Country.class}, version = 1)
public abstract class CountryDataBase extends RoomDatabase {

    private static final String TAG = "CountryDataBase";
    private static final String DB_NAME = "Country.db";
    private static volatile CountryDataBase instance;

    public static CountryDataBase getInstance(Context context){
        if(instance== null) {
            instance = create(context);
        }
        return instance;
    }

    private static CountryDataBase create(Context context){
        return Room.databaseBuilder(context, CountryDataBase.class, DB_NAME).build();
    }
    public abstract CountryDAO getDao();
}