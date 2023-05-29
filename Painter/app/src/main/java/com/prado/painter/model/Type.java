package com.prado.painter.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Type {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    @NonNull
    String Name;

    public Type() {

    }


    public Type(String name) {
    this.setName(name);
    }

    public void setId(int id) {
        this.id = id;
    }

    public  int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }


}