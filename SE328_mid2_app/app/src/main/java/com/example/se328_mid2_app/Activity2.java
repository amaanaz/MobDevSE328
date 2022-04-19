package com.example.se328_mid2_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Button btnAdd = (Button) findViewById(R.id.insert);
        Button act1 = (Button) findViewById(R.id.go2_Act1);
        Button act3 = (Button) findViewById(R.id.go2_Act3);


        EditText id = (EditText) findViewById(R.id.idET);
        EditText name = (EditText) findViewById(R.id.nameET);
        EditText sname = (EditText) findViewById(R.id.surnameET);
        EditText nid = (EditText) findViewById(R.id.nidET);



        //!!Database Code
        DatabaseHelper myDB;
        myDB = new DatabaseHelper(this);

        // BUTTON Add used to enter data in the database
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_ = id.getText().toString();
                String name_ = name.getText().toString();
                String sname_ = sname.getText().toString();
                String nid_ = nid.getText().toString();

                Log.d("Amaan Act2: ","Inserting to table");

                if (name.equals("") || name_.equals("") || sname_.equals("") || nid_.equals(""))
                    Toast.makeText(Activity2.this, "make sure all filed are filled",
                            Toast.LENGTH_SHORT).show();
                else if (!myDB.addData( name_, sname_, nid_))
                    Toast.makeText (Activity2 . this,"Insertion Failed",
                            Toast. LENGTH_SHORT) . show();
                else
                    Toast.makeText (Activity2 . this,"Insertion Complete",
                            Toast. LENGTH_SHORT) . show();
            }
        });



    }
}