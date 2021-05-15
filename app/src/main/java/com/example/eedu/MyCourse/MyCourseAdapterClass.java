package com.example.eedu.MyCourse;


import android.view.View;
import android.view.ViewGroup;

import com.example.eedu.courseDetails.categoryAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCourseAdapterClass extends RecyclerView.Adapter<categoryAdapter.RecyclerViewHolder>{


    @NonNull
    @Override
    public categoryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
