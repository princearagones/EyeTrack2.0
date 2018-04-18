package com.example.pcaragones.eyetrack20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ViewReportActivity extends AppCompatActivity {


    AlertDialogManager alert = new AlertDialogManager();
    Button btnSubmitReport;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnSubmitReport = (Button) findViewById(R.id.submitReport);
        final DatabaseHandler db = new DatabaseHandler(this);

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

        Button submitReport = new Button(this);
        submitReport.setText("Submit Report");
        _linear.addView(submitReport);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                List<Report> reports= db.getAllReports();
                Log.d("Report count", Integer.toString(db.getReportCount()));
                for (Report cn : reports) {
                    String log = "Type: " + cn.get_type() + " ,Remarks: " + cn.get_remarks() + " ,Location Name: " + cn.get_locationName() + " ,isSubmitted: " + cn.isSubmitted()+", userid: "+user.get(SessionManager.KEY_ID);
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    if(!cn.isSubmitted()){
                        Log.d("Submit report:", "Submitting..."+cn.get_id());
//                        cn.setSubmitted(true);
                        HttpHandler h = new HttpHandler();
                        String params[] = {"/api/report/add","POST", "type", cn.get_type(), "remarks", cn.get_remarks(), "locationname", cn.get_locationName(), "locationid", "-1", "userid", user.get(SessionManager.KEY_ID)};
                        h.execute(params);

//                        db.deleteReport(cn);
//                        Report r = db.getReport(db.updateIsSubmittedReport(cn,true));
//                        Log.d("result", Integer.toString(result));
//                        Log.d("New rep", Boolean.toString(r.isSubmitted()));
                    }
                }
            }
        });
        final List<Report> reports= db.getAllReports();
        if(db.getReportCount() == 0){
            TextView tv= new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText("No reports recored");
            _linear.addView(tv);
        }else{
            for (final Report cn : reports) {
                TextView tv= new TextView(this);
                LinearLayout buttons = new LinearLayout (this);
                Button editReport = new Button(this);
                Button deleteReport = new Button(this);
                editReport.setText("Edit Report");
                editReport.setId(cn.get_id());
                deleteReport.setText("Delete Report");
                deleteReport.setId(cn.get_id());
                tv.setLayoutParams(lparams);
                tv.setText("Type: " + cn.get_type() + " \nRemarks: " + cn.get_remarks() + " \nLocation Name: " + cn.get_locationName() );

                deleteReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (Report rep : reports) {
                            if(rep.get_id() == (cn.get_id())) {
                              db.deleteReport(rep);
                                finish();
                                startActivity(getIntent());
                            }
                        }
                    }
                });

                editReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(Report rep : reports) {
                            if(rep.get_id() == (cn.get_id())){
//                                alert.showAlertDialog(ViewReportActivity.this, "Editing", "Type: " + rep.get_type() + " \nRemarks: " + rep.get_remarks() + " \nLocation Name: " + rep.get_locationName() ,false);
                                Intent i = new Intent(ViewReportActivity.this, EditReportActivity.class);
                                i.putExtra("type", cn.get_type());
                                i.putExtra("remarks", cn.get_remarks());
                                i.putExtra("location", cn.get_locationName());
                                i.putExtra("id", String.valueOf(cn.get_id()));
                                startActivity(i);
                            }

                        }
                    }
                });

                _linear.addView(tv);
                buttons.addView(editReport);
                buttons.addView(deleteReport);
                _linear.addView(buttons);
            }
        }
        this.setContentView(_scrl);
    }
}
