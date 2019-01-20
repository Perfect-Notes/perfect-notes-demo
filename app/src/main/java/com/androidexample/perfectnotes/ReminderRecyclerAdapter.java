package com.androidexample.perfectnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidexample.perfectnotes.reminderDb.Reminder;
import com.androidexample.perfectnotes.reminderDb.ReminderDatabase;

import java.util.List;


public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.myViewHolder>  {


    //ArrayList<String> dateList;
    //ArrayList<String> timeList;
    //ArrayList<String> label;
    List<Reminder> reminders;
    Context context;
    ReminderDatabase database;

    ReminderRecyclerAdapter(List<Reminder> reminders, ReminderDatabase database, Context context){
        this.reminders = reminders;
        this.database = database;
        this.context = context;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reminder_list_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.timeText.setText(reminders.get(i).getTime());
        myViewHolder.dateText.setText(reminders.get(i).getDate());
        myViewHolder.reminderHeading.setText(reminders.get(i).getLabel());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
        TextView timeText,dateText,reminderHeading;
        CardView reminderCardLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.time_text);
            dateText = itemView.findViewById(R.id.calendar_data);
            reminderHeading = itemView.findViewById(R.id.reminderLabel);
            reminderCardLayout = itemView.findViewById(R.id.reminderCardLayout);

            reminderCardLayout.setOnLongClickListener(this);
            reminderCardLayout.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            final int position = getAdapterPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Are you Sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //removing the reminder from the database
                    database.reminderDoa().deleteReminder(reminders.get(position));
                    //deleting the reminder from the reminderList
                    reminders.remove(position);

                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(),EditReminderActivity.class);
            intent.putExtra("POSITION",position);
            intent.putExtra("LABEL",reminders.get(position).label);
            intent.putExtra("DATE",reminders.get(position).date);
            intent.putExtra("TIME",reminders.get(position).time);
            Log.i("onClick","button pressed");
            ((Activity) context).startActivityForResult(intent,3);
        }
    }

    public void insertReminder(Reminder reminder){
        reminders.add(reminder);
        database.reminderDoa().insert(reminder);
        Log.i("recycler","inserted" + reminder.getLabel());
        notifyDataSetChanged();
    }

    public void updateReminder(int pos, Reminder reminder){
        Reminder rem = reminders.get(pos);
        rem.setDate(reminder.getDate());
        rem.setTime(reminder.getTime());
        rem.setLabel(reminder.getLabel());
        database.reminderDoa().updateReminder(rem);
        reminders.clear();
        reminders.addAll(database.reminderDoa().getAllReminders());
        notifyDataSetChanged();
    }
}
