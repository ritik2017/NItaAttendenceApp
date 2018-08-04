package com.example.android.nitaattendence;

/**
 * Created by RITIK KUMAR on 17-07-2018.
 */

public class StudentAttendence {

    private String name;
    private String enroll;
    private String date;
    private String course;
    private String year;
    private String section;
    private String department;

    public StudentAttendence(){

    }

    public StudentAttendence(String name, String enroll, String date , String course , String year , String section , String department){
        this.name = name;
        this.enroll = enroll;
        this.date = date;
        this.course = course;
        this.year = year;
        this.section = section;
        this.department = department;
    }

    public String getName(){
        return name;
    }

    public String getEnroll(){
        return enroll;
    }
    public String getDate(){
        return date;
    }
    public String getCourse(){
        return course;
    }

    public String getYear() {
        return year;
    }

    public String getSection() {
        return section;
    }

    public String getDepartment() {
        return department;
    }
}
