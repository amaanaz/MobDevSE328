package com.example.mobdevfinal_amaan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobdevfinal_amaan.Adapters.SQLiteAdapter;
import com.example.mobdevfinal_amaan.Databases.SQLiteHelper;
import java.util.ArrayList;
import es.dmoral.toasty.Toasty;


public class SQLiteListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSQLite;
    private SQLiteHelper dbHelper;
    private SQLiteAdapter StudentAdapter;
    private ArrayList<Student> studentList;
    private OnClickInterface onclickInterface;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_list);

        // change the text of the app bar title
        getSupportActionBar().setTitle("SQLite Student List");

        // display home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // onclick action for the home button
        getSupportActionBar().setHomeButtonEnabled(true);

        // change icon of the home button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.sunny);

        // add subtitle to the app bar
//        getSupportActionBar().setSubtitle("Fayed - Final Project");



        onclickInterface = position -> {
            Toasty.info(SQLiteListActivity.this, "Full Name: " + studentList.get(position).getName() + " " + studentList.get(position).getSurname(), Toast.LENGTH_SHORT).show();
            StudentAdapter.notifyDataSetChanged();
        };

        dbHelper = new SQLiteHelper(this);
        studentList = new ArrayList<>();

        recyclerViewSQLite = findViewById(R.id.recyclerViewSQLite);
        recyclerViewSQLite.setHasFixedSize(true);
        recyclerViewSQLite.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSQLite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSQLite.addItemDecoration(new DividerItemDecoration(recyclerViewSQLite.getContext(), DividerItemDecoration.VERTICAL));
        fillListView();
    }

    // fills the RecycleView List
    public void fillListView() {
        studentList = dbHelper.getAllData();
        StudentAdapter = new SQLiteAdapter(this,studentList, onclickInterface);
        recyclerViewSQLite.setAdapter(StudentAdapter);
    }

    // onclick listener for home icon in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
//            Intent intent = new Intent(SQLiteListActivity.this, WeatherMainActivity.class);
//            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}