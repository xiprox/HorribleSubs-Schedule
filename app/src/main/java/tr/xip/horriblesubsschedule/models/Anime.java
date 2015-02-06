package tr.xip.horriblesubsschedule.models;

import com.orm.SugarRecord;

/**
 * Created by Hikari on 8/29/14.
 */
public class Anime extends SugarRecord<Anime> {
    private String name;
    private String time;
    private int day;

    public Anime() {
        /* empty */
    }

    /**
     * @param name name of the series
     * @param time release time of the series
     */
    public Anime(String name, String time, int day) {
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
