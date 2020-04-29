package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<Student> arrayList;

    Student student;

    public StudentAdapter(Context context, int layout, ArrayList<Student> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.studentName = (TextView) view.findViewById(R.id.student_name_item);
            holder.avatar = (ImageView) view.findViewById(R.id.avatar_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        student = arrayList.get(position);
        holder.studentName.setText(student.getName());
        final String linkanh = "https://" + student.getAvatar();
        Picasso.get()
                .load(linkanh)
                .error(R.drawable.ic_launcher_background)
                .into(holder.avatar);
        return view;
    }

    public class ViewHolder {
        TextView studentName;
        ImageView avatar;
    }
}



