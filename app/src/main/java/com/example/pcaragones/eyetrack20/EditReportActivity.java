package com.example.pcaragones.eyetrack20;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditReportActivity extends AppCompatActivity {

    final DatabaseHandler db = new DatabaseHandler(this);
    EditText type;
    EditText remarks;
    EditText location;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.launcher);

        String curType = getIntent().getStringExtra("type");
        String curRemarks = getIntent().getStringExtra("remarks");
        String curLocation = getIntent().getStringExtra("location");
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        type = (EditText)findViewById(R.id.edit_type_text);
        remarks = (EditText)findViewById(R.id.edit_remarks_text);
        location = (EditText)findViewById(R.id.edit_location_text);
        type.setText(curType, TextView.BufferType.EDITABLE);
        remarks.setText(curRemarks, TextView.BufferType.EDITABLE);
        location.setText(curLocation, TextView.BufferType.EDITABLE);
    }

    public void cancel(View view){
        Intent i = new Intent(this,ViewReportActivity.class);
        startActivity(i);
    }

    public void editReport(View view){
        db.editReport(type.getText().toString(),remarks.getText().toString(),location.getText().toString(), id);
        Toast.makeText(getApplicationContext(), "Editing done", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ViewReportActivity.class);
        startActivity(i);
    }
}
