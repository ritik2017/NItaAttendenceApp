package com.example.android.nitaattendence;

/**
 * Created by RITIK KUMAR on 22-07-2018.
 */

public class TeacherDetails {

    private String nameOfTeacher , courseTaughtByteacher , username , password;

    public TeacherDetails(){

    }

    public TeacherDetails(String nameOfTeacher , String courseTaughtByteacher , String username , String password){

        this.nameOfTeacher = nameOfTeacher;
        this.courseTaughtByteacher = courseTaughtByteacher;
        this.username = username;
        this.password = password;
    }

    public String getNameOfTeacher(){
        return nameOfTeacher;
    }

    public String getCourseTaughtByteacher(){
        return courseTaughtByteacher;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
