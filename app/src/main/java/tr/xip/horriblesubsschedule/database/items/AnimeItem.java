package tr.xip.horriblesubsschedule.database.items;

/**
 * Created by Hikari on 8/29/14.
 */
public class AnimeItem {
    private int databaseItemId;
    private String name;
    private String time;
    private int day;

    /**
     * @param name name of the series
     * @param time release time of the series
     */
    public AnimeItem(String name, String time, int day) {
        this.name = name;
        this.time = time;
        this.day = day;
    }

    /**
     * @param databaseItemId _ID in the database
     * @param name           name of the series
     * @param time           release time of the series
     * @param day            release day of the series
     */
    public AnimeItem(int databaseItemId, String name, String time, int day) {
        this.databaseItemId = databaseItemId;
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public int getDatabaseItemId() {
        return databaseItemId;
    }

    public void setDatabaseItemId(int id) {
        this.databaseItemId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
