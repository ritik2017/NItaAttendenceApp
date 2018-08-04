package com.example.android.nitaattendence;

import android.app.Notification;

/**
 * Created by RITIK KUMAR on 22-07-2018.
 */

public class StudentDetails {

    private String nameOfStudent , enrollmentOfStudent, username , password , year , section, department;

    public StudentDetails(){

    }

    public StudentDetails(String nameOfStudent , String enrollmentOfStudent , String username , String password , String year , String sec , String department){

        this.nameOfStudent = nameOfStudent;
        this.username = username;
        this.password = password;
        this.enrollmentOfStudent = enrollmentOfStudent;
        this.year = year;
        this.section = sec;
        this.department = department;
    }

    public String getNameOfStudent(){
        return nameOfStudent;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEnrollmentOfStudent(){
        return enrollmentOfStudent;
    }

    public String getYear(){
        return year;
    }

    public String getSection(){
        return section;
    }

    public String getDepartment(){
        return department;
    }
}
