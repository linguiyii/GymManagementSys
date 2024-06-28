package edu.demo.gymsys.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.demo.gymsys.database.DatabaseHelper;
import edu.demo.gymsys.model.User;

public class UserDao {

    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 插入用户数据
    public long insertUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        return db.insert(DatabaseHelper.TABLE_USER, null, values);
    }

    // 根据邮箱查询用户
    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER,
                null,
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));
            cursor.close();
            return new User(id, userEmail, userPassword);
        }
        return null;
    }

    // 更新用户数据
    public int updateUser(int id, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        return db.update(DatabaseHelper.TABLE_USER, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // 删除用户数据
    public int deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = null;
        try {
            String query = "SELECT * FROM User";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    User user = new User(id, email,password);
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userList;
    }
}
