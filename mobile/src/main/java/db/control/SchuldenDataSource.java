package db.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.DbHelper;
import modell.Schulden;
import modell.User;

/**
 * Created by Malte on 15.07.2016.
 */
public class SchuldenDataSource {

    private static final String LOG_TAG = EventDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public SchuldenDataSource(Context context) {
        dbHelper = new DbHelper(context);
        Log.d(LOG_TAG, "SchuldenDataSource hat den dbHelper erzeugt.");
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

    //Schulden erstellen, in DB speichern und zurückgeben
    public Schulden insertSchulden(float betrag, User schuldenVon, User schuldenAn) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_BETRAG, betrag);
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_VON, schuldenVon.getId());
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_AN, schuldenAn.getId());

        long insertId = database.insert(DbHelper.SCHULDEN_TABLE_NAME, null, contentValues);

        Log.d(LOG_TAG, "start cursor");

        Cursor cursor = database.query(DbHelper.SCHULDEN_TABLE_NAME,
                new String[]{null}, DbHelper.SchuldenTable._ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Schulden schulden = cursorToSchuldenObject(cursor);
        cursor.close();

        return schulden;
    }

    //Schulden Hilfsmethode
    private Schulden cursorToSchuldenObject(Cursor cursor) {
        Schulden schulden = null;

        int idIndex = cursor.getColumnIndex(DbHelper.SchuldenTable._ID);
        int idBetrag = cursor.getColumnIndex(DbHelper.SchuldenTable.COLUMN_NAME_BETRAG);
        int idSchuldenVon = cursor.getColumnIndex(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_VON);
        int idSchuldenAn = cursor.getColumnIndex(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_AN);

        long id = cursor.getLong(idIndex);
        float betrag = cursor.getFloat(idBetrag);
        long schuldenVon = cursor.getLong(idSchuldenVon);
        long schuldenAn = cursor.getLong(idSchuldenAn);

        schulden = new Schulden(id, betrag, );

        return schulden;
    }

    // Hole Schulden aus DB
    public Schulden getSchulden(long id) {
        Schulden schulden;

        Cursor cursor = database.query(DbHelper.SCHULDEN_TABLE_NAME, null, DbHelper.SchuldenTable._ID + " = ? ",
                new String[]{Long.toString(id)}, null, null, null);

        schulden = cursorToSchuldenObject(cursor);

        return schulden;
    }

    // Liste aller Schulden zurückgeben
    public List<Schulden> getAllSchulden() {
        List<Schulden> schuldenList = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.SCHULDEN_TABLE_NAME,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        Schulden schulden;

        while (!cursor.isAfterLast()) {
            schulden = cursorToSchuldenObject(cursor);
            schuldenList.add(schulden);
            Log.d(LOG_TAG, "ID: " + schulden.getId() + ", Inhalt: " + schulden.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return schuldenList;
    }

    //Schulden updaten
    public int updateSchulden(long id, float betrag, User schuldenVon, User schuldenAn) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_BETRAG, betrag);
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_VON, schuldenVon.getId());
        contentValues.put(DbHelper.SchuldenTable.COLUMN_NAME_FK_USER_AN, schuldenAn.getId());

        return database.update(DbHelper.SCHULDEN_TABLE_NAME, contentValues, "ID = ? ", new String[]{Long.toString(id)});
    }

    //Schulden löschen
    public int deleteSchulden(long id) {
        return database.delete(DbHelper.SCHULDEN_TABLE_NAME,
                "ID = ? ",
                new String[]{Long.toString(id)});
    }

}
