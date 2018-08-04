package com.example.android.nitaattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mStudentListener;

    private Spinner yearSpinnerDropdown , sectionSpinnerDropdown , departmentSpinnerDropdown;
    private String yearSelected , sectionSelected , departmentSelected;

    private ListView mStudentListView;
    private ViewStudentAdapter mStudentAdapter;

    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        yearSpinnerDropdown = (Spinner) findViewById(R.id.year_Spinner_Dropdown);
        sectionSpinnerDropdown = (Spinner) findViewById(R.id.section_Spinner_Dropdown);
        departmentSpinnerDropdown = (Spinner) findViewById(R.id.department_Spinner_Dropdown);
        applyButton = (Button) findViewById(R.id.apply_button);

        mFirebaseDataBase = FirebaseDatabase.getInstance();

        mStudentListView = (ListView) findViewById(R.id.viewstudent_listview);

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


        final ArrayList<StudentDetails> studentDetails = new ArrayList<StudentDetails>();
        mStudentAdapter = new ViewStudentAdapter(this , studentDetails);
        mStudentListView.setAdapter(mStudentAdapter);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearSelected = yearSpinnerDropdown.getSelectedItem().toString();
                sectionSelected = sectionSpinnerDropdown.getSelectedItem().toString();
                departmentSelected = departmentSpinnerDropdown.getSelectedItem().toString();

                studentDetails.clear();

                mDatabaseReference = mFirebaseDataBase.getReference().child(("Students" + yearSelected + sectionSelected + departmentSelected));


                mStudentListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        StudentDetails newStudentDetails = dataSnapshot.getValue(StudentDetails.class);
                        mStudentAdapter.add(newStudentDetails);

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

                mDatabaseReference.addChildEventListener(mStudentListener);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.addstudent, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addstudent_item:
                addNewStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addNewStudent(){
        Intent intent = new Intent(ViewStudentActivity.this, AddStudentActivity.class);
        startActivity(intent);
    }

}
