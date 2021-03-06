package com.androidexample.perfectnotes;

import android.app.Activity;
import android.arch.core.executor.TaskExecutor;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditToDo extends AppCompatActivity {

    TextView subjectTV,descriptionTV;
    ArrayList<String> description,subject;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);
        subjectTV = findViewById(R.id.subjectHeader);
        descriptionTV = findViewById(R.id.description_edit_note);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            //String sub = extras.getString("SUBJECT");
            //String desc = extras.getString("BODY");
            subject = extras.getStringArrayList("SUBJECT_LIST");
            description = extras.getStringArrayList("BODY_LIST");
            pos = extras.getInt("POSITION");
            String sub = subject.get(pos);
            String desc = description.get(pos);
            subjectTV.setText(sub);
            descriptionTV.setText(desc);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit_note_button){
            Intent intent = new Intent(this,EditNote.class);
            intent.putExtra("SUBJECT_LIST",subject);
            intent.putExtra("BODY_LIST",description);
            intent.putExtra("POS",pos);
            startActivityForResult(intent,1);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Intent i=new Intent();

        i.putExtra("SUB_RESULT",data.getStringArrayListExtra("SUB_RESULT"));
        i.putExtra("DESC_RESULT",data.getStringArrayListExtra("DESC_RESULT"));
        i.putExtra("POS",data.getIntExtra("POS",0));
        setResult(resultCode,i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment f=new ToDoFragment();
        Bundle data=new Bundle();
        data.putStringArrayList("DESCRIPTION",description);
        data.putStringArrayList("SUBJECTS",subject);
        data.putBoolean("BACK_PRESSED",true);
        f.setArguments(data);

    }
}
