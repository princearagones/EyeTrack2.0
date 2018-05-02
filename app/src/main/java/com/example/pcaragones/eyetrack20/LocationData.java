package com.example.pcaragones.eyetrack20;

import android.util.Log;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by pcaragones on 7/8/17.
 */

class LocationData {
    private int _id;
    private java.sql.Time _time;
    private java.sql.Date _date;
    private double _lat;
    private double _lng;
    private String _userid;
    private boolean isSubmitted;
    private boolean isReport;

    public LocationData(){}

    public LocationData(java.util.Date date, long latitude, long longitude, String userid, boolean isReport) {
    }

    public LocationData(Time _time, long _lat, long _lng, String _userid, boolean _isReport) {
        this._time = _time;
        this._lat = _lat;
        this._lng = _lng;
        this._userid = _userid;
        this.isReport = _isReport;
    }


    public LocationData(java.util.Date _utilDate, double _lat, double _lng, String _userid, boolean isSubmitted, boolean isReport) {
        this._time = new java.sql.Time(_utilDate.getTime());
        Log.d("showtimeoninit", String.valueOf(_time));
        this._date = new java.sql.Date(_utilDate.getTime());
        this._lat = _lat;
        this._lng = _lng;
        this._userid = _userid;
        this.isSubmitted = isSubmitted;
        this.isReport = isReport;
    }
    private String formatTime(long millis, int GMT) {
        String output = "00:00:00";
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60 + GMT;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;
        if (hours < 10)
            hoursD = "0" + hours;

        output = hoursD + " : " + minutesD + " : " + secondsD;
        return output;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Time get_time() {
        return this._time;
    }

    public String get_formatted_time() {
        return formatTime(_time.getTime(),8);
    }

    public void set_time(Time _time) {
        this._time = _time;
    }

    public Date get_date() {return _date;}

    public void set_date(Date _date) {this._date = _date;}

    public double get_lat() {return _lat;}

    public void set_lat(double _lat) {
        this._lat = _lat;
    }

    public double get_lng() {
        return _lng;
    }

    public void set_lng(double _lng) {
        this._lng = _lng;
    }

    public String get_userid() {
        return _userid;
    }

    public void set_userid(String _userid) {
        this._userid = _userid;
    }

    public boolean isReport() {
        return isReport;
    }

    public void setReport(boolean report) {
        isReport = report;
    }

    public boolean isSubmitted() { return isSubmitted;}

    public void setSubmitted(boolean submitted) {isSubmitted = submitted;}
}
