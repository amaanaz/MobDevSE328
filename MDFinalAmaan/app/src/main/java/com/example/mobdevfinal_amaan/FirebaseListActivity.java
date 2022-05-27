package com.example.mobdevfinal_amaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobdevfinal_amaan.Adapters.FirebaseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FirebaseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAdapter firebaseAdapter;
    private ArrayList<Student> list;
    private FirebaseDatabase DB;
    private DatabaseReference myRef;
    // to check if its running for the first time, as we don't need to check if the data is same
    private boolean flagFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_list);

        getSupportActionBar().setTitle("Firebase Student List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.sunny);


        //Connecting to firebase and initialize the recycle view
        DB = FirebaseDatabase.getInstance("https://mobdevfinalamaan-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = DB.getReference("Student Info");
        recyclerView = findViewById(R.id.studentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        firebaseAdapter = new FirebaseAdapter(this,list);
        recyclerView.setAdapter(firebaseAdapter);

        // updating data in real time from firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Student student = dataSnapshot.getValue(Student.class);
                    list.add(student);
                    // compare the student id and name from firebase with the list student
                    // if different remove from the list and add student from firebase
                    // if same do nothing

                    // check if the student is not null
//                    if (student != null) {
//                        if (flagFirst) {
//                            list.add(student);
//                        } else {
//                            if (!list.get(i).getId().equals(student.getId()) || !list.get(i).getName().equals(student.getName())) {
//                                list.remove(i);
//                                list.add(i, student);
//                            }
//                        }
//                        i++;
//                    }
                }
                flagFirst=false;
                firebaseAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // finishing the activity once the home icon is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
//            Intent intent = new Intent(FirebaseListActivity.this, MainActivity.class);
//            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}