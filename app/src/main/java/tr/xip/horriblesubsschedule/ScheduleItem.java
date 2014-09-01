package tr.xip.horriblesubsschedule;

/**
 * Created by Hikari on 8/29/14.
 */
public class ScheduleItem {
    private String name;
    private String time;
    private int day;

    /**
     * @param name     name of the series
     * @param time     release time of the series
     */
    public ScheduleItem(String name, String time, int day) {
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }
}
