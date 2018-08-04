package com.example.android.nitaattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StudentActivity extends AppCompatActivity {


    private TextView giveAttendenceTextView , viewCoursesTextView, viewTeacherTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        giveAttendenceTextView = (TextView) findViewById(R.id.giveattendence_textview);

        giveAttendenceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentActivity.this , GiveAttendenceQrActivity.class);
                startActivity(intent);
            }
        });


        viewCoursesTextView = (TextView) findViewById(R.id.viewcourses_textview);

        viewCoursesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, ViewCourseStudentActivity.class);
                startActivity(intent);
            }
        });

        viewTeacherTextView = (TextView) findViewById(R.id.viewteacher_textview);

        viewTeacherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentActivity.this, ViewTeacherStudentActivity.class);
                startActivity(intent);

            }
        });

    }
}
