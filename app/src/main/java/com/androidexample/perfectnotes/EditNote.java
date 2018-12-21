package com.androidexample.perfectnotes;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditNote extends AppCompatActivity {

    EditText descriptionET,subjectET;
    ArrayList<String> subject,description;
    int pos;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        descriptionET = findViewById(R.id.descriptionET);
        subjectET = findViewById(R.id.subjectET);
        saveButton = findViewById(R.id.saveButton);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            subject = extras.getStringArrayList("SUBJECT_LIST");
            description = extras.getStringArrayList("BODY_LIST");
            pos = extras.getInt("POS");
            descriptionET.setText(description.get(pos));
            subjectET.setText(subject.get(pos));
        }


        subjectET.addTextChangedListener(saveButtonTextChange);
        descriptionET.addTextChangedListener(saveButtonTextChange);

        //save onClick listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subjectET.getText().toString();
                String desc = descriptionET.getText().toString();
                subject.add(pos,sub);
                description.add(pos,desc);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("SUB_RESULT",subject);
                returnIntent.putExtra("DESC_RESULT",description);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }


    private TextWatcher saveButtonTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sub = subjectET.getText().toString();
            String desc = descriptionET.getText().toString();

            if(sub.isEmpty() || desc.isEmpty()){
                saveButton.setEnabled(false);
            }
            else {
                saveButton.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
