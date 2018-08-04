package com.example.android.nitaattendence;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private TextView takeAttendenceTextView;
    private TextView viewCoursesTextView;
    private TextView viewAttendenceTextView , viewTeacherTextView , viewStudentTextView , viewExcelSheetTextView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("AttendenceDetails");

        takeAttendenceTextView  = (TextView) findViewById(R.id.takeattendence_textview);

        takeAttendenceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , TakeAttendenceActivity.class);
                startActivity(intent);
            }
        });

        viewCoursesTextView = (TextView) findViewById(R.id.viewcourses_textview);
        viewCoursesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , ViewCourses.class);
                startActivity(intent);
            }
        });

        viewAttendenceTextView  =(TextView) findViewById(R.id.viewattendence_textview);

        viewAttendenceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , ViewAttendenceActivity.class);
                startActivity(intent);
            }
        });

        viewTeacherTextView = (TextView) findViewById(R.id.viewteacher_textview);

        viewTeacherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , ViewTeacherActivity.class);
                startActivity(intent);
            }
        });

        viewStudentTextView = (TextView) findViewById(R.id.viewstudent_textview);

        viewStudentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , ViewStudentActivity.class);
                startActivity(intent);
            }
        });

        viewExcelSheetTextView = (TextView) findViewById(R.id.view_excelsheet);

        viewExcelSheetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent  = new Intent(AdminActivity.this , ViewExcelSheetActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.expireqr, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expire_qr:
                expireQr();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void expireQr(){

        TakeAttendenceDetails nullDetails = new TakeAttendenceDetails("Date" , "Otp" , "Course" , "Section" , "Year" , "Department");
        mDatabaseReference.push().setValue(nullDetails);

        Toast.makeText(AdminActivity.this , "QR CODE SUCCESSFULLY EXPIRED" , Toast.LENGTH_SHORT).show();

    }


}
