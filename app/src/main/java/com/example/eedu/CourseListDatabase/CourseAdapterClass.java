package com.example.eedu.CourseListDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eedu.EnrollNowActivity;
import com.example.eedu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapterClass extends RecyclerView.Adapter<CourseAdapterClass.ViewHolder> {
    Context context;
    ArrayList<courseData> list;

    public CourseAdapterClass(Context context, ArrayList<courseData> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.course_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        courseData courseDataList=list.get(position);
        final courseData uid=list.get(position);
        holder.Name.setText(courseDataList.getName());
        holder.Description.setText(courseDataList.getDescription());
        Picasso.with(context).load(courseDataList.getImage()).into(holder.Image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coursepage=new Intent(context, EnrollNowActivity.class);
                coursepage.putExtra("name",courseDataList.getName());
                coursepage.putExtra("description",courseDataList.getDescription());
                coursepage.putExtra("image",courseDataList.getImage());
                //coursepage.putExtra("uid",uid.get);
                context.startActivity(coursepage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Description;
        ImageView Image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.courseName_courseList);
            Description=itemView.findViewById(R.id.courseDescription_courseList);
            Image=itemView.findViewById(R.id.imageview_courseList);
        }
    }
}

