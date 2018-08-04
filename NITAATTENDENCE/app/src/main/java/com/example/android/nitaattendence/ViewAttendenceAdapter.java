package com.example.android.nitaattendence;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;
import static com.example.android.nitaattendence.R.string.coursename;

/**
 * Created by RITIK KUMAR on 21-07-2018.
 */

public class ViewAttendenceAdapter extends ArrayAdapter<StudentAttendence> {

    private Activity context;
    private ArrayList<StudentAttendence> studentAttendences;

    public ViewAttendenceAdapter(Activity context, ArrayList<StudentAttendence> studentAttendences) {
        super(context, R.layout.listview_attendence, studentAttendences);
        this.context = context;
        this.studentAttendences = studentAttendences;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.listview_attendence , null , true);

        TextView textViewEnrollment = (TextView) listItemView.findViewById(R.id.enrollment_textview);
        TextView textViewName = (TextView) listItemView.findViewById(R.id.name_textview);

        StudentAttendence student = studentAttendences.get(position);

        textViewEnrollment.setText(student.getEnroll());
        textViewName.setText(student.getName());

        return listItemView;


    }
}
