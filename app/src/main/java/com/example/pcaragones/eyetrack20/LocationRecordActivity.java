package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LocationRecordActivity extends AppCompatActivity {


    AlertDialogManager alert = new AlertDialogManager();
    Button btnSubmitLocation;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.launcher);

        btnSubmitLocation = (Button) findViewById(R.id.submitReport);
        final DatabaseHandler db = new DatabaseHandler(this);
        //dito ka prince bukas ah
//        db.addLocation(new LocationData(-1,));
        ScrollView _scrl = new ScrollView (this);
        LinearLayout _linear = new LinearLayout (this);
        _scrl.addView(_linear);

        HttpHandler h = new HttpHandler();
        // get user data from session
        session = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();



        LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        _linear.setOrientation(LinearLayout.VERTICAL);

        Button submitLocation = new Button(this);
        submitLocation.setText("Submit Location");
        _linear.addView(submitLocation);
        submitLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                List<LocationData> locations= db.getAllLocation();
                Log.d("Location count", Integer.toString(db.getLocationCount()));
                for (LocationData cn : locations) {
                    String log = "id: " + cn.get_id() + " ,time: " + cn.get_time() + " ,lat: " + cn.get_lat() + " ,long: " + cn.get_lng()+", userid: "+user.get(SessionManager.KEY_ID);
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    if(!cn.isSubmitted()){
                        Log.d("Submit location:", "Submitting..."+cn.get_id());
//                        cn.setSubmitted(true);
                        HttpHandler h = new HttpHandler();
                        String params[] = {"/api/location/add","POST", "time", cn.get_date() + " " + cn.get_time(), "latitude", String.valueOf(cn.get_lat()), "longhitude", String.valueOf(cn.get_lng()), "userid", user.get(SessionManager.KEY_ID), "isreport", "0",};
                        h.execute(params);

//                        db.deleteReport(cn);
//                        Report r = db.getReport(db.updateIsSubmittedReport(cn,true));
//                        Log.d("result", Integer.toString(result));
//                        Log.d("New rep", Boolean.toString(r.isSubmitted()));
                    }
                }
            }
        });
        final List<LocationData> locations= db.getAllLocation();
        if(db.getLocationCount() == 0){
            TextView tv= new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText("No locations recored");
            _linear.addView(tv);
        }else{
            for (final LocationData cn : locations) {
                TextView tv= new TextView(this);
                LinearLayout buttons = new LinearLayout (this);
//                Button editReport = new Button(this);
//                Button deleteReport = new Button(this);
//                editReport.setText("Edit Report");
//                editReport.setId(cn.get_id());
//                deleteReport.setText("Delete Report");
//                deleteReport.setId(cn.get_id());
                tv.setLayoutParams(lparams);
                tv.setText("Date-Time: "+ cn.get_date() +"-"+ cn.get_time() + " \nlat: " + (double)cn.get_lat() + " \nlng: " + (double)cn.get_lng() );

//                deleteReport.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        for (Report rep : reports) {
//                            if(rep.get_id() == (cn.get_id())) {
//                                db.deleteReport(rep);
//                                finish();
//                                startActivity(getIntent());
//                            }
//                        }
//                    }
//                });

//                editReport.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        for(Report rep : reports) {
//                            if(rep.get_id() == (cn.get_id())){
////                                alert.showAlertDialog(ViewReportActivity.this, "Editing", "Type: " + rep.get_type() + " \nRemarks: " + rep.get_remarks() + " \nLocation Name: " + rep.get_locationName() ,false);
//                                Intent i = new Intent(LocationRecordActivity.this, EditReportActivity.class);
//                                i.putExtra("type", cn.get_type());
//                                i.putExtra("remarks", cn.get_remarks());
//                                i.putExtra("location", cn.get_locationName());
//                                i.putExtra("id", String.valueOf(cn.get_id()));
//                                startActivity(i);
//                            }
//
//                        }
//                    }
//                });

                _linear.addView(tv);
//                buttons.addView(editReport);
//                buttons.addView(deleteReport);
//                _linear.addView(buttons);
            }
        }
        this.setContentView(_scrl);
    }
}
