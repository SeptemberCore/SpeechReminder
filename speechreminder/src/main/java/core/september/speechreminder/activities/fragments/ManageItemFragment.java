package core.september.speechreminder.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.List;

import core.september.speechreminder.R;
import core.september.speechreminder.config.DaysOfWeek;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 20/03/14.
 */
public class ManageItemFragment extends Fragment {
    final static String ARG_ID = "eventID";
    long mCurrentPosition = -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getLong(ARG_ID);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manage_event, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getLong(ARG_ID));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(long id) {

        Event selectedItem = (Event) CRUD.getInstance().selectById(Event.class,id);

        int repeatBit = selectedItem.getRepeatBit();


         EditText editTitle = (EditText) getActivity().findViewById(R.id.editTitle);
         EditText editDescription = (EditText) getActivity().findViewById(R.id.editDescription);
         EditText editStartDate = (EditText) getActivity().findViewById(R.id.editStartDate);
         EditText editStartTime = (EditText) getActivity().findViewById(R.id.editStartTime);
         CheckBox checkBoxAllDay = (CheckBox) getActivity().findViewById(R.id.checkBoxAllDay);
         EditText editEndDate = (EditText) getActivity().findViewById(R.id.editEndDate);
         EditText editEndTime = (EditText) getActivity().findViewById(R.id.editEndTime);
         Button buttonConfirm = (Button) getActivity().findViewById(R.id.buttonConfirm);
         CheckBox checkBoxSunday = (CheckBox) getActivity().findViewById(R.id.checkBoxSunday);
         CheckBox checkBoxMonday = (CheckBox) getActivity().findViewById(R.id.checkBoxMonday);
         CheckBox checkBoxTuesday = (CheckBox) getActivity().findViewById(R.id.checkBoxTuesday);
         CheckBox checkBoxWednsesday = (CheckBox) getActivity().findViewById(R.id.checkBoxWednsesday);
         CheckBox checkBoxThursday = (CheckBox) getActivity().findViewById(R.id.checkBoxThursday);
         CheckBox checkBoxFriday = (CheckBox) getActivity().findViewById(R.id.checkBoxFriday);
         CheckBox checkBoxSaturday = (CheckBox) getActivity().findViewById(R.id.checkBoxSaturday);

        //yyyy-MM-dd HH:mm:ss.SSSZ
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        editTitle.setText(selectedItem.getTitle());
        editDescription.setText(selectedItem.getDescription());

        editStartDate.setText(dateFormat.format(selectedItem.getStart()));
        editStartTime.setText(hourFormat.format(selectedItem.getStart()));

        checkBoxAllDay.setChecked(selectedItem.isAllDay());

        editEndDate.setText(dateFormat.format(selectedItem.getEnd()));
        editEndTime.setText(hourFormat.format(selectedItem.getEnd()));

        mCurrentPosition = id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putLong(ARG_ID, mCurrentPosition);
    }

}