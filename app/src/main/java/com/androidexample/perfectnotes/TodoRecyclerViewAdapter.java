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

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.myViewHolder> {

    ArrayList<String> subject;
    ArrayList<String> description;
    Context context;
    TodoRecyclerViewAdapter(ArrayList<String> subject,ArrayList<String> description,Context context){
        this.subject = subject;
        this.description = description;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.to_do_list_item,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int i) {

        holder.listElementHeader.setText(subject.get(i));

    }

    @Override
    public int getItemCount() {
        return subject.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
            TextView listElementHeader;
            CardView todoListCard;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            listElementHeader = itemView.findViewById(R.id.listElementHeader);
            todoListCard = itemView.findViewById(R.id.todoListCard);
            todoListCard.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            final int position = getAdapterPosition();

            final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
            deleteDialog.setCancelable(true);
            deleteDialog.setTitle("Delete Note:");
            deleteDialog.setMessage("Subject: " + subject.get(position) + "\n\nAre you sure?");
            deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    subject.remove(position);
                    description.remove(position);
                    notifyDataSetChanged();
                    dialog.cancel();
                }
            });
            deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            deleteDialog.show();
            return true;
        }

    }
}
