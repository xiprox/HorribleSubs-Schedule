package tr.xip.horriblesubsschedule.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tr.xip.horriblesubsschedule.database.entries.ScheduleEntry;

/**
 * Created by Hikari on 8/30/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Schedule.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_SCHEDULE_TABLE = "CREATE TABLE "
            + ScheduleEntry.TABLE_NAME + " ("
            + ScheduleEntry.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + ScheduleEntry.COLUMN_NAME_DAY + INTEGER_TYPE + COMMA_SEP
            + ScheduleEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + ScheduleEntry.COLUMN_NAME_RELEASE_TIME + TEXT_TYPE + " )";
    private static final String SQL_DELETE_SCHEDULE_TABLE = "DROP TABLE IF EXISTS "
            + ScheduleEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SCHEDULE_TABLE);
        Log.d("DATABASE", "Cleared!");
        onCreate(db);
    }
}
