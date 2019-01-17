package com.androidexample.perfectnotes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditReminderActivity extends AppCompatActivity {


    TextView dateText,timeText;
    TextInputEditText labelText;
    LinearLayout dateLayout,timeLayout;
    Button saveButton;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_edit_reminder);
        dateText = findViewById(R.id.editReminderDateText);
        timeText = findViewById(R.id.editReminderTimeText);
        labelText = findViewById(R.id.editReminderLabel);
        dateLayout = findViewById(R.id.editReminderDateLayout);
        timeLayout = findViewById(R.id.editReminderTimeLayout);
        saveButton = findViewById(R.id.editReminderSaveButton);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            pos = extras.getInt("POSITION");
            dateText.setText(extras.getString("DATE"));
            timeText.setText(extras.getString("TIME"));
            labelText.setText(extras.getString("LABEL"));
        }
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> time = getCurrentSetTime();
                changeTime(time);
            }
        });

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> date = getCurrentSetDate();
                changeDate(date);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timeText.getText().toString();
                String date = dateText.getText().toString();
                String label = labelText.getText().toString();

                //creating a return intent
                Intent returnIntent = new Intent();

                returnIntent.putExtra("POSITION",pos);
                returnIntent.putExtra("DATE",date);
                returnIntent.putExtra("TIME",time);
                returnIntent.putExtra("LABEL",label);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }

    private void changeDate(ArrayList<Integer> date) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                dateText.setText(date);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditReminderActivity.this,listener,date.get(2),date.get(1) - 1,date.get(0));
        datePickerDialog.show();
    }

    private ArrayList<Integer> getCurrentSetDate() {
        String text = dateText.getText().toString();
        String[] data = text.split("/");
        ArrayList<Integer> date = new ArrayList<>();
        for(String i:data){
            date.add(Integer.parseInt(i));
        }
        Log.i("date","" + String.valueOf(date));

        return date;
    }

    public void changeTime(ArrayList<Integer> time){
        final TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minString = String.valueOf(minute);
                if(minute < 10){
                    minString = "0" + minString;
                }
                String chosenTime = String.valueOf(hourOfDay) + ":" + minString;
                timeText.setText(chosenTime);
            }

        };
        int hour = time.get(0);
        int minute = time.get(1);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditReminderActivity.this,listener,hour,minute,false);
        timePickerDialog.show();
    }

    public ArrayList<Integer> getCurrentSetTime(){
        String text = timeText.getText().toString();
        String[] data = text.split(":");
        ArrayList<Integer> time = new ArrayList<>();
        for(String i : data){
            time.add(Integer.parseInt(i));
        }
        Log.i("time","" + String.valueOf(time));
        return time;
    }

}
