package com.example.android.nitaattendence;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RITIK KUMAR on 22-07-2018.
 */

public class ViewStudentAdapter extends ArrayAdapter<StudentDetails> {

    private Activity context;
    private ArrayList<StudentDetails> studentDetails;

    public ViewStudentAdapter(Activity context, ArrayList<StudentDetails> studentDetails) {

        super(context, R.layout.listview_student, studentDetails);
        this.context = context;
        this.studentDetails = studentDetails;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.listview_student, null , true);

        TextView textViewEnrollment = (TextView) listItemView.findViewById(R.id.enrollmentOfStudent_textview);
        TextView textViewName = (TextView) listItemView.findViewById(R.id.nameOfStudent_textview);

        StudentDetails student = studentDetails.get(position);

        textViewEnrollment.setText(student.getEnrollmentOfStudent());
        textViewName.setText(student.getNameOfStudent());

        return listItemView;


    }
}
