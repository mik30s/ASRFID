package edu.tarleton.timestamp;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.HashMap;

public class DatabaseWriter {

    public enum  MODE {
        INSERT, UPDATE, DELETE
    }

    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ID = "subject";
        public static final String COLUMN_NAME_START = "startTime";
        public static final String COLUMN_NAME_END = "endTime";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    DatabaseEntry.COLUMN_NAME_START + " TEXT," +
                    DatabaseEntry.COLUMN_NAME_END + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;

    public class DatabaseHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "subjects.db";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldversion, int newversion) {
            onUpgrade(db , oldversion, newversion);
        }
    }

    private final SQLiteDatabase db;
    private final DatabaseHelper helper;

    public DatabaseWriter(Context ctx) {
        helper = new DatabaseHelper(ctx);
        // Gets the data repository in write mode
        db = helper.getWritableDatabase();
    }

    public long write(HashMap<String, String> map, MODE mode) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseEntry.COLUMN_NAME_ID, map.get("id"));
        if(mode == MODE.INSERT)
            values.put(DatabaseEntry.COLUMN_NAME_START, map.get("start"));
        else
            values.put(DatabaseEntry.COLUMN_NAME_END, map.get("end"));

        // Insert the new row, returning the primary key value of the new row
        switch (mode) {
            case INSERT: return db.insert(DatabaseEntry.TABLE_NAME, null, values);
            case UPDATE: {
                String selection = DatabaseEntry.COLUMN_NAME_ID + " LIKE ?";
                String[] selectionArgs = { ""+values.get(DatabaseEntry.COLUMN_NAME_ID) };
                return db.update(DatabaseEntry.TABLE_NAME, values, selection, selectionArgs);
            }
            default: return -1;
            }
    }
}
