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

public class ViewTeacherAdapter extends ArrayAdapter<TeacherDetails> {

    private Activity context;
    private ArrayList<TeacherDetails> teacherDetails;

    public ViewTeacherAdapter(Activity context, ArrayList<TeacherDetails> teacherDetails) {
        super(context, R.layout.listview_teacher, teacherDetails);
        this.context = context;
        this.teacherDetails = teacherDetails;
    }


    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.listview_teacher, null , true);

        TextView textViewName = (TextView) listItemView.findViewById(R.id.name_textview);
        TextView textViewCourse = (TextView) listItemView.findViewById(R.id.coursename_textview);

        TeacherDetails teacher = teacherDetails.get(position);

        textViewName.setText(teacher.getNameOfTeacher());
        textViewCourse.setText(teacher.getCourseTaughtByteacher());

        return listItemView;


    }
}
