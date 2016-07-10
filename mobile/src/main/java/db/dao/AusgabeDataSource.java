package db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.DbHelper;
import objects.Ausgabe;
import objects.Event;
import objects.User;

/**
 * Created by Malte on 03.07.2016.
 */
public class AusgabeDataSource {

    private static final String LOG_TAG = AusgabeDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public AusgabeDataSource(Context context) {
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

    //Ausgabe erstellen, in DB speichern und zurückgeben
    public Ausgabe insertAusgabe(String title, float betrag, Event event, User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.AusgabeTable.COLUMN_NAME_TITLE, title);
        contentValues.put(DbHelper.AusgabeTable.COLUMN_NAME_BETRAG, betrag);
        contentValues.put(DbHelper.AusgabeTable.COLUMN_NAME_FK_EVENT, Long.toString(event.getId()));
        contentValues.put(DbHelper.AusgabeTable.COLUMN_NAME_FK_USER, Long.toString(user.getId()));

        long insertId = database.insert(DbHelper.AUSGABE_TABLE_NAME, null, contentValues);

        Cursor cursor = database.query(DbHelper.AUSGABE_TABLE_NAME,
               null, DbHelper.AusgabeTable._ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Ausgabe ausgabe = cursorToAusgabeObject(cursor);
        cursor.close();

        return ausgabe;
    }

    //Ausgabe Hilfsmethode
    private Ausgabe cursorToAusgabeObject(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DbHelper.AusgabeTable._ID);
        int idTitle = cursor.getColumnIndex(DbHelper.AusgabeTable.COLUMN_NAME_TITLE);
        int idBetrag = cursor.getColumnIndex(DbHelper.AusgabeTable.COLUMN_NAME_BETRAG);
        int idEvent = cursor.getColumnIndex(DbHelper.AusgabeTable.COLUMN_NAME_FK_EVENT);
        int idUser = cursor.getColumnIndex(DbHelper.AusgabeTable.COLUMN_NAME_FK_USER);

        long id = cursor.getLong(idIndex);
        String title = cursor.getString(idTitle);
        String stringBetrag = cursor.getString(idBetrag);
        String stringEventId = cursor.getString(idEvent);
        String stringUserId = cursor.getString(idUser);

        Ausgabe ausgabe = new Ausgabe(id, Double.valueOf(stringBetrag), title);

        return ausgabe;
    }

    // Liste aller Ausgaben zurückgeben
    public List<Ausgabe> getAllAusgabe() {
        List<Ausgabe> ausgabeList = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.AUSGABE_TABLE_NAME,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        Ausgabe ausgabe;

        while(!cursor.isAfterLast()) {
            ausgabe = cursorToAusgabeObject(cursor);
            ausgabeList.add(ausgabe);
            Log.d(LOG_TAG, "ID: " + ausgabe.getId() + ", Inhalt: " + ausgabe.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return ausgabeList;
    }


    public int updateAusgabe(long id, String titel, Double betrag) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", titel);
        contentValues.put("betrag", betrag);

        return database.update("event", contentValues, "id = ? ", new String[]{Long.toString(id)});
    }

    // Ausgabe löschen
    public void deleteEvent(long id){
        database.delete(DbHelper.EVENT_TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }

}
