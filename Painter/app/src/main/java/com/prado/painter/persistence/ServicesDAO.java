package com.prado.painter.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.prado.painter.model.Service;

@Dao
public interface ServicesDAO {
    @Insert
    long insert(Service service);

    @Delete
    void delete(Service service);

    @Update
    void update(Service service);

    @Query("SELECT * FROM service WHERE id = :id")
    Service queryForId(long id);

    @Query("SELECT * FROM service ")
    List<Service> queryAll();
}