package db.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.DbHelper;
import modell.User;

/**
 * Created by Malte on 01.07.2016.
 */
public class UserDataSource {

    private static final String LOG_TAG = EventDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public UserDataSource(Context context) {
        dbHelper = new DbHelper(context);
        Log.d(LOG_TAG, "UserDataSource hat den dbHelper erzeugt.");
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

        Log.d(LOG_TAG, "start cursor");

        Cursor cursor = database.query(DbHelper.USER_TABLE_NAME,
                null, DbHelper.UserTable._ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        User user = cursorToUserObject(cursor);
        cursor.close();

        return user;
    }

    //User Hilfsmethode
    private User cursorToUserObject(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DbHelper.UserTable._ID);
        int idName = cursor.getColumnIndex(DbHelper.UserTable.COLUMN_NAME_NAME);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);

        User user = new User(id, name);

        return user;
    }

    // Hole User aus DB
    public User getUser(long id) {
        User user;

        Cursor cursor = database.query(DbHelper.USER_TABLE_NAME, null, DbHelper.UserTable._ID + " = ? ",
                new String[]{Long.toString(id)}, null, null, null);

        user = cursorToUserObject(cursor);

        return user;
    }

    // Liste aller User zurückgeben
    public List<User> getAllUser() {
        List<User> eventList = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.USER_TABLE_NAME,
                null, null, null, null, null, null);

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
        contentValues.put(DbHelper.UserTable.COLUMN_NAME_NAME, name);

        return database.update(DbHelper.USER_TABLE_NAME, contentValues, "id = ? ", new String[]{Long.toString(id)});
    }

    //User löschen
    public int deleteUser(long id) {
        return database.delete(DbHelper.USER_TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }

}
