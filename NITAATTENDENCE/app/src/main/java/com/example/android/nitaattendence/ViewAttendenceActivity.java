package com.example.android.nitaattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.android.nitaattendence.R.string.date;
import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class ViewAttendenceActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase , nFirebseDatabase ;
    private DatabaseReference mDatabaseReference , nDatabaseReference;
    private ChildEventListener mCoursesListener , nCourseDateListener;

    private Spinner dateDropdown , monthDropdown , yearDropdown;
    private Spinner coursesSpinnerDropdown , yearSpinnerDropdown , sectionSpinnerDropdown , departmentSpinnerDropdown;

    private String  courseSelected , dateSelected , monthSelected , yearSelected , dateAdded , courseDateSum , yearOfStudent , sectionOfStudent;
    private String departmentOfStudent;
    private Button viewAttendenceButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);

        mFirebaseDatabase= FirebaseDatabase.getInstance();
        nFirebseDatabase = FirebaseDatabase.getInstance();


        mDatabaseReference = mFirebaseDatabase.getReference().child("courses");
        nDatabaseReference = nFirebseDatabase.getReference().child("COURSEDATE");


        final ArrayList<String> coursesDropdownList = new ArrayList<String>();
        coursesDropdownList.add("Select Course");
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
        mDatabaseReference.addChildEventListener(mCoursesListener);

        yearSpinnerDropdown = (Spinner) findViewById(R.id.year_Spinner_Dropdown);
        sectionSpinnerDropdown = (Spinner) findViewById(R.id.section_Spinner_Dropdown);
        departmentSpinnerDropdown = (Spinner) findViewById(R.id.department_Spinner_Dropdown);

        coursesSpinnerDropdown = (Spinner) findViewById(R.id.courses_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, coursesDropdownList);
        coursesSpinnerDropdown.setAdapter(adapter);

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

        ArrayList<String> dateList = new ArrayList<String>();
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
        yearList.add("2021");yearList.add("2022"); yearList.add("2023"); yearList.add("2024");   yearList.add("2025");
        yearList.add("2026");  yearList.add("2027");  yearList.add("2028");   yearList.add("2029");  yearList.add("2030");


        dateDropdown = (Spinner) findViewById(R.id.dateSpinnerDropdown);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, dateList);
        dateDropdown.setAdapter(adapterDate);

        monthDropdown = (Spinner) findViewById(R.id.monthSpinnerDropdown);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, monthList);
        monthDropdown.setAdapter(adapterMonth);

        yearDropdown = (Spinner) findViewById(R.id.yearSpinnerDropdown);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, yearList);
        yearDropdown.setAdapter(adapterYear);

        final ArrayList<String> courseDateList = new ArrayList<String>();

        nCourseDateListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                CourseDate courseDate = dataSnapshot.getValue(CourseDate.class);
                courseDateList.add(courseDate.getCourseDate());

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

        nDatabaseReference.addChildEventListener(nCourseDateListener);


        viewAttendenceButton = (Button) findViewById(R.id.viewattendence_button);

        viewAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateSelected = dateDropdown.getSelectedItem().toString();
                monthSelected = monthDropdown.getSelectedItem().toString();
                yearSelected = yearDropdown.getSelectedItem().toString();
                yearOfStudent = yearSpinnerDropdown.getSelectedItem().toString();
                sectionOfStudent = sectionSpinnerDropdown.getSelectedItem().toString();
                departmentOfStudent = departmentSpinnerDropdown.getSelectedItem().toString();

                dateAdded = dateSelected + monthSelected + yearSelected;
                courseSelected = coursesSpinnerDropdown.getSelectedItem().toString();

                courseDateSum = courseSelected + dateAdded + yearOfStudent + sectionOfStudent + departmentOfStudent;

                int length = courseDateList.size();
                int flag = 0;

                for(int i = 0; i<length ; i++){
                    if(courseDateSum.equals(courseDateList.get(i))){
                        flag = 1;
                        Intent intent = new Intent(ViewAttendenceActivity.this , ViewAttendenceListActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("courseDateFromIntent" , courseDateSum);
                        intent.putExtras(bundle);

                        startActivity(intent);
                        break;
                    }
                }

                if(flag == 0){
                    Toast.makeText(ViewAttendenceActivity.this , "NO SUCH ATTENDENCE FOUND" , Toast.LENGTH_SHORT).show();
                }
           }
        });
    }
}
