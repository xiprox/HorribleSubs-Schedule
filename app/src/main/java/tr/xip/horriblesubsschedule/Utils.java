package tr.xip.horriblesubsschedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hikari on 9/4/14.
 */
public class Utils {

    /**
     * Converts UTC-7 to local time
     * @param time original release time
     * @return
     */
    public static String convertAnimeReleaseTimeToLocal(String time) {
        String[] timeParts = time.split(":");
        long timestamp;

        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0])
                + 7 // +7 to make the time UTC (from UTC-7, obviously)
                + (timeZone.getOffset(new Date().getTime()) / 1000 / 60 / 60)); // Add time difference

        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(timeZone);

        return sdf.format(calendar.getTime());
    }
}
