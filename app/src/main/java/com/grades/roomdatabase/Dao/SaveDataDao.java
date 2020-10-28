package com.grades.roomdatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.grades.roomdatabase.model.ClassModel;
import com.grades.roomdatabase.model.StudentsOfCurrentAccountModelDb;

import java.util.List;

@Dao
public interface SaveDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClass(ClassModel classModel);

    @Query("SELECT * FROM classes WHERE studentNumber=:studentNumber")
    List<ClassModel> getClasses(int studentNumber);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudent(StudentsOfCurrentAccountModelDb classModel);

    @Query("SELECT * FROM studentTable")
    StudentsOfCurrentAccountModelDb studentOfCurrent();

    @Query("UPDATE classes SET className=:className WHERE gradebookNumber = :gradebookNumber")
    void update(String className, int gradebookNumber);






/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClassesDetail(ClassesDetailModelDb classModel);
*/


}
