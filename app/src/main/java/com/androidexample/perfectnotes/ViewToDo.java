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

public class ViewToDo extends AppCompatActivity {

    TextView subjectTV, descriptionTV;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);
        subjectTV = findViewById(R.id.subjectHeader);
        descriptionTV = findViewById(R.id.description_edit_note);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String sub = extras.getString("SUBJECT");
            String desc = extras.getString("DESC");
            pos = extras.getInt("POSITION");


            subjectTV.setText(sub);
            descriptionTV.setText(desc);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_note_button) {
            Intent intent = new Intent(this, EditNote.class);
            intent.putExtra("POSITION", pos);
            intent.putExtra("SUB", subjectTV.getText().toString());
            intent.putExtra("DESC", descriptionTV.getText().toString());
            startActivityForResult(intent, 1);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent();
        i.putExtra("SUB", data.getStringExtra("SUB"));
        i.putExtra("DESC", data.getStringExtra("DESC"));
        i.putExtra("POSITION", data.getIntExtra("POSITION", 0));
        setResult(Activity.RESULT_OK,i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment f = new ToDoFragment();
        Bundle data = new Bundle();

        data.putBoolean("BACK_PRESSED", true);
        f.setArguments(data);

    }
}
