package com.example.mobdevfinal_amaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class FirebaseActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        getSupportActionBar().setTitle("Save in Firebase");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.sunny);

        // Weather icon
        ImageView weatherIconImageVDb = findViewById(R.id.weatherIconInDb);

        // Using Intent to get the icon to the firebase activity
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        String iconUrl = intent.getStringExtra("weather");
        Glide.with(this).load(iconUrl).into(weatherIconImageVDb);

//        Binding XML objects to variables
        EditText studentId = findViewById(R.id.studentId);
        EditText studentName = findViewById(R.id.studentName);
        EditText studentFathersName = findViewById(R.id.studentFathersName);
        EditText studentSurname = findViewById(R.id.studentSurname);
        EditText studentNationalId = findViewById(R.id.studentNationalId);

        RadioButton mRadioBTN = findViewById(R.id.mRadioBTN);
        RadioButton fRadioBTN = findViewById(R.id.fRadioBTN);
        // TextView dataDisplay = findViewById(R.id.dataDisplay);
        Button dateBTN = findViewById(R.id.dateBTN);
        Button viewByIdBtn = findViewById(R.id.searchByIdBTN);
        Button viewAllBtn = findViewById(R.id.viewAllBTN);

        Button insertBtn = findViewById(R.id.insertBTN);
        Button updateBtn = findViewById(R.id.updateBTN);
        Button delBtn = findViewById(R.id.deleteBTN);

        Button go2FBListBtn = findViewById(R.id.goToFbListBTN);
        Button goToSQLiteBTN = findViewById(R.id.goToSQLiteBTN);

        database = FirebaseDatabase
                .getInstance("https://mobdevfinalamaan-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("Student Info");

        // Date Picker
        Calendar cal = Calendar.getInstance();
        DateFormat txtDate = DateFormat.getDateInstance();
        DatePickerDialog.OnDateSetListener selectedDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateBTN.setText(txtDate.format(cal.getTime()));
            }
        };

        // selecting DoB
        dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FirebaseActivity.this, selectedDate,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // insert button gets data from xml and calls insert method
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdID = studentId.getText().toString();
                String stdName = studentName.getText().toString();
                String stdFatherName = studentFathersName.getText().toString();
                String stdSurname = studentSurname.getText().toString();
                String stdNid = studentNationalId.getText().toString();
                String _gender = "";
                if (mRadioBTN.isChecked()) {
                    _gender = " M";
                } else if (fRadioBTN.isChecked()) {
                    _gender = " F";
                }
                String dob = dateBTN.getText().toString();

                // check if all fields are filled
                if (stdID.isEmpty() || stdName.isEmpty() || stdFatherName.isEmpty()
                        || stdSurname.isEmpty() || stdNid.isEmpty() || _gender.isEmpty()
                        || dob.isEmpty()) {
                    Toasty.error(FirebaseActivity.this, "All Fields are Required!", Toast.LENGTH_SHORT).show();
                } else {
                    insertStdData(stdID, stdName, stdFatherName, stdSurname, stdNid,
                            _gender, dob);
                }
            }
        });

        // update std
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdID = studentId.getText().toString();
                String stdName = studentName.getText().toString();
                String stdFatherName = studentFathersName.getText().toString();
                String stdSurname = studentSurname.getText().toString();
                String stdNid = studentNationalId.getText().toString();
                String gender = "";
                if (mRadioBTN.isChecked()) {
                    gender = " M";
                } else if (fRadioBTN.isChecked()) {
                    gender = " F";
                }
                String dob = dateBTN.getText().toString();

                // check if all fields are filled
                if (stdID.isEmpty() || stdName.isEmpty() || stdFatherName.isEmpty()
                        || stdSurname.isEmpty() || stdNid.isEmpty() || gender.isEmpty()
                        || dob.isEmpty()) {
                    Toasty.error(FirebaseActivity.this, "All Fields are Required!", Toast.LENGTH_SHORT).show();
                } else {
                    updateById(stdID, stdName, stdFatherName, stdSurname, stdNid, gender, dob);
                }

            }
        });

        // delete button by id
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdID = studentId.getText().toString();

                // check if id field is empty
                if (stdID.isEmpty()) {
                    Toasty.error(FirebaseActivity.this, "ID missing", Toast.LENGTH_SHORT).show();
                } else {
                    deleteById(stdID);
                }
            }
        });

        // view all std data
//        viewAllBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                displayAllData();
//            }
//        });

        // search for std data based on id
        viewByIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentId.getText().toString();
                if (id.isEmpty()) {
                    Toasty.error(FirebaseActivity.this, "Student ID missing", Toast.LENGTH_SHORT).show();
                } else {
                    displayDataById(id);
                }
            }
        });

        // To view the firebase list when clicking this btn
        go2FBListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirebaseActivity.this, FirebaseListActivity.class);
                startActivity(intent);
            }
        });

        // goToSQLiteBTN
        // goToSQLiteBTN.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // // send iconUrl to DB activity using Intent
        // Intent intent = new Intent(FirebaseActivity.this, SQLiteActivity.class);
        // intent.putExtra("weather", iconUrl);
        // intent.putExtra("city", city);
        // startActivity(intent);
        // }
        // });

        // goToWeatherBTN
        // goToWeatherBTN.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // Intent intent = new Intent(FirebaseActivity.this, WeatherMainActivity.class);
        // intent.putExtra("city", city);
        // startActivity(intent);
        // }
        // });

    }

    // !method for inserting student
    public void insertStdData(String stdID, String stdName, String stdFatherName, String stdSurname, String stdNid,
                              String gender, String dob) {

        int stdNum = Integer.parseInt(stdID);

        if (stdNum < 999) {
            Toasty.error(FirebaseActivity.this, "ID has to be at least 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        // to loop through all students
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Student student = ds.getValue(Student.class);
                    String _stuNum = student.getId();
                    if (stdID.equals(_stuNum)) {
                        Toasty.error(FirebaseActivity.this, "ID already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Student student = new Student(stdID, stdName, stdFatherName, stdSurname,
                        stdNid, gender, dob);
                myRef.child("RollNo" + stdID).setValue(student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toasty.success(FirebaseActivity.this, "New Student Added", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(FirebaseActivity.this, "Data could not inserted", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(FirebaseActivity.this, "Data could not inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // !method for updating student by ID
    public void updateById(String stdID, String stdName, String stdFatherName, String stdSurname, String stdNid,
            String gender, String dob) {
        // looping through all students
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSS : dataSnapshot.getChildren()) {
                    Student student = dataSS.getValue(Student.class);
                    if (student.getId().equals(stdID)) {

                        // update student
                        student.setName(stdName);
                        student.setFathers_name(stdFatherName);
                        student.setSurname(stdSurname);
                        student.setNational_id(stdNid);
                        student.setGender(gender);
                        student.setDateOfBirth(dob);

                        // update student
                        myRef.child(dataSS.getKey()).setValue(student)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toasty.success(FirebaseActivity.this, "Updated successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toasty.error(FirebaseActivity.this, "Data could not updated",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                        return;
                    }
                }
                Toasty.error(FirebaseActivity.this, "Id not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(FirebaseActivity.this, "Data could not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ! method for deleting student by ID
    public void deleteById(String stdID) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) { // loop through all students
                    Student student = ds.getValue(Student.class);
                    if (student.getId().equals(stdID)) {
                        ds.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toasty.success(FirebaseActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(FirebaseActivity.this, "Data could not be deleted", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                        return;
                    }
                    Toasty.error(FirebaseActivity.this, "ID not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(FirebaseActivity.this, "Data could not be deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // get data from firebase
//    public void displayAllData() {
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                StringBuilder sb = new StringBuilder();
//                for (DataSnapshot dataSS : dataSnapshot.getChildren()) {
//                    String buffer = dataSS.getValue().toString();
//                    // format data
//                    String[] data = buffer.split(",");
//
//                    // index 0 has two = sign, so we need to remove it
//                    String[] data1 = data[0].split("=");
//                    String fathersName = data1[1];
//
//                    String gender = data[1].substring(data[1].indexOf("=") + 1);
//                    String nationalId = data[2].substring(data[2].indexOf("=") + 1);
//                    String surname = data[3].substring(data[3].indexOf("=") + 1);
//                    String name = data[4].substring(data[4].indexOf("=") + 1);
//                    String dateOfBirth = data[5].substring(data[5].indexOf("=") + 1);
//                    String year = data[6];
//                    // don't take the last "}" for id
//                    String id = data[7].substring(data[7].indexOf("=") + 1, data[7].length() - 1);
//
//                    // save them in StringBuffer
//                    sb.append("Id: ").append(id).append("\n");
//                    sb.append("Name: ").append(name).append("\n");
//                    sb.append("Father's Name: ").append(fathersName).append("\n");
//                    sb.append("Surname: ").append(surname).append("\n");
//                    sb.append("National Id: ").append(nationalId).append("\n");
//                    sb.append("Gender: ").append(gender).append("\n");
//                    sb.append("Date of birth: ").append(dateOfBirth + ", " + year).append("\n\n");
//                }
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(FirebaseActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Student List");
//                builder.setMessage(sb.toString());
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("Err", "onCancelled: " + databaseError.getMessage());
//            }
//        });
//    }

    // get data from firebase by ID
    public void displayDataById(String stdID) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSS : dataSnapshot.getChildren()) {
                    Student student = dataSS.getValue(Student.class);
                    if (student.getId().equals(stdID)) {
                        String buffer = dataSS.getValue().toString();
                        // format data
                        String[] data = buffer.split(",");

                        // index 0 has two = sign, so we need to remove it
                        String[] data1 = data[0].split("=");
                        String fathersName = data1[1];

                        String gender = data[1].substring(data[1].indexOf("=") + 1);
                        String nationalId = data[2].substring(data[2].indexOf("=") + 1);
                        String surname = data[3].substring(data[3].indexOf("=") + 1);
                        String name = data[4].substring(data[4].indexOf("=") + 1);
                        String dateOfBirth = data[5].substring(data[5].indexOf("=") + 1);
                        String year = data[6].substring(data[6].indexOf("=") + 1);
                        // don't take the last "}" for id
                        String id = data[7].substring(data[7].indexOf("=") + 1, data[7].length() - 1);

                        // save them in StringBuffer
                        StringBuffer sb = new StringBuffer();
                        sb.append("Id: ").append(id).append("\n");
                        sb.append("Name: ").append(name).append("\n");
                        sb.append("Father's Name: ").append(fathersName).append("\n");
                        sb.append("Surname: ").append(surname).append("\n");
                        sb.append("National Id: ").append(nationalId).append("\n");
                        sb.append("Gender: ").append(gender).append("\n");
                        sb.append("Date of Birth: ").append(dateOfBirth + ", " + year).append("\n\n");

                        AlertDialog.Builder builder = new AlertDialog.Builder(FirebaseActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Student Data");
                        builder.setMessage(sb.toString());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        return;
                    }
                }
                Toasty.error(FirebaseActivity.this, "ID not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Err", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    // onclick listener for home icon in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(FirebaseActivity.this, MainActivity.class);
            intent.putExtra("city", city);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}