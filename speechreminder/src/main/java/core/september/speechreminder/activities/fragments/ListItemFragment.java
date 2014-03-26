package core.september.speechreminder.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import core.september.speechreminder.R;
import core.september.speechreminder.activities.adapters.EventModelAdapter;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 19/03/14.
 */
public class ListItemFragment extends ListFragment{



    //private OnListItemSelectedListener mCallback;
    boolean mDualPane;
    int mCurCheckPosition = 0;
    private Event[] eventsArray;


    @Override

    public void onActivityCreated(Bundle savedState) {

        super.onActivityCreated(savedState);


        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Populate list with our static array of titles.

        List<Event> eventList = CRUD.getInstance().select(Event.class);
        eventsArray = (eventList != null && eventList.size() > 0) ?
                eventList.toArray(new Event[eventList.size()]) : new Event[0];
        setListAdapter(new EventModelAdapter(getActivity(), layout, eventsArray));

        // Check to see if we have a frame in which to embed the details

        // fragment directly in the containing UI.

        View detailsFrame = getActivity().findViewById(R.id.content_frame);

        mDualPane = detailsFrame != null

                && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedState != null) {

            // Restore last state for checked position.

            mCurCheckPosition = savedState.getInt(Config.PICKED_ITEM, 0);

        }

        if (mDualPane) {

            // In dual-pane mode, list view highlights selected item.

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            // Make sure our UI is in the correct state.

            showDetails(mCurCheckPosition);

        }

    }



    @Override

    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(Config.PICKED_ITEM, mCurCheckPosition);

    }

    @Override

    public void onListItemClick(ListView l, View v, int pos, long id) {

        getListView().setItemChecked(pos, true);
        showDetails(pos);


    }

    /**

     * Helper function to show the details of a selected item, either by

     * displaying a fragment in-place in the current UI, or starting a

     * whole new activity in which it is displayed.

     */

    void showDetails(int index) {

        mCurCheckPosition = index;

        if (mDualPane) {

            // We can display everything in-place with fragments.

            // Have the list highlight this item and show the data.

            getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.



            Fragment fr =  getFragmentManager().findFragmentById(R.id.content_frame);
            ManageItemFragment details = null == fr ? null : (ManageItemFragment)fr;

            if (details == null || details.getCurrentIndex() != mCurCheckPosition) {

                // Make new fragment to show this selection.

                details = ManageItemFragment.newInstance(index);

                // Execute a transaction, replacing any existing

                // fragment with this one inside the frame.

                FragmentTransaction ft

                        = getFragmentManager().beginTransaction();

                ft.replace(R.id.content_frame, details);

                ft.setTransition(

                        FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                ft.commit();

            }

        } else {

            // Otherwise we need to launch a new activity to display

            // the dialog fragment with selected text.

            Intent intent = new Intent();

            intent.setClass(getActivity(), DetailsActivity.class);

            intent.putExtra(Config.PICKED_ITEM, mCurCheckPosition);

            startActivity(intent);

        }

    }
}
