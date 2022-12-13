package com.course.dietapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    @Insert
    void insert(FoodItem mainData);

    @Delete
    void delete(FoodItem mainData);

    @Delete
    void reset(List<FoodItem> mainData);

//    @Query("UPDATE food SET text = :sText WHERE ID = :sID")
//    void update(int sID, String sText);

    @Query("SELECT * FROM food")
    List<FoodItem> getAll();

    @Query("SELECT * FROM food WHERE date = :date")
    List<FoodItem> getAllByDate(String date);


}
