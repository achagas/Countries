package com.pos.countries.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pos.countries.Model.Country;

import java.util.List;

@Dao
public interface CountryDAO {

    @Query("SELECT * FROM country")
    List<Country> getAllCountry();


    @Insert
    void insert(Country... c);
    @Delete
    void update(Country... c);
    @Update
    void delete(Country... c);

}

