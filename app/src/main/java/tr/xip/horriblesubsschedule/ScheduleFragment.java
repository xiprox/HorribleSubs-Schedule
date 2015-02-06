package tr.xip.horriblesubsschedule;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tr.xip.horriblesubsschedule.database.DatabaseManager;
import tr.xip.horriblesubsschedule.models.Anime;

/**
 * Created by Hikari on 8/29/14.
 */
public class ScheduleFragment extends Fragment {

    public static final String URL_HORRIBLE_SCHEDULE = "http://horriblesubs.info/release-schedule";

    public static final String HEADER_MONDAY = "<h2 class=\"weekday\">Monday</h2>";
    public static final String HEADER_TUESDAY = "<h2 class=\"weekday\">Tuesday</h2>";
    public static final String HEADER_WEDNESDAY = "<h2 class=\"weekday\">Wednesday</h2>";
    public static final String HEADER_THURSDAY = "<h2 class=\"weekday\">Thursday</h2>";
    public static final String HEADER_FRIDAY = "<h2 class=\"weekday\">Friday</h2>";
    public static final String HEADER_SATURDAY = "<h2 class=\"weekday\">Saturday</h2>";
    public static final String HEADER_SUNDAY = "<h2 class=\"weekday\">Sunday</h2>";
    public static final String HEADER_TO_BE_SCHEDULED = "<h2 class=\"weekday\">To be scheduled</h2>";

    private final String TAG = "Schedule Fragment";

    Context context;

    View rootView;
    StickyGridHeadersGridView mScheduleGrid;
    ViewFlipper mViewFlipper;

    List<Anime> mList = new ArrayList<Anime>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, null);
        context = getActivity();

        mScheduleGrid = (StickyGridHeadersGridView) rootView.findViewById(R.id.schedule_grid);
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.schedule_view_flipper);
        mViewFlipper.setInAnimation(context, R.anim.abc_fade_in);
        mViewFlipper.setOutAnimation(context, R.anim.abc_fade_out);

        new LoadTask().execute();

        return rootView;
    }

    private class LoadTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(URL_HORRIBLE_SCHEDULE).get();
                parseSchedule(document);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success)
                /* Save the list to the database for offline usage */
                DatabaseManager.saveScheduleList(mList);
            else
                /* Offline mode: Load the list from database */
                mList = DatabaseManager.getScheduleList();

            /* Check if the list is empty. In case of an empty list, display an error message */
            if (mList.size() != 0)
                mScheduleGrid.setAdapter(new ScheduleItemsAdapter(
                        context, mList, R.layout.item_header, R.layout.item_schedule_item));

            else {
                Toast.makeText(context, R.string.error_cant_load, Toast.LENGTH_LONG).show();

                Activity activity = getActivity();

                if (activity != null)
                    activity.finish();
            }

            if (mViewFlipper.getDisplayedChild() == 0)
                mViewFlipper.showNext();
        }

        private void parseSchedule(Document document) {
            Elements content = document.select("div.entry-content");
            String contentString = content.toString();

            /**
             * Since HorribleSubs' website doesn't have an HTML structure for items in a day
             * that can be parsed any easier we have to deal with strings...
             */
            String mondayContent = contentString.substring(
                    contentString.indexOf(HEADER_MONDAY) + HEADER_MONDAY.length(),
                    contentString.indexOf(HEADER_TUESDAY));
            String tuesdayContent = contentString.substring(
                    contentString.indexOf(HEADER_TUESDAY) + HEADER_TUESDAY.length(),
                    contentString.indexOf(HEADER_WEDNESDAY));
            String wednesdayContent = contentString.substring(
                    contentString.indexOf(HEADER_WEDNESDAY) + HEADER_WEDNESDAY.length(),
                    contentString.indexOf(HEADER_THURSDAY));
            String thursdayContent = contentString.substring(
                    contentString.indexOf(HEADER_THURSDAY) + HEADER_THURSDAY.length(),
                    contentString.indexOf(HEADER_FRIDAY));
            String fridayContent = contentString.substring(
                    contentString.indexOf(HEADER_FRIDAY) + HEADER_FRIDAY.length(),
                    contentString.indexOf(HEADER_SATURDAY));
            String saturdayContent = contentString.substring(
                    contentString.indexOf(HEADER_SATURDAY) + HEADER_SATURDAY.length(),
                    contentString.indexOf(HEADER_SUNDAY));
            String sundayContent = contentString.substring(
                    contentString.indexOf(HEADER_SUNDAY) + HEADER_SUNDAY.length(),
                    contentString.indexOf(HEADER_TO_BE_SCHEDULED));
            String toBeScheduledContent = contentString.substring(
                    contentString.indexOf(HEADER_TO_BE_SCHEDULED) + HEADER_TO_BE_SCHEDULED.length(),
                    contentString.length());

            Elements mondayItems = getAnimeFromDayContent(mondayContent);
            Elements tuesdayItems = getAnimeFromDayContent(tuesdayContent);
            Elements wednesdayItems = getAnimeFromDayContent(wednesdayContent);
            Elements thursdayItems = getAnimeFromDayContent(thursdayContent);
            Elements fridayItems = getAnimeFromDayContent(fridayContent);
            Elements saturdayItems = getAnimeFromDayContent(saturdayContent);
            Elements sundayItems = getAnimeFromDayContent(sundayContent);
            Elements toBeScheduledItems = getAnimeFromDayContent(toBeScheduledContent);

            for (Element element : mondayItems)
                addAnimeToList(element, 0);

            for (Element element : tuesdayItems)
                addAnimeToList(element, 1);

            for (Element element : wednesdayItems)
                addAnimeToList(element, 2);

            for (Element element : thursdayItems)
                addAnimeToList(element, 3);

            for (Element element : fridayItems)
                addAnimeToList(element, 4);

            for (Element element : saturdayItems)
                addAnimeToList(element, 5);

            for (Element element : sundayItems)
                addAnimeToList(element, 6);

            for (Element element : toBeScheduledItems)
                addAnimeToList(element, 7);
        }

        private void addAnimeToList(Element element, int day) {
            String nameWithTime = element.select("div.series-name").text();
            String name = nameWithTime.substring(0, nameWithTime.length() - 5);
            String time = element.select("span.release-time").text();

            Anime item = new Anime(name, time, day);

            mList.add(item);
        }

        private Elements getAnimeFromDayContent(String content) {
            return Jsoup.parse(content).select("div.series-name");
        }
    }
}
