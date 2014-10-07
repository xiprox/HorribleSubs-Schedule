package tr.xip.horriblesubsschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tr.xip.horriblesubsschedule.database.items.AnimeItem;
import tr.xip.horriblesubsschedule.database.tables.AnimeTable;

/**
 * Created by Hikari on 8/30/14.
 */
public class DatabaseManager {

    Context context;

    SQLiteDatabase db;

    public static final String SORT_ASCN = " ASC";
    public static final String SORT_DESC = " DESC";

    private final String TAG = "Database Manager";

    String[] AnimeItemProjection = {
            AnimeTable.COLUMN_NAME_ID,
            AnimeTable.COLUMN_NAME_DAY,
            AnimeTable.COLUMN_NAME_TITLE,
            AnimeTable.COLUMN_NAME_RELEASE_TIME
    };

    public DatabaseManager(Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context).getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public long addAnimeItem(AnimeItem item) {
        ContentValues values = new ContentValues();
        values.put(AnimeTable.COLUMN_NAME_DAY, item.getDay());
        values.put(AnimeTable.COLUMN_NAME_TITLE, item.getName());
        values.put(AnimeTable.COLUMN_NAME_RELEASE_TIME, item.getTime());

        return db.insert(AnimeTable.TABLE_NAME, AnimeTable.COLUMN_NAME_NULLABLE, values);
    }

    public AnimeItem getAnimeItemById(int itemId) {
        String selection = AnimeTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        String sortOrder = AnimeTable.COLUMN_NAME_DAY + SORT_DESC;

        Cursor c = db.query(
                AnimeTable.TABLE_NAME,
                AnimeItemProjection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (c != null && c.moveToFirst()) {
            int entryId = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_ID));
            int day = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_DAY));
            String name = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_TITLE));
            String releaseTime = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_RELEASE_TIME));

            return new AnimeItem(entryId, name, releaseTime, day);
        } else {
            Log.e(TAG, "Couldn't find any entries with ID " + " \"" + itemId + "\"; " + "returning null.");
            return null;
        }
    }

    public AnimeItem getAnimeItemByTitle(String title) {
        String selection = AnimeTable.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {String.valueOf(title)};

        Cursor c = db.query(
                AnimeTable.TABLE_NAME,
                AnimeItemProjection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c != null && c.moveToFirst()) {
            int entryId = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_ID));
            int day = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_DAY));
            String name = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_TITLE));
            String releaseTime = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_RELEASE_TIME));

            return new AnimeItem(entryId, name, releaseTime, day);
        } else {
            Log.e(TAG, "Couldn't find any entries with title" + " \"" + title + "\"; " + "returning null.");
            return null;
        }
    }

    public List<AnimeItem> getScheduleList() {
        List<AnimeItem> list = new ArrayList<AnimeItem>();

        String selectQuery = "SELECT  * FROM " + AnimeTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            do {
                int entryId = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_ID));
                int day = c.getInt(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_DAY));
                String name = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_TITLE));
                String releaseTime = c.getString(c.getColumnIndexOrThrow(AnimeTable.COLUMN_NAME_RELEASE_TIME));

                AnimeItem anime = new AnimeItem(entryId, name, releaseTime, day);
                list.add(anime);
            } while (c.moveToNext());
        } else {
            Log.e(TAG, "No Anime items; returning empty list.");
        }

        return list;
    }

    public void saveScheduleList(List<AnimeItem> list) {
        for (AnimeItem item : list) {
            ContentValues values = new ContentValues();
            values.put(AnimeTable.COLUMN_NAME_DAY, item.getDay());
            values.put(AnimeTable.COLUMN_NAME_TITLE, item.getName());
            values.put(AnimeTable.COLUMN_NAME_RELEASE_TIME, item.getTime());

            db.insert(AnimeTable.TABLE_NAME, AnimeTable.COLUMN_NAME_NULLABLE, values);
        }
    }

    public void clearScheduleList() {
        db.execSQL("DROP TABLE IF EXISTS " + AnimeTable.TABLE_NAME);
    }
}
