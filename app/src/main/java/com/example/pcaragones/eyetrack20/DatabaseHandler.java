package com.example.pcaragones.eyetrack20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

//import java.sql.Date;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcaragones on 7/8/17.
 */

class DatabaseHandler  extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "EyeGuard";

    // Contacts table name
    private static final String TABLE_LOCATION = "location";
    private static final String TABLE_REPORT = "report";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_USERID = "userid";
    private static final String KEY_ISREPORT = "isreport";
    private static final String KEY_TYPE = "type";
    private static final String KEY_REMARKS = "remarks";
    private static final String KEY_LOCATION_NAME = "locationname";
    private static final String KEY_DATE_SUBMITTED = "datesubmitted";
    private static final String KEY_ISSUBMITTED = "issubmitted";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " DATETIME, " + KEY_TIME + "  DATETIME,"
                + KEY_LAT + " TEXT," + KEY_LNG + " TEXT,"+ KEY_USERID + " TEXT,"
                + KEY_ISREPORT+ " INT" + " TEXT," + KEY_ISSUBMITTED+ " INT" +")";
        db.execSQL(CREATE_LOCATION_TABLE);

        String CREATE_REPORT_TABLE = "CREATE TABLE " + TABLE_REPORT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT,"
                + KEY_REMARKS + " TEXT," + KEY_LOCATION_NAME + " TEXT,"
                + KEY_DATE_SUBMITTED + " NUMERIC,"+ KEY_USERID + " TEXT," + KEY_ISSUBMITTED+ " INT" + ")";
        db.execSQL(CREATE_REPORT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);

        // Create tables again
        onCreate(db);
    }

    public void addLocation(LocationData location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, location.get_time().toString()); // Location time
        values.put(KEY_DATE, location.get_date().toString());
        values.put(KEY_LAT, String.valueOf(location.get_lat())); // location lat
        values.put(KEY_LNG, String.valueOf(location.get_lng()));
        values.put(KEY_USERID, location.get_userid());
        values.put(KEY_ISREPORT, location.isReport());

        // Inserting Row
        db.insert(TABLE_LOCATION, null, values);
        Log.d("debugh", "done adding location");
        db.close(); // Closing database connection
    }

    public void addReport(Report report) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, report.get_type());
        values.put(KEY_DATE_SUBMITTED, report.getDateSubmitted().toString()); // Location time
        values.put(KEY_REMARKS, report.get_remarks()); // location lat
        values.put(KEY_LOCATION_NAME, report.get_locationName());
        values.put(KEY_USERID, report.get_userid());
        values.put(KEY_ISSUBMITTED, report.isSubmitted());

        // Inserting Row
        db.insert(TABLE_REPORT, null, values);
        db.close(); // Closing database connection
    }

    public void editReport(String type, String remarks, String location, int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, type);
//        values.put(KEY_DATE_SUBMITTED, report.getDateSubmitted().toString()); // Location time
        values.put(KEY_REMARKS, remarks); // location lat
        values.put(KEY_LOCATION_NAME, location);
//        values.put(KEY_USERID, report.get_userid());
//        values.put(KEY_ISSUBMITTED, report.isSubmitted());

        // Inserting Row
        db.update(TABLE_REPORT, values,"id="+id ,null);
        db.close(); // Closing database connection
    }

//    public LocationData getLocation(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_LOCATION, new String[] { KEY_ID,
//                        KEY_TIME, KEY_LAT, KEY_LNG, KEY_USERID, KEY_ISREPORT }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        LocationData location = new LocationData(Integer.parseInt(cursor.getString(0)),
//                Date.valueOf(cursor.getString(1)),
//                Long.parseLong(cursor.getString(2)),
//                Long.parseLong(cursor.getString(3)),
//                cursor.getString(4), Boolean.parseBoolean(cursor.getString(5)));
//        // return contact
//        return location;
//    }

    public Report getReport(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REPORT, new String[] { KEY_ID,
                        KEY_TYPE, KEY_REMARKS, KEY_LOCATION_NAME, KEY_DATE_SUBMITTED, KEY_ISSUBMITTED, KEY_USERID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Report report = new Report(Integer.parseInt(cursor.getString(0)),
                (cursor.getString(1)), (cursor.getString(2)), (cursor.getString(3)),
                new java.util.Date(cursor.getString(4)), (cursor.getString(5)),Boolean.parseBoolean(cursor.getString(6)));
        // return contact
        return report;
    }

    public List<LocationData> getAllLocation() {
        List<LocationData> locationList = new ArrayList<LocationData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationData location = new LocationData();
                location.set_id(Integer.parseInt(cursor.getString(0)));
                Log.d("STRING ID", cursor.getString(0));
                Log.d("STRING TIME", cursor.getString(1));
                Log.d("STRING DATE", cursor.getString(2));
                Log.d("STRING LAT", cursor.getString(3));
                Log.d("STRING LNG", cursor.getString(4));
                Log.d("STRING USER", cursor.getString(5));
                location.set_date(Date.valueOf(cursor.getString(1)));
                location.set_time(Time.valueOf(cursor.getString(2)));
                location.set_lat(Double.parseDouble(cursor.getString(3)));
                location.set_lng(Double.parseDouble(cursor.getString(4)));
                location.set_userid(cursor.getString(5));
                location.setReport(Boolean.parseBoolean(cursor.getString(6)));
                // Adding contact to list
//                Log.d("is it real", String.valueOf(location.get_lat()));
                locationList.add(location);
            } while (cursor.moveToNext());
        }

        // return contact list
        return locationList;
    }

    public List<Report> getAllReports() {
        List<Report> reportList = new ArrayList<Report>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.set_id(Integer.parseInt(cursor.getString(0)));
                report.set_type(cursor.getString(1));
                report.set_remarks(cursor.getString(2));
                report.set_locationName(cursor.getString(3));
                report.setDateSubmitted(new java.util.Date(cursor.getString(4)));
                report.setSubmitted(Boolean.parseBoolean(cursor.getString(5)));
                report.set_userid(cursor.getString(6));
                // Adding contact to list
                reportList.add(report);
            } while (cursor.moveToNext());
        }

        // return contact list
        return reportList;
    }

    public int updateIsSubmittedReport(Report report, boolean b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ISSUBMITTED, b);

        // updating row
        int res =  db.update(TABLE_REPORT, values, KEY_ID + " = ?", new String[] { String.valueOf(report.get_id()) });
        Log.d("Updating", "updating..."+ Integer.toString(res));
        return res;
    }

    public void deleteReport(Report report) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, KEY_ID + " = ?",
                new String[] { String.valueOf(report.get_id()) });
        db.close();
    }

    public int getReportCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getLocationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}