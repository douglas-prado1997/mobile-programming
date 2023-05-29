package com.prado.painter.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.prado.painter.model.Service;
import com.prado.painter.model.Type;


@Database(entities = {Service.class, Type.class}, version = 1,exportSchema = false)
public abstract class ServicesDatabase extends RoomDatabase {
    public abstract ServicesDAO ServicesDAO();
    public abstract TypeDao TypeDao();


    private static ServicesDatabase instance;

    public static ServicesDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (ServicesDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context,
                            ServicesDatabase.class,
                            "services.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }

}