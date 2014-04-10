package core.september.speechreminder.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import core.september.speechreminder.R;
import core.september.speechreminder.activities.SpeechReminderActivity;
import core.september.speechreminder.activities.adapters.EventModelAdapter;
import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 19/03/14.
 */
public class ListItemFragment extends ListFragment{



    //private OnListItemSelectedListener mCallback;
    //boolean mDualPane;
    //long mCurId = -1;
    private Event[] eventsArray;
    private UpdateListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UpdateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override

    public void onActivityCreated(Bundle savedState) {

        super.onActivityCreated(savedState);


        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Populate list with our static array of titles.

        List<Event> eventList = CRUD.getInstance().select(Event.class);
        eventsArray = (eventList != null && eventList.size() > 0) ?
                eventList.toArray(new Event[eventList.size()]) : new Event[0];

        EventModelAdapter adapter = new EventModelAdapter(getActivity(), layout, eventsArray);
        setListAdapter(new EventModelAdapter(getActivity(), layout, eventsArray));


        // Check to see if we have a frame in which to embed the details

        // fragment directly in the containing UI.

        //View detailsFrame = getActivity().findViewById(R.id.content_frame);

        /*mDualPane = detailsFrame != null

                && detailsFrame.getVisibility() == View.VISIBLE;*/



       /* if (mDualPane) {

            // In dual-pane mode, list view highlights selected item.

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);

            // Make sure our UI is in the correct state.



        }*/


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.list_item_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }

    
/*
    @Override
	public void onDestroyView() {
		super.onDestroyView();
		ListItemFragment f = (ListItemFragment) getFragmentManager()
                                         .findFragmentById(R.id.list_item_fragment);
    if (f != null) 
        getFragmentManager().beginTransaction().remove(f).commit();
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.map, container, false);
        } catch (InflateException e) {
        */
/* map is already there, just return view as it is *//*

        }
        return view;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                Event newEvent = new Event();
                long id = CRUD.getInstance().insert(newEvent);
                //mCurCheckPosition = CRUD.getInstance().select(Event.class).size();

                SpeechReminder.getInstance().selectedEvent = (Event) CRUD.getInstance().selectById(Event.class,id);

                mListener.onUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override

    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        //outState.putLong(Config.EXTRA_FIELD, mCurId);

    }

    /**

     * Helper function to show the details of a selected item, either by

     * displaying a fragment in-place in the current UI, or starting a

     * whole new activity in which it is displayed.

     */

    public void showDetails() {

       // mCurCheckPosition = index;

/*        if (mDualPane) {

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

        } else {*/

        // Otherwise we need to launch a new activity to display

        // the dialog fragment with selected text.

        //List<Event> eventList = CRUD.getInstance().select(Event.class);
        //mCurId = id;

        Intent intent = new Intent();

        intent.setClass(getActivity(), SpeechReminderActivity.DetailsActivity.class);

       // intent.putExtra(Config.EXTRA_FIELD, mCurId);

        startActivity(intent);

        // }

    }

    /* @Override

     public void onListItemClick(ListView l, View v, int pos, long id) {

         getListView().setItemChecked(pos, true);
         showDetails(pos);


     }
 */
    public interface UpdateListener {
        void onUpdate();

        void onElementClicked();
    }
}
