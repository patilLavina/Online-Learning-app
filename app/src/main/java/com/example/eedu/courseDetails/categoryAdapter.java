package com.example.eedu.courseDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eedu.CourseListDatabase.courseData;
import com.example.eedu.CourseListTopicwiseActivity;
import com.example.eedu.MainActivity;
import com.example.eedu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<categoryHelperClass> list;
    String category;

    public categoryAdapter(Context context, ArrayList<categoryHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cardview,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        categoryHelperClass helperClass=list.get(position);
        holder.title.setText(helperClass.getCat());
        Picasso.with(context).load(helperClass.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courselist=new Intent(context,CourseListTopicwiseActivity.class);
                //category=holder.title.setText(helperClass.getCat());
                courselist.putExtra("name",helperClass.cat);
                context.startActivity(courselist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,desc;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image_topics_category);
            title=itemView.findViewById(R.id.text_topics_category);
            //desc=itemView.findViewById(R.id.description_topics_category);


        }
    }
}


