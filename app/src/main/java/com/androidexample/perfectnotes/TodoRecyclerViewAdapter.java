package com.androidexample.perfectnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.myViewHolder> {

    /*ArrayList<String> subject;
    ArrayList<String> description;
    Context context;
    TodoRecyclerViewAdapter(ArrayList<String> subject,ArrayList<String> description,Context context){
        this.subject = subject;
        this.description = description;
        this.context = context;
    }*/
    Context context;
    Cursor cursor;

    TodoRecyclerViewAdapter(Cursor cursor,Context context){
        this.cursor = cursor;
        this.context = context;
    }

    public TodoRecyclerViewAdapter(ArrayList<String> subjectList, ArrayList<String> descriptionList, Context context) {
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

        //holder.listElementHeader.setText(subject.get(i));
        if(!cursor.moveToPosition(i)){
            return;
        }
        String name = cursor.getString(cursor.getColumnIndex(ToDoDatabaseHelper.COL_3));
        holder.listElementHeader.setText(name);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }

        cursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
            TextView listElementHeader;
            CardView todoListCard;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            listElementHeader = itemView.findViewById(R.id.listElementHeader);
            todoListCard = itemView.findViewById(R.id.todoListCard);
            todoListCard.setOnLongClickListener(this);
            todoListCard.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            /*final int position = getAdapterPosition();

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
            deleteDialog.show();*/
            return true;
        }

        @Override
        public void onClick(View v) {
            /*int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(),EditToDo.class);
            intent.putExtra("SUBJECT_LIST",subject);
            intent.putExtra("BODY_LIST",description);
            intent.putExtra("POSITION",position);
            intent.putExtra("BODY",description.get(position));
            context.startActivity(intent);*/
            //((Activity) context).startActivityForResult(intent,2);
        }
    }

}
