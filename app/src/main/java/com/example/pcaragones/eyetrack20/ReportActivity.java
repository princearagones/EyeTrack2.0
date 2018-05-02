package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.HashMap;

public class ReportActivity extends AppCompatActivity {

    EditText txtType, txtRemarks, txtLocation;
    String report_type;

    SessionManager session;

    Button btnCreateReport;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.launcher);

        btnCancel = (Button) findViewById(R.id.cancelReport);
        btnCreateReport = (Button) findViewById(R.id.editReport);

        report_type = getIntent().getStringExtra("report_type");


        final DatabaseHandler db = new DatabaseHandler(this);

        session = new SessionManager(getApplicationContext());

        txtType = (EditText) findViewById(R.id.edit_type_text);
        txtRemarks = (EditText) findViewById(R.id.edit_remarks_text);
        txtLocation = (EditText) findViewById(R.id.location);
        txtType.setText(report_type);
        txtType.setEnabled(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        btnCreateReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String type = txtType.getText().toString();
                String remarks = txtRemarks.getText().toString();
                String location = txtLocation.getText().toString();

                HashMap<String, String> user = session.getUserDetails();
                String userid = user.get(SessionManager.KEY_ID);
                db.addReport(new Report(type, remarks, location, new java.util.Date(), userid, false));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
