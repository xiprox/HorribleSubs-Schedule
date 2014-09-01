package tr.xip.horriblesubsschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import tr.xip.horriblesubsschedule.ScheduleItem;
import tr.xip.horriblesubsschedule.database.entries.ScheduleEntry;

/**
 * Created by Hikari on 8/30/14.
 */
public class DatabaseManager {

    Context context;

    SQLiteDatabase db;

    public static final String SORT_ASCN = " "; // TODO
    public static final String SORT_DESC = " DESC";

    public DatabaseManager(Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context).getWritableDatabase();
    }

    public long addScheduleItem(ScheduleItem item) {
        ContentValues values = new ContentValues();
        values.put(ScheduleEntry.COLUMN_NAME_DAY, item.getDay());
        values.put(ScheduleEntry.COLUMN_NAME_TITLE, item.getName());
        values.put(ScheduleEntry.COLUMN_NAME_RELEASE_TIME, item.getTime());

        return db.insert(ScheduleEntry.TABLE_NAME, ScheduleEntry.COLUMN_NAME_NULLABLE, values);
    }

    public ScheduleItem getScheduleItemById() {
        String[] projection = {
                ScheduleEntry.COLUMN_NAME_ID,
                ScheduleEntry.COLUMN_NAME_DAY,
                ScheduleEntry.COLUMN_NAME_TITLE,
                ScheduleEntry.COLUMN_NAME_RELEASE_TIME
        };

        String sortOrder = ScheduleEntry.COLUMN_NAME_DAY + SORT_DESC;

        Cursor c = db.query(ScheduleEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        c.moveToFirst();
        int day = c.getInt(c.getColumnIndexOrThrow(ScheduleEntry.COLUMN_NAME_DAY));
        String name = c.getString(c.getColumnIndexOrThrow(ScheduleEntry.COLUMN_NAME_TITLE));
        String releaseTime = c.getString(c.getColumnIndexOrThrow(ScheduleEntry.COLUMN_NAME_RELEASE_TIME));

        Log.d("DAY", day +"");
        Log.d("NAME", name);
        Log.d("TIME", releaseTime);

        return new ScheduleItem(name, releaseTime, day);
    }


}
