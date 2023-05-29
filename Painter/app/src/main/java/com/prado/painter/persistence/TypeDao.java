package com.prado.painter.persistence;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.prado.painter.model.Type;

@Dao
public interface TypeDao {

    @Insert
    long insert(Type type);

    @Delete
    void delete(Type type);

    @Update
    void update(Type type);

    @Query("SELECT * FROM Type WHERE id = :id")
    Type queryForId(int id);

    @Query("SELECT * FROM type WHERE name = :name ORDER BY name ASC")
    List<Type> queryForName(String name);

    @Query("SELECT * FROM type ORDER BY name ASC")
    List<Type> queryAll();

    @Query("SELECT count(*) FROM type")
    int total();
}
