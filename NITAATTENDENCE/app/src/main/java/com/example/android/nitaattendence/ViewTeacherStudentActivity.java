package com.example.android.nitaattendence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewTeacherStudentActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mTeacherListener;

    private ListView teacherListView;
    private ViewTeacherAdapter mTeacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher_student);

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
}
