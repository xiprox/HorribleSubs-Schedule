package tr.xip.horriblesubsschedule.database.tables;

import android.provider.BaseColumns;

/**
 * Created by Hikari on 8/30/14.
 */
public class AnimeTable implements BaseColumns {
    public static final String TABLE_NAME = "table_anime";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_DAY = "day";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_RELEASE_TIME = "release_time";
    public static final String COLUMN_NAME_NULLABLE = "nullable";
}
