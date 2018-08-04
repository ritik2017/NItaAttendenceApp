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

public class ViewCourseStudentActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener courseEventListener;

    private ListView mCourseListView;
    private CourseAdapter mCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_student);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("courses");

        mCourseListView = (ListView) findViewById(R.id.course_listview);

        final ArrayList<Courses> courses = new ArrayList<Courses>();
        mCourseAdapter = new CourseAdapter(this , courses);
        mCourseListView.setAdapter(mCourseAdapter);

        courseEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Courses newCourse = dataSnapshot.getValue(Courses.class);
                mCourseAdapter.add(newCourse);

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
        mDatabaseReference.addChildEventListener(courseEventListener);
    }
}
