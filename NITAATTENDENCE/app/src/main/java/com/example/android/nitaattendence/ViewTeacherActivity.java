package com.example.android.nitaattendence;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewTeacherActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mTeacherListener;

    private ListView teacherListView;
    private ViewTeacherAdapter mTeacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);

        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDataBase.getReference().child("TEACHERS");

        teacherListView = (ListView) findViewById(R.id.viewteacher_listview);

        final ArrayList<TeacherDetails> teacherDetails = new ArrayList<TeacherDetails>();
        mTeacherAdapter = new ViewTeacherAdapter(this , teacherDetails);
        teacherListView.setAdapter(mTeacherAdapter);

        mTeacherListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                TeacherDetails newTeacherDetails = dataSnapshot.getValue(TeacherDetails.class);
                mTeacherAdapter.add(newTeacherDetails);
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

        mDatabaseReference.addChildEventListener(mTeacherListener);

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.addteacher, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addteacher_item:
                addNewStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addNewStudent(){
        Intent intent = new Intent(ViewTeacherActivity.this, AddTeacherActivity.class);
        startActivity(intent);
    }

}
