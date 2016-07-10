package db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import db.DbHelper;
import objects.Event;

/**
 * Created by Malte on 29.06.2016.
 */
public class EventDataSource {

    private static final String LOG_TAG = EventDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public EventDataSource(Context context) {
        dbHelper = new DbHelper(context);
        Log.d(LOG_TAG, "EventDataSource hat DB-Helper erzeugt.");
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


    //Event erstellen, in DB speichern und zurückgeben
    public Event insertEvent(String title, String datum) throws ParseException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.EventTable.COLUMN_NAME_TITLE, title);
        contentValues.put(DbHelper.EventTable.COLUMN_NAME_DATE, datum);

        Log.d(LOG_TAG, "end ContenValues");

        long insertId = database.insert(DbHelper.EVENT_TABLE_NAME, null, contentValues);

        Log.d(LOG_TAG, "start cursor");

        Cursor cursor = database.query(DbHelper.EVENT_TABLE_NAME,
                new String[]{DbHelper.EventTable._ID, DbHelper.EventTable.COLUMN_NAME_DATE, DbHelper.EventTable.COLUMN_NAME_TITLE}, DbHelper.EventTable._ID + "=" + insertId,
                null, null, null, null);

        Log.d(LOG_TAG, "end cursor");

        cursor.moveToFirst();

        Log.d(LOG_TAG, "vor Event");

        Event event = cursorToEventObject(cursor);

        Log.d(LOG_TAG, "nach Event mit Datum: " + event.getDatum().toString());

        cursor.close();

        return event;
    }

    //Event Hilfsmethode
    private Event cursorToEventObject(Cursor cursor) throws ParseException {
        Log.d(LOG_TAG, "start cursorToEventObject");

        int idIndex = cursor.getColumnIndex(DbHelper.EventTable._ID);
        int idTitle = cursor.getColumnIndex(DbHelper.EventTable.COLUMN_NAME_TITLE);
        int idDate = cursor.getColumnIndex(DbHelper.EventTable.COLUMN_NAME_DATE);

        Log.d(LOG_TAG, "nach getIndex");

        long id = cursor.getLong(idIndex);
        String title = cursor.getString(idTitle);
        String dateString = cursor.getString(idDate);

        Log.d(LOG_TAG, "vor SDF: " + dateString);

        Event event = new Event(id, Date.valueOf(dateString), title);

        Log.d(LOG_TAG, "ende cursorToEventObject");

        return event;
    }

    // Liste aller Events zurückgeben
    public List<Event> getAllEvents() throws ParseException {
        List<Event> eventList = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.EVENT_TABLE_NAME,
                new String[]{DbHelper.EventTable._ID, DbHelper.EventTable.COLUMN_NAME_DATE, DbHelper.EventTable.COLUMN_NAME_TITLE}, null, null, null, null, null);

        cursor.moveToFirst();
        Event event;

        while (!cursor.isAfterLast()) {
            event = cursorToEventObject(cursor);
            eventList.add(event);
            Log.d(LOG_TAG, "ID: " + event.getId() + ", Inhalt: " + event.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return eventList;
    }


    public boolean updateEvent(long id, String titel, Date datum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", titel);
        contentValues.put("date", date);

        //database.update("event", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    // Event löschen
   /* public Integer deleteEvent(long id) {
        return database.delete(EventDataSource.EventTable.TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }*/
    public void deleteEvent(long id) {
        database.delete(DbHelper.EVENT_TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }

}
