package com.example.android.nitaattendence;

import android.os.Environment;
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

public class ViewExcelSheetActivity extends AppCompatActivity {

    private FirebaseDatabase courseDatabase;
    private DatabaseReference courseReference;
    private ChildEventListener courseEventListener;
    private String courseSelected;
    private Spinner courseDropdown;
    private Button generateExcelButton;

    private  File directory, sd, file;
    private WritableWorkbook workbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_excel_sheet);

        courseDatabase = FirebaseDatabase.getInstance();
        courseReference = courseDatabase.getReference().child("courses");

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

        generateExcelButton = (Button) findViewById(R.id.generate_excel);

        generateExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courseSelected = courseDropdown.getSelectedItem().toString();

                Toast.makeText(ViewExcelSheetActivity.this , courseSelected , Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**  private void createExcelSheet() {
        String csvFile = courseSelected + ".xls";
        directory = new File(sd.getAbsolutePath());
        file = new File(directory, csvFile);
        sd = Environment.getExternalStorageDirectory();
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            createExcelWorkbbok();
            //closing cursor
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private void createExcelWorkbbok(){

        try {

            List<StudentDetails> listdata = new ArrayList<>();
            listdata.add(new StudentDetails("mr","firstName1","middleName1","lastName1"));
            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "NameInitial"));
            sheet.addCell(new Label(1, 0, "firstName"));
            sheet.addCell(new Label(2, 0, "middleName"));
            sheet.addCell(new Label(3, 0, "lastName"));

            for (int i = 0; i < listdata.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata.get(i).getInitial()));
                sheet.addCell(new Label(1, i + 1, listdata.get(i).getFirstName()));
                sheet.addCell(new Label(2, i + 1, listdata.get(i).getMiddleName()));
                sheet.addCell(new Label(3, i + 1, listdata.get(i).getLastName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } */

}
