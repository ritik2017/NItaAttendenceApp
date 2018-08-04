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

import static com.example.android.nitaattendence.R.string.courses;

public class GiveAttendenceActivity extends AppCompatActivity {


    private FirebaseDatabase nFirebaseDatabase;
    private DatabaseReference nDatabaseReference;
    private ChildEventListener mCoursesListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private EditText  nameEdittext , enrollEdittext;
    private Spinner coursesSpinnerDropdown, yearSpinnerDropdown , sectionSpinnerDropdown , departmentSpinnerDropdown;
    private String courseSelected , date , otp , nameOfStudent , enrollOfStudent;
    private String yearOfStudent , sectionOfStudent , departmentOfStudent;
    private Button giveAttendenceButton;
    private FirebaseDatabase attendenceDatabase;
    private DatabaseReference attendenceDatabaseReference;
    private Spinner dateDropdown , monthDropdown , yearDropdown;
    private String dateSelected , monthSelected , yearSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendence);

        Bundle bundle = getIntent().getExtras();

        otp =  bundle.getString("scanResultOtp");

        nFirebaseDatabase = FirebaseDatabase.getInstance();
        nDatabaseReference = nFirebaseDatabase.getReference().child("courses");

        attendenceDatabase = FirebaseDatabase.getInstance();

        final ArrayList<String> coursesDropdownList = new ArrayList<String>();
        coursesDropdownList.add("Select Course");
        courseSelected = "Select Course";
        mCoursesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Courses newCourse = dataSnapshot.getValue(Courses.class);
                coursesDropdownList.add(newCourse.getCourse());
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
        nDatabaseReference.addChildEventListener(mCoursesListener);

        yearSpinnerDropdown = (Spinner) findViewById(R.id.year_Spinner_Dropdown);
        sectionSpinnerDropdown = (Spinner) findViewById(R.id.section_Spinner_Dropdown);
        departmentSpinnerDropdown = (Spinner) findViewById(R.id.department_Spinner_Dropdown);

        final ArrayList<String> year = new ArrayList<String>();
        final ArrayList<String> section = new ArrayList<String>();
        final ArrayList<String> department = new ArrayList<String>();

        year.add("1"); year.add("2"); year.add("3"); year.add("4");
        section.add("A"); section.add("B"); section.add("C"); section.add("D");
        department.add("CSE");  department.add("EE"); department.add("ECE"); department.add("EI"); department.add("ME"); department.add("PE");  department.add("CHE"); department.add("CE"); department.add("Physics"); department.add("Chemistry"); department.add("Mathematics");

        ArrayAdapter<String> yearadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, year);
        yearSpinnerDropdown.setAdapter(yearadapter);

        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, section);
        sectionSpinnerDropdown.setAdapter(sectionadapter);

        ArrayAdapter<String> departmentadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, department);
        departmentSpinnerDropdown.setAdapter(departmentadapter);


        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mDatabaseReference = mFirebaseDatabase.getReference().child("AttendenceDetails");


        final ArrayList<String> takeAttendenceDetailsDate = new ArrayList<String>();
        final ArrayList<String> takeAttendenceDetailsOtp = new ArrayList<String>();
        final ArrayList<String> takeAttendenceDetailsCourse = new ArrayList<String>();
        final ArrayList<String> takeAttendenceDetailsYear= new ArrayList<String>();
        final ArrayList<String> takeAttendenceDetailsSection = new ArrayList<String>();
        final ArrayList<String> takeAttendenceDetailsDepartment = new ArrayList<String>();



        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                TakeAttendenceDetails newTakeAttendenceDetails = dataSnapshot.getValue(TakeAttendenceDetails.class);
                takeAttendenceDetailsDate.add(newTakeAttendenceDetails.getDate());
                takeAttendenceDetailsCourse.add(newTakeAttendenceDetails.getCourse());
                takeAttendenceDetailsOtp.add(newTakeAttendenceDetails.getOtp());
                takeAttendenceDetailsYear.add(newTakeAttendenceDetails.getYear());
                takeAttendenceDetailsSection.add(newTakeAttendenceDetails.getSection());
                takeAttendenceDetailsDepartment.add(newTakeAttendenceDetails.getDepartment());


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

        mDatabaseReference.addChildEventListener(mChildEventListener);

        coursesSpinnerDropdown = (Spinner) findViewById(R.id.courses_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, coursesDropdownList);
        coursesSpinnerDropdown.setAdapter(adapter);

        coursesSpinnerDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nameEdittext = (EditText) findViewById(R.id.name_edittext);
        enrollEdittext = (EditText) findViewById(R.id.enrollment_edittext);

        giveAttendenceButton = (Button) findViewById(R.id.giveattendence_button);


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
        yearList.add("2018");
        yearList.add("2019");
        yearList.add("2020");
        yearList.add("2021");

        dateDropdown = (Spinner) findViewById(R.id.dateSpinnerDropdown);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, dateList);
        dateDropdown.setAdapter(adapterDate);

        monthDropdown = (Spinner) findViewById(R.id.monthSpinnerDropdown);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, monthList);
        monthDropdown.setAdapter(adapterMonth);

        yearDropdown = (Spinner) findViewById(R.id.yearSpinnerDropdown);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, yearList);
        yearDropdown.setAdapter(adapterYear);

        giveAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateSelected = dateDropdown.getSelectedItem().toString();
                monthSelected = monthDropdown.getSelectedItem().toString();
                yearSelected = yearDropdown.getSelectedItem().toString();
                yearOfStudent = yearSpinnerDropdown.getSelectedItem().toString();
                sectionOfStudent = sectionSpinnerDropdown.getSelectedItem().toString();
                departmentOfStudent = departmentSpinnerDropdown.getSelectedItem().toString();


                date  = dateSelected + monthSelected + yearSelected;
                nameOfStudent = nameEdittext.getText().toString();
                enrollOfStudent = enrollEdittext.getText().toString();

                int length = takeAttendenceDetailsOtp.size();

                if(length == 0){
                    Toast.makeText(GiveAttendenceActivity.this, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                }

                else if ((date.equals(takeAttendenceDetailsDate.get(length - 1))) && (courseSelected.equals(takeAttendenceDetailsCourse.get(length - 1))) && (otp.equals(takeAttendenceDetailsOtp.get(length - 1))) &&(yearOfStudent.equals(takeAttendenceDetailsYear.get(length - 1))) && (sectionOfStudent.equals(takeAttendenceDetailsSection.get(length - 1))) && (departmentOfStudent.equals(takeAttendenceDetailsDepartment.get(length - 1)))) {

                    attendenceDatabaseReference = attendenceDatabase.getReference().child((courseSelected + date + yearOfStudent + sectionOfStudent +departmentOfStudent));

                    StudentAttendence newStudentAttendence = new StudentAttendence(nameOfStudent, enrollOfStudent, date, courseSelected , yearOfStudent , sectionOfStudent , departmentOfStudent);
                    attendenceDatabaseReference.push().setValue(newStudentAttendence);

                    Toast.makeText(GiveAttendenceActivity.this , "ATTENDENCE GIVEN SUCCESSFULLY" , Toast.LENGTH_SHORT).show();

                    finish();

                }

                else {
                    Toast.makeText(GiveAttendenceActivity.this, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
