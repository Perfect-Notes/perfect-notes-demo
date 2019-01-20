package com.androidexample.perfectnotes;


import android.app.Activity;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.androidexample.perfectnotes.db.TodoDatabse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {


    public List<Todo> todos;
    Dialog addNote;
    TextInputEditText description, subject;
    public RecyclerView todoList;
    TodoRecyclerViewAdapter todoAdapter;


    Button add;
    TodoDatabse database;
    int pos;

    public static final String TAG="frag";
    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        addNote = new Dialog(getContext());
        addNote.setCancelable(true);
        addNote.setContentView(R.layout.to_do_custom_dialog);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        todoList = getView().findViewById(R.id.toDoList);

        database = Room.databaseBuilder(getActivity(), TodoDatabse.class, "todoDb").allowMainThreadQueries().build();
        todos = database.todoDao().getAllTodos();

        todoList.setLayoutManager(new LinearLayoutManager(getContext()));
        todoAdapter = new TodoRecyclerViewAdapter(todos, database, getContext());
        //todoAdapter = new TodoRecyclerViewAdapter(subjectList,descriptionList,getContext());
//        todoAdapter = new TodoRecyclerViewAdapter(getAllItems(),getContext());
        todoList.setAdapter(todoAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        todoList = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.to_do_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addtoDo_menu) {
            //Toast.makeText(getContext(),"Add clicked",Toast.LENGTH_SHORT).show();
            addNote.show();
            add = addNote.findViewById(R.id.addButtonCustomDialog);
            Button cancel = addNote.findViewById(R.id.cancelButtonCustomDialog);
            subject = addNote.findViewById(R.id.subjectETCustonDialog);
            description = addNote.findViewById(R.id.description);

            //text change listeners
            subject.addTextChangedListener(addNoteTextWatcher);
            description.addTextChangedListener(addNoteTextWatcher);


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNote.dismiss();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private TextWatcher addNoteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sub = subject.getText().toString();
            String desc = description.getText().toString();
            //check if both subject and description have some text written
            add.setEnabled(!sub.isEmpty() && !desc.isEmpty());

            //on click listener for add button if it is enabled
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String sub, desc;
                    try {
                        sub = subject.getText().toString();
                        desc = description.getText().toString();
                        Toast.makeText(getContext(), "ADDED -\n" + sub, Toast.LENGTH_SHORT).show();
                        todoAdapter.insertTodo(new Todo(sub,desc));


                    } catch (NullPointerException e) {
                        Toast.makeText(getContext(), " for null pointer ", Toast.LENGTH_SHORT).show();
                        pos++;
                    }

                    //subjectList.add(sub);
                    //descriptionList.add(desc);
                    //todoAdapter.notifyDataSetChanged();

//                    catch (NullPointerException e){
//                        e.printStackTrace();
//                    }
                    subject.getText().clear();
                    description.getText().clear();
                    addNote.dismiss();
                }
            });

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String sub = data.getStringExtra("SUB");
                String desc = data.getStringExtra("DESC");
                int pos = data.getIntExtra("POSITION", 0);
                todoAdapter.updateTodo(pos,new Todo(sub,desc));


//                Todo t = todos.get(pos - 1);
//                t.setDescription(desc);
//                t.setSubject(sub);
//                database.todoDao().updateTodo(t);
//
//                TodoRecyclerViewAdapter adapter = new TodoRecyclerViewAdapter(todos, database, getContext());
//                todoList.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }
        }
    }


}
