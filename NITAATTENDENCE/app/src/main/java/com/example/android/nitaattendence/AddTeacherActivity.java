package com.example.android.nitaattendence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacherActivity extends AppCompatActivity {

    private EditText nameEditText , courseEditText , usernameEditText , passwordEditText;
    private String name , course , username , password;
    private Button addTeacherButton;

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDataBase.getReference().child("TEACHERS");

        nameEditText = (EditText) findViewById(R.id.nameofteacher_edittext);
        courseEditText = (EditText) findViewById(R.id.coursebyteacher_edittext);
        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        addTeacherButton = (Button) findViewById(R.id.addteacher_button);

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                course = courseEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (!(name.isEmpty()) && !(course.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty())) {
                    TeacherDetails newTeacherDetails = new TeacherDetails(name, course, username, password);
                    mDatabaseReference.push().setValue(newTeacherDetails);

                    Toast.makeText(AddTeacherActivity.this, "STUDENT ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                    finish();
                }

                else{
                    Toast.makeText(AddTeacherActivity.this , "INVALID CREDENTIALS" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
