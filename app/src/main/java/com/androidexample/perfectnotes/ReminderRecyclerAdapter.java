package com.androidexample.perfectnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.myViewHolder>  {


    ArrayList<String> dateList;
    ArrayList<String> timeList;
    ArrayList<String> label;
    Context context;


    ReminderRecyclerAdapter(ArrayList<String> date,ArrayList<String> time,ArrayList<String> label,Context context){
        this.dateList = date;
        this.timeList = time;
        this.context = context;
        this.label = label;
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
        myViewHolder.dateText.setText(dateList.get(i));
        myViewHolder.timeText.setText(timeList.get(i));
        myViewHolder.reminderHeading.setText(label.get(i));

    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView timeText,dateText,reminderHeading;
        CardView reminderCardLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.time_text);
            dateText = itemView.findViewById(R.id.calendar_data);
            reminderHeading = itemView.findViewById(R.id.reminderLabel);
            reminderCardLayout = itemView.findViewById(R.id.reminderCardLayout);

            reminderCardLayout.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            final int position = getAdapterPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Are you Sure?");
            builder.setMessage("Delete reminder: " + label.get(position) + "?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dateList.remove(position);
                    timeList.remove(position);
                    label.remove(position);
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
    }
}
