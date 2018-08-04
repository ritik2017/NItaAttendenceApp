package com.example.android.nitaattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourse extends AppCompatActivity {

    private EditText courseNameEditText;
    private Button addCourseButton;
    private FirebaseDatabase addCourseDatabase;
    private DatabaseReference addCourseDatabaseReference;
    private String courseName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        addCourseDatabase = FirebaseDatabase.getInstance();
        addCourseDatabaseReference = addCourseDatabase.getReference().child("courses");

        courseNameEditText = (EditText) findViewById(R.id.coursename_edittext);



        addCourseButton = (Button) findViewById(R.id.addcourse_button);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseName = courseNameEditText.getText().toString();
                courseName =  courseName.toUpperCase();

                if(!(courseName.isEmpty())){

                    Courses newCourse = new Courses(courseName);

                    addCourseDatabaseReference.push().setValue(newCourse);
                    Toast.makeText(AddCourse.this , "COURSE ADDED SUCESSFULLY" , Toast.LENGTH_SHORT).show();

                   finish();
                }
                else{
                    Toast.makeText(AddCourse.this, "PLEASE ENTER A VALID COURSENAME" , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
