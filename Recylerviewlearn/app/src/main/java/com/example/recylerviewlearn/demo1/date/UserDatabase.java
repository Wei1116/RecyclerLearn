package com.example.recylerviewlearn.demo1.date;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();//创建DAO的抽象类

    private static final String DB_NAME = "myuser.db";
//    private static volatile UserDatabase instance;
//
//    public static synchronized UserDatabase getInstance(Context context){
//        if(instance == null){
//            instance = create(context);
//        }
//        return instance;
//    }
//
//    private static UserDatabase create (final Context context){
//        return Room.databaseBuilder(
//                context,
//                UserDatabase.class,
//                DB_NAME).build();
//
//    }
//
//    public abstract UserDao getUserDao();
    private static UserDatabase INSTANCE;//创建单例

    public static UserDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UserDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UserDatabase.class,
                            DB_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
