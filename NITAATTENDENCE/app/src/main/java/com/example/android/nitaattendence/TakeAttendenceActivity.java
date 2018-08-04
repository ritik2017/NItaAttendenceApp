package com.example.android.nitaattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.android.nitaattendence.R.id.departmentSpinnerDropdown;
import static com.example.android.nitaattendence.R.id.sectionSpinnerDropdown;
import static com.example.android.nitaattendence.R.id.yearSpinnerDropdown;

public class TakeAttendenceActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Spinner coursesDropdown;
    private FirebaseDatabase mFirebaseDatabase , nFirebaseDatabase;
    private DatabaseReference mDatabaseReference , nDatabaseReference;
    private ChildEventListener mCoursesListener;
    private String courseSelected;
    private Button takeAttendenceButton;
    private String date , otp , dateSelected , monthSelected , yearSelected , yearOfStudentSelected , sectionOfStudentSelected , departmentOfStudentSelected;
    private Spinner dateDropdown , monthDropdown , yearDropdown , yearOfStudentDropdown , sectionOfStudentDropdown , departmentOfStudentDropdown;

    private FirebaseDatabase courseDateDatabase;
    private DatabaseReference courseDateReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        nFirebaseDatabase = FirebaseDatabase.getInstance();

        nDatabaseReference = nFirebaseDatabase.getReference().child("AttendenceDetails");
        mDatabaseReference = mFirebaseDatabase.getReference().child("courses");

        courseDateDatabase = FirebaseDatabase.getInstance();
        courseDateReference = courseDateDatabase.getReference().child("COURSEDATE");


        final ArrayList<String> courseDropdownList = new ArrayList<String>();
        courseDropdownList.add("Select Course");

        mCoursesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Courses newCourse = dataSnapshot.getValue(Courses.class);
                courseDropdownList.add(newCourse.getCourse());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //adding child event listener to database
        mDatabaseReference.addChildEventListener(mCoursesListener);


        yearOfStudentDropdown = (Spinner) findViewById(R.id.year_Spinner_Dropdown);
        sectionOfStudentDropdown = (Spinner) findViewById(R.id.section_Spinner_Dropdown);
        departmentOfStudentDropdown = (Spinner) findViewById(R.id.department_Spinner_Dropdown);


        final ArrayList<String> year = new ArrayList<String>();
        final ArrayList<String> section = new ArrayList<String>();
        final ArrayList<String> department = new ArrayList<String>();

        year.add("1"); year.add("2"); year.add("3"); year.add("4");
        section.add("A"); section.add("B"); section.add("C"); section.add("D");
        department.add("CSE");  department.add("EE"); department.add("ECE"); department.add("EI"); department.add("ME"); department.add("PE");  department.add("CHE"); department.add("CE"); department.add("Physics"); department.add("Chemistry"); department.add("Mathematics");

        ArrayAdapter<String> yearadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, year);
        yearOfStudentDropdown.setAdapter(yearadapter);

        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, section);
        sectionOfStudentDropdown.setAdapter(sectionadapter);

        ArrayAdapter<String> departmentadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, department);
        departmentOfStudentDropdown.setAdapter(departmentadapter);

        otpEditText = (EditText) findViewById(R.id.onetimepassword_edittext);
        coursesDropdown = (Spinner) findViewById(R.id.courses_dropdown);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, courseDropdownList);
        coursesDropdown.setAdapter(adapter);

        final ArrayList<String> dateList = new ArrayList<String>();
        dateList.add("DD");
        dateList.add("01");dateList.add("02");dateList.add("03");dateList.add("04");dateList.add("05");dateList.add("06");dateList.add("07");
        dateList.add("08");dateList.add("09");dateList.add("10");dateList.add("11");dateList.add("12");dateList.add("13");dateList.add("14");
        dateList.add("15");dateList.add("16");dateList.add("17");dateList.add("18");dateList.add("19");dateList.add("20");dateList.add("21");
        dateList.add("22");dateList.add("23");dateList.add("24");dateList.add("25");dateList.add("26");dateList.add("27");dateList.add("28");
        dateList.add("29");dateList.add("30");dateList.add("31");

        final ArrayList<String> monthList = new ArrayList<String>();
        monthList.add("MM");
        monthList.add("01");monthList.add("02"); monthList.add("03");monthList.add("04");
        monthList.add("05"); monthList.add("06");monthList.add("07"); monthList.add("08");monthList.add("09");
        monthList.add("10");
        monthList.add("11");
        monthList.add("12");

        final ArrayList<String> yearList = new ArrayList<String>();
        yearList.add("YY");
        yearList.add("2018");   yearList.add("2019");   yearList.add("2020");
        yearList.add("2021"); yearList.add("2022"); yearList.add("2023"); yearList.add("2024");   yearList.add("2025");
        yearList.add("2026");  yearList.add("2027");  yearList.add("2028");   yearList.add("2029");  yearList.add("2030");

        dateDropdown = (Spinner) findViewById(R.id.dateSpinnerDropdown);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, dateList);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateDropdown.setAdapter(adapterDate);

        monthDropdown = (Spinner) findViewById(R.id.monthSpinnerDropdown);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, monthList);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthDropdown.setAdapter(adapterMonth);

        yearDropdown = (Spinner) findViewById(yearSpinnerDropdown);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, yearList);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearDropdown.setAdapter(adapterYear);


        takeAttendenceButton = (Button) findViewById(R.id.takeattendence_button);

        takeAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dateSelected = dateDropdown.getSelectedItem().toString();
                monthSelected = monthDropdown.getSelectedItem().toString();
                yearSelected = yearDropdown.getSelectedItem().toString();
                courseSelected = coursesDropdown.getSelectedItem().toString();
                yearOfStudentSelected = yearOfStudentDropdown.getSelectedItem().toString();
                sectionOfStudentSelected = sectionOfStudentDropdown.getSelectedItem().toString();
                departmentOfStudentSelected = departmentOfStudentDropdown.getSelectedItem().toString();

                date = dateSelected + monthSelected + yearSelected;
                otp = otpEditText.getText().toString();

                if(!(date.isEmpty()) && !(otp.isEmpty()) && !(courseSelected.equals("Select Course"))){

                     TakeAttendenceDetails newTakeAttendenceDetails = new TakeAttendenceDetails(date, otp , courseSelected, yearOfStudentSelected , sectionOfStudentSelected , departmentOfStudentSelected);
                    nDatabaseReference.push().setValue(newTakeAttendenceDetails);

                    CourseDate newCourseDate = new CourseDate((courseSelected + date + yearOfStudentSelected + sectionOfStudentSelected + departmentOfStudentSelected));
                    courseDateReference.push().setValue(newCourseDate);

                    Toast.makeText(TakeAttendenceActivity.this , "ATTENDENCE DETAILS ADDED SUCCESSFULLY" , Toast.LENGTH_SHORT).show();

                    finish();

                }
                else{
                    Toast.makeText(TakeAttendenceActivity.this , "ENTER VALID CREDENTIALS",  Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
