package com.example.se328_mid2_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity3 extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Button btnFind = (Button) findViewById(R.id.fetch);
        Button btnDel = (Button) findViewById(R.id.del);
        EditText search = (EditText) findViewById(R.id.search);

        String[] data = new String[20];

        setListAdapter(new ArrayAdapter<String>(this,
                R.layout.activity_3,
                R.id.list,
                data));

        DatabaseHelper myDB;
        myDB = new DatabaseHelper(this);

        // BUTTON Find used to find data from the database
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_ = search.getText().toString();
                Log.d("Amaan Act3: ", "Fetching Data"+search_);

                if (search_.equals(""))
                    Toast.makeText(Activity3.this, "make sure the filed is filled", Toast.LENGTH_SHORT).show();

                else {
                    Cursor search = myDB.getSpecificResult(search_);

                    if (search.getCount() == 0){
                        Toast.makeText(Activity3.this, "no data found", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        // TODO: Show in List View
                        for (int i = 0; i< search.getCount(); i++) {
                            String response = search.getString(0);
                            response += "\n" + search.getString(1);
                            response += "\n" + search.getString(2);
                            response += "\n" + search.getString(3);

                            data[i] = response;

                        }
                    }
                }
            }
        });

        // BUTTON Delete used to delete data from the database
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_ = search.getText().toString();
                if (search_.equals("")){
                    Toast.makeText(Activity3.this, "Find the data you want to delete", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("Amaan Act3: ", "Deleting Data");
                    Cursor deleted = myDB.delData(search_);
                    Toast.makeText(Activity3.this, "Deleted"+deleted.getString(1), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}