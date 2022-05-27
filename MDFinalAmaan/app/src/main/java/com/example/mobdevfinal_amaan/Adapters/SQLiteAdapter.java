package com.example.mobdevfinal_amaan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdevfinal_amaan.OnClickInterface;
import com.example.mobdevfinal_amaan.R;
import com.example.mobdevfinal_amaan.Student;

import java.util.ArrayList;

//Class for recycle view to display the list dynamically.

public class SQLiteAdapter extends RecyclerView.Adapter<SQLiteAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Student> list;
    private OnClickInterface onClickInterface;

    public SQLiteAdapter(Context context, ArrayList<Student> list, OnClickInterface onClickInterface) {
        this.context = context;
        this.list = list;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_sqlite_listitem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.id.setText(student.getId());
        holder.studentName.setText(student.getName());

        holder.sqliteItem.setOnClickListener(v -> onClickInterface.setClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, studentName;
        LinearLayout sqliteItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_id);
            studentName = itemView.findViewById(R.id.item_name);
            sqliteItem = itemView.findViewById(R.id.sqliteItem);
        }
    }
}
