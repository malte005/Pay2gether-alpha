package db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Malte on 28.06.2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pay2gether.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String DECIMAL_TYPE = " REAL";
    public static final String NOT_NULL = " NOT NULL";
    public static final String COMMA_SEP = ",";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt/geholt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(EventSQL.SQL_CREATE_EVENT);
            db.execSQL(UserSQL.SQL_CREATE_USER);
            db.execSQL(AusgabeSQL.SQL_CREATE_AUSGABE);
            Log.d(LOG_TAG, "Die Tabellen wurden angelegt.");
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabellen: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AUSGABE_TABLE_NAME);
        onCreate(db);
    }


    //######################## E V E N T //########################

    public static final String EVENT_TABLE_NAME = "event";

    //Tabellendafinition
    public static abstract class EventTable implements BaseColumns {
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static abstract class EventSQL {
        private static final String SQL_CREATE_EVENT =
                "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                        EventTable._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                        EventTable.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        EventTable.COLUMN_NAME_DATE + TEXT_TYPE +
                        " )";

        private static final String SQL_DROP_EVENT =
                "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME;
    }

    //######################## E V E N T //########################


    //######################## U S E R //########################

    public static final String USER_TABLE_NAME = "user";

    //Tabellendafinition
    public static abstract class UserTable implements BaseColumns {
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class UserSQL {
        private static final String SQL_CREATE_USER =
                "CREATE TABLE " + USER_TABLE_NAME + " (" +
                        UserTable._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                        UserTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL +
                        " )";

        private static final String SQL_DROP_USER =
                "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    }

    //######################## U S E R //########################

    //######################## A U S G A B E //########################

    public static final String AUSGABE_TABLE_NAME = "ausgabe";

    //Tabellendafinition
    public static abstract class AusgabeTable implements BaseColumns {
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BETRAG = "betrag";
        public static final String COLUMN_NAME_FK_USER = "user_id";
        public static final String COLUMN_NAME_FK_EVENT = "event_id";
    }

    public static abstract class AusgabeSQL {
        private static final String SQL_CREATE_AUSGABE =
                "CREATE TABLE " + AUSGABE_TABLE_NAME + " (" +
                        AusgabeTable._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_BETRAG + DECIMAL_TYPE + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_FK_USER + INTEGER_TYPE + COMMA_SEP +
                        AusgabeTable.COLUMN_NAME_FK_EVENT + INTEGER_TYPE +
                        " )";

        private static final String SQL_DELETE_AUSGABE =
                "DROP TABLE IF EXISTS " + AUSGABE_TABLE_NAME;
    }

    //######################## A U S G A B E //########################

}
