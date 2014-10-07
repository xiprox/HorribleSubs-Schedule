package tr.xip.horriblesubsschedule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.List;

import tr.xip.horriblesubsschedule.database.DatabaseManager;
import tr.xip.horriblesubsschedule.database.items.AnimeItem;

/**
 * Created by Hikari on 8/29/14.
 */
public class ScheduleItemsAdapter extends StickyGridHeadersSimpleArrayAdapter<AnimeItem> {

    DatabaseManager dbMan;

    View rootView;
    Context context;
    List<AnimeItem> mList;
    int mHeaderResId;
    int mItemResId;

    String[] mDaysList;

    public ScheduleItemsAdapter(Context context, List<AnimeItem> items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        this.context = context;
        mList = items;
        mHeaderResId = headerResId;
        mItemResId = itemResId;
        dbMan = new DatabaseManager(context);

        mDaysList = context.getResources().getStringArray(R.array.schedule_days_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rootView = convertView;

        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.item_schedule_item, null);
        }

        final AnimeItem item = mList.get(position);

        TextView mAnimeName = (TextView) rootView.findViewById(R.id.item_schedule_anime_name);
        TextView mAnimeReleaseTime = (TextView) rootView.findViewById(R.id.item_schedule_anime_release_time);

        mAnimeName.setText(item.getName());
        mAnimeReleaseTime.setText(Utils.convertAnimeReleaseTimeToLocal(item.getTime()));

        return rootView;
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
        AnimeItem item = mList.get(position);

        TextView mHeaderTitle = (TextView) convertView.findViewById(R.id.item_header_title);

        mHeaderTitle.setText(mDaysList[item.getDay()]);

        return convertView;
    }
}
