package com.androidexample.perfectnotes;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

    FloatingActionButton addFab;
    Dialog addReminderDialog;
    TextView date,time;
    Calendar calendar;
    Button addButton,cancelButton;
    String chosenDate,chosenTime;
    ReminderRecyclerAdapter reminderRecyclerAdapter;
    RecyclerView reminderRecycler;
    TextInputEditText reminderDialogLabel;


    //ArrayList<String> dateList,timeList,labelList;
    List<Reminder> reminders;

    ReminderDatabase database;
    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //dateList = new ArrayList<>();
        //timeList = new ArrayList<>();
        //labelList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_reminder,container,false);
        addFab = view.findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //dialog
        addReminderDialog = new Dialog(getContext());
        addReminderDialog.setContentView(R.layout.reminder_custom_dialog);
        addReminderDialog.setCancelable(true);


        date = addReminderDialog.findViewById(R.id.alertDialogDateTV);
        time = addReminderDialog.findViewById(R.id.alertDialogTimeTV);
        reminderDialogLabel = addReminderDialog.findViewById(R.id.reminderDialogLabel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = Room.databaseBuilder(getActivity(),ReminderDatabase.class,"ReminderDb").allowMainThreadQueries().build();
        reminders = database.reminderDoa().getAllReminders();

        //recycler view
        reminderRecycler = getView().findViewById(R.id.reminderRecycler);
        reminderRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reminderRecycler.setHasFixedSize(true);
        reminderRecyclerAdapter = new ReminderRecyclerAdapter(reminders,database,getContext());
        reminderRecycler.setAdapter(reminderRecyclerAdapter);


    }

    public void addReminder(){


        addReminderDialog.show();
        LinearLayout alertDialogTimeLayout,alertDialogDateLayout;

        alertDialogDateLayout = addReminderDialog.findViewById(R.id.alertDialogDateLayout);
        alertDialogTimeLayout = addReminderDialog.findViewById(R.id.alertDialogTimeLayout);

        alertDialogDateLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        alertDialogTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        addButton = addReminderDialog.findViewById(R.id.reminderDialogAddButton);
        cancelButton = addReminderDialog.findViewById(R.id.reminderDialogCancelButton);

        //check whether all info is filled before add button is enabled


        //add the items
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reminderLabel = reminderDialogLabel.getText().toString();
                reminderRecyclerAdapter.insertReminder(new Reminder(chosenDate,chosenTime,reminderLabel));
                //clear all the text inside the alert dialog
                date.setText(R.string.sample_date);
                time.setText(R.string.sample_time);
                reminderDialogLabel.setText("");

                addReminderDialog.dismiss();
                //notify dataset change in adapter
                reminderRecyclerAdapter.notifyDataSetChanged();


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText(R.string.sample_date);
                time.setText(R.string.sample_time);
                reminderDialogLabel.setText("");
                addReminderDialog.dismiss();
            }
        });
    }

    public void setDate(){
        calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String chosenDate = String.valueOf(dayOfMonth)+'/' + String.valueOf(month + 1) + '/' + String.valueOf(year);
                date.setText(chosenDate);
                setChosenDate(chosenDate);

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),listener,y,m,d);
        datePickerDialog.show();
    }
    //setter function
    public void setChosenDate(String chosenDate){
        this.chosenDate = chosenDate;
    }

    public void setTime(){
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener listener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minString = String.valueOf(minute);
                if(minute < 10){
                    minString = "0" + minString;
                }
                String chosenTime = String.valueOf(hourOfDay) + ':' + minString;
                time.setText(chosenTime);
                setChosenTime(chosenTime);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),listener,hour,min,false);
        timePickerDialog.show();
    }

    //setter function
    public void setChosenTime(String chosenTime){
        this.chosenTime = chosenTime;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            if(resultCode == Activity.RESULT_OK){
                String time = data.getStringExtra("TIME");
                String date = data.getStringExtra("DATE");
                String label = data.getStringExtra("LABEL");
                int pos = data.getIntExtra("POSITION",0);
                reminderRecyclerAdapter.updateReminder(pos,new Reminder(date,time,label));
                Log.i("updated?","yes");
            }
        }
    }
}
