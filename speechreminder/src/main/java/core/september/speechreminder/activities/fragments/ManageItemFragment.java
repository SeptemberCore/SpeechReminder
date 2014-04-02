package core.september.speechreminder.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import core.september.speechreminder.R;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.config.DaysOfWeek;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 20/03/14.
 */
public class ManageItemFragment extends Fragment {
    final static String ARG_ID = "eventID";
    int mCurrentPosition = -1;
    Event selectedItem = null;
    EditText editTitle = null;
    EditText editDescription = null;
    EditText editStartDate = null;
    EditText editStartTime = null;
    CheckBox checkBoxAllDay = null;
    EditText editEndDate = null;
    EditText editEndTime = null;
    Button buttonConfirm = null;
    Button buttonDelete = null;
    CheckBox checkBoxSunday = null;
    CheckBox checkBoxMonday = null;
    CheckBox checkBoxTuesday = null;
    CheckBox checkBoxWednsesday = null;
    CheckBox checkBoxThursday = null;
    CheckBox checkBoxFriday = null;
    CheckBox checkBoxSaturday = null;

    public static ManageItemFragment newInstance(int index) {

        ManageItemFragment f = new ManageItemFragment();

        // Supply index input as an argument.

        Bundle args = new Bundle();

        args.putInt(Config.PICKED_ITEM, index);

        f.setArguments(args);

        return f;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().

        mCurrentPosition = getActivity().getIntent().getExtras().getInt(Config.PICKED_ITEM);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manage_event, container, false);



    }



    @Override
    public void onStart() {
        super.onStart();
        if(mCurrentPosition > -1) {
            updateArticleView();

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Config.PICKED_ITEM, mCurrentPosition);
    }



    public void updateArticleView() {

        List<Event> eventList = CRUD.getInstance().select(Event.class);

        if(eventList == null || eventList.size() == 0) return;

        selectedItem = eventList.get(mCurrentPosition);

        int repeatBit = selectedItem.getRepeatBit();


         editTitle = (EditText) getActivity().findViewById(R.id.editTitle);
         editDescription = (EditText) getActivity().findViewById(R.id.editDescription);
         editStartDate = (EditText) getActivity().findViewById(R.id.editStartDate);
         editStartTime = (EditText) getActivity().findViewById(R.id.editStartTime);
         checkBoxAllDay = (CheckBox) getActivity().findViewById(R.id.checkBoxSAllDay);
         editEndDate = (EditText) getActivity().findViewById(R.id.editEndDate);
         editEndTime = (EditText) getActivity().findViewById(R.id.editEndTime);

         checkBoxSunday = (CheckBox) getActivity().findViewById(R.id.checkBoxSunday);
         checkBoxMonday = (CheckBox) getActivity().findViewById(R.id.checkBoxMonday);
         checkBoxTuesday = (CheckBox) getActivity().findViewById(R.id.checkBoxTuesday);
         checkBoxWednsesday = (CheckBox) getActivity().findViewById(R.id.checkBoxWednsesday);
         checkBoxThursday = (CheckBox) getActivity().findViewById(R.id.checkBoxThursday);
         checkBoxFriday = (CheckBox) getActivity().findViewById(R.id.checkBoxFriday);
         checkBoxSaturday = (CheckBox) getActivity().findViewById(R.id.checkBoxSaturday);

        buttonConfirm = (Button) getActivity().findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUpdate();
            }
        });


        buttonDelete = (Button) getActivity().findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CRUD.getInstance().delete(selectedItem,"_id=?",""+selectedItem.get_id());
            }
        });

        //yyyy-MM-dd HH:mm:ss.SSSZ


        editTitle.setText(selectedItem.getTitle());
        editDescription.setText(selectedItem.getDescription());

        editStartDate.setText(selectedItem.getStartDate());
        editStartTime.setText(selectedItem.getStartHour());

        checkBoxAllDay.setChecked(selectedItem.isAllDay());

        editEndDate.setText(selectedItem.getEndDate());
        editEndTime.setText(selectedItem.getEndHour());

        List<DaysOfWeek> repeatDays = DaysOfWeek.getRepeating(selectedItem.getRepeatBit());

        checkBoxSunday.setChecked(repeatDays.contains(DaysOfWeek.SUNDAY));
        checkBoxMonday.setChecked(repeatDays.contains(DaysOfWeek.MONDAY));
        checkBoxTuesday.setChecked(repeatDays.contains(DaysOfWeek.TUESDAY));
        checkBoxWednsesday.setChecked(repeatDays.contains(DaysOfWeek.WEDNESDAY));
        checkBoxThursday.setChecked(repeatDays.contains(DaysOfWeek.THURSDAY));
        checkBoxFriday.setChecked(repeatDays.contains(DaysOfWeek.FRIDAY));
        checkBoxSaturday.setChecked(repeatDays.contains(DaysOfWeek.SATURDAY));

    }

    public int getCurrentIndex() {
        return mCurrentPosition;
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putLong(ARG_ID, mCurrentPosition);
    }*/

    private void toModel() {
        selectedItem.setTitle(editTitle.getText().toString());
        selectedItem.setDescription(editDescription.getText().toString());
        String startDate = editStartDate.getText().toString().concat("|").concat(editStartTime.getText().toString());
        Date sDate = selectedItem.toDate(startDate, Config.DATE_FORMAT.concat("|").concat(Config.HOUR_FORMAT));
        selectedItem.setStart(sDate);

        selectedItem.setAllDay(checkBoxAllDay.isChecked());

        String endDate = editEndDate.getText().toString().concat("|").concat(editEndTime.getText().toString());
        Date eDate = selectedItem.toDate(endDate, Config.DATE_FORMAT.concat("|").concat(Config.HOUR_FORMAT));
        selectedItem.setEnd(eDate);

        int repeatNumber = (checkBoxSunday.isChecked() ? 1 : 0) +
                (checkBoxMonday.isChecked() ? 2 : 0) +
                (checkBoxTuesday.isChecked() ? 4 : 0) +
                (checkBoxWednsesday.isChecked() ? 8 : 0) +
                (checkBoxThursday.isChecked() ? 16 : 0) +
                (checkBoxFriday.isChecked() ? 32 : 0) +
                (checkBoxSaturday.isChecked() ? 64 : 0) ;

        selectedItem.setRepeatBit(repeatNumber);


    }

    private void createUpdate() {
        toModel();
        boolean exist = CRUD.getInstance().selectById(Event.class,selectedItem.get_id()) != null;
        if (exist) {
            CRUD.getInstance().update(selectedItem,"_id=?",""+selectedItem.get_id());
        }
        else {
            CRUD.getInstance().insert(selectedItem);
        }
        selectedItem.assign();
    }

}