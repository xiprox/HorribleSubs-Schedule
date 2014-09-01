package tr.xip.horriblesubsschedule;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hikari on 8/29/14.
 */
public class OfflineDataManager {

    public static final String KEY_SCHEDULE_HTML = "schedule_html";

    Context context;
    SharedPreferences data;
    SharedPreferences.Editor dataeditor;

    public OfflineDataManager(Context context) {
        this.context = context;
        data = context.getSharedPreferences("offline_data", 0);
        dataeditor = data.edit();
    }

    public void saveScheduleHTML(String HTML) {
        dataeditor.putString(KEY_SCHEDULE_HTML, HTML).commit();
    }

    public String getScheduleHTML() {
        return data.getString(KEY_SCHEDULE_HTML, "");
    }
}
