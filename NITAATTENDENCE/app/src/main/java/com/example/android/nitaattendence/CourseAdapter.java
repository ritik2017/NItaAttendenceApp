package com.example.android.nitaattendence;

import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by RITIK KUMAR on 16-07-2018.
 */

public class CourseAdapter extends ArrayAdapter<Courses> {

    private Activity context;
    private ArrayList<Courses> course;

    public CourseAdapter(Activity context, ArrayList<Courses> course) {
        super(context, R.layout.listview_course, course);
        this.context = context;
        this.course = course;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.listview_course , null , true);

        TextView textViewCourse = (TextView) listItemView.findViewById(R.id.coursename_textview);

        Courses coursename = course.get(position);

        textViewCourse.setText(coursename.getCourse());


        return listItemView;


    }
}
