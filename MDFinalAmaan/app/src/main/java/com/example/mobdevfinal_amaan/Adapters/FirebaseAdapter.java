package com.example.mobdevfinal_amaan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdevfinal_amaan.R;
import com.example.mobdevfinal_amaan.Student;

import java.util.ArrayList;

//Class for recycle view to display the list dynamically.

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Student> list;

    public FirebaseAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.activity_firebase_listitem,parent,false);
        return  new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.id.setText(student.getId());
        holder.studentName.setText(student.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, studentName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.fbID);
            studentName = itemView.findViewById(R.id.fbStdName);
        }
    }
}
