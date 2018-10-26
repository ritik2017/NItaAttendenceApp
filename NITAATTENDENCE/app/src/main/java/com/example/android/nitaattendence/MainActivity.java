package com.example.android.nitaattendence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText , passwordEditText;
    String username , password;
    Button loginButton;
    private int lengthStudent , lengthTeacher;

    private LinearLayout mainLinearLayout;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseDatabase adminDatabase , studentDatabase;
    private DatabaseReference adminDatabaseReference , studentDatabaseReference;
    private ChildEventListener adminEventListener, studentEventListener;

    public static final int RC_SIGN_IN = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminDatabase = FirebaseDatabase.getInstance();
        studentDatabase = FirebaseDatabase.getInstance();

        adminDatabaseReference = adminDatabase.getReference().child("TEACHERS");
        studentDatabaseReference = studentDatabase.getReference().child("STUDENTS");

        final ArrayList<String> userAdminListFromDatabase = new ArrayList<String>();
        final ArrayList<String> passwordAdminListFromDatabase = new ArrayList<String>();

        final ArrayList<String> userStudentListFromDatabase = new ArrayList<String>();
        final ArrayList<String> passwordStudentListFromDatabase = new ArrayList<String>();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);


        studentEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                StudentDetails studentDetails = dataSnapshot.getValue(StudentDetails.class);
                userStudentListFromDatabase.add(studentDetails.getUsername());
                passwordStudentListFromDatabase.add(studentDetails.getPassword());
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

        adminEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TeacherDetails teacherDetails = dataSnapshot.getValue(TeacherDetails.class);
                userAdminListFromDatabase.add(teacherDetails.getUsername());
                passwordAdminListFromDatabase.add(teacherDetails.getPassword());
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

        adminDatabaseReference.addChildEventListener(adminEventListener);


        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if(!(isNetworkAvailable())){
                    Toast.makeText(MainActivity.this , "NO NETWORK AVAILABLE" , Toast.LENGTH_SHORT).show();
                }

                    int flag = 0;

                lengthStudent = userStudentListFromDatabase.size();

                for (int i = 0; i < lengthStudent; i++) {
                    if (username.equals(userStudentListFromDatabase.get(i)) && password.equals(passwordStudentListFromDatabase.get(i))) {

                            flag = 1;
                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(intent);
                            }
                        }

                lengthTeacher = userAdminListFromDatabase.size();

                        for (int i = 0; i < lengthTeacher; i++) {
                            if (username.equals(userAdminListFromDatabase.get(i)) && password.equals(passwordAdminListFromDatabase.get(i))) {

                                flag = 1;
                                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                        }
                if(flag == 0){
                    Toast.makeText(MainActivity.this , "INVALID CREDENTIALS" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainLinearLayout = (LinearLayout) findViewById(R.id.main_linaerlayout);

        mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "SIGNING YOU IN", Toast.LENGTH_SHORT).show();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

   @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
   private void  closeKeyboard(){

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }
}
