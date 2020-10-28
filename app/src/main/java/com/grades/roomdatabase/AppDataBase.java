package com.grades.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.grades.roomdatabase.Dao.SaveDataDao;
import com.grades.roomdatabase.model.ClassModel;
import com.grades.roomdatabase.model.StudentsOfCurrentAccountModelDb;

@Database(entities = {ClassModel.class, StudentsOfCurrentAccountModelDb.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract SaveDataDao saveDataDao();

}
