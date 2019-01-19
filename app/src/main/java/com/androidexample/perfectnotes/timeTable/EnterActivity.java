package com.androidexample.perfectnotes.timeTable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidexample.perfectnotes.R;


public class EnterActivity extends AppCompatActivity {
    EditText etheead;
    EditText etDescription;
    static int ind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        etheead = findViewById(R.id.etHeading);
        etDescription = findViewById(R.id.etDesscription);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ind = b.getInt("IND");
        }
        Button btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("heading", etheead.getText().toString());
                i.putExtra("description", etDescription.getText().toString());
                i.putExtra("TIME_TABLE",true);
                i.putExtra("IND",ind );

                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
