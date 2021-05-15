package com.example.eedu.QuestionDatabase;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eedu.QuizActivity;

public class QueAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = null;
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent=new Intent(parent.getContext(),QuizActivity.class);
                parent.getContext().startActivity(questionIntent);
            }
        });

        return null;
    }
}
