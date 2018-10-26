package com.example.android.nitaattendence;

import android.*;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import static android.R.attr.y;

public class ViewExcelSheetActivity extends AppCompatActivity {

    private FirebaseDatabase courseDatabase;
    private DatabaseReference courseReference;
    private ChildEventListener courseEventListener;
    private String courseSelected;
    private Spinner courseDropdown;
    private Button generateExcelButton;

    private Spinner yearOfStudentDropdown , sectionOfStudentDropdown , departmentOfStudentDropdown;
    private String yearOfStudent , sectionOfStudent , departmentOfStudent;

    private FirebaseDatabase studentDatabase;
    private DatabaseReference studentDatabaseReference;
    private ChildEventListener studentEventListener;

    private  File directory, sd, file;
    private WritableWorkbook workbook;

    public static final int REQUEST_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_excel_sheet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if(checkPermissionForStorage()){

                }
                else{
                    requestPermissionForStorage();
                }
            }

        courseDatabase = FirebaseDatabase.getInstance();
        courseReference = courseDatabase.getReference().child("courses");

        yearOfStudentDropdown = (Spinner) findViewById(R.id.year_Spinner_Dropdown);
        sectionOfStudentDropdown = (Spinner) findViewById(R.id.section_Spinner_Dropdown);
        departmentOfStudentDropdown = (Spinner) findViewById(R.id.department_Spinner_Dropdown);

        final ArrayList<String> coursesDropdownList = new ArrayList<String>();
        coursesDropdownList.add("Select Course");
        courseSelected = "Select Course";

        courseEventListener = new ChildEventListener() {
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
        courseReference.addChildEventListener(courseEventListener);

        courseDropdown = (Spinner) findViewById(R.id.coursedropdownspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item, coursesDropdownList);
        courseDropdown.setAdapter(adapter);

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


        generateExcelButton = (Button) findViewById(R.id.generate_excel);

        generateExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearOfStudent = yearOfStudentDropdown.getSelectedItem().toString();
                sectionOfStudent = sectionOfStudentDropdown.getSelectedItem().toString();
                departmentOfStudent = departmentOfStudentDropdown.getSelectedItem().toString();

                createExcelSheet();
                Toast.makeText(ViewExcelSheetActivity.this , "Excel Sheet Generated Sucessfully" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createExcelSheet() {

        String state  = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) {

            String csvFile = courseSelected + ".xls";
            sd = Environment.getExternalStorageDirectory();
            directory = new File(sd.getAbsolutePath() + "/Nita Attendence");

            if (!(directory.exists())) {
                directory.mkdir();
            }

            String temp = directory.toString();

            Toast.makeText(ViewExcelSheetActivity.this, temp, Toast.LENGTH_SHORT).show();

            file = new File(directory, csvFile);

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                workbook.write();
                createExcelWorkbook();
                //closing cursor
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        else {

            Toast.makeText(ViewExcelSheetActivity.this , "SD CARD NOT FOUND" , Toast.LENGTH_SHORT).show();

        }
    }

  private void createExcelWorkbook(){

       final ArrayList<String> studentList = new ArrayList<String>();
       final ArrayList<String> enrollList = new ArrayList<String>();
       studentDatabase = FirebaseDatabase.getInstance();
       studentDatabaseReference = studentDatabase.getReference().child(("Students" + yearOfStudent + sectionOfStudent + departmentOfStudent));

       studentEventListener = new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               StudentDetails dataStudentDetails = dataSnapshot.getValue(StudentDetails.class);
               studentList.add(dataStudentDetails.getNameOfStudent());
               enrollList.add(dataStudentDetails.getEnrollmentOfStudent());
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

       studentDatabaseReference.addChildEventListener(studentEventListener);

        try {

            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "Enrollment No."));
            sheet.addCell(new Label(1, 0, "Name"));

            int slno = 1;

            for (int i = 0; i < studentList.size(); i++) {
                sheet.addCell(new Label(0, i + 1, enrollList.get(i)));
                sheet.addCell(new Label(1, i + 1, studentList.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkPermissionForStorage(){

        return (ContextCompat.checkSelfPermission(ViewExcelSheetActivity.this , STORAGE_SERVICE ) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestPermissionForStorage(){

        ActivityCompat.requestPermissions(this , new String[]{STORAGE_SERVICE} , REQUEST_STORAGE);
    }

}
