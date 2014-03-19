package core.september.speechreminder.activities.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.List;

import core.september.speechreminder.activities.adapters.EventModelAdapter;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 19/03/14.
 */
public class ListItemFragment extends ListFragment{

    public interface OnListItemSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onListItemSelected(int position);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
        List<Event> eventList = CRUD.getInstance().select(Event.class);
        Event[] eventsArray = (eventList != null && eventList.size() > 0) ?
                eventList.toArray(new Event[eventList.size()]) : new Event[0];
        setListAdapter(new EventModelAdapter(getActivity(), layout, eventsArray));
    }
}
