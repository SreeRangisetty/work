package com.example.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " +
                TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_DESCRIPTION + " TEXT, " +
                TaskEntry.COLUMN_TASK + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_DUE_DATE + " INTEGER NOT NULL, " +
                TaskEntry.COLUMN_PRIORITY + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_CATEGORY + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }

    public static final class TaskEntry {
        public static final String TABLE_NAME = "tasks";
        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_CATEGORY = "category";
    }
}

