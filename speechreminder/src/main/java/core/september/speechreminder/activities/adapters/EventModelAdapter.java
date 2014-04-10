package core.september.speechreminder.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import core.september.android.basement.Util.Logger;
import core.september.speechreminder.R;
import core.september.speechreminder.activities.fragments.ListItemFragment;
import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.DaysOfWeek;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 19/03/14.
 */
public class EventModelAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private Event[] events;
    private ListItemFragment.UpdateListener mListener;


    public EventModelAdapter(Activity context, int layout, Event[] events) {
        super(context, R.layout.item_row, events);
        this.context = context;
        this.events = events;
        try {
            mListener = (ListItemFragment.UpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            /*viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.ImageView01);*/
            viewHolder.eventDate = (TextView) rowView.findViewById(R.id.eventDate);
            viewHolder.eventTitle = (TextView) rowView.findViewById(R.id.eventTitle);
            viewHolder.checkBoxSunday = (CheckBox) rowView.findViewById(R.id.checkBoxSunday);
            viewHolder.checkBoxMonday = (CheckBox) rowView.findViewById(R.id.checkBoxMonday);
            viewHolder.checkBoxTuesday = (CheckBox) rowView.findViewById(R.id.checkBoxTuesday);
            viewHolder.checkBoxWednsesday = (CheckBox) rowView.findViewById(R.id.checkBoxWednsesday);
            viewHolder.checkBoxThursday = (CheckBox) rowView.findViewById(R.id.checkBoxThursday);
            viewHolder.checkBoxFriday = (CheckBox) rowView.findViewById(R.id.checkBoxFriday);
            viewHolder.checkBoxSaturday = (CheckBox) rowView.findViewById(R.id.checkBoxSaturday);

            viewHolder.checkBoxSAllDay = (CheckBox) rowView.findViewById(R.id.checkBoxSAllDay);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        final Event selectedItem = events[position];
        int repeatBit = selectedItem.getRepeatBit();
        holder.eventDate.setText(selectedItem.toRowLabel());
        holder.eventTitle.setText(selectedItem.getTitle());
        holder.checkBoxSAllDay.setChecked(selectedItem.isAllDay());
        if (repeatBit > 0) {
            List<DaysOfWeek> repeatDays = DaysOfWeek.getRepeating(repeatBit);
            for (DaysOfWeek day : repeatDays) {
                holder.getByDayOfWeek(day).setChecked(true);
            }
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.debug(this, new Throwable("" + position));
                SpeechReminder.getInstance().selectedEvent = selectedItem;
                mListener.onElementClicked();
            }
        });

        return rowView;
    }

    static class ViewHolder {

        public TextView eventDate;
        public TextView eventTitle;
        public CheckBox checkBoxSAllDay;
        public CheckBox checkBoxSunday;
        public CheckBox checkBoxMonday;
        public CheckBox checkBoxTuesday;
        public CheckBox checkBoxWednsesday;
        public CheckBox checkBoxThursday;
        public CheckBox checkBoxFriday;
        public CheckBox checkBoxSaturday;

        public CheckBox getByDayOfWeek(DaysOfWeek input) {
            CheckBox ret = null;
            switch (input) {
                case SUNDAY:
                    ret = checkBoxSunday;
                    break;
                case MONDAY:
                    ret = checkBoxMonday;
                    break;
                case TUESDAY:
                    ret = checkBoxTuesday;
                    break;
                case WEDNESDAY:
                    ret = checkBoxWednsesday;
                    break;
                case THURSDAY:
                    ret = checkBoxThursday;
                    break;
                case FRIDAY:
                    ret = checkBoxFriday;
                    break;
                case SATURDAY:
                    ret = checkBoxSaturday;
                    break;

            }

            return ret;
        }

    }
}
