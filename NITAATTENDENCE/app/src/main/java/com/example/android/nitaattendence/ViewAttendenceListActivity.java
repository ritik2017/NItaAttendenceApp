package com.example.android.nitaattendence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAttendenceListActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener attendenceEventListener;

    private ListView mAttendenceListView;
    private TextView totalStudentTextView;
    private ViewAttendenceAdapter mViewAttendenceAdapter;

    final ArrayList<StudentAttendence> studentAttendence = new ArrayList<StudentAttendence>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence_list);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String courseDate = bundle.getString("courseDateFromIntent");

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference().child(courseDate);

            mAttendenceListView = (ListView) findViewById(R.id.studentattendence_listview);

            mViewAttendenceAdapter = new ViewAttendenceAdapter(this , studentAttendence);
            mAttendenceListView.setAdapter(mViewAttendenceAdapter);

            attendenceEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    StudentAttendence newStudentAttendence = dataSnapshot.getValue(StudentAttendence.class);
                    mViewAttendenceAdapter.add(newStudentAttendence);

                    findTotalStudent();
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

            mDatabaseReference.addChildEventListener(attendenceEventListener);

    }

    public void findTotalStudent(){
        totalStudentTextView = (TextView) findViewById(R.id.totalattendence_textview);
        int length  = studentAttendence.size();
        totalStudentTextView.setText(("TOTAL STUDENT:  " + length));
    }
}
