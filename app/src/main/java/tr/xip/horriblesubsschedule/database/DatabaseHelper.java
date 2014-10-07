package tr.xip.horriblesubsschedule.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tr.xip.horriblesubsschedule.database.tables.AnimeTable;

/**
 * Created by Hikari on 8/30/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";

    private final String TAG = "Database Helper";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_ANIME_TABLE = "CREATE TABLE "
            + AnimeTable.TABLE_NAME + " ("
            + AnimeTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + AnimeTable.COLUMN_NAME_DAY + INTEGER_TYPE + COMMA_SEP
            + AnimeTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + AnimeTable.COLUMN_NAME_RELEASE_TIME + TEXT_TYPE + COMMA_SEP
            + AnimeTable.COLUMN_NAME_NULLABLE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ANIME_TABLE = "DROP TABLE IF EXISTS "
            + AnimeTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ANIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ANIME_TABLE);
        Log.d(TAG, "Cleared!");
        onCreate(db);
    }
}
