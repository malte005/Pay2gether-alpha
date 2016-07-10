package db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.DbHelper;
import objects.User;

/**
 * Created by Malte on 01.07.2016.
 */
public class UserDataSource {

    private static final String LOG_TAG = EventDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public UserDataSource(Context context) {
        Log.d(LOG_TAG, "UserDataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        Log.d(LOG_TAG, "Datenbank wird jetzt geschlossen.");
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    //User erstellen, in DB speichern und zurückgeben
    public User insertUser(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.UserTable.COLUMN_NAME_NAME, name);

        long insertId = database.insert(DbHelper.USER_TABLE_NAME, null, contentValues);

        Cursor cursor = database.query(DbHelper.USER_TABLE_NAME,
                new String[]{DbHelper.UserTable._ID, DbHelper.UserTable.COLUMN_NAME_NAME}, DbHelper.UserTable._ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        User user = cursorToUserObject(cursor);
        cursor.close();

        return user;
    }

    //User Hilfsmethode
    private User cursorToUserObject(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DbHelper.UserTable._ID);
        int idNmae = cursor.getColumnIndex(DbHelper.UserTable.COLUMN_NAME_NAME);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idNmae);

        User user = new User(id, name);

        return user;
    }

    // Liste aller User zurückgeben
    public List<User> getAllEvents() {
        List<User> eventList = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.USER_TABLE_NAME,
                new String[]{DbHelper.UserTable._ID, DbHelper.UserTable.COLUMN_NAME_NAME}, null, null, null, null, null);

        cursor.moveToFirst();
        User user;

        while(!cursor.isAfterLast()) {
            user = cursorToUserObject(cursor);
            eventList.add(user);
            Log.d(LOG_TAG, "ID: " + user.getId() + ", Inhalt: " + user.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return eventList;
    }

    public int updateUser(long id, String name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", name);

        return database.update(DbHelper.USER_TABLE_NAME, contentValues, "id = ? ", new String[]{Long.toString(id)});
    }

    //User löschen
    public void deleteUser(long id){
        database.delete(DbHelper.USER_TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }

}
