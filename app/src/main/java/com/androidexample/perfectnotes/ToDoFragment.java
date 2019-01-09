package com.androidexample.perfectnotes;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {

    public static final String TAG = "fragy";
    Dialog addNote;
    TextInputEditText description, subject;
public     RecyclerView todoList;
    TodoRecyclerViewAdapter todoAdapter;

   public ArrayList<String> descriptionList;
    public ArrayList<String> subjectList;


    Button add;
    SQLiteDatabase database;
    int pos;

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

//        Log.i(TAG, "onCreate: "+getArguments().getBoolean("BACK_PRESSED"));
        if (getArguments() != null) {
            if (getArguments().getBoolean("BACK_PRESSED", false)) {
                ArrayList<String> sub = getArguments().getStringArrayList("SUBJECTS");

            }
        }

        descriptionList = new ArrayList<>();
        subjectList = new ArrayList<>();
        //descriptionList = new ArrayList<>();
        //subjectList = new ArrayList<>();

        //dialog
        addNote = new Dialog(getContext());
        addNote.setCancelable(true);
        addNote.setContentView(R.layout.to_do_custom_dialog);
        ToDoDatabaseHelper helper = new ToDoDatabaseHelper(getActivity());
        database = helper.getWritableDatabase();
        pos=0;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        todoList = getView().findViewById(R.id.toDoList);
        todoList.setLayoutManager(new LinearLayoutManager(getContext()));
        todoList.setHasFixedSize(true);
        //todoList.setAdapter(new TodoRecyclerViewAdapter(subjectList,descriptionList));
        todoAdapter = new TodoRecyclerViewAdapter(subjectList, descriptionList, getContext());

        //todoAdapter = new TodoRecyclerViewAdapter(subjectList,descriptionList,getContext());
        todoAdapter = new TodoRecyclerViewAdapter(getAllItems(),getContext());

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
                        Toast.makeText(getContext(), sub + " " + desc, Toast.LENGTH_SHORT).show();
                        subjectList.add(sub);
                        descriptionList.add(desc);
                        todoAdapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        Toast.makeText(getContext()," for null pointer " ,Toast.LENGTH_SHORT).show();
                        pos++;

                        //subjectList.add(sub);
                        //descriptionList.add(desc);
                        //todoAdapter.notifyDataSetChanged();
                    }
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
                ArrayList<String> sub = data.getStringArrayListExtra("SUB_RESULT");
                ArrayList<String> desc = data.getStringArrayListExtra("DESC_RESULT");
                subjectList = sub;

                descriptionList = desc;
                TodoRecyclerViewAdapter adapter=new TodoRecyclerViewAdapter(sub,desc,getContext());
                todoList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
    public Cursor getAllItems(){
        return database.query(ToDoDatabaseHelper.TABLE_NAME,null,null,null,null,null,ToDoDatabaseHelper.COL_2);
    }

    public void addData(String subject, String description, int pos){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ToDoDatabaseHelper.COL_2,pos);
        contentValues.put(ToDoDatabaseHelper.COL_3,subject);
        contentValues.put(ToDoDatabaseHelper.COL_4,description);

        database.insert(ToDoDatabaseHelper.TABLE_NAME,null,contentValues);
        todoAdapter.swapCursor(getAllItems());
    }

}
