package com.example.android.nitaattendence;

/**
 * Created by RITIK KUMAR on 17-07-2018.
 */

public class TakeAttendenceDetails {

    String date;
    String otp;
    String course;
    String year;
    String department;
    String section;

    public TakeAttendenceDetails(){

    }

    public TakeAttendenceDetails(String date, String otp , String course , String year , String section , String department){
        this.date = date;
        this.otp = otp;
        this.course = course;
        this.year = year;
        this.section = section;
        this.department = department;
    }

    public String getDate(){
        return date;
    }

    public String getOtp(){
        return otp;
    }

    public String getCourse(){
        return  course;
    }

    public String getYear() {
        return year;
    }

    public String getSection(){
        return section;
    }

    public String getDepartment(){
        return department;
    }
}
