package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import objects.Event;
import objects.User;

/**
 * Created by Malte on 28.06.2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pay2gether.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String COMMA_SEP = ",";


    public static abstract class EventSQL {
        private static final String SQL_DELETE_EVENT =
                "DROP TABLE IF EXISTS " + EventTable.TABLE_NAME;

        private static final String SQL_CREATE_EVENT =
                "CREATE TABLE " + EventTable.TABLE_NAME + " (" +
                        EventTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                        EventTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        EventTable.COLUMN_NAME_DATE + DATE_TYPE +
                        " )";

        private static final String getAllResults = "select * from event where id = ";
    }


    public static abstract class UserSQL {
        private static final String SQL_CREATE_USER =
                "CREATE TABLE " + UserTable.TABLE_NAME + " (" +
                        UserTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                        UserTable.COLUMN_NAME_NAME + TEXT_TYPE +
                        " )";

        private static final String SQL_DELETE_USER =
                "DROP TABLE IF EXISTS " + UserTable.TABLE_NAME;

        private static final String getAllResults = "select * from event where id = ";

    }

    public static abstract class AusgabeSQL {
        private static final String SQL_CREATE_AUSGABE =
                "CREATE TABLE " + AusgabeTable.TABLE_NAME + " (" +
                        AusgabeTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_BETRAG + FLOAT_TYPE + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_BETRAG + FLOAT_TYPE + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_FK_USER + " INTEGER" + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_FK_EVENT + " INTEGER" +
                        " )";

        private static final String SQL_DELETE_AUSGABE =
                "DROP TABLE IF EXISTS " + AusgabeTable.TABLE_NAME;

        private static final String getAllResults = "select * from event where id = ";
    }


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static abstract class EventTable implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
    }

    /* Inner class that defines the table contents */
    public static abstract class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
    }

    /* Inner class that defines the table contents */
    public static abstract class AusgabeTable implements BaseColumns {
        public static final String TABLE_NAME = "ausgabe";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BETRAG = "betrag";
        public static final String COLUMN_NAME_FK_USER = "userid";
        public static final String COLUMN_NAME_FK_EVENT = "eventid";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventSQL.SQL_CREATE_EVENT);
        db.execSQL(UserSQL.SQL_CREATE_USER);
        db.execSQL(AusgabeSQL.SQL_CREATE_AUSGABE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AusgabeTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEvent(String title, Date datum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventTable.COLUMN_NAME_TITLE, title);
        contentValues.put(EventTable.COLUMN_NAME_DATE, date);
        db.insert(EventTable.TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertUser(String name, Date datum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.COLUMN_NAME_NAME, name);
        db.insert(UserTable.TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertAusgabe(String title, float betrag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AusgabeTable.COLUMN_NAME_TITLE, title);
        contentValues.put(AusgabeTable.COLUMN_NAME_BETRAG, betrag);
        contentValues.put(AusgabeTable.COLUMN_NAME_FK_EVENT, event);
        contentValues.put(AusgabeTable.COLUMN_NAME_FK_USER, user);
        db.insert(AusgabeTable.TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getEventData(String tabelle, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        switch (tabelle) {
            case "event":
                res = db.rawQuery(EventSQL.getAllResults + id, null);
            case "user":
                res = db.rawQuery(UserSQL.getAllResults + id, null);
            case "ausgabe":
                res = db.rawQuery(AusgabeSQL.getAllResults + id, null);
        }
        return res;
    }

    public int numberOfRows(String tabelle) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = 0;
        switch (tabelle) {
            case "event":
                numRows = (int) DatabaseUtils.queryNumEntries(db, EventTable.TABLE_NAME);
            case "user":
                numRows = (int) DatabaseUtils.queryNumEntries(db, UserTable.TABLE_NAME);
            case "ausgabe":
                numRows = (int) DatabaseUtils.queryNumEntries(db, AusgabeTable.TABLE_NAME);
        }
        return numRows;
    }

    public boolean updateEvent(Integer id, String titel, Date datum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", titel);
        contentValues.put("date", date);
        db.update("event", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateUser(Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.update("user", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateAusgabe(Integer id, String titel, Date datum, Event event, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datum);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", titel);
        contentValues.put("date", date);
        contentValues.put("event", event.getId());
        contentValues.put("user", user.getId());
        db.update("event", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(EventTable.TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

}
