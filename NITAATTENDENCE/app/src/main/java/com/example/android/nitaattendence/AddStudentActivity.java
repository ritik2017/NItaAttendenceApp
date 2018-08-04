package com.example.android.nitaattendence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.R.attr.y;

public class AddStudentActivity extends AppCompatActivity {

    private EditText nameEditText , enrollEditText , usernameEditText , passwordEditText;
    private String nameOfStudent , enrollOfStudent , username , password , yearSelected , sectionSelected, departmentSelected;

    private Button addStudentButton;
    private Spinner yearDropdown, sectionDropdown , departmentDropdown;

    private FirebaseDatabase mFirebaseDataBase , studentDatabase;
    private DatabaseReference mDatabaseReference , studentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDataBase.getReference().child("STUDENTS");

        studentDatabase = FirebaseDatabase.getInstance();

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        enrollEditText = (EditText) findViewById(R.id.enrollment_edittext);
        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        addStudentButton = (Button) findViewById(R.id.addstudent_button);
        yearDropdown = (Spinner) findViewById(R.id.yearSpinnerDropdown);
        sectionDropdown = (Spinner) findViewById(R.id.sectionSpinnerDropdown);
        departmentDropdown = (Spinner) findViewById(R.id.departmentSpinnerDropdown);

        final ArrayList<String> year = new ArrayList<String>();
        final ArrayList<String> section = new ArrayList<String>();
        final ArrayList<String> department = new ArrayList<String>();

        year.add("1"); year.add("2"); year.add("3"); year.add("4");
        section.add("A"); section.add("B"); section.add("C"); section.add("D");
        department.add("CSE");  department.add("EE"); department.add("ECE"); department.add("EI"); department.add("ME"); department.add("PE");  department.add("CHE"); department.add("CE"); department.add("Physics"); department.add("Chemistry"); department.add("Mathematics");

        ArrayAdapter<String> yearadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, year);
        yearDropdown.setAdapter(yearadapter);

        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, section);
       sectionDropdown.setAdapter(sectionadapter);

        ArrayAdapter<String> departmentadapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, department);
        departmentDropdown.setAdapter(departmentadapter);



        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOfStudent = nameEditText.getText().toString();
                enrollOfStudent = enrollEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                yearSelected = yearDropdown.getSelectedItem().toString();
                sectionSelected = sectionDropdown.getSelectedItem().toString();
                departmentSelected = departmentDropdown.getSelectedItem().toString();

                studentReference = studentDatabase.getReference().child(("Students" + yearSelected + sectionSelected + departmentSelected));

                if(!(nameOfStudent.isEmpty()) && !(enrollOfStudent.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty())){
                    StudentDetails newStudentDetails = new StudentDetails(nameOfStudent , enrollOfStudent, username , password ,yearSelected , sectionSelected , departmentSelected);
                    mDatabaseReference.push().setValue(newStudentDetails);
                    studentReference.push().setValue(newStudentDetails);

                    Toast.makeText(AddStudentActivity.this, "STUDENT ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                    finish();
                }

                else {
                    Toast.makeText(AddStudentActivity.this , "INVALID CREDENTIALS" , Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
