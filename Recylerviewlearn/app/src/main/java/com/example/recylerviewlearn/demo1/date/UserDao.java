package com.example.recylerviewlearn.demo1.date;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUsers(User ... users);

    @Update
    void updateUsersDate(User ... users);

    @Delete
    void deleteUsers(User ... users);

    @Query("DELETE FROM User WHERE uid in(SELECT uid FROM User ORDER BY uid DESC LIMIT 1 )")
    void delectLastUser();

    @Query("DELETE FROM User WHERE uid = :uid")
    void delectPosUser(int uid);


    @Query("SELECT * FROM User")
    List<User> loadAllUsers();

//    @Query("SELECT * FROM User WHERE mType = type")
//    public List<User> loadTypeUsers(String type);

}
