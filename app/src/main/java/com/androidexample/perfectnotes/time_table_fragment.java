package com.androidexample.perfectnotes;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidexample.perfectnotes.dbTimeTable.TimeTableDatabase;
import com.androidexample.perfectnotes.timeTable.EnterActivity;

import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class time_table_fragment extends Fragment {
    Button btnClicked;
    String[] colors = {"#BFAFB2", "#FF5470", "#FFDB00", "#87FF2A", "#9DE093", "#2E5894", "#00CC99", "#778BA5", "#DB91EF", "#FFCFF1", "#FCD667", "#FFDF46"};
    int clicked;
    android.support.v7.widget.GridLayout gridLayout;
    List<TimeTable> timeTablesList;
    TimeTableDatabase database;

    public time_table_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_table_fragment, container, false);
        gridLayout = view.findViewById(R.id.gridLayout);
    return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int child = gridLayout.getChildCount();
        database = Room.databaseBuilder(getActivity(), TimeTableDatabase.class, "timetable.db").allowMainThreadQueries().build();

        timeTablesList = database.timeTableDao().getAllTimeTables();

        if (database.timeTableDao().getAllTimeTables().isEmpty()) {
            for (int i = 0; i < child; i++) {
                View view = gridLayout.getChildAt(i);
                if (view instanceof Button) {
                    Random random = new Random();
                    TimeTable t = new TimeTable("TAP TO ADD", i);
                    timeTablesList.add(t);
                    database.timeTableDao().insert(t);
                    int index = random.nextInt(colors.length - 1);
                    view.setBackgroundColor(Color.parseColor(colors[index]));
                    ((Button) view).setText("TAP TO ADD");
                    ((Button) view).setTextSize(18);
                    ((Button) view).setTextColor(Color.parseColor("#D3D3D3"));

                }
            }
        } else {
            timeTablesList = database.timeTableDao().getAllTimeTables();
            int ind = 0;
            for (int i = 0; i < child; i++) {
                View view = gridLayout.getChildAt(i);
                if (view instanceof Button) {
                    Random random = new Random();
                    TimeTable t = timeTablesList.get(ind);

                    int index = random.nextInt(colors.length - 1);
                    view.setBackgroundColor(Color.parseColor(colors[index]));
                    if (!t.getSubject().equals("TAP TO ADD")) {
                        ((Button) view).setText(t.getSubject());
                        ((Button) view).setTextColor(Color.parseColor("#000000"));

                    }else {
                        ((Button) view).setText(t.getSubject());
                        ((Button) view).setTextSize(18);
                        ((Button) view).setTextColor(Color.parseColor("#D3D3D3"));
                    }
                    ind++;

                }
            }
        }
        for (int i = 0; i < child; i++) {

            View view = gridLayout.getChildAt(i);

            if (view instanceof Button) {
                btnClicked = (Button) view;
                final int finalI = i;

                btnClicked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked = finalI;
                        Intent i = new Intent(getActivity(), EnterActivity.class);

                        startActivityForResult(i, 1);
                    }
                });

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String s = data.getStringExtra("heading");
                View btn = (Button) gridLayout.getChildAt(clicked);
                for (int i = 0; i <timeTablesList.size() ; i++) {
                    if (timeTablesList.get(i).getId()==clicked){
                        TimeTable t=timeTablesList.get(i);
                        t.setSubject(s);
                        t.setId(t.getId());
                        database.timeTableDao().updateTimeTable(t);
                    }
                }
                ((Button) btn).setText(s);
                ((Button) btn).setTextColor(Color.parseColor("#000000"));

            }
        }
    }
}
