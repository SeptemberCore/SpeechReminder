package core.september.speechreminder.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import core.september.speechreminder.R;
import core.september.speechreminder.activities.fragments.ListItemFragment;

/**
 * Created by christian on 19/03/14.
 */
public class LeftMenuAdapter extends ArrayAdapter<Integer> {
    final private int[] icons = new int[]{R.drawable.ic_action_settings, R.drawable.ic_location_web_site, R.drawable.ic_content_email};
    final private int[] texts = new int[]{R.string.action_settings, R.string.location_web_site, R.string.content_email};
    private Activity context;
    private Integer[] positions;
    private ListItemFragment.UpdateListener mListener;

    public LeftMenuAdapter(Activity context, int layout, Integer[] positions) {
        super(context, R.layout.left_drawer_row, positions);
        this.context = context;
        this.positions = positions;
        /*try {
            mListener = (ListItemFragment.UpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }*/
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.left_drawer_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            /*viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.ImageView01);*/
            viewHolder.icon = (ImageView) rowView.findViewById(R.id.menu_icon);
            viewHolder.menuEntry = (TextView) rowView.findViewById(R.id.menu_entry);
            //viewHolder.checkBoxSAllDay = (CheckBox) rowView.findViewById(R.id.checkBoxSAllDay);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.icon.setImageDrawable(context.getResources().getDrawable(icons[position]));
        holder.menuEntry.setText(context.getResources().getString(texts[position]));


        return rowView;
    }

    static class ViewHolder {

        public ImageView icon;
        public TextView menuEntry;


    }
}
