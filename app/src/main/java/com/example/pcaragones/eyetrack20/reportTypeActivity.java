package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class reportTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_type);

    }
    public void createReportBrokenFacilities(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Broken Facilities");
        startActivity(i);
    }
    public void createReportCarAccident(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Car Accident");
        startActivity(i);
    }
    public void createReportMissingObjects(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Missing Object");
        startActivity(i);
    }
    public void createReportParking(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Parking Issues");
        startActivity(i);
    }
    public void createReportSuspiciousPerson(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Suspicious Person");
        startActivity(i);
    }
    public void createReportOthers(View view) {
        Intent i = new Intent(getApplicationContext(), ReportActivity.class);
        i.putExtra("report_type","Others");
        startActivity(i);
    }
}
