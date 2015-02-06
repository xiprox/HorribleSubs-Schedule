package tr.xip.horriblesubsschedule.database;

import java.util.List;

import tr.xip.horriblesubsschedule.models.Anime;

/**
 * Created by Hikari on 8/30/14.
 */
public class DatabaseManager {

    public static List<Anime> getScheduleList() {
        return Anime.listAll(Anime.class);
    }

    public static void saveScheduleList(List<Anime> anime) {
        Anime.deleteAll(Anime.class);
        Anime.saveInTx(anime);
    }
}
