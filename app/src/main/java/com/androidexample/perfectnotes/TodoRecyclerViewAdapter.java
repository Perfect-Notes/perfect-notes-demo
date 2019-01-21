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

import com.androidexample.perfectnotes.db.TodoDatabse;

import java.util.List;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.myViewHolder> {


    public static final String TAG = "recycler check";
    Context context;
    List<Todo> todos;
    TodoDatabse database;

    public TodoRecyclerViewAdapter(List<Todo> todos,TodoDatabse database, Context context) {
        this.database=database;
        this.todos = todos;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.to_do_list_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int i) {

        holder.listElementHeader.setText(todos.get(i).getSubject());
 }

    @Override
    public int getItemCount() {
        if (todos == null) {
            return 0;
        }
        return todos.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
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

            final int position = getAdapterPosition();

            final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
            deleteDialog.setCancelable(true);
            deleteDialog.setTitle("Delete Note:");
            deleteDialog.setMessage("Subject: " + todos.get(position).getSubject() + "\n\nAre you sure?");
            deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "onClick: " + todos.get(position).getId());
                    database.todoDao().deleteTodo(todos.get(position));
                    todos.remove(position);
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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), ViewToDo.class);
            intent.putExtra("POSITION", position);
            intent.putExtra("SUBJECT", todos.get(position).getSubject());
            intent.putExtra("DESC", todos.get(position).getDescription());
//            context.startActivity(intent);
            ((Activity) context).startActivityForResult(intent, 2);
        }
    }

    public Todo getTodo(int position) {
        return todos.get(position);
    }
    public void insertTodo(Todo todo){
        todos.add(todo);
        database.todoDao().insert(todo);
        Log.i(TAG, "insertTodo: "+todo.getId());
        notifyDataSetChanged();
    }
    public void updateTodo(int pos,Todo todo){
        Todo t=todos.get(pos);
        t.setSubject(todo.getSubject());
        t.setDescription(t.getDescription());
        database.todoDao().updateTodo(t);
        todos.clear();
        todos.addAll(database.todoDao().getAllTodos());
        notifyDataSetChanged();
        Log.i(TAG, "updateTodo: "+todo.getId());
    }

}
