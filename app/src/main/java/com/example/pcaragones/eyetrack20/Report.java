package com.example.pcaragones.eyetrack20;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by pcaragones on 7/8/17.
 */

class Report {
    private int _id;
    private String _type;
    private String _remarks;
    private String _locationName;
    private java.sql.Time _time;
    private java.sql.Date _date;
    private String _userid;
    private boolean isSubmitted;

    public Report(){

    }

//    public Report(int _id, String _type, String _remarks, String _locationName, java.util.Date dateSubmitted, String _userid, boolean isSubmitted) {
//        this._id = _id;
//        this._type = _type;
//        this._remarks = _remarks;
//        this._locationName = _locationName;
//        this.DateSubmitted = dateSubmitted;
//        this._userid = _userid;
//        this.isSubmitted = isSubmitted;
//    }

    public Report(String _type, String _remarks, String _locationName, java.util.Date _utilDate, String _userid, boolean isSubmitted) {
        this._type = _type;
        this._remarks = _remarks;
        this._locationName = _locationName;
        this._time = new java.sql.Time(_utilDate.getTime());
        this._date = new java.sql.Date(_utilDate.getTime());
        this._userid = _userid;
        this.isSubmitted = isSubmitted;
    }
//    public Report(String _type, String _remarks, String _locationName, java.util.Date dateSubmitted, String _userid, boolean isSubmitted) {
//        this._type = _type;
//        this._remarks = _remarks;
//        this._locationName = _locationName;
//        this.DateSubmitted = dateSubmitted;
//        this._userid = _userid;
//        this.isSubmitted = isSubmitted;
//    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_remarks() {
        return _remarks;
    }

    public void set_remarks(String _remarks) {
        this._remarks = _remarks;
    }

    public String get_locationName() {
        return _locationName;
    }

    public void set_locationName(String _locationName) {
        this._locationName = _locationName;
    }

    public Time get_time() { return _time; }

    public void set_time(Time _time) { this._time = _time; }

    public java.sql.Date get_date() { return _date; }

    public void set_date(java.sql.Date _date) { this._date = _date; }

    //    public java.util.Date getDateSubmitted() {
//        return this.DateSubmitted;
//    }
//
//    public void setDateSubmitted(java.util.Date dateSubmitted) {
//        DateSubmitted = dateSubmitted;
//    }

    public String get_userid() {
        return _userid;
    }

    public void set_userid(String _userid) {
        this._userid = _userid;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }


}
