package tr.xip.horriblesubsschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Hikari on 8/29/14.
 */
public class ScheduleItemsAdapter extends StickyGridHeadersSimpleArrayAdapter<ScheduleItem> {

    View rootView;
    Context context;
    List<ScheduleItem> mList;
    int mHeaderResId;
    int mItemResId;

    String[] mDaysList;

    public ScheduleItemsAdapter(Context context, List<ScheduleItem> items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        this.context = context;
        mList = items;
        mHeaderResId = headerResId;
        mItemResId = itemResId;

        mDaysList = context.getResources().getStringArray(R.array.schedule_days_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rootView = convertView;

        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.item_schedule_item, null);
        }

        TextView mAnimeName = (TextView) rootView.findViewById(R.id.item_schedule_anime_name);
        TextView mAnimeReleaseTime = (TextView) rootView.findViewById(R.id.item_schedule_anime_release_time);

        ScheduleItem item = mList.get(position);

        mAnimeName.setText(item.getName());
        mAnimeReleaseTime.setText(convertTimeToLocal(item.getTime()));

        return rootView;
    }

    private String convertTimeToLocal(String time) {
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

    @Override
    public long getHeaderId(int position) {
        return mList.get(position).getDay();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_header, null);
        }
        ScheduleItem item = mList.get(position);

        TextView mHeaderTitle = (TextView) convertView.findViewById(R.id.item_header_title);

        mHeaderTitle.setText(mDaysList[item.getDay()]);

        return convertView;
    }
}
